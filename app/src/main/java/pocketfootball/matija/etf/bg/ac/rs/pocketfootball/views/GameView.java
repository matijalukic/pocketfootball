package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.R;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.SettingsActivity;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.DrawableViewGenerator;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.GameLogic;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.PlayerBall;

import static pocketfootball.matija.etf.bg.ac.rs.pocketfootball.SettingsActivity.DEFAULT_GAME_SPEED;

public class GameView extends View  {

    private GameLogic gameLogic;
    private GameLogic.GameEventsListener gameEventsListener;
    private SharedPreferences sharedPreferences;
    private Bitmap bgBitmap;
    private Rect wholeScreenRect;

    public void setGameEventsListener(GameLogic.GameEventsListener gameEventsListener) {
        this.gameEventsListener = gameEventsListener;
    }

    private Paint testPaint;
    private Paint textPaint;

    private String dimensionsText;

    private int idImageOne, idImageTwo;

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    private GameLogic.PlayerType redPlayerType = GameLogic.PlayerType.HUMAN;
    private GameLogic.PlayerType bluePlayerType = GameLogic.PlayerType.HUMAN;

    // saving player types
    public void setPlayerTypes(GameLogic.PlayerType redPlayerType, GameLogic.PlayerType bluePlayerType){
        this.redPlayerType = redPlayerType;
        this.bluePlayerType = bluePlayerType;
    }

    private void createGame(){
        gameLogic = new GameLogic(getWidth(), getHeight());

        // set default values
        int preferencesNumberOfGoals = sharedPreferences.getInt(getResources().getString(R.string.max_goals_key), SettingsActivity.DEFAULT_NUMBER_OF_GOALS);
        int preferencesGameSecondsDuration = sharedPreferences.getInt(getResources().getString(R.string.game_duration_key), SettingsActivity.DEFAULT_GAME_DURATION_SECONDS);
        int preferencesGameSpeed = sharedPreferences.getInt(getResources().getString(R.string.game_speed_key), DEFAULT_GAME_SPEED);

        gameLogic.setGameDuration(preferencesGameSecondsDuration);
        gameLogic.setMaxGoals(preferencesNumberOfGoals);

        // setting player types
        gameLogic.setPlayerTypes(redPlayerType, bluePlayerType);

        GameLogic.setGameSpeed(preferencesGameSpeed);


        // setting the background
        String bgImageName = sharedPreferences.getString(getResources().getString(R.string.game_bg_image_key), SettingsActivity.DEFAULT_BACKGROUND_IMAGE_NAME);
        int idBgDrawable = getResources().getIdentifier(bgImageName, "drawable", getContext().getPackageName());
        bgBitmap = BitmapFactory.decodeResource(getResources(), idBgDrawable);

        // set rect for displaying bg
        wholeScreenRect = new Rect();
        wholeScreenRect.set(0,0, getWidth(), getHeight());

        dimensionsText = String.format("%d x %d", gameLogic.getW(), gameLogic.getH());
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        testPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        testPaint.setColor(Color.BLUE);

        textPaint = new Paint(Paint.LINEAR_TEXT_FLAG);
        textPaint.setColor(Color.RED);

        sharedPreferences = getContext().getSharedPreferences(getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public void setTeamImages(int oneDrawableId, int twoDrawableId){
        idImageOne = oneDrawableId;
        idImageTwo = twoDrawableId;
    }

    public void drawTeamsImage(){
        Bitmap redTeamBitmap = BitmapFactory.decodeResource(getResources(), idImageOne);
        Bitmap blueTeamBitmap = BitmapFactory.decodeResource(getResources(), idImageTwo);

        // fill the red teams
        for(PlayerBall teamMember: gameLogic.getRedTeam()){
            ((PlayerBallView) teamMember.getDrawable()).setTeamImage(redTeamBitmap);
        }

        // fill the red teams
        for(PlayerBall teamMember: gameLogic.getBlueTeam()){
            ((PlayerBallView) teamMember.getDrawable()).setTeamImage(blueTeamBitmap);
        }
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createGame();
        if(gameEventsListener != null){
            gameLogic.setEventsListener(gameEventsListener);
        }

        drawTeamsImage();
    }



    private long currentTime = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // first draw background
        if(bgBitmap != null){
            canvas.drawBitmap(bgBitmap, null, wholeScreenRect, testPaint);
        }

        // first time
        if(currentTime == 0)
            currentTime = System.currentTimeMillis();

        // for each Games DrawableViewGenerator calls it
        for(DrawableViewGenerator drawableViewGenerator: gameLogic.getDrawableGeneratorElements()){
            // call a DrawableView from generator
            drawableViewGenerator.getDrawable().draw(canvas);
        }

        // update game logic
        long nowTime = System.currentTimeMillis();
        gameLogic.update(( (float)(nowTime - currentTime) ) / 1000);
        currentTime = nowTime;

        invalidate();
    }



}
