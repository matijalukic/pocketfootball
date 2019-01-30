package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.support.annotation.NonNull;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.GameLogic;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.DrawableView;

/**
 * Created by Matija on 29 Jan 19.
 */

public abstract class DrawableViewGenerator {

    protected GameLogic gameLogic;

    public DrawableViewGenerator(@NonNull GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        // register the drawable generator in game when init
        gameLogic.addDrawableGeneratorElement(this);
    }

    // Lazy init, of drawable on the canvas
    public abstract DrawableView getDrawable();
}
