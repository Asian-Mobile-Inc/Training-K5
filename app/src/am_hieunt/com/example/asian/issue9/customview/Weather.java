package com.example.asian.issue9.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.asian.R;
import com.example.asian.issue9.model.Hour;

import java.util.List;

public class Weather extends View {
    private Paint paintRain, paintWintryMix, paintCoordinate, paintRect, paintTextX, paintTextY, paintDotted,
            paintToltal, paintSnow;
    private List<Hour> hours;
    private Context mContext;
    private String mToltal, mTitel, mHour, mTemprature;
    private Drawable mIcon;
    private int sizeText34, sizeText12, sizeTotal;
    private float scale = 1f, scrollX = 0f, scrollY = 0f, lastTouchX = 0, lastTouchY = 0;
    private float startXChart, endXChart, startYChart, endYChart;
    private ScaleGestureDetector scaleDetector;
    private GestureDetector gestureDetector;

    public Weather(Context context, List<Hour> hours) {
        super(context);
        mContext = context;
        this.hours = hours;
        sizeText12 = 12;
        sizeText34 = 34;
        sizeTotal = 0;
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        gestureDetector = new GestureDetector(context, new ScrollListener());
        initPaint(context);
        initData(context);
    }

    public Weather(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Weather(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

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
                scrollX += event.getX() - lastTouchX;
                scrollY += event.getY() - lastTouchY;
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                fixScrollBounds();
                invalidate();
                break;
//                case MotionEvent.ACTION_UP:
//                    detectSelectedColumn(event.getX());
//                    break;
        }
        return true;
    }

    private void initPaint(Context context) {
        paintRain = new Paint();
        paintRain.setColor(context.getColor(R.color.blue_81CFFA));
        paintRain.setStyle(Paint.Style.FILL);

        paintWintryMix = new Paint();
        paintWintryMix.setColor(context.getColor(R.color.blue_8185FA));
        paintWintryMix.setStyle(Paint.Style.FILL);

        paintSnow = new Paint();
        paintSnow.setColor(context.getColor(R.color.white));
        paintSnow.setStyle(Paint.Style.FILL);

        paintCoordinate = new Paint();
        paintCoordinate.setColor(context.getColor(R.color.white));
        paintCoordinate.setAlpha(50);
        paintCoordinate.setStrokeWidth(3);

        paintRect = new Paint();
        paintRect.setColor(context.getColor(R.color.white));
        paintRect.setStyle(Paint.Style.FILL_AND_STROKE);
        paintRect.setAlpha(30);

        paintTextX = new Paint();
        paintTextX.setColor(context.getColor(R.color.white));
        paintTextX.setStyle(Paint.Style.FILL);
        paintTextX.setAlpha(100);
        paintTextX.setTextSize(pxToDp(sizeText12));

        paintTextY = new Paint();
        paintTextY.setColor(context.getColor(R.color.white));
        paintTextY.setStyle(Paint.Style.FILL);
        paintTextY.setAlpha(100);
        paintTextY.setTextSize(pxToDp(8));

        paintDotted = new Paint();
        paintDotted.setColor(context.getColor(R.color.white));
        paintDotted.setStyle(Paint.Style.FILL);
        paintDotted.setPathEffect(new DashPathEffect(new float[] {10, 10}, 0));
        paintDotted.setAlpha(50);
        paintDotted.setStrokeWidth(3);

        paintToltal = new Paint();
        paintToltal.setColor(context.getColor(R.color.white));
        paintToltal.setStyle(Paint.Style.FILL);
        paintToltal.setStrokeWidth(5);
        paintToltal.setTextSize(pxToDp(sizeText34));
    }

    @SuppressLint({"DrawAllocation", "UseCompatLoadingForDrawables"})
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        sizeTotal = 0;
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), pxToDp(15), pxToDp(15), paintRect);
        sizeTotal += sizeText34;
        canvas.drawText(mToltal, pxToDp(10), pxToDp(sizeTotal), paintToltal);
        sizeTotal += sizeText12 + 10;
        canvas.drawText(mTitel, pxToDp(10), pxToDp(sizeTotal), paintTextX);
        sizeTotal += 10;
        drawCoordinate(pxToDp(10), pxToDp(sizeTotal), getWidth() - pxToDp(10), getHeight() - pxToDp(40),getWidth() - pxToDp(20), getHeight() - pxToDp(40 + sizeTotal), canvas);
