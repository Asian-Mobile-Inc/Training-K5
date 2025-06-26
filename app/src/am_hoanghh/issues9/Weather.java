package issues9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.asian.R;

import java.util.List;

public class Weather extends View {
    private Paint mTempPaint;
    private Paint mDottedLinePaint;
    private Paint mSolidLinePaint;
    private TextPaint mTextHourPaint;
    private TextPaint mTextLevelRainPaint;

    private ScaleGestureDetector scaleGestureDetector;
    private float lastTouchX;
    private boolean isDragging = false;
    private float translateX = 0f;
    private float scaleFactor = 1.0f;

    private Paint mRainPaint;
    private Paint mWintryPaint;
    private List<Hour> mHourLists;

    private static int sLevelRain;

    public Weather(Context context, List<Hour> hourLists) {
        super(context);
        init(context);
        init1();

        this.mHourLists = hourLists;
    }

    private void init(Context context) {
        mTempPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTempPaint.setColor(context.getColor(R.color.yellow_FFAD2A));
        mTempPaint.setStrokeWidth(dpToFloat(3));
        mTempPaint.setStrokeCap(Paint.Cap.ROUND);

        mDottedLinePaint = new Paint();
        mDottedLinePaint.setColor(context.getColor(R.color.white_15FFFFFF));
        mDottedLinePaint.setStyle(Paint.Style.STROKE);
        mDottedLinePaint.setPathEffect(new DashPathEffect(new float[] {3f,3f}, 0f));

        mSolidLinePaint = new Paint();
        mSolidLinePaint.setColor(context.getColor(R.color.white_15FFFFFF));
        mSolidLinePaint.setStyle(Paint.Style.STROKE);

        mTextHourPaint = new TextPaint();
        mTextHourPaint.setColor(context.getColor(R.color.white_40FFFFFF));
        mTextHourPaint.setTextSize(dpToFloat(12));

        mTextLevelRainPaint = new TextPaint();
        mTextLevelRainPaint.setColor(context.getColor(R.color.white_96FFFFFF));
        mTextLevelRainPaint.setTextSize(dpToFloat(8));

        mRainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRainPaint.setColor(context.getColor(R.color.blue_81CFFA));
        mRainPaint.setStyle(Paint.Style.FILL);
        mRainPaint.setStrokeCap(Paint.Cap.ROUND);

        mWintryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWintryPaint.setColor(context.getColor(R.color.purple_8185FA));
        mWintryPaint.setStyle(Paint.Style.FILL);
        mWintryPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public float dpToFloat(int dpSize) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize, dm);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(scaleFactor, 1.0f);
        canvas.translate(translateX, 0);

        drawColumnChart(canvas);
        drawTemperature(canvas);
        drawDottedLine(canvas);
        drawSolidLine(canvas);

        canvas.restore();
    }

    private void drawTemperature(Canvas canvas) {
        int maxX = 15;
        float maxY = getMaxValueTemperature();

        float factorX = (getWidth() - dpToFloat(25)) / (float) maxX;
        float factorY = (getHeight() - dpToFloat(60)) / maxY;

        for (int i = 1; i < mHourLists.size(); ++i) {
            int x0 = i - 1;
            float y0 = mHourLists.get(i - 1).getmTemperature();
            float y1 = mHourLists.get(i).getmTemperature();

            int sx = (int) (x0 * factorX);
            int sy = getHeight() - (int) dpToFloat(30) - (int) (y0 * factorY);
            int ex = (int) (i * factorX);
            int ey = getHeight() - (int) dpToFloat(30) - (int) (y1 * factorY);
            canvas.drawLine(sx, sy * 2, ex, ey * 2, mTempPaint);
        }
    }

    @SuppressLint("DefaultLocale")
    private void drawColumnChart(Canvas canvas) {
        float barWidth = dpToFloat(6);

        int maxX = 15;
        float maxY = getMaxValueColumnChart();
        float factorX = (getWidth() - dpToFloat(41)) / (float) maxX;
        float factorY = (getHeight() - dpToFloat(60)) / maxY;
        sLevelRain = (int) maxY / 5;

        for (int i = 0; i < mHourLists.size(); i++) {
            float xR = i * factorX + dpToFloat(4);
            float yR = (float) (getHeight() - dpToFloat(30) - mHourLists.get(i).getmRain() * factorY);
            canvas.drawRoundRect(xR, yR, xR + barWidth, getHeight() - dpToFloat(30), 70f, 30f, mRainPaint);

            float yW = (float) (getHeight() - dpToFloat(30) - mHourLists.get(i).getmWintry() * factorY);
            canvas.drawRoundRect(xR + dpToFloat(6), yW, xR + dpToFloat(6) + barWidth, getHeight() - dpToFloat(30), 70f, 30f, mWintryPaint);

            if (i % 4 == 0) {
                canvas.drawText(String.format("%02d", i), xR - dpToFloat(1), getHeight() - dpToFloat(16), mTextHourPaint);
            }
        }
    }

    private void drawDottedLine(Canvas canvas) {
        float factorX = (float) (getWidth() - dpToFloat(25)) / 4;
        float y = (float) getHeight() - dpToFloat(10);
        for (int i = 1; i < 4; i++) {
            float x1 = factorX * i;
            canvas.drawLine(x1, y, x1, 0, mDottedLinePaint);
        }
    }

    private void drawSolidLine(Canvas canvas) {
        float factorX = (float) (getWidth() - dpToFloat(25)) / 4;
        float y = (float) getHeight() - dpToFloat(10);
        canvas.drawLine(factorX * 4, y, factorX * 4, 0, mSolidLinePaint);

        float factorY = (float) (getHeight() - dpToFloat(60)) / 5;
//        canvas.drawLine(0, (float) getHeight() - dpToFloat(30), factorX * 4, (float) getHeight() - dpToFloat(30), mSolidLinePaint);
        for (int i = 0; i < 6; i++) {
            float xPoint = (float) (getWidth() - dpToFloat(25));
            float yPoint = dpToFloat(30) + factorY * i;
            canvas.drawLine(0, yPoint, xPoint, yPoint, mSolidLinePaint);

            if (i > 0) {
                canvas.drawText(i == 5 ? "0 mm" : String.valueOf(sLevelRain * (5 - i)), xPoint + dpToFloat(3), yPoint + dpToFloat(3), mTextLevelRainPaint);
            }
        }
    }

    private void init1() {
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(@NonNull ScaleGestureDetector detector) {
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

//                Log.d("DEBUG", String.valueOf(event.getX()));
//                Log.d("DEBUG", String.valueOf(event.getY()));
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

    private Float getMaxValueColumnChart() {
        float max = 0;
        for (Hour hour: mHourLists) {
            max = Math.max(max, Math.max(hour.getmRain(), hour.getmWintry()));
        }
        return max;
    }

    private Float getMaxValueTemperature() {
        float max = 0;
        for (Hour hour: mHourLists) {
            max = Math.max(max, hour.getmTemperature());
        }
        return max;
    }
}
