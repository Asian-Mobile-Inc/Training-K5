package issues11.Customview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.example.asian.R;

public class CircularLoadingView extends View {
    private static final float STROKE_WIDTH = 8f;
    private static final float SWEEP_ANGLE = 45f;
    private static final long DURATION = 1000L;
    private static float sRotateAngle = 0f;
    private final Context context;
    private Paint bgPaint;
    private Paint fgPaint;
    private ValueAnimator animator;

    public CircularLoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        init();
    }

    private void init() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(context.getColor(R.color.black_801A1A1A));
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(STROKE_WIDTH);

        fgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fgPaint.setColor(context.getColor(R.color.purple_576CEC));
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(STROKE_WIDTH);
        fgPaint.setStrokeCap(Paint.Cap.ROUND);

        animator = ValueAnimator.ofFloat(0f, 360f);
        animator.setDuration(DURATION);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(v -> {
            sRotateAngle = (float) v.getAnimatedValue();
            invalidate();
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.cancel();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        float radius = Math.min(getWidth(), getHeight()) / 2f;
        float cx = getWidth() / 2f;
        float cy = getHeight() / 2f;
        @SuppressLint("DrawAllocation") RectF rectF = new RectF(cx - radius + STROKE_WIDTH, cy - radius + STROKE_WIDTH, cx + radius - STROKE_WIDTH, cy + radius - STROKE_WIDTH);

        canvas.drawArc(rectF, 0f, 360f, false, bgPaint);
        canvas.drawArc(rectF, sRotateAngle, SWEEP_ANGLE, false, fgPaint);
    }
}