//        sizeTotal = getHeight() - pxToDp(30);
    }

    private void initData(Context context) {
        mToltal = "6 mm";
        mTitel = getContext().getString(R.string.total_in_the_past_24_hours);
        mHour = "";
        mIcon = null;
    }

    private void setDrawable(Drawable icon, Drawable drawable) {
        icon = drawable;
        invalidate();
    }

    private void setText(String s, String text) {
        s = text;
        invalidate();
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
        startXChart = startX + pxToDp(10);
        startYChart = startY + (heightTotal - startY) / 13;
        endXChart = widthTotal;
        endYChart = heightTotal;
        canvas.save();
        canvas.clipRect(startX, startY + (heightTotal - startY) / 13f - 3, endX, heightTotal + 5);
        canvas.scale(1f, scale, lastTouchX, heightTotal);
        for (int i = 0; i <= 12; i += 2) {
            float y = heightTotal - (i * (heightTotal - startY) / 13f) + scrollY;
            if (i != 0) {
                canvas.drawLine(startX, y, widthTotal, y, paintCoordinate);
            }
            canvas.drawText((i == 0) ? i + " mm" : (i != 12) ? i + "" : "", widthTotal + 5, y + 5, paintTextY);
        }
        canvas.restore();
        canvas.save();
        canvas.clipRect(startX, startY, widthTotal, endY);
        canvas.scale(scale, 1f, lastTouchX, heightTotal);
        String label = "";
        for (int i = 0; i < 19; i += 6) {
            float x = startX + pxToDp(10) + scrollX + i * (widthTotal - startX - pxToDp(10)) / 24;
            Log.d("SSSSS", scrollX +", "+x);
            float y = (endY - heightTotal/14);
            if (i != 0) {
                canvas.drawLine(x, startY, x, y, paintDotted);
            }
            label = String.format("%02d", i);
            canvas.drawText(label, x,y + pxToDp(12) + 5, paintTextX);
        }
        canvas.restore();
        canvas.save();
        canvas.clipRect(startX + pxToDp(10), startY + (heightTotal - startY) / 13, widthTotal, heightTotal);
        canvas.scale(scale, scale, lastTouchX, heightTotal);
        canvas.translate(scrollX, scrollY);
        float widthRain;
        float widthWintryMix;
        float heightWintryMix;
        float heightSnow;
        float heightRain;
        float widthSnow;
        float widthChart;
//        for (int i = 0; i < hours.size(); i++) {
//            widthRain = startX + pxToDp(10) + i * (widthTotal - startX - pxToDp(10)) / 24 + scrollX;
//            widthChart = startX + pxToDp(10) + (i + 1) * (widthTotal - startX - pxToDp(10)) / 24 + scrollX;
//            widthWintryMix = widthRain + (widthChart - widthRain) / 3;
//            widthSnow = widthWintryMix + (widthChart - widthRain) / 3;
//            heightRain = heightTotal - (heightTotal - startY) / 13 * hours.get(i).getRain() + scrollY;
//            heightWintryMix = heightTotal - (heightTotal - startY) / 13 * hours.get(i).getWintryMix() + scrollY;
//            heightSnow = heightTotal - (heightTotal - startY) / 13 * hours.get(i).getSnow() + scrollY;
//            canvas.drawRect(widthRain, heightTotal, widthWintryMix, heightRain, paintRain);
//            canvas.drawRect(widthWintryMix, heightTotal, widthSnow, heightWintryMix, paintWintryMix);
//            canvas.drawRect(widthSnow, heightTotal, widthChart, heightSnow, paintSnow);
//        }
        canvas.restore();
        canvas.save();
        canvas.clipRect(startX, startY, widthTotal, startY + (heightTotal - startY)/13);
        canvas.scale(1f, 1f);
        Drawable[] drawables = new Drawable[]{
                mContext.getDrawable(R.drawable.ic_rain),
                mContext.getDrawable(R.drawable.ic_cloud),
                mContext.getDrawable(R.drawable.ic_rain),
                mContext.getDrawable(R.drawable.ic_sun),
                mContext.getDrawable(R.drawable.ic_sun),
                mContext.getDrawable(R.drawable.ic_sun),
                mContext.getDrawable(R.drawable.ic_rain),
                mContext.getDrawable(R.drawable.ic_rain),
                mContext.getDrawable(R.drawable.ic_rain),
                mContext.getDrawable(R.drawable.ic_rain),
                mContext.getDrawable(R.drawable.ic_rain),
                mContext.getDrawable(R.drawable.ic_cloud)
        };
        for (int i = 0; i<12; i++) {
            drawables[i].setBounds((int) ((startX + pxToDp(5) + i * (widthTotal - startX) / 12)), (int) startY + pxToDp(3), (int) (startX + (i+1) * (widthTotal - startX) / 12 - pxToDp(5)), (int) (startY + (heightTotal - startY) / 13 - pxToDp(3)));
            drawables[i].draw(canvas);
        }
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private int pxToDp(int px) {
        return Math.round(px * mContext.getResources().getDisplayMetrics().density);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(1f, Math.min(scale, 3f));
            fixScrollBounds();
            invalidate();
            return true;
        }
    }

    private class ScrollListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            scrollX -= distanceX;
            scrollY -= distanceY;
            fixScrollBounds();
            invalidate();
            return true;
        }
    }

    private void fixScrollBounds() {
        float contentWidth = (endXChart - startXChart) * scale;
        float contentHeight = (endYChart - startYChart) * scale;
        float maxScrollX = Math.max(0, contentWidth - (endXChart - startXChart));
        float maxScrollY = contentHeight - (endYChart - startYChart);
        scrollX = Math.max(-maxScrollX, Math.min(scrollX, 0));
        scrollY = Math.max(0, Math.min(scrollY, maxScrollY));
    }
}






















