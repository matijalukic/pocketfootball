package pocketfootball.matija.etf.bg.ac.rs.pocketfootball;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final static String PLAYER_ONE_ID = "player_one_name";
    final static String PLAYER_TWO_ID = "player_two_name";

    EditText editPlayerOne;
    EditText editPlayerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // link the edit texts
        editPlayerOne = findViewById(R.id.player_one_name);
        editPlayerTwo = findViewById(R.id.player_two_name);


        // restore state
        if(savedInstanceState != null){
            editPlayerOne.setText(savedInstanceState.getString(PLAYER_ONE_ID));
            editPlayerTwo.setText(savedInstanceState.getString(PLAYER_TWO_ID));
        }
    }

    public void startGame(View view) {
        if(editPlayerOne.getText().toString().isEmpty() || editPlayerTwo.getText().toString().isEmpty()){
            Toast.makeText(this, R.string.players_validation, Toast.LENGTH_SHORT).show();

            return;
        }

        Intent startGame = new Intent(this, GameActivity.class);
        startGame.putExtra(PLAYER_ONE_ID, editPlayerOne.getText().toString());
        startGame.putExtra(PLAYER_TWO_ID, editPlayerTwo.getText().toString());

        startActivity(startGame);
    }

    // Save When destory
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(PLAYER_ONE_ID, editPlayerOne.getText().toString());
        outState.putString(PLAYER_TWO_ID, editPlayerTwo.getText().toString());
    }



    public void viewResults(View view){
        Intent viewResults = new Intent(this, MatchesActivity.class);
        startActivity(viewResults);
    }
}
