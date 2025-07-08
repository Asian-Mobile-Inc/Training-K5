package com.example.asian.issue9.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asian.R;
import com.example.asian.issue9.model.Hour;

import java.util.ArrayList;
import java.util.List;

public class Weather extends View {
    private Paint paintRain, paintWintryMix, paintCoordinate, paintTemperature, paintRect,
            paintTextX, paintTextY, paintDotted, paintTotal, paintSnow, paintLine, paintWeather, paintTemp;
    private List<Hour> hours;
    private String mTotal, mTitle, mHour, mTemperature;
    private Drawable mIcon;
    private int sizeTotal;
    private float scale = 1f, scrollX = 0f, scrollY = 0f, lastTouchX, lastTouchY,
            startXChart, endXChart, startYChart, endYChart, selectedX = -1f;
    private ScaleGestureDetector scaleDetector;
    private GestureDetector gestureDetector;

    public Weather(Context context) {
        super(context);
    }

    public Weather(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        hours = new ArrayList<>();
        sizeTotal = 0;
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        gestureDetector = new GestureDetector(context, new ScrollListener());
        initPaint();
        initData();
    }

    public Weather(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        paintRain = new Paint();
        paintRain.setColor(getContext().getColor(R.color.blue_81CFFA));
        paintRain.setStyle(Paint.Style.FILL);

        paintWintryMix = new Paint();
        paintWintryMix.setColor(getContext().getColor(R.color.blue_8185FA));
        paintWintryMix.setStyle(Paint.Style.FILL);

        paintSnow = new Paint();
        paintSnow.setColor(getContext().getColor(R.color.white));
        paintSnow.setStyle(Paint.Style.FILL);

        paintTemperature = new Paint();
        paintTemperature.setColor(getContext().getColor(R.color.yellow_FFAD2A));
        paintTemperature.setStyle(Paint.Style.FILL);

        paintTemp = new Paint();
        paintTemp.setColor(getContext().getColor(R.color.yellow_FFAD2A));
        paintTemp.setStrokeWidth(5);
        paintTemp.setStyle(Paint.Style.FILL_AND_STROKE);
        paintTemp.setShadowLayer(26, 0,13, getContext().getColor(R.color.yellow_FFAD2A));

        paintCoordinate = new Paint();
        paintCoordinate.setColor(getContext().getColor(R.color.white));
        paintCoordinate.setAlpha(70);
        paintCoordinate.setStrokeWidth(3);

        paintLine = new Paint();
        paintLine.setColor(getContext().getColor(R.color.white));
        paintLine.setStrokeWidth(3);

        paintRect = new Paint();
        paintRect.setColor(getContext().getColor(R.color.white));
        paintRect.setStyle(Paint.Style.FILL_AND_STROKE);
        paintRect.setAlpha(50);

        paintTextX = new Paint();
        paintTextX.setColor(getContext().getColor(R.color.white));
        paintTextX.setStyle(Paint.Style.FILL);
        paintTextX.setAlpha(120);
        paintTextX.setTextSize(pxToDp(12));

        paintTextY = new Paint();
        paintTextY.setColor(getContext().getColor(R.color.white));
        paintTextY.setStyle(Paint.Style.FILL);
        paintTextY.setAlpha(120);
        paintTextY.setTextSize(pxToDp(8));

        paintWeather = new Paint();
        paintWeather.setColor(getContext().getColor(R.color.white));
        paintWeather.setStyle(Paint.Style.FILL);
        paintWeather.setTextSize(pxToDp(10));

        paintDotted = new Paint();
        paintDotted.setColor(getContext().getColor(R.color.white));
        paintDotted.setStyle(Paint.Style.FILL);
        paintDotted.setPathEffect(new DashPathEffect(new float[] {10, 10}, 0));
        paintDotted.setAlpha(70);
        paintDotted.setStrokeWidth(3);

        paintTotal = new Paint();
        paintTotal.setColor(getContext().getColor(R.color.white));
        paintTotal.setStyle(Paint.Style.FILL);
        paintTotal.setStrokeWidth(5);
        paintTotal.setTextSize(pxToDp(34));
    }

