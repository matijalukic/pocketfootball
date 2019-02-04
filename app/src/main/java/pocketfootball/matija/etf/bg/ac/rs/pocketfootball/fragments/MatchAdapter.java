package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.fragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.R;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.Match;


public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchHolder> {

    private List<Match> matchList = new ArrayList<>();
    @NonNull
    @Override
    public MatchHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.match_view, viewGroup, false);
        return new MatchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchHolder matchHolder, int i) {
        Match currMatch = matchList.get(i);
        matchHolder.redTeamName.setText(String.valueOf(currMatch.redPlayer));
        matchHolder.blueTeamName.setText(String.valueOf(currMatch.bluePlayer));
        matchHolder.redTeamScore.setText(String.valueOf(currMatch.redScore));
        matchHolder.blueTeamScore.setText(String.valueOf(currMatch.blueScore));
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
        for(Match match: matchList){
            Log.d("matches", match.toString());
        }
        notifyDataSetChanged();
    }

    class MatchHolder extends RecyclerView.ViewHolder{
        private TextView redTeamName;
        private TextView blueTeamName;
        private TextView redTeamScore;
        private TextView blueTeamScore;

        MatchHolder(@NonNull View itemView) {
            super(itemView);
            redTeamName = itemView.findViewById(R.id.red_team_name);
            blueTeamName = itemView.findViewById(R.id.blue_team_name);
            blueTeamScore = itemView.findViewById(R.id.blue_team_score);
            redTeamScore = itemView.findViewById(R.id.red_team_score);
        }

    }
}
