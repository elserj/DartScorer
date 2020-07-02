package com.example.dartscorer;

import androidx.annotation.ColorInt;
import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cricket extends Activity {

    //private TextView mplayerList;
    private ArrayList<String> mplayerNames = new ArrayList<String>();
    int ltblue = Color.argb(30, 0, 0, 255);
    int white = Color.argb(0, 0, 0, 0);
    public Integer counter =0;
    public static boolean turn;
    // scoreStack will store the darts as they are entered for the undo function
    public ArrayList<String> scoreStack = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket);

        //  mplayerList = (TextView) findViewById(R.id.tvCricket);
        for (int i = 0; i < CustomAdapter.editModelArrayList.size(); i++) {
            //mplayerList.setText(mplayerList.getText() + " " + CustomAdapter.editModelArrayList.get(i).getEditTextValue()+System.getProperty("line.separator"));
            mplayerNames.add(CustomAdapter.editModelArrayList.get(i).getEditTextValue());
        }
        int nameSize = mplayerNames.size();
        //mplayerList.setText(String.valueOf(nameSize));

        // Call the cricket layout from activity_cricket.xml
        LinearLayout cricketLayout = (LinearLayout) findViewById(R.id.cricket_main_layout);
        // create a view to inflate the cricket_rows.xml
        View view = getLayoutInflater().inflate(R.layout.cricket_rows, cricketLayout, false);
        // This add the far right column, to act like a legend
        cricketLayout.addView(view);

        View childView;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // player array
        List<CricketPlayer> cricketPlayerArrayList = new ArrayList<CricketPlayer>();

        // Below loop is only on initialization
        for (int i = 0; i < nameSize; i++) {
            // loop through and add a new view for each player, blank each imagebutton
            childView = inflater.inflate(R.layout.cricket_rows, null);
            TextView nameText = (TextView) childView.findViewById(R.id.tvCricketNames);
            nameText.setText(mplayerNames.get(i));
            ImageButton dart20 = (ImageButton) childView.findViewById(R.id.ibtnCricket20);
            dart20.setImageResource(R.drawable.darts_blank);
            //dart20.setOnClickListener(this);
            ImageButton dart19 = (ImageButton) childView.findViewById(R.id.ibtnCricket19);
            dart19.setImageResource(R.drawable.darts_blank);
            //dart19.setOnClickListener(this);
            ImageButton dart18 = (ImageButton) childView.findViewById(R.id.ibtnCricket18);
            dart18.setImageResource(R.drawable.darts_blank);
            //dart18.setOnClickListener(this);
            ImageButton dart17 = (ImageButton) childView.findViewById(R.id.ibtnCricket17);
            dart17.setImageResource(R.drawable.darts_blank);
            //dart17.setOnClickListener(this);
            ImageButton dart16 = (ImageButton) childView.findViewById(R.id.ibtnCricket16);
            dart16.setImageResource(R.drawable.darts_blank);
            //dart16.setOnClickListener(this);
            ImageButton dart15 = (ImageButton) childView.findViewById(R.id.ibtnCricket15);
            dart15.setImageResource(R.drawable.darts_blank);
            //dart15.setOnClickListener(this);
            ImageButton dartbull = (ImageButton) childView.findViewById(R.id.ibtnCricketBull);
            dartbull.setImageResource(R.drawable.darts_blank);
            //dartbull.setOnClickListener(this);
            TextView scoreView = (TextView) childView.findViewById(R.id.tvCricketScore);
            scoreView.setText("0");

            cricketLayout.addView(childView);

            if (i == 0) {
                childView.findViewById(R.id.tvCricketNames).setBackgroundColor(ltblue);
                childView.findViewById(R.id.btnDone).setEnabled(true);
                childView.findViewById(R.id.btnDone).setVisibility(View.VISIBLE);
                childView.findViewById(R.id.btnUndo).setEnabled(false);
                childView.findViewById(R.id.btnUndo).setVisibility(View.VISIBLE);
                cricketPlayerArrayList.add(new CricketPlayer(mplayerNames.get(i), childView, true));
            } else {
                childView.findViewById(R.id.ibtnCricketBull).setEnabled(false);
                childView.findViewById(R.id.ibtnCricket20).setEnabled(false);
                childView.findViewById(R.id.ibtnCricket19).setEnabled(false);
                childView.findViewById(R.id.ibtnCricket18).setEnabled(false);
                childView.findViewById(R.id.ibtnCricket17).setEnabled(false);
                childView.findViewById(R.id.ibtnCricket16).setEnabled(false);
                childView.findViewById(R.id.ibtnCricket15).setEnabled(false);
                cricketPlayerArrayList.add(new CricketPlayer(mplayerNames.get(i), childView, false));

            }
        }

        // Move on to the actual game
        playGame(cricketPlayerArrayList);
    }


    class myOnClickHandler implements View.OnClickListener {
        private CricketPlayer player;
        private View playerView;

        public myOnClickHandler(CricketPlayer player){
            this.player = player;
            // Need to get the full View for the player
            playerView = player.getPlayerView();
        }


        @Override
        public void onClick(View v) {
            // Logic for each dart button
            Integer number;
            ImageButton btn;
            // If a dart image button has been clicked, enable the undo button
            playerView.findViewById(R.id.btnUndo).setEnabled(true);
            switch (v.getId()) {
                case R.id.ibtnCricket15:
                    number = player.getNum15();
                    btn = v.findViewById(R.id.ibtnCricket15);
                    if (number == 0) {
                        btn.setImageResource(R.drawable.darts_single);
                        scoreStack.add("num15");
                    } else if (number == 1) {
                        btn.setImageResource(R.drawable.darts_double);
                        scoreStack.add("num15");
                    } else if (number == 2) {
                        btn.setImageResource(R.drawable.darts_triple);
                        scoreStack.add("num15");
                    } else if (number == 3){
                        Integer score = player.getScore();
                        player.setScore(score + 15);
                        score = player.getScore();
                        TextView scoreView = playerView.findViewById(R.id.tvCricketScore);
                        scoreView.setText(score.toString());
                        scoreStack.add("15");
                        break;
                    }
                    player.incrementDart("num15");
                    break;
                case R.id.ibtnCricket16:
                    number = player.getNum16();
                    btn = v.findViewById(R.id.ibtnCricket16);
                    if (number == 0) {
                        btn.setImageResource(R.drawable.darts_single);
                        scoreStack.add("num16");
                    } else if (number == 1) {
                        btn.setImageResource(R.drawable.darts_double);
                        scoreStack.add("num16");
                    } else if (number == 2) {
                        btn.setImageResource(R.drawable.darts_triple);
                        scoreStack.add("num16");
                    } else if (number == 3){
                        Integer score = player.getScore();
                        player.setScore(score + 16);
                        score = player.getScore();
                        TextView scoreView = playerView.findViewById(R.id.tvCricketScore);
                        scoreView.setText(score.toString());
                        scoreStack.add("16");
                        break;
                    }
                    player.incrementDart("num16");
                    break;
                case R.id.ibtnCricket17:
                    number = player.getNum17();
                    btn = v.findViewById(R.id.ibtnCricket17);
                    if (number == 0) {
                        btn.setImageResource(R.drawable.darts_single);
                        scoreStack.add("num17");
                    } else if (number == 1) {
                        btn.setImageResource(R.drawable.darts_double);
                        scoreStack.add("num17");
                    } else if (number == 2) {
                        btn.setImageResource(R.drawable.darts_triple);
                        scoreStack.add("num17");
                    } else if (number == 3){
                        Integer score = player.getScore();
                        player.setScore(score + 17);
                        score = player.getScore();
                        TextView scoreView = playerView.findViewById(R.id.tvCricketScore);
                        scoreView.setText(score.toString());
                        scoreStack.add("17");
                        break;
                    }
                    player.incrementDart("num17");
                    break;
                case R.id.ibtnCricket18:
                    number = player.getNum18();
                    btn = v.findViewById(R.id.ibtnCricket18);
                    if (number == 0) {
                        btn.setImageResource(R.drawable.darts_single);
                        scoreStack.add("num18");
                    } else if (number == 1) {
                        btn.setImageResource(R.drawable.darts_double);
                        scoreStack.add("num18");
                    } else if (number == 2) {
                        btn.setImageResource(R.drawable.darts_triple);
                        scoreStack.add("num18");
                    } else if (number == 3){
                        Integer score = player.getScore();
                        player.setScore(score + 18);
                        score = player.getScore();
                        TextView scoreView = playerView.findViewById(R.id.tvCricketScore);
                        scoreView.setText(score.toString());
                        scoreStack.add("18");
                        break;
                    }
                    player.incrementDart("num18");
                    break;
                case R.id.ibtnCricket19:
                    number = player.getNum19();
                    btn = v.findViewById(R.id.ibtnCricket19);
                    if (number == 0) {
                        btn.setImageResource(R.drawable.darts_single);
                        scoreStack.add("num19");
                    } else if (number == 1) {
                        btn.setImageResource(R.drawable.darts_double);
                        scoreStack.add("num19");
                    } else if (number == 2) {
                        btn.setImageResource(R.drawable.darts_triple);
                        scoreStack.add("num19");
                    } else if (number == 3){
                        Integer score = player.getScore();
                        player.setScore(score + 19);
                        score = player.getScore();
                        TextView scoreView = playerView.findViewById(R.id.tvCricketScore);
                        scoreView.setText(score.toString());
                        scoreStack.add("19");
                        break;
                    }
                    player.incrementDart("num19");
                    break;
                case R.id.ibtnCricket20:
                    number = player.getNum20();
                    btn = v.findViewById(R.id.ibtnCricket20);
                    if (number == 0) {
                        btn.setImageResource(R.drawable.darts_single);
                        scoreStack.add("num20");
                    } else if (number == 1) {
                        btn.setImageResource(R.drawable.darts_double);
                        scoreStack.add("num20");
                    } else if (number == 2) {
                        btn.setImageResource(R.drawable.darts_triple);
                        scoreStack.add("num20");
                    } else if (number == 3){
                        Integer score = player.getScore();
                        player.setScore(score + 20);
                        score = player.getScore();
                        TextView scoreView = playerView.findViewById(R.id.tvCricketScore);
                        scoreView.setText(score.toString());
                        scoreStack.add("num20");
                        break;
                    }
                    player.incrementDart("num20");
                    break;
                case R.id.ibtnCricketBull:
                    number = player.getNumBull();
                    btn = v.findViewById(R.id.ibtnCricketBull);
                    if (number == 0) {
                        btn.setImageResource(R.drawable.darts_single);
                        scoreStack.add("numBull");
                    } else if (number == 1) {
                        btn.setImageResource(R.drawable.darts_double);
                        scoreStack.add("numBull");
                    } else if (number == 2) {
                        btn.setImageResource(R.drawable.darts_triple);
                        scoreStack.add("numBull");
                    } else if (number == 3){
                        Integer score = player.getScore();
                        player.setScore(score + 25);
                        score = player.getScore();
                        TextView scoreView = playerView.findViewById(R.id.tvCricketScore);
                        scoreView.setText(score.toString());
                        scoreStack.add("25");
                        break;
                    }
                    player.incrementDart("numBull");
                    break;
            }

        }
    }





    // begin game
    // Need to start in new runnable and thread to not block main (UI) thread
    public void playGame(List<CricketPlayer> cricketPlayerList) {
        playGameRunnable runnable = new playGameRunnable(cricketPlayerList);
        new Thread(runnable).start();
    }

    class playGameRunnable implements Runnable {
        boolean gameOn = true;
        View currPlayerView;
        List<CricketPlayer> cricketPlayerList;
        // Initialize a max score to keep track across turns.
        Integer maxScore = 0;

        private Object lock = new Object();

        playGameRunnable(List<CricketPlayer> cricketPlayerList) {
            this.cricketPlayerList = cricketPlayerList;
        }

        // Actual logic for game goes here
        public void run() {


            // Run until there is a winner
            while(gameOn) {
                final Integer playerNumber = counter % cricketPlayerList.size();
                currPlayerView = cricketPlayerList.get(playerNumber).getPlayerView();
                // Clean up from the previous turn
                Integer prevplayerNumber = (counter + cricketPlayerList.size() - 1) % cricketPlayerList.size();
                View prevPlayerView = cricketPlayerList.get(prevplayerNumber).getPlayerView();
                endTurn(prevPlayerView, cricketPlayerList.get(prevplayerNumber));
                turn = true;
                while (turn) {
                    final CricketPlayer currPlayer = cricketPlayerList.get(playerNumber);
                    startTurn(currPlayerView, currPlayer);
                    Button doneButton = currPlayerView.findViewById(R.id.btnDone);

                    doneButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            synchronized (lock) {
                                lock.notifyAll();
                                turn = false;
                            }
                        }

                    });
                    // If the player clicks undo, undo the last thing in the scoreStack
                    Button undoButton = currPlayerView.findViewById(R.id.btnUndo);
                    undoButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            synchronized (lock) {
                                lock.notify();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(scoreStack.size() >= 1) {
                                            if(scoreStack.size() == 1) {
                                                currPlayerView.findViewById(R.id.btnUndo).setEnabled(false);
                                            }
                                            String lastMove = scoreStack.remove(scoreStack.size() - 1);
                                            if (lastMove.equals("num15")) {
                                                currPlayer.decrementDart("num15");
                                                ImageButton currButton = currPlayerView.findViewById(R.id.ibtnCricket15);
                                                if (currPlayer.getNum15() == 0) {
                                                    currButton.setImageResource(R.drawable.darts_blank);
                                                } else if (currPlayer.getNum15() == 1) {
                                                    currButton.setImageResource(R.drawable.darts_single);
                                                } else if (currPlayer.getNum15() == 2) {
                                                    currButton.setImageResource(R.drawable.darts_double);
                                                }
                                            } else if (lastMove.equals("num16")) {
                                                currPlayer.decrementDart("num16");
                                                ImageButton currButton = currPlayerView.findViewById(R.id.ibtnCricket16);
                                                if (currPlayer.getNum16() == 0) {
                                                    currButton.setImageResource(R.drawable.darts_blank);
                                                } else if (currPlayer.getNum16() == 1) {
                                                    currButton.setImageResource(R.drawable.darts_single);
                                                } else if (currPlayer.getNum16() == 2) {
                                                    currButton.setImageResource(R.drawable.darts_double);
                                                }
                                            } else if (lastMove.equals("num17")) {
                                                currPlayer.decrementDart("num17");
                                                ImageButton currButton = currPlayerView.findViewById(R.id.ibtnCricket17);
                                                if (currPlayer.getNum17() == 0) {
                                                    currButton.setImageResource(R.drawable.darts_blank);
                                                } else if (currPlayer.getNum17() == 1) {
                                                    currButton.setImageResource(R.drawable.darts_single);
                                                } else if (currPlayer.getNum17() == 2) {
                                                    currButton.setImageResource(R.drawable.darts_double);
                                                }
                                            } else if (lastMove.equals("num18")) {
                                                currPlayer.decrementDart("num18");
                                                ImageButton currButton = currPlayerView.findViewById(R.id.ibtnCricket18);
                                                if (currPlayer.getNum18() == 0) {
                                                    currButton.setImageResource(R.drawable.darts_blank);
                                                } else if (currPlayer.getNum18() == 1) {
                                                    currButton.setImageResource(R.drawable.darts_single);
                                                } else if (currPlayer.getNum18() == 2) {
                                                    currButton.setImageResource(R.drawable.darts_double);
                                                }
                                            } else if (lastMove.equals("num19")) {
                                                currPlayer.decrementDart("num19");
                                                ImageButton currButton = currPlayerView.findViewById(R.id.ibtnCricket19);
                                                if (currPlayer.getNum19() == 0) {
                                                    currButton.setImageResource(R.drawable.darts_blank);
                                                } else if (currPlayer.getNum19() == 1) {
                                                    currButton.setImageResource(R.drawable.darts_single);
                                                } else if (currPlayer.getNum19() == 2) {
                                                    currButton.setImageResource(R.drawable.darts_double);
                                                }
                                            } else if (lastMove.equals("num20")) {
                                                currPlayer.decrementDart("num20");
                                                ImageButton currButton = currPlayerView.findViewById(R.id.ibtnCricket20);
                                                if (currPlayer.getNum20() == 0) {
                                                    currButton.setImageResource(R.drawable.darts_blank);
                                                } else if (currPlayer.getNum20() == 1) {
                                                    currButton.setImageResource(R.drawable.darts_single);
                                                } else if (currPlayer.getNum20() == 2) {
                                                    currButton.setImageResource(R.drawable.darts_double);
                                                }
                                            } else if (lastMove.equals("numBull")) {
                                                currPlayer.decrementDart("numBull");
                                                ImageButton currButton = currPlayerView.findViewById(R.id.ibtnCricketBull);
                                                if (currPlayer.getNumBull() == 0) {
                                                    currButton.setImageResource(R.drawable.darts_blank);
                                                } else if (currPlayer.getNumBull() == 1) {
                                                    currButton.setImageResource(R.drawable.darts_single);
                                                } else if (currPlayer.getNumBull() == 2) {
                                                    currButton.setImageResource(R.drawable.darts_double);
                                                }
                                            } else {
                                                Integer currScore = currPlayer.getScore();
                                                Integer subtract = Integer.parseInt(lastMove);
                                                currPlayer.setScore(currScore - subtract);
                                                currScore = currPlayer.getScore();
                                                TextView scoreView = currPlayerView.findViewById(R.id.tvCricketScore);
                                                scoreView.setText(currScore.toString());
                                            }
                                        }
                                    }
                                });
                            }

                        }
                    });
                    synchronized (lock) {
                        try {
                            while(turn) {lock.wait();}
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

        // Method for initializing stuff on turn
        public void startTurn(View currPlayerView, CricketPlayer player) {
            // Need to make a temporary View variable to store currPlayerView so I can declare it final and run on UI thread
            final View tempView = currPlayerView;
            final CricketPlayer tempPlayer = player;

            scoreStack.clear();
            // turn on buttons
            runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  tempView.findViewById(R.id.tvCricketNames).setBackgroundColor(ltblue);
                                  tempView.findViewById(R.id.btnDone).setEnabled(true);
                                  tempView.findViewById(R.id.btnDone).setVisibility(View.VISIBLE);
                                  tempView.findViewById(R.id.btnUndo).setEnabled(false);
                                  tempView.findViewById(R.id.btnUndo).setVisibility(View.VISIBLE);
                                  tempView.findViewById(R.id.ibtnCricketBull).setEnabled(true);
                                  tempView.findViewById(R.id.ibtnCricket20).setEnabled(true);
                                  tempView.findViewById(R.id.ibtnCricket19).setEnabled(true);
                                  tempView.findViewById(R.id.ibtnCricket18).setEnabled(true);
                                  tempView.findViewById(R.id.ibtnCricket17).setEnabled(true);
                                  tempView.findViewById(R.id.ibtnCricket16).setEnabled(true);
                                  tempView.findViewById(R.id.ibtnCricket15).setEnabled(true);

                                  ImageButton dartbull = (ImageButton) tempView.findViewById(R.id.ibtnCricketBull);
                                  dartbull.setOnClickListener(new myOnClickHandler(tempPlayer));
                                  ImageButton dart20 = (ImageButton) tempView.findViewById(R.id.ibtnCricket20);
                                  dart20.setOnClickListener(new myOnClickHandler(tempPlayer));
                                  ImageButton dart19 = (ImageButton) tempView.findViewById(R.id.ibtnCricket19);
                                  dart19.setOnClickListener(new myOnClickHandler(tempPlayer));
                                  ImageButton dart18 = (ImageButton) tempView.findViewById(R.id.ibtnCricket18);
                                  dart18.setOnClickListener(new myOnClickHandler(tempPlayer));
                                  ImageButton dart17 = (ImageButton) tempView.findViewById(R.id.ibtnCricket17);
                                  dart17.setOnClickListener(new myOnClickHandler(tempPlayer));
                                  ImageButton dart16 = (ImageButton) tempView.findViewById(R.id.ibtnCricket16);
                                  dart16.setOnClickListener(new myOnClickHandler(tempPlayer));
                                  ImageButton dart15 = (ImageButton) tempView.findViewById(R.id.ibtnCricket15);
                                  dart15.setOnClickListener(new myOnClickHandler(tempPlayer));


                              }
                          });
        }

        // Method for stuff to do at end of turn
        public void endTurn(View currPlayerView, CricketPlayer player) {
            // Need to make a temporary View variable to store currPlayerView so I can declare it final and run on UI thread
            final View tempView = currPlayerView;
            // Things to do after turn is done
            // like turn buttons back off
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tempView.findViewById(R.id.tvCricketNames).setBackgroundColor(white);
                    tempView.findViewById(R.id.btnDone).setEnabled(false);
                    tempView.findViewById(R.id.btnDone).setVisibility(View.GONE);
                    tempView.findViewById(R.id.btnUndo).setEnabled(false);
                    tempView.findViewById(R.id.btnUndo).setVisibility(View.GONE);
                    tempView.findViewById(R.id.ibtnCricketBull).setEnabled(false);
                    tempView.findViewById(R.id.ibtnCricket20).setEnabled(false);
                    tempView.findViewById(R.id.ibtnCricket19).setEnabled(false);
                    tempView.findViewById(R.id.ibtnCricket18).setEnabled(false);
                    tempView.findViewById(R.id.ibtnCricket17).setEnabled(false);
                    tempView.findViewById(R.id.ibtnCricket16).setEnabled(false);
                    tempView.findViewById(R.id.ibtnCricket15).setEnabled(false);
                    counter++;
                }
            });


            // Check if score is largest and change maxScore if it is
            if(player.getScore() > maxScore) {
                maxScore = player.getScore();
            }

            // Check to see if there is a winner
            if((player.getNumBull() == 3) && (player.getNum15() == 3) && (player.getNum16() == 3) && (player.getNum17() == 3) && (player.getNum18() == 3) && (player.getNum19() == 3) && (player.getNum20() == 3) && (player.getScore() == maxScore)) {
                gameOn = false;
                Integer rounds = (int) Math.floor(counter / cricketPlayerList.size())+1;
                Intent intent = new Intent(Cricket.this, CricketWinner.class);
                Bundle extras = new Bundle();
                extras.putString("EXTRA_NAME", player.getNamePlayer());
                extras.putInt("EXTRA_SCORE", player.getScore());
                extras.putInt("EXTRA_ROUNDS",rounds);
                intent.putExtras(extras);
                startActivity(intent);
            }
        }
    }
}