    private void initData() {
        mTotal = "6" +getContext().getString(R.string.mm);
        mTitle = getContext().getString(R.string.total_in_the_past_24_hours);
        mHour = "";
        mTemperature = "";
        mIcon = null;
        Hour hour;
        hour = new Hour(1.0F, 0.25F, 0.0F, 6.1F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.3F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.1F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.5F);
        hours.add(hour);
        hour = new Hour(4.25F, 0.25F, 0.0F, 7.0F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.5F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.7F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.7F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.5F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.8F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.3F);
        hours.add(hour);
        hour = new Hour(9.25F, 0.25F, 0.0F, 6.5F);
        hours.add(hour);
        hour = new Hour(5.8F, 3.0F, 0.0F, 6.3F);
        hours.add(hour);
        hour = new Hour(4.0F, 1.0F, 0.0F, 6.5F);
        hours.add(hour);
        hour = new Hour(4.0F, 6.0F, 0.0F, 7.0F);
        hours.add(hour);
        hour = new Hour(8.25F, 6.0F, 0.0F, 7.3F);
        hours.add(hour);
        hour = new Hour(8.25F, 6.0F, 0.0F, 7.6F);
        hours.add(hour);
        hour = new Hour(2.0F, 1.0F, 0.0F, 7.5F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 7.3F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 7.0F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.8F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 7.0F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.8F);
        hours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.5F);
        hours.add(hour);
    }

    @SuppressLint({"DrawAllocation", "UseCompatLoadingForDrawables"})
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        sizeTotal = 0;
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), pxToDp(15), pxToDp(15), paintRect);
        sizeTotal += 44;
        canvas.drawText(mTotal, pxToDp(10), pxToDp(sizeTotal), paintTotal);
        canvas.drawText(mTemperature, getWidth() - pxToDp(10) - paintTotal.measureText(mTemperature), pxToDp(sizeTotal), paintTotal);
        if (mIcon != null) {
            mIcon.setBounds((int) (getWidth() - pxToDp(45) - paintTotal.measureText(mTemperature)), pxToDp(sizeTotal - 26), (int) (getWidth() - pxToDp(15) - paintTotal.measureText(mTemperature)), pxToDp(sizeTotal + 5));
            mIcon.draw(canvas);
        }
        sizeTotal += 12 + 10;
        canvas.drawText(mTitle, pxToDp(10), pxToDp(sizeTotal), paintTextX);
        float xCenter = getWidth() / 2f;
        float textWidth = paintTextX.measureText(mHour);
        float x = xCenter - textWidth / 2f;
        canvas.drawText(mHour, x, pxToDp(sizeTotal), paintTextX);
        sizeTotal += 10;
        drawCoordinate(pxToDp(10), pxToDp(sizeTotal), getWidth() - pxToDp(10), getHeight() - pxToDp(40),
                getWidth() - pxToDp(20), getHeight() - pxToDp(40 + sizeTotal), canvas);
        int distance = (int) ((getWidth() - pxToDp(20) - (pxToDp(68)
                + paintWeather.measureText(getContext().getString(R.string.rain))
                + paintWeather.measureText(getContext().getString(R.string.wintry_mix))
                + paintWeather.measureText(getContext().getString(R.string.snow))
                + paintWeather.measureText(getContext().getString(R.string.temperature)))) / 3);
        float topRound = getHeight() - pxToDp(30);
        float bottomRound = getHeight() - pxToDp(18);
        canvas.drawRoundRect(pxToDp(10), topRound, pxToDp(22), bottomRound, pxToDp(999), pxToDp(999), paintRain);
        canvas.drawText(getContext().getString(R.string.rain), pxToDp(27), getHeight() - pxToDp(20), paintWeather);
        float widthRain = distance + paintWeather.measureText(getContext().getString(R.string.rain));
        canvas.drawRoundRect(pxToDp(27) + widthRain,topRound, pxToDp(39) + widthRain, bottomRound, pxToDp(999), pxToDp(999), paintWintryMix);
        canvas.drawText(getContext().getString(R.string.wintry_mix), pxToDp(44) + widthRain, getHeight() - pxToDp(20), paintWeather);
        float widthWintryMix = widthRain + distance + paintWeather.measureText(getContext().getString(R.string.wintry_mix));
        canvas.drawRoundRect(pxToDp(44) + widthWintryMix, topRound, pxToDp(56) + widthWintryMix, bottomRound, pxToDp(999), pxToDp(999), paintSnow);
        canvas.drawText(getContext().getString(R.string.snow), pxToDp(60) + widthWintryMix, getHeight() - pxToDp(20), paintWeather);
        float widthSnow = widthWintryMix + distance + paintWeather.measureText(getContext().getString(R.string.snow));
        canvas.drawRoundRect(pxToDp(60) + widthSnow, topRound, pxToDp(72) + widthSnow, bottomRound, pxToDp(999), pxToDp(999), paintTemperature);
        canvas.drawText(getContext().getString(R.string.temperature), pxToDp(77) + widthSnow, getHeight() - pxToDp(20), paintWeather);
    }

    private void setTextHour(String text) {
        mHour = text;
        invalidate();
    }

    private void setTextTotal(String text) {
        mTotal = text;
        invalidate();
    }

    private void setTextTemperature() {
        mTemperature = getContext().getString(R.string._21);
        invalidate();
    }
    
    private void setTextTitle() {
        mTitle = "";
        invalidate();
    }

    private void setIcon(Drawable drawable) {
        mIcon = drawable;
        invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - lastTouchX;
                float dy = event.getY() - lastTouchY;
                scrollX += dx;
                scrollY += dy;
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                fixScroll();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                detectSelectedColumn(event.getX());
                break;
        }
        return true;
    }

    @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables"})
    private void detectSelectedColumn(float x) {
        float columnWidth = (endXChart - startXChart - pxToDp(10)) / 24f;
        float adjustedX = (x - scrollX - pxToDp(5)) / scale;
        int index = (int) (adjustedX / columnWidth);
        if (index > 0 && index <= 24 && x >= startXChart && x <= endXChart) {
            setTextHour(String.format("%02d:00 - %02d:00", index - 1, index));
            selectedX = x;
            setTextTotal(hours.get(index - 1).getRain() + " mm");
            if (hours.get(index - 1).getRain() > 0 ) {
                setIcon(getContext().getDrawable(R.drawable.ic_rain));
            } else {
                setIcon(getContext().getDrawable(R.drawable.ic_sun));
            }
            setTextTitle();
            setTextTemperature();
        }
    }

    private void drawCoordinate(float startX, float startY, float endX, float endY, float widthTotal, float heightTotal, Canvas canvas) {
        canvas.drawRoundRect(startX, startY, endX, endY, pxToDp(8), pxToDp(8), paintRect);
        float widthCoordinateX = endX - widthTotal / 26f - widthTotal / 32f;
        float heightCoordinateX = endY - heightTotal / 14f;
        float heightCoordinateY = heightCoordinateX + heightTotal / 28f;
        canvas.drawLine(startX, heightCoordinateX, widthCoordinateX, heightCoordinateX, paintCoordinate);
        canvas.drawLine(widthCoordinateX, startY, widthCoordinateX, heightCoordinateY, paintCoordinate);
        drawChart(startX, startY, endX, endY, widthCoordinateX, heightCoordinateX, canvas);
    }

    @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables"})
    private void drawChart(float startX, float startY, float endX, float endY, float widthTotal, float heightTotal, Canvas canvas) {
        startXChart = startX;
        startYChart = startY + (heightTotal - startY) / 13;
        endXChart = widthTotal;
        endYChart = heightTotal;
        canvas.save();
        canvas.clipRect(startX, startY + (heightTotal - startY) / 13f - 3, endX, heightTotal + 5);
        canvas.scale(1f, scale, startX, heightTotal);
        canvas.translate(0, scrollY / scale);
        for (int i = 0; i <= 12; i += 2) {
            float y = heightTotal - (i * (heightTotal - startY) / 13f);
            if (i != 0) {
                canvas.drawLine(startX, y, widthTotal, y, paintCoordinate);
            }
            canvas.drawText((i == 0) ? i + getContext().getString(R.string.mm) : (i != 12) ? i + "" : "", widthTotal + 5, y + 5, paintTextY);
        }
        canvas.restore();
        canvas.save();
        canvas.clipRect(startX, startY, widthTotal, endY);
        canvas.scale(scale, 1f, startX, heightTotal);
        canvas.translate(scrollX / scale, 0);
        String label = "";
        for (int i = 0; i < 19; i += 6) {
            float x = startX + pxToDp(10) + i * (widthTotal - startX - pxToDp(10)) / 24;
            float y = (endY - heightTotal/14);
            if (i != 0) {
                canvas.drawLine(x, startY, x, y, paintDotted);
            }
            label = String.format("%02d", i);
            canvas.drawText(label, x,y + pxToDp(12) + 5, paintTextX);
        }
        canvas.restore();
        canvas.save();
        canvas.clipRect(startX, startY + (heightTotal - startY) / 13, widthTotal, heightTotal);
        canvas.scale(scale, scale, startX, heightTotal);
        canvas.translate(scrollX / scale, scrollY / scale);
        float widthRain, widthWintryMix, heightWintryMix, heightSnow,
                heightRain, widthSnow, widthChart, temperatureStart, temperatureEnd;
        temperatureStart = heightTotal - (heightTotal - startY) / 13 * 8.25f;
        for (int i = 0; i < hours.size(); i++) {
            widthRain = startX + pxToDp(10) + i * (widthTotal - startX - pxToDp(10)) / 24;
            widthChart = startX + pxToDp(10) + (i + 1) * (widthTotal - startX - pxToDp(10)) / 24;
            widthWintryMix = widthRain + (widthChart - widthRain) / 3;
            widthSnow = widthWintryMix + (widthChart - widthRain) / 3;
            heightRain = heightTotal - (heightTotal - startY) / 13 * hours.get(i).getRain();
            heightWintryMix = heightTotal - (heightTotal - startY) / 13 * hours.get(i).getWintryMix();
            heightSnow = heightTotal - (heightTotal - startY) / 13 * hours.get(i).getSnow();
            temperatureEnd = heightTotal - (heightTotal - startY) / 13 * hours.get(i).getTemperature();
            canvas.drawRect(widthRain, heightTotal, widthWintryMix, heightRain, paintRain);
            canvas.drawRect(widthWintryMix, heightTotal, widthSnow, heightWintryMix, paintWintryMix);
            canvas.drawRect(widthSnow, heightTotal, widthChart, heightSnow, paintSnow);
            canvas.drawLine(widthRain, temperatureStart, widthChart, temperatureEnd, paintTemp);
            temperatureStart = temperatureEnd;
        }
        canvas.restore();
        canvas.save();
        canvas.clipRect(startX, startY, widthTotal, startY + (heightTotal - startY)/13);
        canvas.scale(1f, 1f);
        Drawable[] drawables = new Drawable[]{
               getContext().getDrawable(R.drawable.ic_rain),
               getContext().getDrawable(R.drawable.ic_cloud),
               getContext().getDrawable(R.drawable.ic_rain),
               getContext().getDrawable(R.drawable.ic_sun),
               getContext().getDrawable(R.drawable.ic_sun),
               getContext().getDrawable(R.drawable.ic_sun),
               getContext().getDrawable(R.drawable.ic_rain),
               getContext().getDrawable(R.drawable.ic_rain),
               getContext().getDrawable(R.drawable.ic_rain),
               getContext().getDrawable(R.drawable.ic_rain),
               getContext().getDrawable(R.drawable.ic_rain),
               getContext().getDrawable(R.drawable.ic_cloud)
        };
        for (int i = 0; i<12; i++) {
            if (drawables[i] != null) {
                drawables[i].setBounds((int) ((startX + pxToDp(7) + i * (widthTotal - startX) / 12)), (int) startY + pxToDp(3), (int) (startX + (i+1) * (widthTotal - startX) / 12 - pxToDp(7)), (int) (startY + (heightTotal - startY) / 13 - pxToDp(3)));
                drawables[i].draw(canvas);
            }
        }
        canvas.restore();
        if (selectedX != -1) {
            canvas.drawLine(selectedX, startY, selectedX, heightTotal, paintLine);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private int pxToDp(int px) {
        return Math.round(px * getContext().getResources().getDisplayMetrics().density);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(1f, Math.min(scale, 3f));
            fixScroll();
            invalidate();
            return true;
        }
    }

    private class ScrollListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            scrollX -= distanceX;
            scrollY -= distanceY;
            fixScroll();
            invalidate();
            return true;
        }
    }

    private void fixScroll() {
        float viewWidth = endXChart - startXChart;
        float contentWidth = viewWidth * scale;
        float maxScrollX = Math.max(0, contentWidth - viewWidth);
        float contentHeight = (endYChart - startYChart) * scale;
        float viewHeight = endYChart - startYChart;
        float maxScrollY = Math.max(0, contentHeight - viewHeight);
        scrollX = Math.max(-maxScrollX, Math.min(scrollX, 0));
        scrollY = Math.max(0, Math.min(scrollY, maxScrollY));
    }
}
