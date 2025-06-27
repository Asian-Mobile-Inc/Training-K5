package com.example.asian.issue9.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asian.R;
import com.example.asian.issue9.model.Hour;

import java.util.List;

public class Weather extends View {
    private Paint paintRain, paintWintryMix, paintCoordinate;
    private List<Hour> hours;

    public Weather(Context context, List<Hour> hours) {
        super(context);
        paintRain = new Paint();
        paintRain.setColor(context.getColor(R.color.blue_81CFFA));
        paintRain.setStyle(Paint.Style.FILL);
        paintWintryMix = new Paint();
        paintWintryMix.setColor(context.getColor(R.color.blue_8185FA));
        paintWintryMix.setStyle(Paint.Style.FILL);
        paintCoordinate = new Paint();
        paintCoordinate.setColor(context.getColor(R.color.gray));
        paintCoordinate.setAlpha(20);
        paintCoordinate.setStrokeWidth(10);
        this.hours = hours;
    }

    public Weather(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Weather(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paintCoordinate);
        canvas.drawLine(getWidth(), getHeight(), getWidth(), 0, paintCoordinate);
        float widthChart = getWidth() - 5;
        float heightChart = getHeight() - 5;
        for (int i = 0; i<hours.size(); i++) {
            canvas.drawRect((float) (i * widthChart) / 24, heightChart - (float) heightChart / 12 * hours.get(i).getRain(), (float) (i * widthChart) / 24 + (float) widthChart / 24 / 4, heightChart, paintRain);
            canvas.drawRect((float) (i * widthChart) / 24 + (float) widthChart / 24 / 4, heightChart - (float) heightChart / 12 * hours.get(i).getWintryMix(), (float) (i * widthChart) / 24 + 2* ((float) widthChart / 24 / 4), heightChart, paintWintryMix);
        }
        for (int i = 0; i<hours.size(); i++)
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
