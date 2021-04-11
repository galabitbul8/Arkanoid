package com.galab_rotemle.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {
    private float xCenter,yCenter,radius;
    private Paint ball;


    public Ball(float xCenter, float yCenter, float radius){
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius;

        this.ball = new Paint();
        this.ball.setColor(Color.WHITE);
        this.ball.setStyle(Paint.Style.FILL);
    }
    public void draw(Canvas canvas){
        canvas.drawCircle(this.xCenter,this.yCenter,this.radius, this.ball);
    }

    public float getxCenter() {
        return xCenter;
    }

    public float getyCenter() {
        return yCenter;
    }

    public float getRadius() {
        return radius;
    }

    public void setxCenter(float xCenter) {
        this.xCenter = xCenter;
    }

    public void setyCenter(float yCenter) {
        this.yCenter = yCenter;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
