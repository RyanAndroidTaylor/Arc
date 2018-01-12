package com.payclip.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.RouterTransaction
import com.payclip.example.timer.TimerController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val router = Conductor.attachRouter(this, container, savedInstanceState)

        if (!router.hasRootController())
            router.setRoot(RouterTransaction.with(TimerController()))
    }
}
