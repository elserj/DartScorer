package com.example.dartscorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CricketWinner extends AppCompatActivity {
    private String NAME;
    private Integer SCORE;
    private Integer ROUNDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_winner);

        // Get the game mode and number of players
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        NAME = extras.getString("EXTRA_NAME");
        SCORE = extras.getInt("EXTRA_SCORE");
        ROUNDS = extras.getInt("EXTRA_ROUNDS");

        TextView winnerName = (TextView)findViewById(R.id.cricketwinnerName);
        winnerName.setText(NAME);
        TextView winnerScore = (TextView)findViewById(R.id.cricketwinnerScore);
        winnerScore.setText(SCORE.toString());
        TextView winnerRounds = (TextView)findViewById(R.id.cricketwinnerRounds);
        winnerRounds.setText(ROUNDS.toString());
        Log.d("myTag", "score = "+SCORE+ "  rounds = "+ROUNDS);
    }
}
