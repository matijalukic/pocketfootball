package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import android.util.Log;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.DrawableView;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views.PlayerBallView;

/**
 * Created by Matija on 29 Jan 19.
 */

public class PlayerBall extends DrawableViewGenerator implements Updatable {

    protected static float decreaseAccelerationsCoef = 0.1f;

    private boolean selected = false;
    private PlayerBallView playerBallView;
    protected float diameter;
    protected float x, y;
    protected int color;
//    private float ax = 50000f, ay = 50000f; // acceleration vector per seconds
    
    private float velX = 0, velY = 0;

    public float getDiameter() {
        return diameter;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static void fixCollidedPosition(PlayerBall ballOne, PlayerBall ballTwo){
        float avgX = (ballOne.x + ballTwo.x) /2;
        float avgY = (ballOne.y + ballTwo.y) /2;

        float ratio = Math.abs(ballOne.y - ballTwo.y) / Math.abs(ballOne.x - ballTwo.x);
        double fiRadians = Math.atan(ratio); // angle of the line defined by centers of the balls

        float collisionPointDiffOne = ballOne.diameter - (float)Math.sqrt(Math.pow(ballOne.x - avgX, 2f) + Math.pow(ballOne.y - avgY, 2f));
        float collisionPointDiffTwo = ballTwo.diameter - (float)Math.sqrt(Math.pow(ballTwo.x - avgX, 2f) + Math.pow(ballTwo.y - avgY, 2f));

        // move by X axis
        if(ballOne.x < ballTwo.x){
            ballOne.x -= collisionPointDiffOne * Math.cos(fiRadians);
            ballTwo.x += collisionPointDiffTwo * Math.cos(fiRadians);
        }
        else{
            ballOne.x += collisionPointDiffOne * Math.cos(fiRadians);
            ballTwo.x -= collisionPointDiffTwo * Math.cos(fiRadians);
        }

        // move by Y axis balls
        if(ballOne.y < ballTwo.y){
            ballOne.y -= collisionPointDiffOne * Math.sin(fiRadians);
            ballTwo.y += collisionPointDiffTwo * Math.sin(fiRadians);
        }
        else{
            ballOne.y += collisionPointDiffOne * Math.sin(fiRadians);
            ballTwo.y -= collisionPointDiffTwo * Math.sin(fiRadians);
        }
    }

    public static void resolveCollision(PlayerBall ballOne, PlayerBall ballTwo){
        double collisionAngle = Math.atan2(ballTwo.y - ballOne.y, ballTwo.x - ballOne.x); // angle for ball one
        double collisionAngle2 = Math.atan2(ballOne.y - ballTwo.y,  ballOne.x - ballTwo.x); // for ball two
//
//        Log.d("ballOne", " " + ballOne.velX + ":" + ballOne.velY);
//        Log.d("ballTwo", " " + ballTwo.velX + ":" + ballTwo.velY);
//        Log.d("resolveCollision", Math.toDegrees(collisionAngle) + " degrees = " + Math.toDegrees(collisionAngle2));
//
//        ballOne.velY *=-1;
//        ballTwo.velY *=-1;

        double ballOneSin = ballOne.velIntesity() * Math.sin(ballOne.velAngle() - collisionAngle);
        double ballTwoCos = ballTwo.velIntesity() * Math.cos(ballTwo.velAngle() - collisionAngle2);

        // new
        double newOneVelX = ballTwoCos * Math.cos(collisionAngle2) + Math.sin(collisionAngle) * ballOneSin;
        double newOneVelY = ballTwoCos * Math.sin(collisionAngle2) + Math.cos(collisionAngle) * ballOneSin;

        // ball two
        double ballOneCos = ballOne.velIntesity() * Math.cos(ballOne.velAngle() - collisionAngle);
        double ballTwoSin = ballTwo.velIntesity() * Math.sin(ballTwo.velAngle() - collisionAngle2);

        double newTwoVelX = ballOneCos * Math.cos(collisionAngle) + Math.sin(collisionAngle2) * ballTwoSin;
        double newTwoVelY = ballOneCos * Math.sin(collisionAngle) + Math.cos(collisionAngle2) * ballTwoSin;

//        ballOne.velY *=-1;
//        ballTwo.velY *=-1;
//

//        float newOneVelX = ballTwo.velX;
//        float newOneVelY = ballTwo.velY;
//        float newTwoVelX = ballOne.velX;
//        float newTwoVelY = ballOne.velY;


        ballOne.setVelocity((float)newOneVelX, (float)newOneVelY);
        ballTwo.setVelocity((float)newTwoVelX, (float)newTwoVelY);

    }

    // intesity of the vector velocity
    private double velIntesity(){
        return Math.sqrt(Math.pow(velX, 2) + Math.pow(velY, 2));
    }
    // velocity vector angle in radians
    private double velAngle(){
        return Math.atan2(velY, velX);
    }

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
        if (playerBallView == null) {
            playerBallView = new PlayerBallView(x, y, diameter, color);
        }
        return playerBallView;
    }

