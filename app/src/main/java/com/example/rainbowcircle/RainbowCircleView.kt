package com.example.rainbowcircle

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


private const val NUMBER_OF_SECTORS = 7
private const val SWEEP_ANGLE = 360F / NUMBER_OF_SECTORS


class RainbowCircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val rainbowColors = listOf(
        R.color.red,
        R.color.orange,
        R.color.yellow,
        R.color.green,
        R.color.blue,
        R.color.navy,
        R.color.purple
    )


    init {
        isClickable = true
    }


    private val words = listOf("Android", "Kotlin", "Java", "Aston", "Job")
    var mainSector = 0
    private var rotationAnimator: ObjectAnimator? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val radius = 400f
    override fun performClick(): Boolean {
        rotationAnimation()
        return super.performClick()
    }

    private fun rotationAnimation() {
        rotationAnimator?.cancel()

        val randomAngle = (0..360).random().toFloat()
        mainSector = getSector(randomAngle)
        rotationAnimator = ObjectAnimator.ofFloat(this, ROTATION, 0f, randomAngle).apply {
            duration = 3000
            start()
        }
    }

    private fun getSector(randomAngle: Float): Int {
        return when {
            randomAngle <= SWEEP_ANGLE -> 0
            randomAngle in (SWEEP_ANGLE..2 * SWEEP_ANGLE) -> 1
            randomAngle in (2 * SWEEP_ANGLE..3 * SWEEP_ANGLE) -> 2
            randomAngle in (3 * SWEEP_ANGLE..4 * SWEEP_ANGLE) -> 3
            randomAngle in (4 * SWEEP_ANGLE..5 * SWEEP_ANGLE) -> 4
            randomAngle in (5 * SWEEP_ANGLE..6 * SWEEP_ANGLE) -> 5
            else -> 6
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        var startAngle = 0F

        for (i in 0 until NUMBER_OF_SECTORS) {
            paint.color = context.getColor(rainbowColors[i])
            canvas.drawArc(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius, startAngle, SWEEP_ANGLE, true, paint
            )
            startAngle += SWEEP_ANGLE
        }
        paint.color = Color.BLACK
        paint.strokeWidth = 15f
    }
}
