package issues13.ex2.customview

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.asian.R

class CircularLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val strokeWidth = 16f
    private val sweepAngle = 45f
    private var rotateAngle = 0f
    private val duration = 1000L
    private val startAngle = 0f
    private val sweepAngleBg = 360f

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.blue_B3BDFF)
        style = Paint.Style.STROKE
        strokeWidth = this@CircularLoadingView.strokeWidth
    }

    private val fgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.purple_576CEC)
        style = Paint.Style.STROKE
        strokeWidth = this@CircularLoadingView.strokeWidth
        strokeCap = Paint.Cap.ROUND
    }

    private val animator = ValueAnimator.ofFloat(0f, 360f).apply {
        duration = this@CircularLoadingView.duration
        interpolator = LinearInterpolator()
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener {
            rotateAngle = it.animatedValue as Float
            invalidate()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val radius = (width.coerceAtMost(height) - strokeWidth) / 2f
        val cx = width / 2f
        val cy = height / 2f
        val rect = RectF(
            cx - radius,
            cy - radius,
            cx + radius,
            cy + radius
        )

        canvas.drawArc(rect, startAngle, sweepAngleBg, false, bgPaint)
        canvas.drawArc(rect, rotateAngle, sweepAngle, false, fgPaint)
    }
}
