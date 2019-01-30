package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.util.Log;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.DrawableView;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.PlayerBallView;

/**
 * Created by Matija on 29 Jan 19.
 */

public class PlayerBall extends DrawableViewGenerator implements Updatable {

    private static float decreaseAccelerationsCoef = 0.1f;

    private boolean selected = false;
    private PlayerBallView playerBallView;
    private float diameter;
    private float x, y;
    private int color;
    private float ax = 10000f, ay = -10000f; // acceleration vector per seconds, can be only positive



    public PlayerBall(GameLogic game, float x, float y, float diameter, int color) {
        super(game); // invokes the parent constructor to register the DrawableViewGenerator at game
        this.diameter = diameter;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    // Lazy load View of the player view
    @Override
    public DrawableView getDrawable() {
        if(playerBallView == null){
            playerBallView = new PlayerBallView(x, y, diameter, color);
        }
        return playerBallView;
    }

    public void setAcceleration(float accX, float accY){
        ax = accX;
        ay = accY;
    }


    private void detectWallCollision(){
        // top wall collision
        if(y < diameter && ay < 0)
            ay = Math.abs(ay);  // change to bottom

        // bottom wall collision
        if(y > (gameLogic.getH() - diameter) && ay > 0)
            ay = -Math.abs(ay); // change the direction

        // left wall collision
        if(x < diameter && ax < 0)
            ax = Math.abs(ax);

        // right wall collision
        if(x > (gameLogic.getW() - diameter) && ax > 0)
            ax = -Math.abs(ax);
    }


    private float getTractionCoefY(){
//        double angle = Math.toRadians(Math.atan(ay / ax));
        int coef = 1;
        if( ay > 0 )
            coef = (-1);

        return coef * Math.abs(ay) * decreaseAccelerationsCoef;
//        return (float)(Math.abs(Math.cos(angle)) * coef * decreaseAccelerationsCoef);
    }

    private float getTractionCoefX(){
//        double angle = Math.toRadians(Math.atan(ay / ax));
        int coef = 1;
        if( ax > 0 )
            coef = (-1);


        return coef * Math.abs(ax) * decreaseAccelerationsCoef;
        //return (float)(Math.abs(Math.sin(angle)) * coef * decreaseAccelerationsCoef);
    }

    private void updateAcceleration(float dt){

        ay += getTractionCoefY() * dt;
        ax += getTractionCoefX() * dt;
    }

    private void updatePosition(float dt){
        y += ay * dt * dt / 2; // acceleration per time vertical
        x += ax * dt * dt / 2;
    }



    // Updates the position and speed
    @Override
    public void update(float dt) {
        detectWallCollision();

        updateAcceleration(dt);
        updatePosition(dt);

        playerBallView.setX(x);
        playerBallView.setY(y);
    }

    public void selectBall(){
        this.selected = true;

        // highlight the view
        this.playerBallView.setOpacity(PlayerBallView.OPACITY_HIGHLIGHTED);
    }

    public void removeSelection(){
        this.selected = false;
        this.playerBallView.setOpacity(PlayerBallView.OPACITY_DIMMED);
    }

    public boolean isSelected() {
        return selected;
    }

    // Dot with (x,y) is in ball
    public boolean containsDot(float x, float y){
        float dx = Math.abs(this.x - x);
        float dy = Math.abs(this.y - y);
        float distanceFromCenter = (float)Math.sqrt(dx * dx + dy * dy);

        // true if the distance is smaller than  diameter
        return distanceFromCenter < diameter;
    }
}
