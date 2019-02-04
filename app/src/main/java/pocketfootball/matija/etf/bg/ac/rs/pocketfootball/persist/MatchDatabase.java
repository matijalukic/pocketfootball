package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Matija on 01 Feb 19.
 */
@Database(entities = {Match.class}, version = 1, exportSchema = false)
public abstract class MatchDatabase extends RoomDatabase {
    public abstract MatchDao matchDao();
}
