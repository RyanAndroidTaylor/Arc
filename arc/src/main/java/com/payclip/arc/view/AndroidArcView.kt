package com.payclip.arc.view

import android.view.View
import com.payclip.arc.ArcAction
import com.payclip.arc.ArcState
import com.payclip.arc.ArcTrigger
import com.payclip.arc.controller.ArcController

/**
 * Created by ryantaylor on 12/16/17.
 */

abstract class AndroidArcView<A : ArcAction, S : ArcState, T : ArcTrigger>(override val arcController: ArcController<A, S, T>,
                                                                           val view: View) : ArcView<A, S, T> {
}