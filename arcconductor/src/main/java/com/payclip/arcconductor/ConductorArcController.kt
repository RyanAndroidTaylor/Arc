package com.payclip.arcconductor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.payclip.arc.ArcAction
import com.payclip.arc.ArcState
import com.payclip.arc.ArcTrigger
import com.payclip.arc.controller.ArcController
import com.payclip.arc.view.AndroidArcView

/**
 * Created by ryantaylor on 1/11/18.
 */
abstract class ConductorArcController<A : ArcAction, S : ArcState, T : ArcTrigger> : Controller(), ArcController<A, S, T> {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return createArcView(inflater).let {
            arcView = it

            it.view
        }
    }

    override fun dispatch(action: A) {
        arcInteractor.dispatch(action)
    }

    override fun render(state: S) {
        arcView?.render(state)
    }

    override fun trigger(trigger: T) {
        arcView?.react(trigger)
    }

    abstract fun createArcView(inflater: LayoutInflater): AndroidArcView<A, S, T>
}