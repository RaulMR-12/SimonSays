package com.example.simon

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SimonButton(context: Context, attrs: AttributeSet): View(context, attrs) {
    private var color: Int = Color.RED
    private val paint = Paint()

    init{
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
    }

    fun setColor(color: Int){
        this.color = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        canvas?.apply {
            drawColor(Color.TRANSPARENT)

            paint.color = color
            drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        }
    }
}