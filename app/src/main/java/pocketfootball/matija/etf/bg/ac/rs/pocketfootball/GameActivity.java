package pocketfootball.matija.etf.bg.ac.rs.pocketfootball;

import android.annotation.SuppressLint;
import android.gesture.Gesture;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.controllers.GameGestureListener;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.GameView;

public class GameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    private GameView gameView;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        // get game view
        gameView = findViewById(R.id.game_view);

        mGestureDetector = new GestureDetector(gameView.getContext(), this);

        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {
        gameView.getGameLogic().selectBall(motionEvent.getX(), motionEvent.getY());

        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d("onFling", v + ":" + v1);
        gameView.getGameLogic().moveBall(motionEvent.getX(), motionEvent.getY(), v, v1);
        // remove selections
        gameView.getGameLogic().removeSelection();
        return true;
    }

}
