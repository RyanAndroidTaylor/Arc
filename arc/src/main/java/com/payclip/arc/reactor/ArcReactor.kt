package com.payclip.arc.reactor

import com.payclip.arc.ArcAction
import java.util.concurrent.TimeUnit

/**
 * Created by ryantaylor on 1/12/18.
 */
abstract class ArcReactor<A : ArcAction, R : A> {
    protected var reactor: ArcReactor<R, *>? = null
    protected var watcher: ((R) -> Unit)? = null

    abstract fun dispatch(action: A): A?

    fun watch(watcher: (R) -> Unit) {
        this.watcher = watcher
    }

    private fun <T : R> attach(reactor: ArcReactor<R, T>): ArcReactor<R, T> {
        this.reactor = reactor

        reactor.watcher = watcher
        watcher = null

        return reactor
    }

    fun debounce(delay: Long, timeUnit: TimeUnit): ArcReactor<R, R> {
        val reactor = object : DebounceReactor<R>(delay, timeUnit) {}

        attach(reactor)

        return reactor
    }

    fun filter(predicate: (R) -> Boolean): ArcReactor<R, R> {
        val reactor = object : FilterReactor<R>(predicate) {}

        attach(reactor)

        return reactor
    }

    fun <T : R> redirect(klass: Class<T>): ArcReactor<R, T> {
        val reactor = object : RedirectTypeReactor<R, T>(klass) {}

        attach(reactor)

        return reactor
    }
}