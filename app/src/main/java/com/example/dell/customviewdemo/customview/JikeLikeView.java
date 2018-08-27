package com.example.dell.customviewdemo.customview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class JikeLikeView extends View {

    private Paint paint;
    private String content = "123";
    private String contentNext = "124";
    private int offsetX = 250;
    private int offsetY = 500;
    private float textSize = 50;
    private Rect rectMove;
    private Rect rectStatic;
    private float contentWidth;
    private float lineSpace;
    private float moveWidth;
    private float staticWidth;

    private float progressY;

    public int getAlphaInt() {
        return alphaInt;
    }

    public void setAlphaInt(int alphaInt) {
        this.alphaInt = alphaInt;
    }

    private int alphaInt;
    private ObjectAnimator animator;

    public float getProgressY() {
        return progressY;
    }

    public void setProgressY(float progressY) {
        this.progressY = progressY;
        invalidate();
    }

    public JikeLikeView(Context context) {
        this(context, null);
    }

    public JikeLikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JikeLikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public JikeLikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        paint = new Paint();
        rectMove = new Rect();
        rectStatic = new Rect();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.reset();
        paint.setTextSize(textSize);
//        canvas.drawText(content, offsetX, offsetY, paint);

        moveWidth = paint.measureText(content, content.length() - 1, content.length());
        staticWidth = paint.measureText(content, 0, content.length() - 1);

        //内容宽度
        contentWidth = paint.measureText(content);
        //行高
        lineSpace = paint.getFontSpacing();
        //静止的那一块
        paint.getTextBounds(content, 0, content.length() - 1, rectStatic);
        //动的那一块
        paint.getTextBounds(content, content.length() - 1, content.length(), rectMove);

        rectMove.left = (int) (contentWidth - moveWidth + offsetX);
        rectMove.top = (int) (offsetY - lineSpace * 2);
        rectMove.right = (int) (contentWidth + offsetX);
        rectMove.bottom = (int) (offsetY + lineSpace);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(rectMove, paint);

        rectStatic.left = offsetX;
        rectStatic.top = (int) (offsetY - lineSpace * 2);
        rectStatic.right = (int) (offsetX + staticWidth);
        rectStatic.bottom = (int) (offsetY + lineSpace);

        canvas.save();
        canvas.clipRect(rectStatic);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(content, offsetX, offsetY, paint);
        canvas.restore();

        canvas.save();
        canvas.clipRect(rectMove);
        canvas.translate(0, -lineSpace * progressY / 100);
        paint.setStyle(Paint.Style.FILL);
        //设置要被顶掉的数字的透明度
        paint.setAlpha(255 - alphaInt);
        canvas.drawText(content, offsetX, offsetY, paint);
        //设置下一个到来的数字的透明度
        paint.setAlpha(alphaInt);
        canvas.drawText(contentNext, offsetX, offsetY + lineSpace, paint);
        canvas.restore();


    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}