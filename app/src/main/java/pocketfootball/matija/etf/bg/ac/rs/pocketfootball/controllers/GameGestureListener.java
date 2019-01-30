package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.controllers;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.GameLogic;


public class GameGestureListener extends GestureDetector.SimpleOnGestureListener {

    private GameLogic gameLogic;

    public GameGestureListener(@NonNull GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("onFling", velocityX + ":" + velocityY);
        gameLogic.moveBall(e1.getX(), e1.getY(), velocityX, velocityY);
        return true;
    }
}
