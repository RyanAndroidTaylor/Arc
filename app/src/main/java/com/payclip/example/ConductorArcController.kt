package com.payclip.example

import android.os.Looper
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
        if (Looper.myLooper() == Looper.getMainLooper())
            arcView?.render(state)
        else
            activity?.runOnUiThread { arcView?.render(state) }
    }

    override fun trigger(trigger: T) {
        if (Looper.myLooper() == Looper.getMainLooper())
            arcView?.react(trigger)
        else
            activity?.runOnUiThread { arcView?.react(trigger) }
    }

    abstract fun createArcView(inflater: LayoutInflater): AndroidArcView<A, S, T>
}