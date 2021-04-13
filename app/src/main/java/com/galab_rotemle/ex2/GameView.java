package com.galab_rotemle.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;



public class GameView extends View {


    private static final int GET_READY = 1;
    private static final int PLAYING = 2;
    private static final int GAME_OVER = 3;
    private Paint score;
    private Paint lives;
    private Paint life,lifeIn,lifeOut;
    private Paint [] deaths;
    private Thread ballThread;

    private int scoreNum,lifesNumber, state;

    private int height,width;
    private float tempX;
    private BrickCollection bricks;
    private Ball ball;
    private Paddle paddle;
    private boolean toched, isRun;
    private float brickWidth,brickHeight;
    private Paint startGame, finishGame;
    private Thread thread;

    public GameView(Context context,AttributeSet attrs) {
        super(context, attrs);
        state =1;
        scoreNum=0;

        toched = false;
        lifesNumber = 3;
        score = new Paint();
        score.setColor(Color.GREEN);
        score.setTextSize(75);

        lives = new Paint();
        lives.setColor(Color.GREEN);
        lives.setTextSize(75);
        lives.setTextAlign(Paint.Align.RIGHT);

        lifeIn = new Paint();
        lifeIn.setColor(Color.WHITE);
        lifeIn.setTextSize(70);
        lifeIn.setTextAlign(Paint.Align.RIGHT);

        lifeOut = new Paint();
        lifeOut.setColor(Color.rgb(66,66,66));
        lifeOut.setTextSize(70);
        lifeOut.setTextAlign(Paint.Align.RIGHT);

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
        startGame.setTextAlign(Paint.Align.CENTER);

        finishGame = new Paint();
        finishGame.setColor(Color.GREEN);
        finishGame.setTextSize(75);
        finishGame.setTextAlign(Paint.Align.CENTER);

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
            if(i<(this.deaths.length - this.lifesNumber))
                this.deaths[i].setColor(Color.parseColor("#434343"));
            else
                this.deaths[i].setColor(Color.parseColor("#ffffff"));
        }

        // draw the bricks collection
        this.bricks.draw(canvas);

        // draw the ball and the paddle
        this.ball.draw(canvas);
        this.paddle.draw(canvas);

        switch (state){
            case GET_READY:
                //when the player start to play
                canvas.drawText("Click to PLAY!",this.width/2,this.height/2,this.startGame);
                break;
            case PLAYING:
                break;
            case GAME_OVER:
                //when the player finish to play
                canvas.drawText("GAME OVER - You Loss!",this.width/2,this.height/2,this.startGame);
                break;
        }

        if(state == PLAYING)
            if(toched){
                this.paddle.movePaddle(tempX,this.width);
                invalidate();
            }

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
        this.paddle = new Paddle((float)this.width/2-brickWidth/2,(float)this.width/2+brickWidth/2,(float)this.height-150-this.brickHeight/2,(float)this.height-150);
        this.ball = new Ball(this.width/2,this.height-150-brickHeight,brickHeight/2);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.tempX = event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(state == GET_READY){
                    startBallMovement();
                    state = PLAYING;
                }
                if(state == PLAYING)
                    this.toched = true;
                if(state == GAME_OVER) {
                    state = GET_READY;
                    prepareNewGame();
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if(state == PLAYING)
                    this.toched = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if(state == PLAYING)
                    this.toched = false;
                Log.d("myLog", "onTouchEvent UP: ");
                break;
        }


        return true;
    }

    public void startBallMovement()
    {
        isRun = true;
        ballThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(isRun)
                {
                    // update Hands
                    SystemClock.sleep(3);
                    moveBall(width, height);
                    // call to onDraw() from Thread
                    postInvalidate();

                }
            }
        });
        ballThread.start();
    }

    public void stopBall()
    {
        isRun = false;
    }
    private void moveBall(int w, int h)
    {
        this.ball.moveBall(w, h);
        if( this.ball.getyCenter()== h - 185 && this.ball.getxCenter() > this.paddle.getX1() && this.ball.getxCenter() < this.paddle.getX2()){
            this.ball.switchYDirection();
        }
        // strike
        if(this.ball.getyCenter() > h - this.ball.getRadius()) {
            if(lifesNumber == 1){
                lifesNumber --;
                state=GAME_OVER;
            }
            else {
                lifesNumber --;
                state=GET_READY;
                resetLocations();
            }
            isRun = false;
        }
        didTouchBrick();

    }

    private void didTouchBrick() {
        for (int i=0; i<this.bricks.getRows(); i++)
            for (int j=0; j < this.bricks.getColumns(); j++) {
                if(ball.getxCenter() >= bricks.getBrick()[i][j].getX1() && ball.getxCenter() <= bricks.getBrick()[i][j].getX2()
                && ball.getyCenter() >= bricks.getBrick()[i][j].getY1()  && ball.getyCenter() <= bricks.getBrick()[i][j].getY2()
                && !bricks.getBrick()[i][j].isBroke()) {
                    // check hit direction
                    if(ball.getxCenter() == bricks.getBrick()[i][j].getX1() || ball.getxCenter() == bricks.getBrick()[i][j].getX2())
                        ball.switchXDirection();
                    else if(ball.getyCenter() == bricks.getBrick()[i][j].getY1() || ball.getyCenter() == bricks.getBrick()[i][j].getY2())
                        ball.switchYDirection();
                    bricks.getBrick()[i][j].setBrickBreak(true);
                    scoreNum += 5*lifesNumber;

                    // Finish game
                    if(bricks.removeBrick() == 0)
                        prepareNewGame();

                }
            }
    }
    // place the paddle and the ball on the center
    private void resetLocations ()  {
        paddle.setX1((float)this.width/2-brickWidth/2);
        paddle.setX2((float)this.width/2+brickWidth/2);
        paddle.setY1((float)this.height-150-this.brickHeight/2);
        paddle.setY2((float)this.height-150);
        ball.setxCenter(this.width/2);
        ball.setyCenter(this.height-150-brickHeight);
    }

    private void prepareNewGame() {
        state=GET_READY;
        resetLocations();
        isRun = false;
        scoreNum = 0;
        lifesNumber=3;
        this.bricks = new BrickCollection(this.width,this.height);

    }
}




