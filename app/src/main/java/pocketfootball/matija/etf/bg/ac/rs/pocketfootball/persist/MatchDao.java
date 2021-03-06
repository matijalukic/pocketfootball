package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * Created by Matija on 01 Feb 19.
 */
@Dao
public interface MatchDao {
    @Query("SELECT M.id , COUNT(case when M.redScore > M.blueScore then 1 else null end) as redScore, COUNT(case when M.blueScore > M.redScore then 1 else null end) as blueScore, M.redPlayer, M.bluePlayer  FROM `Match` M GROUP BY redPlayer, bluePlayer ORDER BY id DESC")
    LiveData<List<Match>> matches();

    @Query("SELECT * FROM `Match` WHERE redPlayer = :playerOne AND bluePlayer = :playerTwo ORDER BY id DESC")
    LiveData<List<Match>> playersMatches(String playerOne, String playerTwo);

    @Insert
    void insert(Match newMatch);

    @Query("DELETE FROM `Match` WHERE redPlayer = :playerOne AND bluePlayer = :playerTwo")
    void deletePlayersMatches(String playerOne, String playerTwo);

    @Query("DELETE FROM `Match`")
    void deleteAll();
}
