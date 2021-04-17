package com.galab_rotemle.ex2;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;


public class BrickCollection {
    private static final int MAX_ROWS = 3; //6
    private static final int MAX_COLUMNS = 13; //7
    private static final int MIN_ROWS = 3; //2
    private static final int MIN_COLUMNS = 13; //3
    private float width, height;
    private int rows,columns, numberOfBricks;
    private float brickWidth,brickHeight; //TODO: set the size of the brick - width and height

    private Brick[][] bricks;

    public BrickCollection(float width,float height){
        this.height = height;
        this.width = width;
        // TODO: fix this row column problem
        this.columns = (int)(Math.random()*(MAX_ROWS+1-MIN_ROWS))+MIN_ROWS;
        this.rows = (int)(Math.random()*(MAX_COLUMNS+1-MIN_COLUMNS))+MIN_COLUMNS;
        this.bricks = new Brick[this.rows][this.columns];
        this.numberOfBricks = this.rows * this.columns;
        Log.i("test",this.rows + " - " + this.columns);


        float x1=0,x2=0,y1=0,y2=0;

        for(int i=0;i<this.rows;i++){
            x1 = x2 + 5;
            x2 = (((this.width-5)/this.rows)*(i+1));
            y2 =250;
            for (int j=0;j<this.columns;j++){
                y1 = y2 + 5;
                y2 = (250 + (this.height/20)*(j+1));
                this.bricks[i][j] = new Brick( x1, x2, y1, y2);
            }
        }
        this.brickHeight = this.height/20;
        this.brickWidth = x2-x1;
        Log.i("test",this.brickHeight + " - " + this.brickWidth);
    }

    public void draw(Canvas canvas){
        for(int i=0;i<this.rows;i++){
            for (int j=0;j<this.columns;j++){
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

    public int getRows() { return  rows; }

    public int getColumns() { return  columns; }
    public int removeBrick() {
        this.numberOfBricks--;
        return this.numberOfBricks;
    }
    public Brick[][] getBrick() {
        return bricks;
    }

}
