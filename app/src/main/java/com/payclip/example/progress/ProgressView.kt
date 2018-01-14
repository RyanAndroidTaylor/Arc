package com.payclip.example.progress

import android.view.View
import com.payclip.arc.view.AndroidArcView
import kotlinx.android.synthetic.main.controller_timer.view.*

/**
 * Created by ryantaylor on 1/13/18.
 */
class ProgressView(controller: ProgressController, view: View) : AndroidArcView<ProgressAction, ProgressState, ProgressTrigger>(controller, view) {

    override fun render(state: ProgressState) {
        view.progressWidget.update(state.progress)
    }

    override fun react(trigger: ProgressTrigger) {

    }

}