package com.galab_rotemle.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Ball {
    private float xCenter,yCenter,radius, dx, dy;
    private Paint ball;


    public Ball(float xCenter, float yCenter, float radius){
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius;
        this.dy = -1;
         this.dx = -1;;
        setRandomDx();
        this.ball = new Paint();
        this.ball.setColor(Color.WHITE);
        this.ball.setStyle(Paint.Style.FILL);
    }
    public void draw(Canvas canvas){
        canvas.drawCircle(this.xCenter,this.yCenter,this.radius, this.ball);
        Log.d("BallDraw", "draw ball at: x " + this.xCenter + " y " + this.yCenter);
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

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setRandomDx() {
        double range = Math.random()*(2) -1;
        if(range < 0)
            range = -1;
        else
            range = 1;
//        this.dx = ((int)(Math.random()*(3))+1) * (float)range;
        this.dx = 3;
    }

    public boolean inBallRange(float x, float y) {
        Log.d("rotemLog", "inBallRange radius: " + this.getRadius());

        Log.d("rotemLog", "inBallRange test: " + (Math.sqrt(Math.pow(x - this.getxCenter(), 2)+ Math.pow(y - this.getyCenter(),2)) ));
        return (Math.sqrt(Math.pow(x - this.getxCenter(), 2)+ Math.pow(y - this.getyCenter(),2)) <= this.getRadius());
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

    public void switchYDirection() {
        dy = -dy;
    }
    public void switchXDirection() {
        dx = -dx;
    }
    public void moveBall(float width,float height){
        this.xCenter = this.xCenter + dx;
        this.yCenter = this.yCenter + dy;

        if(xCenter-radius<0 || xCenter+radius>width)
            dx=-dx;

        if(yCenter+radius>height || yCenter-radius<150)
            dy = -dy;
    }
}
