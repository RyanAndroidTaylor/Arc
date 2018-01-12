package com.payclip.arc

/**
 * Created by ryantaylor on 12/19/17.
 */
interface ArcActionWatcher<in A : ArcAction> {
    fun onAction(action: A)
}