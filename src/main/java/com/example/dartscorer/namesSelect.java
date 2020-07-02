package com.example.dartscorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class namesSelect extends AppCompatActivity {
    private String GAME;
    private Integer numPlayers;
    private ListView mplayerList;
    ArrayList<String> mPlayerNames;
    private CustomAdapter customAdapter;
    public ArrayList<EditModel> editModelArrayList;
    private Button btnstartGame;
    private Class nextActivity;
    private CheckBox doubleIn, doubleOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names_select);

        // Get the game mode and number of players
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        GAME = extras.getString("EXTRA_GAME");
        numPlayers = extras.getInt("EXTRA_NUM_PLAYERS");
        TextView textView = (TextView) findViewById(R.id.textNamesGame);
        textView.setText("Game chosen is: "+GAME);

        // Choose class for next activity based on GAME mode chosen
        if(GAME.equals("Cricket")) {
            nextActivity = Cricket.class;
        }else if(GAME.equals("301") || GAME.equals("501") || GAME.equals("701")) {
            nextActivity = threezeroone.class;
            LinearLayout doubleLayout = findViewById(R.id.lldouble);
            doubleLayout.setVisibility(View.VISIBLE);
            doubleIn = findViewById(R.id.cbDoublein);
            doubleOut = findViewById(R.id.cbDoubleout);
        }

        mplayerList = (ListView) findViewById(R.id.lvplayerNames);
        btnstartGame = (Button) findViewById(R.id.btnstartGame);

        editModelArrayList = populateList(numPlayers);
        customAdapter = new CustomAdapter(this,editModelArrayList);
        mplayerList.setAdapter(customAdapter);


        btnstartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(namesSelect.this, nextActivity);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_GAME", GAME);
                if(!GAME.equals("Cricket")) {
                    if (doubleIn.isChecked()) {
                        extras.putBoolean("EXTRA_DOUBLEIN", true);
                    }
                    if (doubleOut.isChecked()) {
                        extras.putBoolean("EXTRA_DOUBLEOUT", true);
                    }
                }
                intent.putExtras(extras);
                startActivity(intent);
            }
        });


    }
    private ArrayList<EditModel> populateList(int numPlayers) {
        ArrayList<EditModel> list = new ArrayList<>();
        for(int i=1; i<=numPlayers; i++) {
            EditModel editModel = new EditModel();
            editModel.setEditTextValue(String.valueOf("Player "+i));
            list.add(editModel);
        }
        return list;
    }

}

