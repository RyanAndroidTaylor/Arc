package com.payclip.example.timer

import android.view.LayoutInflater
import android.view.View
import com.payclip.arc.view.ArcView
import com.payclip.example.ConductorArcController
import com.payclip.example.R
import com.payclip.example.progress.ProgressController
import kotlinx.android.synthetic.main.controller_timer.view.*

/**
 * Created by ryantaylor on 1/11/18.
 */
class TimerController : ConductorArcController<TimerAction, TimerState, TimerTrigger>() {
    override val layoutId = R.layout.controller_timer

    override val arcInteractor = TimerInteractor(this)

    override var arcView: ArcView<TimerAction, TimerState, TimerTrigger>? = null

    lateinit var progressController: ProgressController

    override fun createArcView(inflater: LayoutInflater) = TimerView(this, inflater.inflate(layoutId, null))

    override fun bind(view: View) {
        progressController = ProgressController(view.progressWidget)
    }
}