package com.example.asian.issue13.ex2.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.asian.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CustomProgress: View {
    private var mPaintProgress: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.blue_576CEC)
        style = Paint.Style.STROKE
        strokeWidth = 20f
        strokeCap = Paint.Cap.ROUND
    }
    private var mPaintBackground: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.blue_C5CDFA)
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }
    private var mProgress: Int = 0
    private lateinit var viewScope: CoroutineScope

    constructor(context: Context?) : super(context) {
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,defStyleAttr) {
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding = mPaintProgress.strokeWidth / 2
        val rect = RectF(padding, padding, width - padding, width - padding)
        canvas.drawArc(rect, 270f, 360f, false, mPaintBackground)
        canvas.drawArc(rect, 270f, -(360 * (mProgress / 100f)), false, mPaintProgress)
    }

    fun animateProgress() {
        viewScope = MainScope()
        viewScope.launch {
            var i = 0
            while (true) {
                mProgress = i
                invalidate()
                delay(40)
                i += 5
                if (i > 100) {
                    i = 0
                }
            }
        }
    }

    fun close() {
        viewScope.cancel()
    }
}
