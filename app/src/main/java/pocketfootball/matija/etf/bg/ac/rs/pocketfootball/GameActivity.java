package pocketfootball.matija.etf.bg.ac.rs.pocketfootball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.GameLogic;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.GameSave;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.Match;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.MatchRepository;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.GameView;

public class GameActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GameLogic.GameEventsListener{
    private final static String GAME_KEY = "GAME_KEY";

    private GameView gameView;
    private GestureDetector mGestureDetector;
    private MatchRepository matchRepository;
    private MediaPlayer ballKickedPlayer;
    private MediaPlayer goalScoredPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        ballKickedPlayer = MediaPlayer.create(this, R.raw.ball_kick);
        goalScoredPlayer = MediaPlayer.create(this, R.raw.goal);

        // get game view
        gameView = findViewById(R.id.game_view);

        // inject saved game
        if(savedInstanceState != null) {
                // load from bundle
                GameSave startedGame = (GameSave) savedInstanceState.getSerializable(GAME_KEY);
                if (startedGame != null) gameView.setSavedGame(startedGame);
        }

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

        // set the images sent from intent
        String imageNameOne = getIntent().getStringExtra(MainActivity.PLAYER_ONE_IMAGE);
        String imageNameTwo = getIntent().getStringExtra(MainActivity.PLAYER_TWO_IMAGE);

        int idImageOne = getResources().getIdentifier(imageNameOne, "drawable", getPackageName());
        int idImageTwo = getResources().getIdentifier(imageNameTwo, "drawable", getPackageName());

        gameView.setTeamImages(idImageOne, idImageTwo);

        // setting player types
        GameLogic.PlayerType redPlayerType = getIntent().getBooleanExtra(GameLogic.RED_PLAYER_TYPE_HUMAN, true) ? GameLogic.PlayerType.HUMAN : GameLogic.PlayerType.VIRTUAL;
        GameLogic.PlayerType bluePlayerType = getIntent().getBooleanExtra(GameLogic.BLUE_PLAYER_TYPE_HUMAN, true) ? GameLogic.PlayerType.HUMAN : GameLogic.PlayerType.VIRTUAL;

        gameView.setPlayerTypes(redPlayerType, bluePlayerType);

        // game continuing
        if(getIntent().getBooleanExtra("continueGame", false)){
            gameView.load();
        }

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
    public void gameEnds(int redScore, int blueScore) {
        Match newMatch = new Match();
        newMatch.bluePlayer = getIntent().getStringExtra(MainActivity.PLAYER_TWO_ID);
        newMatch.redPlayer = getIntent().getStringExtra(MainActivity.PLAYER_ONE_ID);

        newMatch.redScore = redScore;
        newMatch.blueScore = blueScore;

        matchRepository.insertMatch(newMatch);

        Intent viewMatches = new Intent(this, MatchActivity.class);

        viewMatches.putExtra(MainActivity.PLAYER_ONE_ID, newMatch.redPlayer);
        viewMatches.putExtra(MainActivity.PLAYER_TWO_ID, newMatch.bluePlayer);

        startActivity(viewMatches);
    }



    @Override
    public void ballKicked() {
        ballKickedPlayer.start();
    }

    @Override
    public void goalScored() {
        goalScoredPlayer.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save game in bundle
        if(gameView.getGameLogic() != null) {
            outState.putSerializable(GAME_KEY, gameView.getGameLogic().save());
            gameView.save();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Log.d("onBackPressed", "back pressed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.refreshTime(); // time fix because the game didnt count time
        gameView.setPaused(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.setPaused(true);
        gameView.save();
    }
}
