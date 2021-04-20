package com.galab_rotemle.ex2;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;


public class BrickCollection {
    private static final int MAX_ROWS = 6;
    private static final int MAX_COLUMNS = 7;
    private static final int MIN_ROWS = 2;
    private static final int MIN_COLUMNS = 3;
    private float width, height;
    private int rows,columns, numberOfBricks,bricks_rows, bricks_cols ;
    private float brickWidth,brickHeight;

    private Brick[][] bricks;

    public BrickCollection(float width,float height, int bricks_cols, int bricks_rows){
        this.height = height;
        this.width = width;
        this.bricks_cols = bricks_cols;
        this.bricks_rows = bricks_rows;

//        this.columns = (int)(Math.random()*(MAX_ROWS+1-MIN_ROWS))+MIN_ROWS;
//        this.rows = (int)(Math.random()*(MAX_COLUMNS+1-MIN_COLUMNS))+MIN_COLUMNS;
        this.bricks = new Brick[bricks_rows][bricks_cols];
        this.numberOfBricks = this.bricks_rows * this.bricks_cols;


        float x1=0,x2=0,y1=0,y2=0;

        for(int i=0;i<this.bricks_rows;i++){
            x1 = x2 + 5;
            x2 = (((this.width-5)/this.bricks_rows)*(i+1));
            y2 =250;
            for (int j=0;j<this.bricks_cols;j++){
                y1 = y2 + 5;
                y2 = (250 + (this.height/20)*(j+1));
                this.bricks[i][j] = new Brick( x1, x2, y1, y2);
            }
        }
        this.brickHeight = this.height/20;
        this.brickWidth = x2-x1;
    }

    public void draw(Canvas canvas){
        for(int i=0;i<this.bricks_rows;i++){
            for (int j=0;j<this.bricks_cols;j++){
                this.bricks[i][j].draw(canvas);
            }
        }
    }

    public float getBrickHeight() {
        return brickHeight;
    }

    public float getBrickWidth() {
        return brickWidth;
    }

    public int getRows() { return  this.bricks_rows; }

    public int getColumns() { return  this.bricks_cols; }
    public int removeBrick() {
        this.numberOfBricks--;
        return this.numberOfBricks;
    }
    public Brick[][] getBrick() {
        return bricks;
    }

}
