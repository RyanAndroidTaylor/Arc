package com.payclip.example.progress

import android.view.View
import com.payclip.arc.controller.ViewArcController
import com.payclip.arc.view.ArcView

/**
 * Created by ryantaylor on 1/13/18.
 */
class ProgressController(view: View) : ViewArcController<ProgressAction, ProgressState, ProgressTrigger>(view) {
    override val arcInteractor = ProgressInteractor(this)

    override var arcView: ArcView<ProgressAction, ProgressState, ProgressTrigger>? = null

    override fun dispatch(action: ProgressAction) {
        arcInteractor.dispatch(action)
    }

    override fun createArcView(view: View) = ProgressView(this, view)

    override fun attached() {
        arcInteractor.dispatch(ProgressAction.ProgressUpdate(0.15f))
    }
}