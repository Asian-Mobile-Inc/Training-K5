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
    private Paint paintRain, paintWintryMix;
    private List<Hour> hours;

    public Weather(Context context, List<Hour> hours) {
        super(context);
        paintRain = new Paint();
        paintRain.setColor(context.getColor(R.color.blue_81CFFA));
        paintRain.setStyle(Paint.Style.FILL);
        paintWintryMix = new Paint();
        paintWintryMix.setColor(context.getColor(R.color.blue_8185FA));
        paintWintryMix.setStyle(Paint.Style.FILL);
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
        for (int i = 0; i<hours.size(); i++) {
            canvas.drawRect((float) (i * getWidth()) / 24, getHeight() - (float) getHeight() / 12 * hours.get(i).getRain(), (float) (i * getWidth()) / 24 + (float) getWidth() / 24 / 4, getHeight(), paintRain);
            canvas.drawRect((float) (i * getWidth()) / 24 + (float) getWidth() / 24 / 4, getHeight() - (float) getHeight() / 12 * hours.get(i).getRain(), (float) (i * getWidth()) / 24 + (float) getWidth() / 24 / 4, getHeight(), paintWintryMix);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
