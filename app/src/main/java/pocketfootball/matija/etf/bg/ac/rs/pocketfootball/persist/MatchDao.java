package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


/**
 * Created by Matija on 01 Feb 19.
 */
@Dao
public interface MatchDao {
    @Query("SELECT * FROM `Match`")
    LiveData<List<Match>> matches();

    @Insert
    void insert(Match newMatch);

    @Query("DELETE FROM `Match`")
    void deleteAll();
}
