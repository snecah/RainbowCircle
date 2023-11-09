package com.example.rainbowcircle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class RandomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val words = listOf("Android", "Kotlin", "Java", "Aston", "Job")
    var isPrintNothing: Boolean = true
    var savedWord: String? = null
    var currentWord = ""

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        textSize = 60f
        strokeWidth = 5f
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawARGB(80, 102, 204, 255)
        val centerX = width / 4f
        val centerY = height / 2f
        if (savedWord != null) {
            canvas.drawText(savedWord!!, centerX, centerY, paint)
            savedWord = null
        }
        if (!isPrintNothing) {
            currentWord = words.random()
            canvas.drawText(currentWord, centerX, centerY, paint)
        }
    }
}