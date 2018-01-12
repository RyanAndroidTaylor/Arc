package com.payclip.example.timer

import com.payclip.arc.ArcAction
import com.payclip.arc.ArcState
import com.payclip.arc.ArcTrigger

/**
 * Created by ryantaylor on 1/11/18.
 */

data class TimerState(val time: Double = 0.0) : ArcState

sealed class TimerTrigger : ArcTrigger {
    object Started : TimerTrigger()
    object Stopped : TimerTrigger()
}

sealed class TimerAction : ArcAction {
    object TimerStarted : TimerAction()
    object TimerStopped : TimerAction()
    object ToggleTimer : TimerAction()

    class TimeUpdate(val time: Double) : TimerAction()
}