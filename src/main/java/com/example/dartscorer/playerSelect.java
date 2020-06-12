package com.example.dartscorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class playerSelect extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.dartscorer.MESSAGE";
    private NumberPicker playerPicker;
    private int numPlayers;
    Button btnnamesSelect;
    private String GAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);


        Intent intent = getIntent();
        GAME = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.gameMode);
        textView.setText(GAME);
        playerPicker = findViewById(R.id.playerNumPicker);
        playerPicker.setMaxValue(6);
        playerPicker.setMinValue(1);
        // set default value for numPlayers otherwise if numberpicker is not moved, won't register.
        numPlayers = 1;
        playerPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                numPlayers = playerPicker.getValue();

            }
        });
        btnnamesSelect = findViewById(R.id.btnNamesSelect);
        btnnamesSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNamesSelect();
            }
        });
    }

    private void openNamesSelect() {
        Intent intent = new Intent(this, namesSelect.class);
        Bundle extras = new Bundle();
        extras.putString("EXTRA_GAME", GAME);
        extras.putInt("EXTRA_NUM_PLAYERS", numPlayers);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
