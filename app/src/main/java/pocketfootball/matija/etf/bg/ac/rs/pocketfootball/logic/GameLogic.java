package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matija on 29 Jan 19.
 */

public class GameLogic implements Updatable {
    private static float FLING_SCALE = .2f;
    private static final float MIN_FLING_SCALE = .2f;
    private static final float INTERVAL_FLING_SCALE = 1f;

    // progress from 0 to 100
    public static void setGameSpeed(int progress)
    {
        FLING_SCALE = MIN_FLING_SCALE + ((float)progress) / 100 * INTERVAL_FLING_SCALE;
    }

    // Events of the game
    public interface GameEventsListener {
        void ballKicked();
        void goalScored();
        void gameEnds(int redScore, int blueScore);
    }

    private GameEventsListener eventsListener;

    public void setEventsListener(GameEventsListener eventsListener) {
        this.eventsListener = eventsListener;
    }

    private boolean running = true;
    private int w, h;

    // List of elements that updates positions
    List<Updatable> movableElements;

    // List of the elements that has instances of drawable view
    List<DrawableViewGenerator> drawableGeneratorElements;

    // all balls in the game
    private List<PlayerBall> allPlayersBalls;

    // match ball
    private MatchBall matchBall;

    private PlayerBall redTopBall, redLeftBall, redRightBall; // red teams balls
    private PlayerBall blueTopBall, blueLeftBall, blueRightBall; // blue teams balls

    private List<PlayerBall> redTeam = new ArrayList<>(3), blueTeam = new ArrayList<>(3), playingTeam = redTeam;

    // selected ball int the game
    private PlayerBall selectedBall;

    private float playersBallDiameter;
    private float matchBallDiameter;

    private List<GoalPost> goalPostsList = new ArrayList<>(4);

    private Score score;
    private Timer timer;
    private PlayerTimer playerTimer;

    /**
     * Start positions of the player balls
     */
    private void initAllBalls() {
        playersBallDiameter = w / 12;
        matchBallDiameter = w / 16;

        // middle bottom ball
        float x = w / 2;
        float y = 2 * h / 3;
        allPlayersBalls.add(redTopBall = new PlayerBall(this, x, y, playersBallDiameter, Color.RED));

        // middle top ball
        y = h / 3;
        allPlayersBalls.add(blueTopBall = new PlayerBall(this, x, y, playersBallDiameter, Color.BLUE));
        // left bottom ball
        x = w / 4;
        y = 5 * h / 6;
        allPlayersBalls.add(redLeftBall = new PlayerBall(this, x, y, playersBallDiameter, Color.RED));
        redLeftBall.setVelocity(0, 100);
        // left top ball
        y = h / 6;
        allPlayersBalls.add(blueLeftBall = new PlayerBall(this, x, y, playersBallDiameter, Color.BLUE));
        blueLeftBall.setVelocity(0, -50);
        // right bottom ball
        x = 3 * w / 4;
        y = 5 * h / 6;
        allPlayersBalls.add(redRightBall = new PlayerBall(this, x, y, playersBallDiameter, Color.RED));

        // right top ball
        y = h / 6;
        allPlayersBalls.add(blueRightBall = new PlayerBall(this, x, y, playersBallDiameter, Color.BLUE));

        // center match ball
        x = w / 2;
        y = h / 2;
        allPlayersBalls.add(matchBall = new MatchBall(this, x, y, matchBallDiameter, Color.YELLOW));

        // add balls to teams
        redTeam.add(redTopBall);
        redTeam.add(redRightBall);
        redTeam.add(redLeftBall);
        blueTeam.add(blueTopBall);
        blueTeam.add(blueLeftBall);
        blueTeam.add(blueRightBall);
    }

    private void setInitPositions() {
        // stop all balls
        for (PlayerBall ball : allPlayersBalls) {
            ball.setVelocity(0f, 0f);
        }

        // middle bottom ball
        float x = w / 2;
        float y = 2 * h / 3;
        redTopBall.setPosition(x, y);


        // middle top ball
        y = h / 3;
        blueTopBall.setPosition(x, y);


        // left bottom ball
        x = w / 4;
        y = 5 * h / 6;
        redLeftBall.setPosition(x, y);

        // left top ball
        y = h / 6;
        blueLeftBall.setPosition(x, y);

        // right bottom ball
        x = 3 * w / 4;
        y = 5 * h / 6;
        redRightBall.setPosition(x, y);

        // right top ball
        y = h / 6;
        blueRightBall.setPosition(x, y);

        // center match ball
        x = w / 2;
        y = h / 2;
        matchBall.setPosition(x, y);
    }


    private Goal redGoal, blueGoal;

    private void makeGoals() {
        float startX = w / 4;
        float endX = 6 * w / 8;

        float startY = 0;
        float endY = h / 12;

        redGoal = new Goal(this, startX, startY, endX, endY);

        startY = h * 11 / 12;
        endY = h;
        blueGoal = new Goal(this, startX, startY, endX, endY);
    }


    private float goalPostWidth;
    private float goalPostHeight;

