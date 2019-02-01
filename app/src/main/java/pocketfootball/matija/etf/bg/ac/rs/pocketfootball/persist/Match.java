package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by Matija on 01 Feb 19.
 */
@Entity()
public class Match {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String redPlayer;
    public String bluePlayer;

    public int redScore;
    public int blueScore;

    @Override
    public String toString(){
        return redPlayer + " " + redScore + ":" + blueScore + " " + bluePlayer;
    }
}
