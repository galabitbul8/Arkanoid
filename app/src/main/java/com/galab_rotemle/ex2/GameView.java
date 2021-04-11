package com.galab_rotemle.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;



public class GameView extends View {
    private static int GET_READY = 1;
    private static int PLAYING = 2;
    private static int GAME_OVER = 3;
    private Paint score;
    private Paint lives;
    private Paint life;
    private Paint [] deaths;

    private int scoreNum, strikes;
    private int height,width;
    private BrickCollection bricks;
    private Ball ball;
    private Paddle paddle;
    private float brickWidth,brickHeight;
    private Paint startGame, finishGame;

    public GameView(Context context,AttributeSet attrs) {
        super(context, attrs);
        scoreNum=0;
        strikes=0;
        score = new Paint();
        score.setColor(Color.GREEN);
        score.setTextSize(75);

        lives = new Paint();
        lives.setColor(Color.GREEN);
        lives.setTextSize(75);
        lives.setTextAlign(Paint.Align.RIGHT);

        // TODO: fill the life with white color when the player have the life and remove white and put שקוף if the player loss the life he had
        life = new Paint();
        life.setColor(Color.GREEN);

        deaths = new Paint[3];
        for (int i=0; i<3; i++) {
            deaths[i] = new Paint();
            deaths[i].setColor(Color.WHITE);
        }


        startGame = new Paint();
        startGame.setColor(Color.GREEN);
        startGame.setTextSize(75);

        finishGame = new Paint();
        finishGame.setColor(Color.GREEN);
        finishGame.setTextSize(75);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the upper view - score and lives
        canvas.drawText("Score: "+scoreNum,50,100,score);
        canvas.drawText("Lives: ",getWidth()-(3*70)- 20,100,lives);
        canvas.drawCircle(getWidth() - 40,80,30,life);
        canvas.drawCircle(getWidth() - 110,80,30,life);
        canvas.drawCircle(getWidth() - 180,80,30,life);

        // Fill the lives remaining with white
        for (int i=0; i<deaths.length; i++) {
            canvas.drawCircle(getWidth() - 180 + 70*i,80,25,deaths[i]);
            if(i<(this.deaths.length - this.strikes))
                this.deaths[i].setColor(Color.parseColor("#434343"));
        }

        // draw the bricks collection
        this.bricks.draw(canvas);

        // draw the ball and the paddle
        this.ball.draw(canvas);
        this.paddle.draw(canvas);

        //when the player start to play TODO:put the text in the middle when the playar start play
//        canvas.drawText("Click to PLAY!",this.width/2,this.height/2,this.startGame);
        //when the player finish to play
//        canvas.drawText("GAME OVER - You Loss!",this.width/2,this.height/2,this.startGame);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.height = getHeight();
        this.width = getWidth();

        this.bricks = new BrickCollection(this.width,this.height);
        this.brickWidth =  this.bricks.getBrickWidth();
        this.brickHeight = this.bricks.getBrickHeight();

        //TODO: fix size of paddle and of the ball
        this.paddle = new Paddle(this.width/2-brickWidth/2,this.height-150-this.brickHeight,this.width/2+this.brickWidth/2, this.height-150);
        this.ball = new Ball(this.width/2,this.height-150-this.brickHeight-this.brickHeight,this.brickHeight/2);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float tempX = event.getX();
        float tempY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }


    }


}


