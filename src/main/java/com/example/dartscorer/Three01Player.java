package com.example.dartscorer;


import android.view.View;

// Class to hold all info for 301 player
public class Three01Player {
    private String namePlayer;
    private int score;
    private View playerView;

    public Three01Player(String name, View view, String game) {
        namePlayer = name;
        score = Integer.parseInt(game);
        playerView = view;
    }

    public View getPlayerView() {
        return this.playerView;
    }

     public int getScore() {
        return score;
    }

    public void setScore(int number) {
        this.score = number;
    }

    public String getNamePlayer() {
        return namePlayer;
    }
}
