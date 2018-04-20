package com.isoftston.issuser.fusioncharge.weights;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by issuser on 2018/4/20.
 */

public class OvalView extends View {

    public OvalView(Context context) {
        super(context);
    }

    public OvalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 创建画笔
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        RectF rectF = new RectF(60, 100, 200, 240);
        rectF.set(0,0,getWidth(),getHeight());
        canvas.drawOval(rectF, p);
    }
}
