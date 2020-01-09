package com.example.petgame.Training;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

/**
 * Creates the view of the game
 * Responsible for player interactions.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    GameThread gameThread;

    private GestureDetector mDetector;
    OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // pass the events to the gesture detector
            // a return value of true means the detector is handling it
            // a return value of false means the detector didn't
            // recognize the event
            return mDetector.onTouchEvent(event);
        }
    };

    //Customer view class
    public GameView(Context context) {
        super(context);
        initView();
        mDetector = new GestureDetector(context,new MyGestureListener());
    }

    private void initView() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        gameThread = new GameThread(holder);

        setOnTouchListener(touchListener);
    }





    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!gameThread.isRunning()){
            gameThread = new GameThread(holder);
            gameThread.start();
        }else{
            gameThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (gameThread.isRunning()){
            gameThread.setIsRunning(false);
            boolean retry = true;
            while(retry){
                try{
                    gameThread.join();
                    retry = false;
                }catch(InterruptedException e){

                }
            }
        }
    }

    // In the SimpleOnGestureListener subclass you should override
    // onDown and any other gesture that you want to detect.
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d("Gesture","onDown: ");
            // don't return false here or else none of the other
            // gestures will work
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (AppConstants.getGameEngine().getGameState() == 1 ){
                AppConstants.getGameEngine().getJumpManager().setState(true);
            }
            Log.i("Gesture", "onSingleTapConfirmed: ");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            int type = Constants.PET_TYPE.getValue();
            if (type == 2){
                if (AppConstants.getGameEngine().getGameState() == 1 ){
                    Constants.JUMP_VELOCITY.setValue(Constants.POWER_JUMP_VELOCITY.getValue());
                    AppConstants.getGameEngine().getJumpManager().setState(true);
                    Constants.JUMP_VELOCITY.setValue(Constants.BASE_JUMP_VELOCITY.getValue());
                }
            }
            else if (type == 3){
                if (AppConstants.getGameEngine().getGameState() == 1 ){
                    Constants.JUMP_VELOCITY.setValue(Constants.POWER_JUMP_VELOCITY.getValue());
                    AppConstants.getGameEngine().getJumpManager().setState(true);
                    Constants.JUMP_VELOCITY.setValue(Constants.BASE_JUMP_VELOCITY.getValue());
                }
            }
            Log.i("Gesture", "onLongPress: ");
        }


        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i("Gesture", "onDoubleTap: ");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            //Log.i("Gesture", "onScroll: ");
            return false;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            //Log.d("Gesture", "onFling: ");
            return false;
        }
    }

}
