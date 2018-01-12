package com.payclip.arc.reactor

import com.payclip.arc.ArcAction

/**
 * Created by ryantaylor on 1/12/18.
 */
abstract class RedirectTypeReactor<A : ArcAction, R : A>(private val klass: Class<R>) : ArcReactor<A, R>() {

    override fun dispatch(action: A): A? {
        if (klass.isInstance(action)) {
            watcher?.invoke(action as R)

            reactor?.dispatch(action as R)

            return null
        }

        return action
    }
}