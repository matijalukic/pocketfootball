package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist;


import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import static android.arch.persistence.room.Room.databaseBuilder;

/**
 * Created by Matija on 01 Feb 19.
 */
@Database(entities = {Match.class}, version = 1, exportSchema = false)
public abstract class MatchDatabase extends RoomDatabase {
    private static MatchDatabase dbInstance;
    public static synchronized MatchDatabase getDB(Application application){
        if(dbInstance == null){
            dbInstance = Room.databaseBuilder(application, MatchDatabase.class, "matches")
                    .fallbackToDestructiveMigration()
                    .build();;
        }

        return dbInstance;
    }



    public abstract MatchDao matchDao();
}
