package issues9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
    private Paint mRoundedRectangle;
    private Paint mSymbolWeather;
    private TextPaint mTextSymbolWeather;
    private TextPaint mTextDescriptionWeather;
    private Paint mImageWeather;
    private TextPaint mTextStatusWeather;

    private final List<Hour> mHourLists;
    private final List<Point> mPointLists = new ArrayList<>();

    private ScaleGestureDetector mScaleGestureDetector;
    private float mLastTouchX;
    private boolean mIsDragging = false;
    private float mTranslateX = 0f;
    private float mScaleFactorX = 1.0f;
    private float mScaleFactorY = 1.0f;
    private int selectedIndex = -1;

    private static int sLevelRain;
    private static float sMaxXValue;
    private static boolean sIsActionDown;
    private static float sXSliderPosition;

    private static final float ROUNDED_RECTANGLE_MARGIN_TOP = 100.0f;
    private static final float ROUNDED_RECTANGLE_HEIGHT = 300.0f;
    private static final float ROUNDED_RECTANGLE_MARGIN_HORIZONTAL = 10.0f;
    private static final float ROUNDED_RECTANGLE_RADIUS = 15.0f;
    private static final float SYMBOL_WEATHER_MARGIN_BOTTOM = 25.0f;
    private static final float SYMBOL_WEATHER_MARGIN_START = 70.0f;
    private static final float BAR_WIDTH = 6.0f;
    private static final float MARGIN_CHART_TO_RIGHT = 25.0f + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL;
    private static final float MARGIN_CHART_TO_TOP = 30.0f;
    private static final float MARGIN_CHART_TO_BOTTOM = 30.0f;
    private static final float MARGIN_ICON_WEATHER_TO_TOP = 12.0f;
    private static final int MAX_HOUR_ITEM_INIT = 15;
    private static final float MARGIN_COLUMN = 4;
    private static final int ROW_NUMBER = 5;

    public Weather(Context context, List<Hour> hourLists) {
        super(context);
        this.mHourLists = hourLists;
        this.mContext = context;
        init();
        initScaleGestureDetector();
    }

    private void init() {
        mRoundedRectangle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRoundedRectangle.setColor(mContext.getColor(R.color.white_20FFFFFF));
        mRoundedRectangle.setStrokeWidth(dpToFloat(0.5f));

        mSymbolWeather = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        mSymbolWeather.setStyle(Paint.Style.FILL);

        mTextSymbolWeather = new TextPaint();
        mTextSymbolWeather.setColor(Color.WHITE);
        mTextSymbolWeather.setTextSize(dpToFloat(11));

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

        mTextDescriptionWeather = new TextPaint();
        mTextDescriptionWeather.setColor(Color.WHITE);
        mTextDescriptionWeather.setTextSize(dpToFloat(12));

        mImageWeather = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTextStatusWeather = new TextPaint();
        mTextStatusWeather.setColor(Color.WHITE);
        mTextStatusWeather.setTextSize(dpToFloat(34));
    }

    public float dpToFloat(float dpSize) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpSize, dm);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        float rightEdge = getWidth() - dpToFloat(MARGIN_CHART_TO_RIGHT);
        canvas.clipRect(dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL), dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + MARGIN_CHART_TO_TOP), rightEdge, dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT));
        canvas.translate(mTranslateX, 0);
        float pivotY = dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_BOTTOM);
        canvas.scale(mScaleFactorX, mScaleFactorY, 0, pivotY);
        drawColumnChart(canvas);
        canvas.restore();

        canvas.save();
        canvas.clipRect(dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL), dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP), rightEdge, dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT));
        canvas.translate(mTranslateX, 0);
        canvas.scale(mScaleFactorX, 1.0f);
        drawTemperature(canvas);
        drawPolygonGradient(canvas);
        drawSlider(canvas);
        drawIconWeather(canvas);
        canvas.restore();

        drawDottedLine(canvas);
        drawSolidLine(canvas);
        drawRoundedRectangle(canvas);
        drawTextOverviewDescription(canvas);

        drawSymbolCircle(canvas, SYMBOL_WEATHER_MARGIN_START, mContext.getColor(R.color.blue_81CFFA));
        drawTextSymbol(canvas, mContext.getString(R.string.textview_text_rain), SYMBOL_WEATHER_MARGIN_START);

        drawSymbolCircle(canvas, SYMBOL_WEATHER_MARGIN_START + dpToFloat(80), mContext.getColor(R.color.purple_8185FA));
        drawTextSymbol(canvas, mContext.getString(R.string.textview_text_wintry_mix), SYMBOL_WEATHER_MARGIN_START + dpToFloat(80));

        drawSymbolCircle(canvas, SYMBOL_WEATHER_MARGIN_START + dpToFloat(180), mContext.getColor(R.color.white));
        drawTextSymbol(canvas, mContext.getString(R.string.textview_text_snow), SYMBOL_WEATHER_MARGIN_START + dpToFloat(180));

        drawSymbolCircle(canvas, SYMBOL_WEATHER_MARGIN_START + dpToFloat(260), mContext.getColor(R.color.yellow_FFAD2A));
        drawTextSymbol(canvas, mContext.getString(R.string.textview_text_temperature), SYMBOL_WEATHER_MARGIN_START + dpToFloat(260));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LinearGradient gradient = new LinearGradient(0, 0, 0, h, mContext.getColor(R.color.yellow_7AC8A534), mContext.getColor(R.color.yellow_00C8A534), Shader.TileMode.CLAMP);
        mPolygonPaint.setShader(gradient);
    }

    private void drawRoundedRectangle(Canvas canvas) {
        float radius = dpToFloat(ROUNDED_RECTANGLE_RADIUS);
        float[] corners = new float[]{
                radius, radius,        // Top left radius in px
                radius, radius,        // Top right radius in px
                radius, radius,          // Bottom right radius in px
                radius, radius           // Bottom left radius in px
        };

        final Path path = new Path();
        path.addRoundRect(new RectF(dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL), dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP), getWidth() - dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL), dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP) + dpToFloat(ROUNDED_RECTANGLE_HEIGHT)), corners, Path.Direction.CW);
        canvas.drawPath(path, mRoundedRectangle);
    }

    private void drawSymbolCircle(Canvas canvas, float x, int color) {
        mSymbolWeather.setColor(color);
        canvas.drawCircle(x, getHeight() - dpToFloat(SYMBOL_WEATHER_MARGIN_BOTTOM), dpToFloat(8), mSymbolWeather);
    }

    private void drawTextSymbol(Canvas canvas, String text, float x) {
        canvas.drawText(text, x + dpToFloat(15), getHeight() - dpToFloat(SYMBOL_WEATHER_MARGIN_BOTTOM) + 10, mTextSymbolWeather);
    }

    private void drawTemperature(Canvas canvas) {
        float maxY = getMaxValueTemperature();

        float factorX = (getWidth() - dpToFloat(MARGIN_CHART_TO_RIGHT + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL)) / (float) MAX_HOUR_ITEM_INIT;
        float factorY = dpToFloat(ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_TOP - MARGIN_CHART_TO_BOTTOM) / maxY;

        // To draw polygon gradient
        mPointLists.clear();
        mPointLists.add(new Point(0, dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_BOTTOM)));
        for (int i = 1; i < mHourLists.size(); ++i) {
            int x0 = i - 1;
            float y0 = mHourLists.get(i - 1).getmTemperature();
            float y1 = mHourLists.get(i).getmTemperature();

            float sx = x0 * factorX + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL;
            float sy = dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_BOTTOM) - (y0 * factorY);
            float ex = i * factorX + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL;
            float ey = dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_BOTTOM) - (y1 * factorY);

            canvas.drawLine(sx, sy * 1.5f, ex, ey * 1.5f, mTempPaint);

            if (i == 1) {
                mPointLists.add(new Point(sx, sy * 1.5f));
            }
            mPointLists.add(new Point(ex, ey * 1.5f));

            if (i == mHourLists.size() - 1) {
                sMaxXValue = ex;
                mPointLists.add(new Point(ex, dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_BOTTOM)));
            }
        }
        mPointLists.add(new Point(0, dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_BOTTOM)));
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
        float barWidth = dpToFloat(BAR_WIDTH);
        float maxY = getMaxValueColumnChart();
        float factorX = (getWidth() - dpToFloat(71 + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL * 2)) / (float) MAX_HOUR_ITEM_INIT;
        float factorY = dpToFloat(ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_TOP  - MARGIN_CHART_TO_BOTTOM) / maxY;
        sLevelRain = (int) maxY / ROW_NUMBER;

        for (int i = 0; i < mHourLists.size(); i++) {
            float xR = i * factorX + dpToFloat(MARGIN_COLUMN + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL);

            // Draw rain column
            float yR = dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_BOTTOM) - mHourLists.get(i).getmRain() * factorY;
            canvas.drawRoundRect(xR, yR, xR + barWidth, dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_BOTTOM), 70f, 30f, mRainPaint);

            // Draw wintry column
            float yW = dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_BOTTOM) - mHourLists.get(i).getmWintry() * factorY;
            canvas.drawRoundRect(xR + barWidth, yW, xR + barWidth * 2, dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_BOTTOM), 70f, 30f, mWintryPaint);

            if (i == mHourLists.size() - 1) {
                sMaxXValue = xR + barWidth * 2;
            }

            // Draw hour text
            if (i % 4 == 0) {
                canvas.drawText(mContext.getString(R.string.drawtext_text_hour, i), xR - dpToFloat(1), dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - 16), mTextHourPaint);
            }
        }
    }

    private void drawIconWeather(Canvas canvas) {
        float factorX = (getWidth() - dpToFloat(71 + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL * 2)) / (float) MAX_HOUR_ITEM_INIT;

        for (int i = 0; i < mHourLists.size(); i++) {
            float xR = i * factorX + dpToFloat(MARGIN_COLUMN + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL);

            Bitmap bitmap = getBitmapFromStatusWeather(mHourLists.get(i));
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            float top = dpToFloat(MARGIN_ICON_WEATHER_TO_TOP + ROUNDED_RECTANGLE_MARGIN_TOP);
            RectF rectF = new RectF(xR, top, xR + bitmap.getWidth(), top + bitmap.getHeight());
            canvas.drawBitmap(bitmap, rect, rectF, mStatusWeatherPaint);
        }
    }

    private void drawDottedLine(Canvas canvas) {
        float factorX = (getWidth() - dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL + MARGIN_CHART_TO_RIGHT)) / 4;
        float y = dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - 10);
        for (int i = 1; i < 4; i++) {
            float x1 = dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL) + factorX * i;
            canvas.drawLine(x1, y, x1, dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP), mDottedLinePaint);
        }
    }

    private void drawSolidLine(Canvas canvas) {
        float factorX = (getWidth() - dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL + MARGIN_CHART_TO_RIGHT)) / 4;
        float y = dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - 10);
        float x = dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL) + factorX * 4;
        canvas.drawLine(x, y, x, dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP), mSolidLinePaint);

        float factorY = dpToFloat(ROUNDED_RECTANGLE_HEIGHT - MARGIN_CHART_TO_TOP - MARGIN_CHART_TO_BOTTOM) / ROW_NUMBER;
        for (int i = 0; i < 6; i++) {
            float xPoint = dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL) + getWidth() - dpToFloat(MARGIN_CHART_TO_RIGHT + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL);
            float yPoint = dpToFloat(MARGIN_CHART_TO_TOP + ROUNDED_RECTANGLE_MARGIN_TOP) + factorY * i;
            canvas.drawLine(dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL), yPoint, xPoint, yPoint, mSolidLinePaint);

            // Draw level rain text
            if (i > 0) {
                canvas.drawText(i == 5 ? mContext.getString(R.string.text_0_mm) : mContext.getString(R.string.text_level_rain, sLevelRain * (5 - i) / mScaleFactorY), xPoint + dpToFloat(3), yPoint + dpToFloat(3), mTextLevelRainPaint);
            }
        }
    }

    private void initScaleGestureDetector() {
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(@NonNull ScaleGestureDetector detector) {
                float proposedScale = mScaleFactorX * detector.getScaleFactor();
                float newContentWidth = sMaxXValue * proposedScale;
                if (newContentWidth < getWidth()) {
                    proposedScale = getWidth() / sMaxXValue;
                }
                proposedScale = Math.max(0.5f, Math.min(proposedScale, 2.0f));
                mScaleFactorX = proposedScale;
                mScaleFactorY = Math.max(0.5f, Math.min(mScaleFactorY * detector.getScaleFactor(), 2.0f));
                invalidate();
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchX = event.getX();
                mIsDragging = true;
                sIsActionDown = true;

                float clickXInChart = (event.getX() - mTranslateX - dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL)) / mScaleFactorX;
                float factorX = (getWidth() - dpToFloat(71 + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL * 2)) / (float) MAX_HOUR_ITEM_INIT;

                selectedIndex = Math.round((clickXInChart - dpToFloat(11)) / factorX);
                if (selectedIndex < 0) selectedIndex = 0;
                if (selectedIndex >= mHourLists.size()) selectedIndex = mHourLists.size() - 1;

                sXSliderPosition = selectedIndex * factorX + dpToFloat(10 + ROUNDED_RECTANGLE_MARGIN_HORIZONTAL);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsDragging) {
                    float dx = event.getX() - mLastTouchX;
                    mTranslateX += dx;
                    checkContentOutOfWidth();
                    mLastTouchX = event.getX();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsDragging = false;
                sIsActionDown = false;
                selectedIndex = -1;
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
            float y = dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP + ROUNDED_RECTANGLE_HEIGHT - 10);
            canvas.drawLine(sXSliderPosition, 0, sXSliderPosition, y, mSliderPaint);
        }
    }

    private void drawTextOverviewDescription(Canvas canvas) {
        if (selectedIndex >= 0) {
            Hour item = mHourLists.get(selectedIndex);

            Bitmap bitmap = getBitmapBigIconFromStatusWeather(item);
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            float right = getWidth() - dpToFloat(100);
            RectF rectF = new RectF(right - bitmap.getWidth(), dpToFloat(12), right, dpToFloat(12) + bitmap.getHeight());
            canvas.drawBitmap(bitmap, rect, rectF, mImageWeather);

            canvas.drawText(mContext.getString(R.string.textview_text_number_mm, item.getmRain()), dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL), dpToFloat(40), mTextStatusWeather);
            canvas.drawText(mContext.getString(R.string.textview_text_number_temperature, item.getmTemperature()), getWidth() - dpToFloat(90), dpToFloat(40), mTextStatusWeather);
            canvas.drawText(mContext.getString(R.string.textview_text_hour, selectedIndex), (float) getWidth() / 2 - 40, dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP - 10), mTextDescriptionWeather);
        } else {
            canvas.drawText(mContext.getString(R.string.textview_text_total_in_the_past_24_hours), dpToFloat(ROUNDED_RECTANGLE_MARGIN_HORIZONTAL), dpToFloat(ROUNDED_RECTANGLE_MARGIN_TOP - 10), mTextDescriptionWeather);
        }
    }

    private void checkContentOutOfWidth() {
        float contentWidth = sMaxXValue * mScaleFactorX;
        float maxTranslateX = getWidth() - contentWidth;

        if (contentWidth <= getWidth()) {
            mTranslateX = 0;
        } else {
            if (mTranslateX > 0) {
                mTranslateX = 0;
            } else if (mTranslateX < maxTranslateX) {
                mTranslateX = maxTranslateX;
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

    private Bitmap getBitmapBigIconFromStatusWeather(Hour item) {
        int resourceId = 0;
        if (item.getmRain() > 0) {
            resourceId = R.drawable.ic_rain_big;
        } else if (item.getmWintry() > 0) {
            resourceId = R.drawable.ic_cloud_big;
        } else {
            resourceId = R.drawable.ic_sun_big;
        }

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), resourceId, null);
        return drawableToBitmap(drawable);
    }
}
