package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views;

import android.graphics.Canvas;

/**
 * Created by Matija on 31 Jan 19.
 */

public class MatchBallView extends PlayerBallView{

    public MatchBallView(float x, float y, float diameter, int color) {
        super(x, y, diameter, color);
        // highlight the ball
        ballPaint.setAlpha(OPACITY_HIGHLIGHTED);
    }

}