    private void setGoalPosts() {
        goalPostWidth = w / 32;
        goalPostHeight = h / 12;

        float startX = w / 4;
        float endX = 6 * w / 8;

        float startY = 0;
        float endY = h / 12;

        // top left post
        goalPostsList.add(new GoalPost(this, startX, startY, startX + goalPostWidth, startY + goalPostHeight));
        // top right post
        goalPostsList.add(new GoalPost(this, endX - goalPostWidth, endY - goalPostHeight, endX, endY));


        startY = h * 11 / 12;
        endY = h;

        // bottom  left post
        goalPostsList.add(new GoalPost(this, startX, startY, startX + goalPostWidth, startY + goalPostHeight));
        // bottom right post
        goalPostsList.add(new GoalPost(this, endX - goalPostWidth, endY - goalPostHeight, endX, endY));

    }

    int maxGoals = Integer.MAX_VALUE; // temp value

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
        initAllBalls();

        setGoalPosts();

        score = new Score(this);

        timer = new Timer(this, 5f);

        playerTimer = new PlayerTimer(this);
    }


    public void setGameDuration(int seconds){
        timer.setSeconds(seconds);
    }

    public void setMaxGoals(int maxGoals){
        this.maxGoals = maxGoals;
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
    void addDrawableGeneratorElement(DrawableViewGenerator newDrawableGenerator) {
        drawableGeneratorElements.add(newDrawableGenerator);
    }

    public List<PlayerBall> getRedTeam() {
        return redTeam;
    }

    public List<PlayerBall> getBlueTeam() {
        return blueTeam;
    }

    // checks the number of goals
    private boolean somebodyWon(){
        return score.getRedScore() >= maxGoals || score.getBlueScore() >= maxGoals;
    }

    // update the game
    @Override
    public void update(float dt) {
        if(running) {
            // detect collision before update
            detectCollision(dt);

            // detect goal post collision
            detectPostCollision();

            // blue scores
            if (redGoal.isGoal(matchBall)) {
                score.redScores();
                setInitPositions();
                playingTeam = blueTeam;
                playerTimer.resetTimer();

                eventsListener.goalScored();
            }

            // red scores
            if (blueGoal.isGoal(matchBall)) {
                score.blueScores();
                setInitPositions();
                playingTeam = redTeam;
                playerTimer.resetTimer();

                eventsListener.goalScored();
            }


            // delegate updates to elements
            for (Updatable updateable : allPlayersBalls) {
                updateable.update(dt);
            }

            // update timer
            timer.update(dt);
            // if the games ends
            if (timer.timesUp() || somebodyWon()) {
                running = false; // stop the game
                // notify the controller
                if (eventsListener != null) {
                    eventsListener.gameEnds(score.getRedScore(), score.getBlueScore());
                }
            }

            // update player timer
            playerTimer.update(dt);
            if(playerTimer.expired()){
                switchPlayingTeam();
            }

        }
    }



    public void moveBall(float x, float y, float velocityX, float velocityY) {
        // for each ball set acceleration
        if (selectedBall != null)
            selectedBall.moveBall(FLING_SCALE * velocityX, FLING_SCALE * velocityY);
    }

    private void switchPlayingTeam(){
        if(playingTeam == redTeam){
            playingTeam = blueTeam;
        }
        else
            playingTeam = redTeam;

        playerTimer.resetTimer();
    }

    public int getTeamColor(){
        if(playingTeam == redTeam)
            return Color.RED;
        return Color.BLUE;
    }

    // select ball to move
    public void selectBall(float x, float y) {
        // select ball from playing teams
        for (PlayerBall ball : playingTeam) {
            if (ball.containsDot(x, y)) {
                ball.selectBall();
                selectedBall = ball;

                // if ball is selected switch team
                switchPlayingTeam();
            } else {
                ball.removeSelection();
            }
        }
    }

    // remove selection at the end of fling
    public void removeSelection() {
        for (PlayerBall ball : allPlayersBalls) {
            if (ball.isSelected())
                ball.removeSelection();
        }
        selectedBall = null;
    }

    // collision detection
    private void detectCollision(float dt) {
        // Each pair
        for (int i = 0; i < allPlayersBalls.size(); i++) {
            PlayerBall ball = allPlayersBalls.get(i);
            for (int j = i + 1; j < allPlayersBalls.size(); j++) {
                PlayerBall otherBall = allPlayersBalls.get(j);

                if (!ball.equals(otherBall) && ball.collide(otherBall)) {
                    // send ball kicked signal
                    if(ball instanceof MatchBall || otherBall instanceof MatchBall){
                        eventsListener.ballKicked();
                    }

                    PlayerBall.resolveCollision(ball, otherBall);
                    PlayerBall.fixCollidedPosition(ball, otherBall);
                }
            }

        }
    }

    private void detectPostCollision() {
        // pair the each goal post with each ball
        for (GoalPost goalPost : goalPostsList) {
            for (PlayerBall ball : allPlayersBalls) {
                if (goalPost.detectCollsion(ball)) {
                    ball.resovlePostCollision(goalPost);
                }
            }
        }
    }
}
