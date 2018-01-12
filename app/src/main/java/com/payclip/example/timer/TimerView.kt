package com.payclip.example.timer

import android.view.LayoutInflater
import com.payclip.arc.view.AndroidArcView
import com.payclip.example.R
import kotlinx.android.synthetic.main.controller_timer.view.*
import java.text.DecimalFormat

/**
 * Created by ryantaylor on 1/11/18.
 */
class TimerView(timerController: TimerController, layoutInflater: LayoutInflater) : AndroidArcView<TimerAction, TimerState, TimerTrigger>(timerController, layoutInflater) {

    private val decimalFormat = DecimalFormat("#.00")

    override fun layoutId() = R.layout.controller_timer

    init {
        view.timer_trigger.setOnClickListener { timerController.dispatch(TimerAction.ToggleTimer) }
    }

    override fun render(state: TimerState) {
        view.timer_text.text = decimalFormat.format(state.time)
    }

    override fun react(trigger: TimerTrigger) {
        when (trigger) {
            is TimerTrigger.Started -> view.timer_trigger.text = "Stop"
            is TimerTrigger.Stopped -> view.timer_trigger.text = "Start"
        }
    }
}