package issues9;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Example extends View {
    private List<Float> dataPoints = new ArrayList<>();
    private float scaleFactor = 1.0f;
    private float translateX = 0f;

    private float lastTouchX;
    private boolean isDragging = false;

    public Example(Context context) {
        super(context);
    }

    public Example(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Example(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Example(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
    }

    public void setData(List<Float> data) {
        this.dataPoints = data;
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        canvas.scale(scaleFactor, 1.0f);
        canvas.translate(translateX, 0);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        float barWidth = 30f;

        for (int i = 0; i < dataPoints.size(); i++) {
            float x = i * (barWidth + 10);
            float y = getHeight() - dataPoints.get(i);
            canvas.drawRect(x, y, x + barWidth, getHeight(), paint);
        }

        canvas.restore();
    }

    private ScaleGestureDetector scaleGestureDetector;

    private void init1() {
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                scaleFactor *= detector.getScaleFactor();
                scaleFactor = Math.max(0.5f, Math.min(scaleFactor, 3.0f));
                invalidate();
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = event.getX();
                isDragging = true;
                break;

            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    float dx = event.getX() - lastTouchX;
                    translateX += dx;
                    lastTouchX = event.getX();
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDragging = false;
                break;
        }
        return true;
    }
}
