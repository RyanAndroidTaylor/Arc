package com.payclip.arc.controller

import com.payclip.arc.ArcAction
import com.payclip.arc.ArcState
import com.payclip.arc.ArcTrigger
import com.payclip.arc.interactor.ArcInteractor
import com.payclip.arc.view.ArcView

/**
 * Created by ryantaylor on 12/14/17.
 */
interface ArcController<A : ArcAction, S : ArcState, T : ArcTrigger> {
    val arcInteractor: ArcInteractor<A, S, T>
    var arcView: ArcView<A, S, T>?

    fun dispatch(action: A)
    fun render(state: S)
    fun trigger(trigger: T)
}