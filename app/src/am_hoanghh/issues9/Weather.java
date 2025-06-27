package issues9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.example.asian.R;

import java.util.ArrayList;
import java.util.List;

public class Weather extends View {
    private final Context mContext;
    private Paint mTempPaint;
    private Paint mDottedLinePaint;
    private Paint mSolidLinePaint;
    private TextPaint mTextHourPaint;
    private TextPaint mTextLevelRainPaint;
    private Paint mSliderPaint;
    private Paint mRainPaint;
    private Paint mWintryPaint;
    private Paint mPolygonPaint;
    private Paint mStatusWeatherPaint;

    private final List<Hour> mHourLists;
    private final List<Point> mPointLists = new ArrayList<>();
    private final OnTouchChartListener mListener;

    private ScaleGestureDetector mScaleGestureDetector;
    private float lastTouchX;
    private boolean isDragging = false;
    private float translateX = 0f;
    private float scaleFactor = 1.0f;

    private static int sLevelRain;
    private static float sMaxXValue;
    private static boolean sIsActionDown;
    private static float sXSliderPosition;

    private static final float BAR_WIDTH = 6.0f;
    private static final float MARGIN_CHART_TO_RIGHT = 25.0f;
    private static final float MARGIN_CHART_TO_TOP = 30.0f;
    private static final float MARGIN_CHART_TO_BOTTOM = 30.0f;

    public Weather(Context context, List<Hour> hourLists, OnTouchChartListener listener) {
        super(context);
        this.mHourLists = hourLists;
        this.mContext = context;
        this.mListener = listener;
        init();
        initScaleGestureDetector();
    }

    private void init() {
        mTempPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTempPaint.setColor(mContext.getColor(R.color.yellow_FFAD2A));
        mTempPaint.setStrokeWidth(dpToFloat(3));
        mTempPaint.setStrokeCap(Paint.Cap.ROUND);

        mDottedLinePaint = new Paint();
        mDottedLinePaint.setColor(mContext.getColor(R.color.white_15FFFFFF));
        mDottedLinePaint.setStyle(Paint.Style.STROKE);
        mDottedLinePaint.setPathEffect(new DashPathEffect(new float[]{3f, 3f}, 0f));

        mSolidLinePaint = new Paint();
        mSolidLinePaint.setColor(mContext.getColor(R.color.white_15FFFFFF));
        mSolidLinePaint.setStyle(Paint.Style.STROKE);

        mTextHourPaint = new TextPaint();
        mTextHourPaint.setColor(mContext.getColor(R.color.white_40FFFFFF));
        mTextHourPaint.setTextSize(dpToFloat(12));

        mTextLevelRainPaint = new TextPaint();
        mTextLevelRainPaint.setColor(mContext.getColor(R.color.white_96FFFFFF));
        mTextLevelRainPaint.setTextSize(dpToFloat(8));

        mRainPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRainPaint.setColor(mContext.getColor(R.color.blue_81CFFA));
        mRainPaint.setStyle(Paint.Style.FILL);
        mRainPaint.setStrokeCap(Paint.Cap.ROUND);

        mWintryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWintryPaint.setColor(mContext.getColor(R.color.purple_8185FA));
        mWintryPaint.setStyle(Paint.Style.FILL);
        mWintryPaint.setStrokeCap(Paint.Cap.ROUND);

        mSliderPaint = new Paint();
        mSliderPaint.setColor(mContext.getColor(R.color.white));
        mSliderPaint.setStyle(Paint.Style.STROKE);
        mSliderPaint.setStrokeWidth(dpToFloat(0.75f));

        mPolygonPaint = new Paint();
        mPolygonPaint.setStyle(Paint.Style.FILL);

        mStatusWeatherPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public float dpToFloat(float dpSize) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize, dm);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(translateX, 0);
        canvas.scale(scaleFactor, 1.0f);

