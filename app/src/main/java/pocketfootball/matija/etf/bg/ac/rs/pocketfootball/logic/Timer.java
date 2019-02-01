package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.support.annotation.NonNull;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.DrawableView;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.TimerView;

/**
 * Created by Matija on 01 Feb 19.
 */

public class Timer extends DrawableViewGenerator implements Updatable {

    private TimerView timerView;
    private boolean counting = true;
    private float seconds;

    public Timer(@NonNull GameLogic gameLogic, float seconds) {
        super(gameLogic);
        this.seconds = seconds;
    }

    public boolean timesUp(){
        return seconds <= 0 && counting == false;
    }

    public void restart(float newCounting){
        seconds = newCounting;
        counting = true;
    }

    public boolean isCounting() {
        return counting;
    }

    @Override
    public DrawableView getDrawable() {
        if(timerView == null){
            timerView = new TimerView(seconds);
        }
        return timerView;
    }

    @Override
    public void update(float dt) {
        seconds -= dt;
        if(seconds < 0) {
            counting = false;
            timerView.updates(0f);
        }else
            timerView.updates(seconds);
    }
}
