package com.payclip.arc

/**
 * Created by ryantaylor on 12/19/17.
 */
interface ArcStateWatcher<in S : ArcState> {
    fun update(state: S)
}