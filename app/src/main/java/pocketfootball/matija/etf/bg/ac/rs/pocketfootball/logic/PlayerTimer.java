package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.support.annotation.NonNull;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.DrawableView;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.PlayerTimerView;

public class PlayerTimer extends DrawableViewGenerator implements Updatable {

    private PlayerTimerView playerTimerView;
    private float seconds = 5f;
    private final float maxSeconds = 5f;

    public PlayerTimer(@NonNull GameLogic gameLogic) {
        super(gameLogic);
        playerTimerView = new PlayerTimerView(gameLogic.getTeamColor(), gameLogic.getW() / 64f, gameLogic.getH());

    }

    // switch color
    public void resetTimer(){
        seconds = maxSeconds;
        playerTimerView.setColor(gameLogic.getTeamColor());
    }

    public boolean expired(){
        return seconds <= 0;
    }

    @Override
    public void update(float dt) {
        seconds -= dt;
        // update height
        playerTimerView.setH( seconds / maxSeconds * gameLogic.getH() );
    }

    @Override
    public DrawableView getDrawable() {
        return playerTimerView;
    }
}
