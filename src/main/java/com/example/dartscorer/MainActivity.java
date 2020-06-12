package com.example.dartscorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.dartscorer.MESSAGE";
    private RadioGroup radioGame;
    Button btnplayerSelect;
    private String GAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGame = findViewById(R.id.radioGame);
        btnplayerSelect = findViewById(R.id.btnPlayerSelect);
        btnplayerSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radioGame.getCheckedRadioButtonId();
                findRadioButton(checkedId);
                openplayerSelect();

            }
        });
    }

    private void findRadioButton(int checkedId) {
        switch (checkedId) {
            case R.id.radioButton:
                GAME = "Cricket";
                break;
            case R.id.radioButton1:
                GAME = "301";
                break;
        }
    }
    public void openplayerSelect() {
        Intent intent = new Intent(this, playerSelect.class);
        intent.putExtra(EXTRA_MESSAGE, GAME);
        startActivity(intent);
    }
}
