package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Matija on 01 Feb 19.
 */

public class MatchViewModel extends AndroidViewModel {
    private MatchRepository matchRepository;
    private LiveData<List<Match>> allMatches;

    public MatchViewModel(@NonNull Application application) {
        super(application);
        matchRepository = new MatchRepository(application);
    }

    public LiveData<List<Match>> getMatches(){
        if(allMatches == null){
            allMatches = matchRepository.getMatches();
        }
        return allMatches;
    }


    public LiveData<List<Match>> getPlayersMatches(String playerOne, String playerTwo){
        return matchRepository.getPlayersMatches(playerOne, playerTwo);
    }

    public void deleteAll(){
        matchRepository.deleteAll();
    }

    public void deletePlayersMatches(String playerOne, String playerTwo){
        matchRepository.deletePlayersMatches(playerOne, playerTwo);
    }

}
