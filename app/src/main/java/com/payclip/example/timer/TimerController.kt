package com.payclip.example.timer

import android.view.LayoutInflater
import com.payclip.arc.view.ArcView
import com.payclip.example.ConductorArcController

/**
 * Created by ryantaylor on 1/11/18.
 */
class TimerController : ConductorArcController<TimerAction, TimerState, TimerTrigger>() {
    override val arcInteractor = TimerInteractor(this)

    override var arcView: ArcView<TimerAction, TimerState, TimerTrigger>? = null

    override fun createArcView(inflater: LayoutInflater) = TimerView(this, inflater)
}