package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.support.annotation.NonNull;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.DrawableView;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.GoalPostView;

/**
 * Created by Matija on 31 Jan 19.
 */

public class GoalPost extends DrawableViewGenerator {
    private float startX, startY;
    private float endX, endY;
    private GoalPostView goalPostView;

    public GoalPost(@NonNull GameLogic gameLogic, float startX, float startY, float endX, float endY) {
        super(gameLogic);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    // if there is collsion by x
    private boolean collisionByX(PlayerBall ball) {
        return (ball.x - ball.getDiameter()) <= endX && (ball.x + ball.getDiameter()) >= startX;
    }

    private boolean collisionByY(PlayerBall ball) {
        return (ball.y - ball.getDiameter()) <= endY && (ball.y + ball.getDiameter()) >= startY;
    }


    // Detect collision between post and the ball
    public boolean detectCollsion(PlayerBall ball) {
        // collision from the top
        return collisionByX(ball) && collisionByY(ball);
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getEndX() {
        return endX;
    }

    public float getEndY() {
        return endY;
    }

    @Override
    public DrawableView getDrawable() {
        if (goalPostView == null) {
            goalPostView = new GoalPostView(startX, startY, endX, endY);
        }
        return goalPostView;
    }
}
