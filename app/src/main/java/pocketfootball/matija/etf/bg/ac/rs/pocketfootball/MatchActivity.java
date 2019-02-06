package pocketfootball.matija.etf.bg.ac.rs.pocketfootball;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.fragments.MatchAdapter;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.Match;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.MatchViewModel;

public class MatchActivity extends AppCompatActivity {
    RecyclerView playersMatches;
    String playerOne;
    String playerTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        playersMatches = findViewById(R.id.players_matches);
        playersMatches.setHasFixedSize(true);
        playersMatches.setLayoutManager(new LinearLayoutManager(this));

        final MatchAdapter matchAdapter = new MatchAdapter();
        playersMatches.setAdapter(matchAdapter);

        // get player names
        playerOne = getIntent().getStringExtra(MainActivity.PLAYER_ONE_ID);
        playerTwo = getIntent().getStringExtra(MainActivity.PLAYER_TWO_ID);

        setTitle(playerOne + " vs " + playerTwo);

        MatchViewModel matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);
        matchViewModel.getPlayersMatches(playerOne, playerTwo).observe(this, new Observer<List<Match>>() {
            @Override
            public void onChanged(@Nullable List<Match> matches) {
                if(matches != null) {
                    matchAdapter.setMatchList(matches);
                }
            }
        });

    }


    public void removeMatches(View view) {
        MatchViewModel matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);
        matchViewModel.deletePlayersMatches(playerOne, playerTwo);
    }
}
