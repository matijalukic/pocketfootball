package pocketfootball.matija.etf.bg.ac.rs.pocketfootball;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.fragments.MatchAdapter;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.Match;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.MatchViewModel;

public class MatchesActivity extends AppCompatActivity {

    RecyclerView allMatches;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        allMatches = findViewById(R.id.all_matches);
        allMatches.setHasFixedSize(true);
        allMatches.setLayoutManager(new LinearLayoutManager(this));

        final MatchAdapter matchAdapter = new MatchAdapter();
        allMatches.setAdapter(matchAdapter);

        MatchViewModel matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);
        matchViewModel.getMatches().observe(this, new Observer<List<Match>>() {
            @Override
            public void onChanged(@Nullable List<Match> matches) {
                if(matches != null) {
                    matchAdapter.setMatchList(matches);
                }
            }
        });


    }

    public void removeAll(View view) {
        MatchViewModel matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);

        matchViewModel.deleteAll();
    }
}
