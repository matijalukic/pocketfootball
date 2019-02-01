package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Matija on 01 Feb 19.
 */

public class MatchRepository {
    private LiveData<List<Match>> allMatches;
    private MatchDao matchDao;
    private MatchDatabase matchDatabase;


    public MatchRepository(Application application) {
         matchDatabase = Room.databaseBuilder(application, MatchDatabase.class, "matches")
                .fallbackToDestructiveMigration()
                .build();

         matchDao = matchDatabase.matchDao();
    }

    public LiveData<List<Match>> getMatches(){
        return matchDao.matches();
    }

    public void insertMatch(Match newMatch){
        new AsyncTask<Match, Void, Void>(){
            @Override
            protected Void doInBackground(Match... matches) {
                if(matches != null && matches.length > 0)
                matchDao.insert(matches[0]);
                return null;
            }
        }.execute(newMatch);
    }

    public void deleteAll(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                matchDao.deleteAll();
                return null;
            }
        }.execute();
    }

}
