package com.payclip.example.timer

import com.payclip.arc.interactor.BaseInteractor
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

/**
 * Created by ryantaylor on 1/11/18.
 */
class TimerInteractor(timerController: TimerController) : BaseInteractor<TimerAction, TimerState, TimerTrigger>(timerController) {
    override var state = TimerState()

    private val timer = Timer { dispatch(TimerAction.TimeUpdate(it)) }

    override fun transform(action: TimerAction): TimerAction {
        return when (action) {
            is TimerAction.ToggleTimer -> transformToggleTimerAction(action)
            is TimerAction.TimerStarted,
            is TimerAction.TimerStopped,
            is TimerAction.TimeUpdate -> action
        }
    }

    override fun reduce(action: TimerAction): TimerState {
        return when (action) {
            is TimerAction.TimeUpdate -> state.copy(time = state.time + action.time)
            is TimerAction.TimerStarted,
            is TimerAction.TimerStopped,
            is TimerAction.ToggleTimer -> state.copy()
        }
    }

    override fun react(action: TimerAction): TimerTrigger? {
        return when (action) {
            is TimerAction.TimerStarted -> TimerTrigger.Started
            is TimerAction.TimerStopped -> TimerTrigger.Stopped
            is TimerAction.ToggleTimer,
            is TimerAction.TimeUpdate -> null
        }
    }

    private fun transformToggleTimerAction(action: TimerAction.ToggleTimer): TimerAction {
        return if (timer.toggle())
            TimerAction.TimerStarted
        else
            TimerAction.TimerStopped
    }
}

class Timer(private val timeUpdate: (time: Double) -> Unit) {

    private var running = false

    fun toggle(): Boolean {
        return if (running) {
            stop()

            false
        } else {
            start()

            true
        }
    }

    private fun start() {
        if (running)
            return

        running = true

        launch {
            while (running) {
                delay(10, TimeUnit.MILLISECONDS)

                timeUpdate(0.01)
            }
        }
    }

    private fun stop() {
        running = false
    }
}