package com.example.dartscorer;

import android.graphics.Color;
import android.view.View;

// Class to hold all info on each player
public class CricketPlayer {
    private String namePlayer;
    private int num20, num19, num18, num17, num16, num15, numBull, score;
    private boolean turn;
    private View playerView;
    int ltblue = Color.argb(30, 0, 0, 255);
    int white = Color.argb(0, 0, 0, 0);

    public CricketPlayer(String name, View view, boolean turn) {
        namePlayer = name;
        num20 = 0;
        num19 = 0;
        num18 = 0;
        num17 = 0;
        num16 = 0;
        num15 = 0;
        numBull = 0;
        score = 0;
        this.turn = turn;
        playerView = view;

    }

    public View getPlayerView() {
        return this.playerView;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(View v, boolean b) {
        v.findViewById(R.id.ibtnCricket15).setEnabled(b);
        v.findViewById(R.id.ibtnCricket16).setEnabled(b);
        v.findViewById(R.id.ibtnCricket17).setEnabled(b);
        v.findViewById(R.id.ibtnCricket18).setEnabled(b);
        v.findViewById(R.id.ibtnCricket19).setEnabled(b);
        v.findViewById(R.id.ibtnCricket20).setEnabled(b);
        v.findViewById(R.id.ibtnCricketBull).setEnabled(b);
        if(b) {
            v.findViewById(R.id.tvCricketNames).setBackgroundColor(ltblue);
            v.findViewById(R.id.btnDone).setEnabled(true);
            v.findViewById(R.id.btnDone).setVisibility(View.VISIBLE);
        }else{
            v.findViewById(R.id.tvCricketNames).setBackgroundColor(white);
        v.findViewById(R.id.btnDone).setEnabled(false);
        v.findViewById(R.id.btnDone).setVisibility(View.GONE);
        }

        this.turn = b;
    }

    public int getScore() {
        return score;
    }

    public int getNum15() {
        return num15;
    }

    public int getNum16() {
        return num16;
    }

    public int getNum17() {
        return num17;
    }

    public int getNum18() {
        return num18;
    }

    public int getNum19() {
        return num19;
    }

    public int getNum20() {
        return num20;
    }

    public int getNumBull() {
        return numBull;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public void incrementDart(String number) {
        if(number == "num20") {
            this.num20++;
        }else if(number == "num19") {
            this.num19++;
        }else if(number == "num18") {
            this.num18++;
        }else if(number == "num17") {
            this.num17++;
        }else if(number == "num16") {
            this.num16++;
        }else if(number == "num15") {
            this.num15++;
        }else if(number == "numBull") {
            this.numBull++;
        }

    }

    public void setScore(int number) {
        this.score = number;
    }


}

