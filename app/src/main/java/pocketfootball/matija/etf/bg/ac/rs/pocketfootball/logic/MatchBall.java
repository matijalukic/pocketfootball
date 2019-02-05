package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.DrawableView;

/**
 * Created by Matija on 31 Jan 19.
 */

public class MatchBall extends PlayerBall {


    public MatchBall(GameLogic game, float x, float y, float diameter, int color) {
        super(game, x, y, diameter, color);
    }

    @Override
    public void moveBall(float velX, float velY) {
        // user cant move match ball
    }

}
