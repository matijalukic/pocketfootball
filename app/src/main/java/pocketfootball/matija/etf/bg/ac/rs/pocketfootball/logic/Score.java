package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.support.annotation.NonNull;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.DrawableView;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.ScoreView;

/**
 * Created by Matija on 01 Feb 19.
 */

public class Score extends DrawableViewGenerator {

    private ScoreView scoreView;
    private int redScore = 0, blueScore = 0;


    public Score(@NonNull GameLogic gameLogic) {
        super(gameLogic);
        scoreView = new ScoreView();

    }

    public void blueScores(){
        blueScore++;
        scoreView.setScores(redScore, blueScore);
    }

    public void redScores(){
        redScore++;
        scoreView.setScores(redScore, blueScore);
    }

    public int getRedScore() {
        return redScore;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public void setScores(int red, int blue){
        redScore = red;
        blueScore = blue;

        if(scoreView != null){
            scoreView.setScores(red, blue);
        }
    }

    @Override
    public DrawableView getDrawable() {
        return scoreView;
    }
}
