package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic;

import java.io.Serializable;

/**
 * Representation of the game for saving
 */
public class GameSave implements Serializable {

    private float timeLeft;
    private int redScores, blueScores;
    private GameLogic.PlayerType redPlayerType, bluePlayerType;

    public GameSave(float timeLeft, int redScores, int blueScores, GameLogic.PlayerType redPlayerType, GameLogic.PlayerType bluePlayerType) {
        this.timeLeft = timeLeft;
        this.redScores = redScores;
        this.blueScores = blueScores;

        this.redPlayerType = redPlayerType;
        this.bluePlayerType = bluePlayerType;
    }

    public float getTimeLeft() {
        return timeLeft;
    }

    public int getRedScores() {
        return redScores;
    }

    public int getBlueScores() {
        return blueScores;
    }

    public GameLogic.PlayerType getRedPlayerType() {
        return redPlayerType;
    }

    public GameLogic.PlayerType getBluePlayerType() {
        return bluePlayerType;
    }
}