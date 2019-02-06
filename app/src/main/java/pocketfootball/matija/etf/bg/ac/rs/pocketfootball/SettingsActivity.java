package pocketfootball.matija.etf.bg.ac.rs.pocketfootball;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

public class SettingsActivity extends AppCompatActivity {
    public static final int DEFAULT_NUMBER_OF_GOALS = 10;
    public static final int DEFAULT_GAME_DURATION_SECONDS = 120;
    public static final int DEFAULT_GAME_SPEED = 0;
    public static final String DEFAULT_BACKGROUND_IMAGE_NAME = "grass";


    private EditText editNumberOfGoals, editGameDuration;
    private SeekBar editGameSpeed;
    private ImageView bgImageView;
    private List<String> bgImagesNames;
    private int bgImageIndex;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bgImagesNames = Arrays.asList(getResources().getStringArray(R.array.bg_images));


        // load settings
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // games
        editNumberOfGoals = findViewById(R.id.number_of_goals);
        editGameDuration = findViewById(R.id.number_of_seconds);
        editGameSpeed = findViewById(R.id.seek_game_speed);
        bgImageView = findViewById(R.id.background_image_view);


        // set default values
        int preferencesNumberOfGoals = sharedPreferences.getInt(getResources().getString(R.string.max_goals_key), DEFAULT_NUMBER_OF_GOALS);
        int preferencesGameSecondsDuration = sharedPreferences.getInt(getResources().getString(R.string.game_duration_key), DEFAULT_GAME_DURATION_SECONDS);
        int preferencesGameSpeed = sharedPreferences.getInt(getResources().getString(R.string.game_speed_key), DEFAULT_GAME_SPEED);
        String preferenceBgImageName = sharedPreferences.getString(getResources().getString(R.string.game_bg_image_key), DEFAULT_BACKGROUND_IMAGE_NAME);

        // set index of the bg current selected bg image
        bgImageIndex = bgImagesNames.indexOf(preferenceBgImageName);
        // set the bg image view
        bgImageView.setImageResource(getResources().getIdentifier(preferenceBgImageName, "drawable", getPackageName()));

        // change image on click
        bgImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bgImageIndex++;
                if(bgImageIndex >= bgImagesNames.size()) bgImageIndex = 0;

                String newBgImageName = bgImagesNames.get(bgImageIndex);

                bgImageView.setImageResource(getResources().getIdentifier(newBgImageName, "drawable", getPackageName()));

                SharedPreferences.Editor editPreferences = sharedPreferences.edit();
                editPreferences.putString(getResources().getString(R.string.game_bg_image_key), newBgImageName);
                editPreferences.apply();
            }
        });


        editNumberOfGoals.setText(String.valueOf(preferencesNumberOfGoals));
        editGameDuration.setText(String.valueOf(preferencesGameSecondsDuration));

        // set game speed progress
        editGameSpeed.setProgress(preferencesGameSpeed);


        // changing number of goals
        editNumberOfGoals.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    // save to the perferences
                    SharedPreferences.Editor editPreferences = sharedPreferences.edit();
                    editPreferences.putInt(getResources().getString(R.string.max_goals_key), Integer.parseInt(editable.toString()));
                    editPreferences.apply();

                    Toast.makeText(getApplicationContext(), "Number of goals changed!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // changing duration of the game
        editGameDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    // save to the perferences
                    SharedPreferences.Editor editPreferences = sharedPreferences.edit();
                    editPreferences.putInt(getResources().getString(R.string.game_duration_key), Integer.parseInt(editable.toString()));
                    editPreferences.apply();

                    Toast.makeText(getApplicationContext(), "Game duration changed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // set game speed
        editGameSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // save to the perferences
                SharedPreferences.Editor editPreferences = sharedPreferences.edit();
                editPreferences.putInt(getResources().getString(R.string.game_speed_key), progress);
                editPreferences.apply();

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
