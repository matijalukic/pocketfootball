package pocketfootball.matija.etf.bg.ac.rs.pocketfootball;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.Match;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.MatchViewModel;

public class MatchesActivity extends AppCompatActivity {

    ListView allMatches;
    ArrayAdapter<Match> matchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        allMatches = findViewById(R.id.all_matches);

        MatchViewModel matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);

        matchAdapter = new ArrayAdapter<Match>(this, android.R.layout.simple_list_item_1, new ArrayList<Match>());
        allMatches.setAdapter(matchAdapter);

        matchViewModel.getMatches().observe(this, new Observer<List<Match>>() {
            @Override
            public void onChanged(@Nullable List<Match> matches) {
                Log.d("Matches", "Loaded!" );
                matchAdapter.clear();
                if(matches != null)
                    matchAdapter.addAll(matches);
            }
        });
    }

    public void removeAll(View view) {
        MatchViewModel matchViewModel = ViewModelProviders.of(this).get(MatchViewModel.class);

        matchViewModel.deleteAll();
    }
}
