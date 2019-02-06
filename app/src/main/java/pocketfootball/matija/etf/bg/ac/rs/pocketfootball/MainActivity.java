package pocketfootball.matija.etf.bg.ac.rs.pocketfootball;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static String PLAYER_ONE_ID = "player_one_name";
    final static String PLAYER_TWO_ID = "player_two_name";
    final static String PLAYER_TWO_IMAGE = "player_two_image";
    final static String PLAYER_ONE_IMAGE = "player_one_image";

    EditText editPlayerOne;
    EditText editPlayerTwo;

    ImageSwitcher imageSwitcherOne;
    ImageSwitcher imageSwitcherTwo;

    List<String> imageNames;
    int indexImagePlayerOne = 0;
    int indexImagePlayerTwo = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // fill the image names
        imageNames = Arrays.asList(getResources().getStringArray(R.array.team_images));


        // link the edit texts
        editPlayerOne = findViewById(R.id.player_one_name);
        editPlayerTwo = findViewById(R.id.player_two_name);


        imageSwitcherOne = findViewById(R.id.player_one_image);
        imageSwitcherTwo = findViewById(R.id.player_two_image);

        imageSwitcherOne.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });

        imageSwitcherTwo.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });

        imageSwitcherOne.setImageResource(getResources().getIdentifier(imageNames.get(indexImagePlayerOne), "drawable", getPackageName()));
        imageSwitcherTwo.setImageResource(getResources().getIdentifier(imageNames.get(indexImagePlayerTwo), "drawable", getPackageName()));


        // set image switch listeners
        imageSwitcherOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indexImagePlayerOne++;
                if(indexImagePlayerOne >= imageNames.size()) indexImagePlayerOne = 0;
                imageSwitcherOne.setImageResource(getResources().getIdentifier(imageNames.get(indexImagePlayerOne), "drawable", getPackageName()));
            }
        });

        imageSwitcherTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indexImagePlayerTwo++;
                if(indexImagePlayerTwo >= imageNames.size()) indexImagePlayerTwo = 0;
                imageSwitcherTwo.setImageResource(getResources().getIdentifier(imageNames.get(indexImagePlayerTwo), "drawable", getPackageName()));
            }
        });



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
        startGame.putExtra(PLAYER_ONE_IMAGE, imageNames.get(indexImagePlayerOne));
        startGame.putExtra(PLAYER_TWO_IMAGE, imageNames.get(indexImagePlayerTwo));

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

    public void viewSettings(View view) {
        Intent viewSettings = new Intent(this, SettingsActivity.class);
        startActivity(viewSettings);

    }
}
