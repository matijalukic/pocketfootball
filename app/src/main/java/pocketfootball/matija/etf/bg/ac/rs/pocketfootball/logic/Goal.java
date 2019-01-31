package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.support.annotation.NonNull;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.DrawableView;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.GoalView;

/**
 * Created by Matija on 31 Jan 19.
 */

public class Goal extends DrawableViewGenerator {

    private GoalView goalView;
    private float startX, startY;
    private float endX, endY;

    public Goal(@NonNull GameLogic gameLogic, float startX, float startY,  float endX, float endY) {
        super(gameLogic);

        this.startX = startX;
        this.startY = startY;

        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public DrawableView getDrawable() {
        if(goalView == null){
            goalView = new GoalView(startX, startY, endX, endY);
        }
        return goalView;
    }

    public boolean isGoal(MatchBall ball){
        if(startX <= ball.getX() - ball.diameter &&
                endX >= ball.getX() + ball.diameter &&
                startY <= ball.getY() - ball.diameter &&
                endY >= ball.getY() + ball.diameter)
            return true;

        return false;
    }


}
