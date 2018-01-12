package com.payclip.arc.reactor

import com.payclip.arc.ArcAction
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

/**
 * Created by ryantaylor on 1/12/18.
 */
abstract class DebounceReactor<A : ArcAction>(private val delay: Long,
                                     private val timeUnit: TimeUnit) : ArcReactor<A, A>() {

    private var lastEvent: ActionEvent<A>? = null
    private var newEvent: ActionEvent<A>? = null

    private var waiting: Boolean = false

    override fun dispatch(action: A): A? {
        newEvent = ActionEvent(action, System.currentTimeMillis())

        if (!waiting)
            debounce()

        return null
    }

    private fun debounce() {
        lastEvent = newEvent
        newEvent = null

        waiting = true

        launch {
            delay(delay, timeUnit)

            lastEvent?.let { lastEvent ->
                if (newEvent == null) {
                    watcher?.invoke(lastEvent.action)

                    reactor?.dispatch(lastEvent.action)

                    waiting = false
                } else {
                    debounce()
                }
            } ?: debounce()
        }
    }

    private data class ActionEvent<out A : ArcAction>(val action: A, val timeStamp: Long)
}