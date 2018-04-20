package com.isoftston.issuser.fusioncharge.weights;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.isoftston.issuser.fusioncharge.R;

/**
 * Created by issuser on 2018/4/20.
 */

public class LoadingView extends View{

    //view的默认宽度
    private int mWidth;
    //view的默认高度
    private int mHeight;
    //线条粗细
    private int paintBold;
    //线条长度
    private int lineLength;
    //背景线条颜色
    private int bgPaintColor;
    //上层线条颜色
    private int beforePaintColor;
    //进度文字颜色
    private int textColor;
    //线条个数
    private int lines;
    //背景画笔
    private Paint bgPaint;
    //前景画笔
    private Paint bfPaint;
    //进度文字画笔
    private Paint textPaint;
    //当前进度
    private int progress;
    /*最大进度*/
    private int max;

    public LoadingView(Context context) {
        super(context);
        init();
        initPaint();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initPaint();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initPaint();
    }

    private void init(){
                paintBold =10;
        lineLength = 25;
        bgPaintColor = Color.GRAY;
        beforePaintColor = Color.YELLOW;
        lines =  20;
        max = 100;
        progress =  10;
        textColor = Color.BLACK;
    }
    /**
     * 加载我们在attrs.xml文件的自定义的属性
     */
    private void loadAttrs(Context context, AttributeSet attrs) {
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.a_zhon);
//        paintBold = array.getDimensionPixelSize(R.styleable.a_zhon_paintBold, 10);
//        lineLength = array.getDimensionPixelSize(R.styleable.a_zhon_lineLength, 25);
//        bgPaintColor = array.getColor(R.styleable.a_zhon_backgroundColor, Color.GRAY);
//        beforePaintColor = array.getColor(R.styleable.a_zhon_beforeColor, Color.YELLOW);
//        lines = array.getInt(R.styleable.a_zhon_lines, 20);
//        max = array.getInt(R.styleable.a_zhon_max, 100);
//        progress = array.getInt(R.styleable.a_zhon_progress, 0);
//        textColor = array.getColor(R.styleable.a_zhon_textColor, Color.BLACK);
//        array.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(getResources().getColor(R.color.text_gray));
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(paintBold);
        //使得画笔更加圆滑
        bgPaint.setStrokeJoin(Paint.Join.ROUND);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        bfPaint = new Paint();
        bfPaint.setColor(getResources().getColor(R.color.app_blue));
        bfPaint.setAntiAlias(true);
        bfPaint.setStrokeWidth(paintBold);
        bfPaint.setStrokeJoin(Paint.Join.ROUND);
        bfPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40);
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
        super.onDraw(canvas);
        int x =mWidth/2;
        int y=mHeight/2;
        int r=x-5;
        for (int i = 0; i < lines; i++) {
            //绘制下层菊花
            canvas.drawLine(x, y - r, x, y - r + lineLength, bgPaint);
            canvas.rotate(360 / lines, x, y);
        }

        //获取需要绘制多少个刻度

        int count = (progress * lines) / max;
        //绘制中间的文字进度
//        canvas.drawText((progress * 100 / max) + "%", x, y + 5, textPaint);
        //绘制上层菊花,也就是进度
        canvas.rotate(360 / lines, x, y);
        for (; count > 0; count--) {
            canvas.drawLine(x, y - r, x, y - r + lineLength, bfPaint);
            canvas.rotate(360 / lines, x, y);
        }
    }

    public void startAnimation(int start, int current, int duration) {
        ValueAnimator progressAnimator = ValueAnimator.ofInt(start, current);
        progressAnimator.setDuration(duration);
//        progressAnimator.setTarget(progress);
        progressAnimator.setRepeatCount(2);
        progressAnimator.setRepeatMode(ValueAnimator.REVERSE);
        progressAnimator.setInterpolator(new BounceInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e("yzh","progress---"+progress);
                progress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }

    /*设置进度最大值*/
    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    /*设置当前进度*/
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
