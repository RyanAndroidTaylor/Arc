package com.payclip.arc.controller

import android.os.Handler
import android.os.Looper
import android.view.View
import com.payclip.arc.ArcAction
import com.payclip.arc.ArcState
import com.payclip.arc.ArcTrigger
import com.payclip.arc.view.AndroidArcView

/**
 * Created by ryantaylor on 1/12/18.
 */
abstract class ViewArcController<A : ArcAction, S : ArcState, T : ArcTrigger>(val view: View) : ArcController<A, S, T> {

    private val mainThreadHandler = Handler(Looper.getMainLooper())

    private val attachListener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {

        }

        override fun onViewAttachedToWindow(v: View?) {
            if (arcView == null)
                arcView = createArcView(view)

            attached()
        }

    }

    init {
        view.addOnAttachStateChangeListener(attachListener)
    }

    override fun render(state: S) {
        if (Looper.myLooper() == Looper.getMainLooper())
            arcView?.render(state)
        else
            mainThreadHandler.post { arcView?.render(state) }
    }

    override fun trigger(trigger: T) {
        if (Looper.myLooper() == Looper.getMainLooper())
            arcView?.react(trigger)
        else
            mainThreadHandler.post { arcView?.react(trigger) }
    }

    abstract fun createArcView(view: View): AndroidArcView<A, S, T>

    abstract fun attached()
}