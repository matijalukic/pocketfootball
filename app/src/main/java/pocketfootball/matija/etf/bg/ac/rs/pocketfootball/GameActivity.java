package pocketfootball.matija.etf.bg.ac.rs.pocketfootball;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.GameLogic;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.Match;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.MatchRepository;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.GameView;

public class GameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GameLogic.GameEventsListener{

    private GameView gameView;
    private GestureDetector mGestureDetector;
    private MatchRepository matchRepository;

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

        // set events listener of the game
        gameView.setGameEventsListener(this);

        // make repository of data
        matchRepository = new MatchRepository(getApplication());
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

    // game events


    @Override
    public void start() {

    }

    @Override
    public void gameEnds(int redScore, int blueScore) {
        Match newMatch = new Match();
        newMatch.bluePlayer = getIntent().getStringExtra(MainActivity.PLAYER_TWO_ID);
        newMatch.redPlayer = getIntent().getStringExtra(MainActivity.PLAYER_ONE_ID);

        newMatch.redScore = redScore;
        newMatch.blueScore = blueScore;

        matchRepository.insertMatch(newMatch);

        Intent viewMatches = new Intent(this, MatchesActivity.class);
        startActivity(viewMatches);
    }
}
