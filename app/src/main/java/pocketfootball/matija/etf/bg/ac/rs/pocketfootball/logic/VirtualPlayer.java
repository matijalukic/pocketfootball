package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import java.util.List;

public class VirtualPlayer implements Updatable{
    private GameLogic game;
    private float playerTimer;

    private final float MIN_GAME_VELCCITY = -1000f;
    private final float INTERVAL_GAME_VELOCITY = 2000f;

    public VirtualPlayer(GameLogic game) {
        this.game = game;
        restartTimer();
    }

    public void restartTimer(){
        playerTimer = 1f + (float)Math.random() * 3f; // 1 - 4 seconds lag
    }

    public boolean expired(){
        return playerTimer < 0;
    }

    public void moveRandomBall(List<PlayerBall> playersBalls){
        // select random ball
        PlayerBall randomSelectedBall = playersBalls.get((int)(Math.random() * playersBalls.size()));
        game.selectBallVirtual(randomSelectedBall);

        // random velocity
        // -1000f to 1000f

        float velX = MIN_GAME_VELCCITY + (float)Math.random() * INTERVAL_GAME_VELOCITY;
        float velY = MIN_GAME_VELCCITY + (float)Math.random() * INTERVAL_GAME_VELOCITY;

        // randomly move
        game.moveBall(randomSelectedBall.getX(), randomSelectedBall.getY(), velX, velY);
    }



    @Override
    public void update(float dt) {
        playerTimer -= dt;
    }
}
