package com.galab_rotemle.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Paddle {
    private float x1,x2,y1,y2, direction;
    private Paint paddle;


    public Paddle(float x1, float x2, float y1, float y2){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.direction = 1;

        this.paddle = new Paint();
        this.paddle.setColor(Color.BLUE);
        this.paddle.setStyle(Paint.Style.FILL);
    }
    public void draw(Canvas canvas){
        canvas.drawRect(this.x1, this.y1, this.x2, this.y2, this.paddle);
    }

    public float getX1() {
        return x1;
    }

    public float getX2() {
        return x2;
    }

    public float getY1() {
        return y1;
    }

    public float getY2() {
        return y2;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getDirection() {
        return direction;
    }

    public void movePaddle(float x,float width){
        if(x>=width/2){
            if(x2<width) {
                this.x1 += 10;
                this.x2 += 10;
                this.direction = 1;
            }
        }else{
            if(x1>0){
                this.x1 -= 10;
                this.x2 -= 10;
                this.direction = -1;
            }

        }
    }
}
