package com.galab_rotemle.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Brick {
    private float x1,x2,y1,y2;
    private Paint brick;
    private boolean brickBreak;


    public Brick(float x1, float x2, float y1, float y2){
        this.brickBreak = false;

        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

        this.brick = new Paint();
        this.brick.setColor(Color.RED);
        this.brick.setStyle(Paint.Style.FILL);
    }
    public void draw(Canvas canvas){
        if(!brickBreak)
            canvas.drawRect(this.x1, this.y1, this.x2, this.y2, this.brick);
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

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public void setBrickBreak(boolean brickBreak) {
        this.brickBreak = brickBreak;
    }

    public boolean isBroke() {
        return brickBreak;
    }

}
