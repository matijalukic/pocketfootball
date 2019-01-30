package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matija on 29 Jan 19.
 */

public class GameLogic implements Updatable {
    private static final float FLING_SCALE = 10;

    // Events of the game
    interface GameEventsListener{
        void start(); // called when the game is started
    }
    private GameEventsListener eventsListener;
    public void setEventsListener(GameEventsListener eventsListener) {
        this.eventsListener = eventsListener;
    }

    private boolean started = false;
    private int w,h;

    // List of elements that updates positions
    List<Updatable> movableElements;

    // List of the elements that has instances of drawable view
    List<DrawableViewGenerator> drawableGeneratorElements;

    // player ball
    private List<PlayerBall> downPlayerBalls;
    private PlayerBall playerBall;

    private PlayerBall selectedBall;

    /**
     * Start positions of the player balls
     */
    private void initPlayersBalls(){
        float playersBallDiameter = w / 12;

        // first bottom ball
        float x = w / 2;
        float y = 2 * h / 3;
        playerBall = new PlayerBall(this, x,y, playersBallDiameter, Color.WHITE);
        downPlayerBalls.add(playerBall);

        // second left bottom ball
        x  = w / 4;
        y = 5 * h / 6;
        downPlayerBalls.add(new PlayerBall(this, x, y, playersBallDiameter, Color.WHITE));

        // third right bottom ball
        x = 3 * w / 4;
        downPlayerBalls.add(new PlayerBall(this, x, y, playersBallDiameter, Color.WHITE));
    }

    public GameLogic(int width, int height) {
        w = width;
        h = height;

        // empty list of generator elements
        drawableGeneratorElements = new ArrayList<>();

        // empty list
        downPlayerBalls = new ArrayList<>();

        // empty list of movable elements
        movableElements = new ArrayList<>();

        // create players balls
        initPlayersBalls();
    }


    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public List<DrawableViewGenerator> getDrawableGeneratorElements() {
        return drawableGeneratorElements;
    }

    // Package Access - only DrawableViewGenerator class invokes it
    // Register the new DrawableViewGenerator to the list
    void addDrawableGeneratorElement(DrawableViewGenerator newDrawableGenerator){
        drawableGeneratorElements.add(newDrawableGenerator);
    }

    // update the game
    @Override
    public void update(float dt) {
        // delegate updates to elements
        for(Updatable updateable: downPlayerBalls){
            updateable.update(dt);
        }
    }

    // change the
    public void moveBall(float x, float y, float velocityX, float velocityY){
        // for each ball set acceleration
        if(selectedBall != null)
            selectedBall.setAcceleration(FLING_SCALE * velocityX, FLING_SCALE * velocityY);
    }

    public void selectBall(float x, float y){
        for(PlayerBall ball: downPlayerBalls){
            if(ball.containsDot(x, y)){
                ball.selectBall();
                selectedBall = ball;
            }
            else{
                ball.removeSelection();
            }
        }
    }

    public void removeSelection(){
        for(PlayerBall ball: downPlayerBalls){
            if(ball.isSelected())
                ball.removeSelection();
        }
        selectedBall = null;
    }
}