        drawColumnChart(canvas);
        drawTemperature(canvas);
        drawPolygonGradient(canvas);
        drawDottedLine(canvas);
        drawSolidLine(canvas);
        drawSlider(canvas);

        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LinearGradient gradient = new LinearGradient(0, 0, 0, h, mContext.getColor(R.color.yellow_7AC8A534), mContext.getColor(R.color.yellow_00C8A534), Shader.TileMode.CLAMP);
        mPolygonPaint.setShader(gradient);
    }

    private void drawTemperature(Canvas canvas) {
        int maxX = 15;
        float maxY = getMaxValueTemperature();

        float factorX = (getWidth() - dpToFloat(25)) / (float) maxX;
        float factorY = (getHeight() - dpToFloat(60)) / maxY;

        // To draw polygon gradient
        mPointLists.clear();
        mPointLists.add(new Point(0, getHeight() - dpToFloat(30)));
        for (int i = 1; i < mHourLists.size(); ++i) {
            int x0 = i - 1;
            float y0 = mHourLists.get(i - 1).getmTemperature();
            float y1 = mHourLists.get(i).getmTemperature();

            int sx = (int) (x0 * factorX);
            int sy = getHeight() - (int) dpToFloat(30) - (int) (y0 * factorY);
            int ex = (int) (i * factorX);
            int ey = getHeight() - (int) dpToFloat(30) - (int) (y1 * factorY);
            canvas.drawLine(sx, sy * 2, ex, ey * 2, mTempPaint);

            if (i == 1) {
                mPointLists.add(new Point(sx, sy * 2));
            }
            mPointLists.add(new Point(ex, ey * 2));

            if (i == mHourLists.size() - 1) {
                sMaxXValue = ex;
                mPointLists.add(new Point(ex, getHeight() - dpToFloat(30)));
            }
        }
        mPointLists.add(new Point(0, getHeight() - dpToFloat(30)));
    }

    private void drawPolygonGradient(Canvas canvas) {
        Path path = new Path();
        path.reset();
        path.moveTo(mPointLists.get(0).x, mPointLists.get(0).y);
        for (int i = 1; i < mPointLists.size(); i++) {
            path.lineTo(mPointLists.get(i).x, mPointLists.get(i).y);
        }
        canvas.drawPath(path, mPolygonPaint);
    }

    private void drawColumnChart(Canvas canvas) {
        float barWidth = dpToFloat(6);
        int maxX = 15;
        float maxY = getMaxValueColumnChart();
        float factorX = (getWidth() - dpToFloat(41)) / (float) maxX;
        float factorY = (getHeight() - dpToFloat(60)) / maxY;
        sLevelRain = (int) maxY / 5;

        for (int i = 0; i < mHourLists.size(); i++) {
            // Draw rain column
            float xR = i * factorX + dpToFloat(4);
            float yR = (float) (getHeight() - dpToFloat(30) - mHourLists.get(i).getmRain() * factorY);
            canvas.drawRoundRect(xR, yR, xR + barWidth, getHeight() - dpToFloat(30), 70f, 30f, mRainPaint);

            // Draw wintry column
            float yW = (float) (getHeight() - dpToFloat(30) - mHourLists.get(i).getmWintry() * factorY);
            canvas.drawRoundRect(xR + barWidth, yW, xR + barWidth * 2, getHeight() - dpToFloat(30), 70f, 30f, mWintryPaint);

            // Draw hour text
            if (i % 4 == 0) {
                canvas.drawText(mContext.getString(R.string.drawtext_text_hour, i), xR - dpToFloat(1), getHeight() - dpToFloat(16), mTextHourPaint);
            }

            // Draw icon weather bitmap
            Bitmap bitmap = getBitmapFromStatusWeather(mHourLists.get(i));
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            float top = dpToFloat(12);
            RectF rectF = new RectF(xR, top, xR + bitmap.getWidth(), top + bitmap.getHeight());
            canvas.drawBitmap(bitmap, rect, rectF, mStatusWeatherPaint);
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
        for (int i = 0; i < 6; i++) {
            float xPoint = (float) (getWidth() - dpToFloat(25));
            float yPoint = dpToFloat(30) + factorY * i;
            canvas.drawLine(0, yPoint, xPoint, yPoint, mSolidLinePaint);

            // Draw level rain text
            if (i > 0) {
                canvas.drawText(i == 5 ? "0 mm" : String.valueOf(sLevelRain * (5 - i)), xPoint + dpToFloat(3), yPoint + dpToFloat(3), mTextLevelRainPaint);
            }
        }
    }

    private void initScaleGestureDetector() {
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(@NonNull ScaleGestureDetector detector) {
                float proposedScale = scaleFactor * detector.getScaleFactor();
                float newContentWidth = sMaxXValue * proposedScale;
                if (newContentWidth < getWidth()) {
                    proposedScale = getWidth() / sMaxXValue;
                }
                proposedScale = Math.max(0.5f, Math.min(proposedScale, 2.0f));
                scaleFactor = proposedScale;
                invalidate();
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = event.getX();
                isDragging = true;
                sIsActionDown = true;

                float clickXInChart = (event.getX() - translateX) / scaleFactor;
                int maxX = 15;
                float factorX = (getWidth() - dpToFloat(41)) / (float) maxX;

                int selectedIndex = Math.round((clickXInChart - dpToFloat(6)) / factorX);
                if (selectedIndex < 0) selectedIndex = 0;
                if (selectedIndex >= mHourLists.size()) selectedIndex = mHourLists.size() - 1;

                mListener.onTouchListener(selectedIndex);
                sXSliderPosition = selectedIndex * factorX + dpToFloat(10);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDragging) {
                    float dx = event.getX() - lastTouchX;
                    translateX += dx;
                    checkContentOutOfWidth();
                    lastTouchX = event.getX();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isDragging = false;
                sIsActionDown = false;
                mListener.onTouchListener(-1);
                invalidate();
                break;
        }
        return true;
    }

    private Float getMaxValueColumnChart() {
        float max = 0;
        for (Hour hour : mHourLists) {
            max = Math.max(max, Math.max(hour.getmRain(), hour.getmWintry()));
        }
        return max;
    }

    private Float getMaxValueTemperature() {
        float max = 0;
        for (Hour hour : mHourLists) {
            max = Math.max(max, hour.getmTemperature());
        }
        return max;
    }

    private void drawSlider(Canvas canvas) {
        if (sIsActionDown) {
            float y = (float) getHeight() - dpToFloat(10);
            canvas.drawLine(sXSliderPosition, 0, sXSliderPosition, y, mSliderPaint);
        }
    }

    private void checkContentOutOfWidth() {
        float contentWidth = sMaxXValue * scaleFactor;
        float maxTranslateX = getWidth() - contentWidth;

        if (contentWidth <= getWidth()) {
            translateX = 0;
        } else {
            if (translateX > 0) {
                translateX = 0;
            } else if (translateX < maxTranslateX) {
                translateX = maxTranslateX;
            }
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private Bitmap getBitmapFromStatusWeather(Hour item) {
        int resourceId = 0;
        if (item.getmRain() > 0) {
            resourceId = R.drawable.ic_rain_top;
        } else if (item.getmWintry() > 0) {
            resourceId = R.drawable.ic_cloud;
        } else {
            resourceId = R.drawable.ic_sun;
        }

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), resourceId, null);
        return drawableToBitmap(drawable);
    }
}
