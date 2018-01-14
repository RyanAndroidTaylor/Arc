package com.payclip.example.progress

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by ryantaylor on 1/13/18.
 */
class ProgressWidget @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val backgroundHeight = 30
    private val progressHeight = 26

    private val backgroundPaint = Paint().apply {
        color = Color.GRAY
    }

    private val progressPaint = Paint().apply {
        color = Color.BLUE
    }

    private var progress = 0.0f

    // We are handling all the drawing
    @SuppressLint("MissingSuperCall")
    override fun draw(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
        canvas.drawRect(0f, 10f, width * progress, height.toFloat() - 10f, progressPaint)
    }

    fun update(progress: Float) {
        this.progress = progress
    }

    fun increate(progress: Float) {
        this.progress += progress
    }
}