package com.payclip.arc.interactor

import com.payclip.arc.*
import com.payclip.arc.controller.ArcController

/**
 * Created by ryantaylor on 12/16/17.
 */
abstract class BaseInteractor<A : ArcAction, S : ArcState, T : ArcTrigger>(override val arcController: ArcController<A, S, T>) : ArcInteractor<A, S, T> {
    private val stateWatchers = mutableListOf<ArcStateWatcher<S>>()
    private val actionWatchers = mutableListOf<ArcActionWatcher<A>>()

    protected abstract var state: S

    override fun dispatch(action: A) {
        //TODO Implement Reactors

        consume(action)
    }

    private fun consume(action: A) {
        val currentAction = transform(action)

        actionWatchers.forEach { it.onAction(currentAction) }

        state = reduce(currentAction)

        arcController.render(state)

        react(currentAction)?.let { arcController.trigger(it) }

        stateWatchers.forEach { it.update(state) }
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
}