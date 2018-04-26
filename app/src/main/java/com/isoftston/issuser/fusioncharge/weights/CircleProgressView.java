package com.isoftston.issuser.fusioncharge.weights;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.isoftston.issuser.fusioncharge.R;

/**
 * Created by issuser on 2018/4/22.
 */

public class CircleProgressView extends View{


    private int mWidth;
    private int mHeight;
    /**
     * 画笔对象的引用
     */
    private Paint paint;
    /**
     * 圆环的颜色
     */
    private int roundColor;
    //圆环进度的颜色
    private int roundProgressColor;
    //圆环的宽度
    private float roundWidth;
    //最大进度
    private long max;
    //当前进度
    private long progress;

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CircleProgressView(Context context) {
        super(context);
        init();
    }

    private void init(){
        roundColor=getContext().getResources().getColor(R.color.line);
        roundProgressColor=getContext().getResources().getColor(R.color.app_blue);
        roundWidth=4;
        max=100;
        progress=0;

        paint=new Paint();
    }

    public void setMax(long max){
        this.max=max;
    }
    public void setProgress(long progress){
        this.progress=progress;
        invalidate();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=getViewSize(100,widthMeasureSpec);
        mHeight=getViewSize(100,heightMeasureSpec);
    }

    private int getViewSize(int defaultSize, int measureSpec) {
        int viewSize = defaultSize;
        //获取测量模式
        int mode = MeasureSpec.getMode(measureSpec);
        //获取大小
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: //如果没有指定大小，就设置为默认大小
                viewSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST: //如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                viewSize = size;
                break;
            case MeasureSpec.EXACTLY: //如果是固定的大小，那就不要去改变它
                viewSize = size;
                break;
        }
        return viewSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //画最外层的大圆环
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);
        canvas.drawCircle(mWidth/2,mHeight/2,mWidth/2-10,paint);

        //画圆弧
        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundProgressColor);
        RectF oval = new RectF(10,10,mWidth-10,mHeight-10);  //用于定义的圆弧的形状和大小的界限

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, 270, 360 * progress / max, false, paint);  //根据进度画圆弧

        // 画圆上的两个点
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);
        PointF startPoint = calcArcEndPointXY(mWidth/2, mHeight/2, mWidth/2-10, 360 * progress / max, 270);
        canvas.drawCircle(startPoint.x, startPoint.y, 10, paint);
    }
    public  PointF calcArcEndPointXY(float cirX, float cirY, float radius, float cirAngle){
        float posX = 0.0f;
        float posY = 0.0f;
        //将角度转换为弧度
        float arcAngle = (float) (Math.PI * cirAngle / 180.0);
        if (cirAngle < 90)
        {
            posX = cirX + (float)(Math.cos(arcAngle)) * radius;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius;
        }
        else if (cirAngle == 90)
        {
            posX = cirX;
            posY = cirY + radius;
        }
        else if (cirAngle > 90 && cirAngle < 180)
        {
            arcAngle = (float) (Math.PI * (180 - cirAngle) / 180.0);
            posX = cirX - (float)(Math.cos(arcAngle)) * radius;
            posY = cirY + (float)(Math.sin(arcAngle)) * radius;
        }
        else if (cirAngle == 180)
        {
            posX = cirX - radius;
            posY = cirY;
        }
        else if (cirAngle > 180 && cirAngle < 270)
        {
            arcAngle = (float) (Math.PI * (cirAngle - 180) / 180.0);
            posX = cirX - (float)(Math.cos(arcAngle)) * radius;
            posY = cirY - (float)(Math.sin(arcAngle)) * radius;
        }
        else if (cirAngle == 270)
        {
            posX = cirX;
            posY = cirY - radius;
        }
        else
        {
            arcAngle = (float) (Math.PI * (360 - cirAngle) / 180.0);
            posX = cirX + (float)(Math.cos(arcAngle)) * radius;
            posY = cirY - (float)(Math.sin(arcAngle)) * radius;
        }
        return new PointF(posX, posY);
    }

    public  PointF calcArcEndPointXY(float cirX, float cirY, float radius, float cirAngle, float orginAngle){
        cirAngle = (orginAngle + cirAngle) % 360;
        return calcArcEndPointXY(cirX, cirY, radius, cirAngle);
    }
}
