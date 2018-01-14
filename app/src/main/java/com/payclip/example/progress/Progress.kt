package com.payclip.example.progress

import com.payclip.arc.ArcAction
import com.payclip.arc.ArcState
import com.payclip.arc.ArcTrigger

/**
 * Created by ryantaylor on 1/13/18.
 */

data class ProgressState(val progress: Float = 0f) : ArcState

sealed class ProgressTrigger : ArcTrigger {

}

sealed class ProgressAction : ArcAction {

    class ProgressUpdate(val progress: Float) : ProgressAction()
}