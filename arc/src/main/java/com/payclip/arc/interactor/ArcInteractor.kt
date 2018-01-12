package com.payclip.arc.interactor

import com.payclip.arc.ArcAction
import com.payclip.arc.ArcState
import com.payclip.arc.ArcTrigger
import com.payclip.arc.controller.ArcController

/**
 * Created by ryantaylor on 12/14/17.
 */
interface ArcInteractor<A : ArcAction, S : ArcState, T : ArcTrigger> {
    val arcController: ArcController<A, S, T>

    fun dispatch(action: A)
}