//
//public class Weather extends View {
//    private Paint paintRain, paintWintryMix, paintCoordinate, paintRect, paintTextX, paintTextY, paintDotted, paintSelectedText;
//    private List<Hour> hours;
//    private Context mContext;
//
//    private float scale = 1f, scaleY = 1f;
//    private final float maxScale = 3f, minScale = 1f;
//    private float scrollX = 0f, scrollY = 0f;
//    private float lastTouchX, lastTouchY;
//    private int selectedIndex = -1;
//
//    private ScaleGestureDetector scaleDetector;
//    private GestureDetector gestureDetector;
//
//    private OnColumnSelectedListener columnSelectedListener;
//
//    public interface OnColumnSelectedListener {
//        void onColumnSelected(int index, String timeLabel);
//    }
//
//    public void setOnColumnSelectedListener(OnColumnSelectedListener listener) {
//        this.columnSelectedListener = listener;
//    }
//
//    public Weather(Context context, List<Hour> hours) {
//        super(context);
//        init(context, hours);
//    }
//
//    public Weather(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init(context, null);
//    }
//
//    public Weather(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context, null);
//    }
//
//    private void init(Context context, List<Hour> hourData) {
//        this.mContext = context;
//        this.hours = hourData;
//
//        paintRain = new Paint();
//        paintRain.setColor(context.getColor(R.color.blue_81CFFA));
//        paintRain.setStyle(Paint.Style.FILL);
//
//        paintWintryMix = new Paint();
//        paintWintryMix.setColor(context.getColor(R.color.blue_8185FA));
//        paintWintryMix.setStyle(Paint.Style.FILL);
//
//        paintCoordinate = new Paint();
//        paintCoordinate.setColor(context.getColor(R.color.white));
//        paintCoordinate.setAlpha(50);
//        paintCoordinate.setStrokeWidth(3);
//
//        paintRect = new Paint();
//        paintRect.setColor(context.getColor(R.color.white));
//        paintRect.setStyle(Paint.Style.FILL);
//        paintRect.setAlpha(30);
//
//        paintTextX = new Paint();
//        paintTextX.setColor(context.getColor(R.color.white));
//        paintTextX.setAlpha(100);
//        paintTextX.setTextSize(pxToDp(12));
//
//        paintTextY = new Paint();
//        paintTextY.setColor(context.getColor(R.color.white));
//        paintTextY.setAlpha(100);
//        paintTextY.setTextSize(pxToDp(8));
//
//        paintDotted = new Paint();
//        paintDotted.setColor(context.getColor(R.color.white));
//        paintDotted.setStyle(Paint.Style.STROKE);
//        paintDotted.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));
//        paintDotted.setAlpha(50);
//        paintDotted.setStrokeWidth(3);
//
//        paintSelectedText = new Paint();
//        paintSelectedText.setColor(context.getColor(R.color.white));
//        paintSelectedText.setTextSize(pxToDp(14));
//        paintSelectedText.setStyle(Paint.Style.FILL);
//
//        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
//        gestureDetector = new GestureDetector(context, new GestureListener());
//    }
//
//    @SuppressLint("DrawAllocation")
//    @Override
//    protected void onDraw(@NonNull Canvas canvas) {
//        super.onDraw(canvas);
//
//        float viewWidth = getWidth();
//        float viewHeight = getHeight();
//        float graphHeight = viewHeight - viewHeight / 14f;
//        float columnWidth = viewWidth / 26f;
//
//        // Trục tọa độ & nền (cố định)
//        canvas.drawRoundRect(0, 0, viewWidth, viewHeight, pxToDp(8), pxToDp(8), paintRect);
//        canvas.drawLine(0, graphHeight, viewWidth, graphHeight, paintCoordinate);
//        canvas.drawLine(viewWidth, 0, viewWidth, graphHeight, paintCoordinate);
//
//        for (int i = 0; i <= 24; i += 6) {
//            float x = columnWidth * i * scale + columnWidth / 2 + scrollX;
//            if (i != 0 && i != 24)
//                canvas.drawLine(x, 0, x, graphHeight, paintDotted);
//
//            if (i != 24) {
//                String label = String.format("%02d", i);
//                canvas.drawText(label, x, graphHeight + pxToDp(12), paintTextX);
//            }
//        }
//
//        for (int i = 0; i <= 12; i += 2) {
//            float y = graphHeight - (i * getHeight() / 14f * scaleY) + scrollY;
//            canvas.drawLine(0, y, viewWidth, y, paintCoordinate);
//            canvas.drawText(i + " mm", viewWidth - pxToDp(30), y + 5, paintTextY);
//        }
//
//        // Biểu đồ có scroll/scale
//        canvas.save();
//        canvas.translate(scrollX, scrollY);
//        canvas.scale(scale, scaleY, 0, graphHeight);
//
//        if (hours != null) {
//            for (int i = 0; i < hours.size(); i++) {
//                float baseX = columnWidth * i + columnWidth / 2;
//                float rainTop = graphHeight - getHeight() / 14f * hours.get(i).getRain();
//                float mixTop = graphHeight - getHeight() / 14f * hours.get(i).getWintryMix();
//
//                float widthRain = baseX;
//                float widthMix = widthRain + columnWidth / 3;
//                float widthSnow = widthMix + columnWidth / 3;
//
//                canvas.drawRect(widthRain, graphHeight, widthMix, rainTop, paintRain);
//                canvas.drawRect(widthMix, graphHeight, widthSnow, mixTop, paintWintryMix);
//            }
//        }
//
//        canvas.restore();
//
//        // Line chỉ mục
//        if (selectedIndex >= 0 && selectedIndex < (hours == null ? 0 : hours.size())) {
//            float x = columnWidth * selectedIndex * scale + columnWidth / 2 + scrollX;
//            canvas.drawLine(x, 0, x, getHeight(), paintCoordinate);
//            String label = String.format("%02d:00", selectedIndex);
//            canvas.drawText(label, x - pxToDp(10), pxToDp(20), paintSelectedText);
//        }
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        scaleDetector.onTouchEvent(event);
//        gestureDetector.onTouchEvent(event);
//
//        if (!scaleDetector.isInProgress()) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    lastTouchX = event.getX();
//                    lastTouchY = event.getY();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    float dx = event.getX() - lastTouchX;
//                    float dy = event.getY() - lastTouchY;
//                    scrollX += dx;
//                    scrollY += dy;
//                    lastTouchX = event.getX();
//                    lastTouchY = event.getY();
//                    fixScrollBounds();
//                    invalidate();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    detectSelectedColumn(event.getX());
//                    break;
//            }
//        }
//        return true;
//    }
//
//    private void detectSelectedColumn(float x) {
//        float columnWidth = getWidth() / 26f;
//        float adjustedX = (x - scrollX) / scale;
//        int index = (int) (adjustedX / columnWidth);
//        if (index >= 0 && index < (hours == null ? 0 : hours.size())) {
//            selectedIndex = index;
//            invalidate();
//
//            if (columnSelectedListener != null) {
//                String label = String.format("%02d:00", index);
//                columnSelectedListener.onColumnSelected(index, label);
//            }
//        }
//    }
//
//    private void fixScrollBounds() {
//        float contentWidth = (hours == null ? 0 : hours.size()) * getWidth() / 26f * scale;
//        float contentHeight = getHeight() * scaleY;
//        float maxScrollX = Math.max(0, contentWidth - getWidth());
//        float maxScrollY = Math.max(0, contentHeight - getHeight());
//
//        scrollX = Math.max(-maxScrollX, Math.min(scrollX, 0));
//        scrollY = Math.max(-maxScrollY, Math.min(scrollY, 0));
//    }
//
//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            scale *= detector.getScaleFactor();
//            scaleY *= detector.getScaleFactor();
//            scale = Math.max(minScale, Math.min(scale, maxScale));
//            scaleY = Math.max(minScale, Math.min(scaleY, maxScale));
//            fixScrollBounds();
//            invalidate();
//            return true;
//        }
//    }
//
//    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            scrollX -= distanceX;
//            scrollY -= distanceY;
//            fixScrollBounds();
//            invalidate();
//            return true;
//        }
//    }
//
//    private int pxToDp(int px) {
//        return Math.round(px * mContext.getResources().getDisplayMetrics().density);
//    }
//}
