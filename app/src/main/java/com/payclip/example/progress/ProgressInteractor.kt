package com.payclip.example.progress

import com.payclip.arc.interactor.BaseInteractor

/**
 * Created by ryantaylor on 1/13/18.
 */
class ProgressInteractor(controller: ProgressController) : BaseInteractor<ProgressAction, ProgressState, ProgressTrigger>(controller) {
    override var state = ProgressState()

    override fun transform(action: ProgressAction): ProgressAction {
        return action
    }

    override fun reduce(action: ProgressAction): ProgressState {
        return when (action) {
            is ProgressAction.ProgressUpdate -> state.copy(progress = action.progress)
        }
    }

    override fun react(action: ProgressAction): ProgressTrigger? {
        return null
    }
}