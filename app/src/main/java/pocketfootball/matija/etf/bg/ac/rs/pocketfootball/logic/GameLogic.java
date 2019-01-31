package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matija on 29 Jan 19.
 */

public class GameLogic implements Updatable {
    private static final float FLING_SCALE = .05f;

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

    // all balls in the game
    private List<PlayerBall> allPlayersBalls;

    // match ball
    private MatchBall matchBall;


    private PlayerBall playerBall;

    // selected ball int the game
    private PlayerBall selectedBall;

    private float playersBallDiameter;
    private float matchBallDiameter;

    /**
     * Start positions of the player balls
     */
    private void startBallsPosition(){
        playersBallDiameter = w / 12;
        matchBallDiameter = w / 16;

        // middle bottom ball
        float x = w / 2;
        float y = 2 * h / 3;
        allPlayersBalls.add(playerBall = new PlayerBall(this, x,y, playersBallDiameter, Color.RED));

        // middle top ball
        y = h / 3;
        allPlayersBalls.add(new PlayerBall(this, x,y, playersBallDiameter, Color.BLUE));

        // left bottom ball
        x  = w / 4;
        y = 5 * h / 6;
        allPlayersBalls.add(new PlayerBall(this, x, y, playersBallDiameter, Color.RED));

        // left top ball
        y = h / 6;
        allPlayersBalls.add(new PlayerBall(this, x, y, playersBallDiameter, Color.BLUE));

        // right bottom ball
        x = 3 * w / 4;
        y = 5 * h / 6;
        allPlayersBalls.add(new PlayerBall(this, x, y, playersBallDiameter, Color.RED));

        // right top ball
        y = h / 6;
        allPlayersBalls.add(new PlayerBall(this, x, y, playersBallDiameter, Color.BLUE));

        // center match ball
        x = w / 2;
        y = h / 2;
        allPlayersBalls.add(matchBall = new MatchBall(this, x, y, matchBallDiameter, Color.YELLOW));
    }

    private Goal redGoal, blueGoal;

    private void makeGoals(){
        float startX = w / 4;
        float endX = 6 * w / 8;

        float startY = 0;
        float endY = h / 12;

        redGoal = new Goal(this, startX, startY, endX, endY);

        startY = h * 11 / 12;
        endY = h;
        blueGoal = new Goal(this, startX, startY, endX, endY);
    }

    public GameLogic(int width, int height) {
        w = width;
        h = height;

        // empty list of generator elements
        drawableGeneratorElements = new ArrayList<>();

        // empty list
        allPlayersBalls = new ArrayList<>();

        // empty list of movable elements
        movableElements = new ArrayList<>();

        // create goals
        makeGoals();

        // create players balls
        startBallsPosition();
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
        // detect collision before update
        detectCollision(dt);

        // detect goals
        if(redGoal.isGoal(matchBall))
            Log.d("GOAL!", "RED GOALS");
        if(blueGoal.isGoal(matchBall))
            Log.d("GOAL!", "BLUE GOALS");

        // delegate updates to elements
        for(Updatable updateable: allPlayersBalls){
            updateable.update(dt);
        }
    }

    // change the
    public void moveBall(float x, float y, float velocityX, float velocityY){
        // for each ball set acceleration
        if(selectedBall != null)
            selectedBall.moveBall(FLING_SCALE * velocityX, FLING_SCALE * velocityY);
    }

    // select ball to move
    public void selectBall(float x, float y){
        for(PlayerBall ball: allPlayersBalls){
            if(ball.containsDot(x, y)){
                ball.selectBall();
                selectedBall = ball;
            }
            else{
                ball.removeSelection();
            }
        }
    }

    // remove selection at the end of fling
    public void removeSelection(){
        for(PlayerBall ball: allPlayersBalls){
            if(ball.isSelected())
                ball.removeSelection();
        }
        selectedBall = null;
    }

    // collision detection
    private void detectCollision(float dt){
        // Each pair
        for(int i=0; i < allPlayersBalls.size(); i++){
            PlayerBall ball = allPlayersBalls.get(i);
            for(int j = i + 1; j < allPlayersBalls.size(); j++){
                PlayerBall otherBall = allPlayersBalls.get(j);

                if(!ball.equals(otherBall) && ball.collide(otherBall)){
                    PlayerBall.fixCollidedPosition(ball, otherBall);
                    PlayerBall.resolveCollision(ball, otherBall, dt);
                }
            }

        }
    }
}
