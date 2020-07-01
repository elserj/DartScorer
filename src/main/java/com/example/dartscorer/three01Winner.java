package com.example.dartscorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class three01Winner extends AppCompatActivity {
    private String NAME, GAME;
    private Integer ROUNDS;
    private Boolean doubleIn, doubleOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three01_winner);

         // Get the game mode and number of players
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        NAME = extras.getString("EXTRA_NAME");
        ROUNDS = extras.getInt("EXTRA_ROUNDS");
        GAME = extras.getString("EXTRA_GAME");
        doubleIn = extras.getBoolean("EXTRA_DOUBLEIN");
        doubleOut = extras.getBoolean("EXTRA_DOUBLEOUT");

        TextView winnerName = (TextView)findViewById(R.id.three01winnerName);
        winnerName.setText(NAME);
        TextView winnerRounds = (TextView)findViewById(R.id.three01winnerRounds);
        winnerRounds.setText(ROUNDS.toString());
        Button btn = (Button)findViewById(R.id.three01WinnerDone);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });
    }
    private void restartGame() {
        Intent intent = new Intent(this, threezeroone.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_GAME", GAME);
        if(doubleIn) {
            extras.putBoolean("EXTRA_DOUBLEIN", true);
        }
        if(doubleOut) {
            extras.putBoolean("EXTRA_DOUBLEOUT", true);
        }
        intent.putExtras(extras);
        startActivity(intent);
    }
}
