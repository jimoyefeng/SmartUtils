package com.example.user.testkotlin.whgetview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class Firstview extends View {
    Paint paint = new Paint();
    Path path = new Path(); // 初始化 Path 对象
    public Firstview(Context context) {
        super(context);
    }

    public Firstview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Firstview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setAntiAlias(true);
//        // 绘制一个圆
////        canvas.drawCircle(300, 300, 200, paint);
//        RectF rectf_head = new RectF(0, 0, 140, 140);//确定外切矩形范围
////        rectf_head.offset(100, 20);//使rectf_head所确定的矩形向右偏移100像素，向下偏移20像素
//        canvas.drawArc(rectf_head, 270, 90, true, paint);
//
//        rectf_head.offset(0, 5);//使rectf_head所确定的矩形向右偏移100像素，向下偏移20像素
//        canvas.drawArc(rectf_head, 0, 120, true, paint);

        canvas.drawColor(Color.parseColor("#88880000"));

        // 使用 path 对图形进行描述（这段描述代码不必看懂）
//        path.addArc(200, 200, 400, 400, -225, 225);
//        path.arcTo(400, 200, 600, 400, -180, 225, false);
//        path.lineTo(400, 542);
        path.lineTo(100,100);
        canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
