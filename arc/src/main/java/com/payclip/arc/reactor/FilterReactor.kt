package com.payclip.arc.reactor

import com.payclip.arc.ArcAction

/**
 * Created by ryantaylor on 1/12/18.
 */
abstract class FilterReactor<A : ArcAction>(private val predicate: (A) -> Boolean) : ArcReactor<A, A>() {

    override fun dispatch(action: A): A? {
        if (predicate(action)) {
            watcher?.invoke(action)

            reactor?.dispatch(action)
        }

        return null
    }
}