    public void setVelocity(float accX, float accY) {
        velX = accX;
        velY = accY;
    }


    private void detectWallCollision() {
        // top wall collision
        if (y < diameter && velY < 0)
            velY = Math.abs(velY);  // change to bottom

        // bottom wall collision
        if (y > (gameLogic.getH() - diameter) && velY > 0)
            velY = -Math.abs(velY); // change the direction

        // left wall collision
        if (x < diameter && velX < 0)
            velX = Math.abs(velX);

        // right wall collision
        if (x > (gameLogic.getW() - diameter) && velX > 0)
            velX = -Math.abs(velX);
    }

    public void resovlePostCollision(GoalPost goalPost){
        // collision from top of the post
        if( y < goalPost.getStartY() ){
            // direction to top
            velY = (-1) * Math.abs(velY);
        }

        // collision from bottom of the post
        if( y > goalPost.getEndY() ){
            // direction to bottom
            velY = Math.abs(velY);
        }

        // collison on the left of the post
        if( x < goalPost.getStartX()){
            // direction to left
            velX = (-1) * Math.abs(velX);
        }

        // collision on the right of the post
        if( x > goalPost.getEndX() ){
            // direction to right
            velX = Math.abs(velX);
        }
    }


    private float getTractionCoefY() {
//        double angle = Math.toRadians(Math.atan(ay / ax));
        int coef = 1;
        if (velY > 0)
            coef = (-1);

        return coef * Math.abs(velY) * decreaseAccelerationsCoef;
//        return (float)(Math.abs(Math.cos(angle)) * coef * decreaseAccelerationsCoef);
    }

    private float getTractionCoefX() {
//        double angle = Math.toRadians(Math.atan(ay / ax));
        int coef = 1;
        if (velX > 0)
            coef = (-1);


        return coef * Math.abs(velX) * decreaseAccelerationsCoef;
        //return (float)(Math.abs(Math.sin(angle)) * coef * decreaseAccelerationsCoef);
    }

    private void updateAcceleration(float dt) {

        velY += getTractionCoefY() * dt;
        velX += getTractionCoefX() * dt;
    }

    private void updatePosition(float dt) {
//        y += ay * dt * dt / 2; // acceleration per time vertical
//        x += ax * dt * dt / 2;
        y += velY * dt;
        x += velX * dt;

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

    public void selectBall() {
        this.selected = true;

        // highlight the view
        this.playerBallView.setOpacity(PlayerBallView.OPACITY_HIGHLIGHTED);
    }

    public void removeSelection() {
        this.selected = false;
        this.playerBallView.setOpacity(PlayerBallView.OPACITY_DIMMED);
    }

    public boolean isSelected() {
        return selected;
    }

    // Dot with (x,y) is in ball
    public boolean containsDot(float x, float y) {
        float dx = Math.abs(this.x - x);
        float dy = Math.abs(this.y - y);
        float distanceFromCenter = (float) Math.sqrt(dx * dx + dy * dy);

        // true if the distance is smaller than  diameter
        return distanceFromCenter < diameter;
    }

    public boolean collide(PlayerBall otherBall) {
        float dx = Math.abs(this.x - otherBall.x);
        float dy = Math.abs(this.y - otherBall.y);
        float distanceBetweenCenters = (float) Math.sqrt(dx * dx + dy * dy);

        // true if the distance is smaller than  diameter
        return distanceBetweenCenters < (diameter + otherBall.diameter);
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }


    public void moveBall(float velX, float velY){
        this.setVelocity(velX, velY);
    }

}
