package com.payclip.arc.interactor

import com.payclip.arc.*
import com.payclip.arc.controller.ArcController
import com.payclip.arc.reactor.ArcReactor
import com.payclip.arc.reactor.DebounceReactor
import com.payclip.arc.reactor.FilterReactor
import com.payclip.arc.reactor.RedirectTypeReactor
import java.util.concurrent.TimeUnit

/**
 * Created by ryantaylor on 12/16/17.
 */
abstract class BaseInteractor<A : ArcAction, S : ArcState, T : ArcTrigger>(override val arcController: ArcController<A, S, T>) : ArcInteractor<A, S, T> {
    private val reactors = mutableListOf<ArcReactor<A, *>>()
    private val stateWatchers = mutableListOf<ArcStateWatcher<S>>()
    private val actionWatchers = mutableListOf<ArcActionWatcher<A>>()

    protected abstract var state: S

    override fun dispatch(action: A) {
        var currentAction: A? = action

        reactors.forEach { reactor ->
            currentAction?.let { currentAction = reactor.dispatch(it) }
        }

        currentAction?.let { consume(it) }
    }

    private fun consume(action: A) {
        val currentAction = transform(action)

        actionWatchers.forEach { it.onAction(currentAction) }

        val previousState = state
        state = reduce(currentAction)

        // No need to render if the state has not changed
        if (previousState != state) {
            arcController.render(state)

            stateWatchers.forEach { it.update(state) }
        }

        react(currentAction)?.let { arcController.trigger(it) }
    }

    protected abstract fun transform(action: A): A

    protected abstract fun reduce(action: A): S

    protected abstract fun react(action: A): T?

    fun watchState(stateWatcher: (S) -> Unit) {
        stateWatchers.add(object : ArcStateWatcher<S> {
            override fun update(state: S) {
                stateWatcher(state)
            }
        })
    }

    fun watchActions(actionWatcher: (A) -> Unit) {
        actionWatchers.add(object : ArcActionWatcher<A> {
            override fun onAction(action: A) {
                actionWatcher(action)
            }
        })
    }

    fun <R : A> attach(reactor: ArcReactor<A, R>): ArcReactor<A, R> {
        if (!reactors.contains(reactor)) {
            reactors.add(reactor)

            reactor.watch { consume(it) }
        }

        return reactor
    }

    protected fun debounce(delay: Long, timeUnit: TimeUnit): ArcReactor<A, A> {
        val reactor = object : DebounceReactor<A>(delay, timeUnit) {}

        return attach(reactor)
    }

    protected fun filter(predicate: (A) -> Boolean): ArcReactor<A, A> {
        val reactor = object : FilterReactor<A>(predicate) {}

        return attach(reactor)
    }

    protected fun <R : A> redirect(klass: Class<R>): ArcReactor<A, R> {
        val reactor = object : RedirectTypeReactor<A, R>(klass) {}

        return attach(reactor)
    }
}