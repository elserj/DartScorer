package com.example.dartscorer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class threezeroone extends Activity {

    private float X;
    private float Y;
    private int[] imgLocation = new int[2];
    private float[] imgSize = new float[2];
    private float[] imgCenter = new float[2];
    private float scaleFactor;

    private ArrayList<String> mplayerNames = new ArrayList<String>();
    int ltblue = Color.argb(30, 0, 0, 255);
    int white = Color.argb(0, 0, 0, 0);
    public Integer counter =0;
    public static boolean turn;
    public static boolean doubleIn, doubleOut;

    private String GAME;

    public ImageView imgView;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threezeroone);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        GAME = extras.getString("EXTRA_GAME");

        doubleIn = extras.getBoolean("EXTRA_DOUBLEIN");
        doubleOut = extras.getBoolean("EXTRA_DOUBLEOUT");

        if(doubleIn) {

            Log.d("myTag", "doubleIN");
        }
        if(doubleOut) {
            Log.d("TAG", "doubleOUT");
        }

        imgView = (ImageView) findViewById(R.id.dartboard_full);

        // Get the player names from the nameSelect activity
        for (int i = 0; i < CustomAdapter.editModelArrayList.size(); i++) {
            mplayerNames.add(CustomAdapter.editModelArrayList.get(i).getEditTextValue());
        }
        int nameSize = mplayerNames.size();

        // Call the 301 layout from activity_threezerone.xml
        LinearLayout three01Layout = (LinearLayout) findViewById(R.id.ll301Player);
        // create a view to inflate the three01_rows.xml
        View view = getLayoutInflater().inflate(R.layout.three01_rows, three01Layout, false);


        View childView;
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //player array
        List<Three01Player> three01PlayerList = new ArrayList<Three01Player>();

        // Loop for initialization
        for (int i =0; i< nameSize; i++) {
            childView = inflater.inflate(R.layout.three01_rows, null);
            TextView nameText = (TextView) childView.findViewById(R.id.tv301Names);
            nameText.setText(mplayerNames.get(i));

            TextView scoreText = (TextView) childView.findViewById(R.id.tv301Score);
            scoreText.setText(GAME);
            three01Layout.addView(childView);

            if (i == 0) {
                childView.findViewById(R.id.tv301Names).setBackgroundColor(ltblue);
                childView.findViewById(R.id.btn301Done).setEnabled(true);
                childView.findViewById(R.id.btn301Done).setVisibility(View.VISIBLE);
            }
            three01PlayerList.add(new Three01Player(mplayerNames.get(i),childView, GAME));
        }

        playGame(three01PlayerList);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void playGame(List<Three01Player> three01PlayerList) {

        imgView.getLocationOnScreen(imgLocation);
        ViewTreeObserver viewTreeObserver = imgView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imgView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                imgSize[0] = imgView.getWidth();
                imgSize[1] = imgView.getHeight();
            }
        });

        // If the clear button is pressed, clear out the scores
        Button clearButton = (Button) findViewById(R.id.clearScores);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvdart1 = (TextView) findViewById(R.id.dart1);
                TextView tvdart2 = (TextView) findViewById(R.id.dart2);
                TextView tvdart3 = (TextView) findViewById(R.id.dart3);
                tvdart1.setText("");
                tvdart2.setText("");
                tvdart3.setText("");
            }
        });

        // This will detect the touch event and figure out the dart from where it is first touched.
        imgView.setOnTouchListener(new View.OnTouchListener() {
                                       @Override
                                       public boolean onTouch(View v, MotionEvent event) {
                                           // Need to only get coordinates on first press, else we get a bunch of touches registered
                                           if(event.getAction() == MotionEvent.ACTION_DOWN) {
                                               X = event.getX();
                                               Y = event.getY();

                                               imgCenter[0] = (imgLocation[0] + imgSize[0]) / 2.0f;
                                               imgCenter[1] = (imgLocation[1] + imgSize[1]) / 2.0f;

                                               // Calculate the scaleFactor to multiply the distances by so it will scale on different screens
                                               // Needs to be divided by "300" because my initial testing was on a device with 600 wide screen
                                               scaleFactor = imgCenter[0]/300.0f;


                                               X = X - imgCenter[0];
                                               Y = Y - imgCenter[1];

                                               float radius = getRadius(X, Y);
                                               float theta = getTheta(X, Y);


                                               TextView tvdart1 = (TextView) findViewById(R.id.dart1);
                                               TextView tvdart2 = (TextView) findViewById(R.id.dart2);
                                               TextView tvdart3 = (TextView) findViewById(R.id.dart3);

                                               String currDart = "";

                                               if (tvdart1.getText().equals("")) {
                                                   currDart = "dart1";
                                               } else if (!tvdart1.getText().equals("") && tvdart2.getText().equals("")) {
                                                   currDart = "dart2";
                                               } else if (!tvdart2.getText().equals("") && tvdart3.getText().equals("")) {
                                                   currDart = "dart3";
                                               } else {
                                                   Log.d("Tag", "3 darts already recorded");
                                                   currDart = "Done";
                                               }

                                               if(!currDart.equals("Done")) {
                                                   int id = getResources().getIdentifier(currDart, "id", getPackageName());
                                                   TextView currdartView = (TextView) findViewById(id);
                                                   String sector = getSector(radius);

                                                   if (sector.equals("single_bull")) {
                                                       currdartView.setText(R.string.singleBull);
                                                   } else if (sector.equals("double_bull")) {
                                                       currdartView.setText(R.string.doubleBull);
                                                   }else {

                                                       // Else need to find the sector and number
                                                       String multiplier;
                                                       int score = getScore(theta);

                                                       if (sector.equals("inner_sector") || sector.equals("outer_sector")) {
                                                           multiplier = "single";
                                                           String text = multiplier + " " + score;
                                                           currdartView.setText(text);
                                                       } else if (sector.equals("double")) {
                                                           multiplier = "double";
                                                           String text = multiplier + " " + score;
                                                           currdartView.setText(text);
                                                       } else if (sector.equals("triple")) {
                                                           multiplier = "triple";
                                                           String text = multiplier + " " + score;
                                                           currdartView.setText(text);
                                                       } else if (sector.equals("miss")) {
                                                           String text = "miss";
                                                           currdartView.setText(text);
                                                       }
                                                   }
                                               }
                                           }
                                           return false;
                                       }
                                   });
        play301GameRunnable runnable = new play301GameRunnable(three01PlayerList);
        new Thread(runnable).start();
    }

    class play301GameRunnable implements Runnable {
        boolean gameOn = true;
        View currPlayerView;
        List<Three01Player> three01PlayerList;

        private Object lock = new Object();

        play301GameRunnable(List<Three01Player> three01PlayerList) {
            this.three01PlayerList = three01PlayerList;
        }

        public void run() {
            // Run until there is a winner
            while(gameOn) {
                final Integer playerNumber = counter % three01PlayerList.size();
                currPlayerView = three01PlayerList.get(playerNumber).getPlayerView();
                // Clean up from previous turn
                Integer prevplayerNumber = (counter + three01PlayerList.size() - 1) % three01PlayerList.size();
                View prevPlayerView = three01PlayerList.get(prevplayerNumber).getPlayerView();
                endTurn(prevPlayerView, three01PlayerList.get(prevplayerNumber));
                turn = true;
                while(turn) {
                    startTurn(currPlayerView, three01PlayerList.get(playerNumber));
                    Button doneButton = currPlayerView.findViewById(R.id.btn301Done);

                    doneButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            synchronized (lock) {
                                lock.notify();
                                turn = false;
                            }
                        }
                    });

                    synchronized (lock) {
                        try {
                            while (turn) {
                                lock.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

        }

        public void endTurn(View currPlayerView, final Three01Player player) {
            // Need to make a temporary View variable to store currPlayerView so I can declare it final and run on UI thread
            final View tempView = currPlayerView;
            // Things to do after turn is done
            // like turn buttons back off
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tempView.findViewById(R.id.btn301Done).setEnabled(false);
                    tempView.findViewById(R.id.btn301Done).setVisibility(View.GONE);
                    tempView.findViewById(R.id.tv301Names).setBackgroundColor(white);
                    TextView tvdart1 = (TextView) findViewById(R.id.dart1);
                    TextView tvdart2 = (TextView) findViewById(R.id.dart2);
                    TextView tvdart3 = (TextView) findViewById(R.id.dart3);

                    if(tvdart1.getText().toString().equals("")) {
                        tvdart1.setText("miss");
                    }

                    if(tvdart2.getText().toString().equals("")) {
                        tvdart2.setText("miss");
                    }

                    if(tvdart3.getText().toString().equals("")) {
                        tvdart3.setText("miss");
                    }

                    int cumulScore = player.getScore();
                    cumulScore -= computeDart(tvdart1.getText().toString());
                    cumulScore -= computeDart(tvdart2.getText().toString());
                    cumulScore -= computeDart(tvdart3.getText().toString());

                    // Check to see if negative and throw away if it is
                    if(cumulScore < 0) {
                        cumulScore = player.getScore();
                    }

                    // Check if winner
                    if(cumulScore == 0) {
                        gameOn = false;
                        Integer rounds = (int) Math.floor(counter / three01PlayerList.size())+1;
                        Intent intent = new Intent(threezeroone.this, three01Winner.class);
                        Bundle extras = new Bundle();
                        extras.putString("EXTRA_NAME", player.getNamePlayer());
                        extras.putInt("EXTRA_ROUNDS",rounds);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }

                    player.setScore(cumulScore);
                    String playerScore = Integer.toString(player.getScore());
                    TextView scoreView = tempView.findViewById(R.id.tv301Score);
                    scoreView.setText(playerScore);



                    tvdart1.setText("");
                    tvdart2.setText("");
                    tvdart3.setText("");
                    counter++;
                }
            });
        }

        public void startTurn(View currPlayerView, Three01Player player) {
            final View tempView = currPlayerView;
            final Three01Player tempPlayer = player;
            // turn on button
            runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   tempView.findViewById(R.id.btn301Done).setEnabled(true);
                   tempView.findViewById(R.id.btn301Done).setVisibility(View.VISIBLE);
                   tempView.findViewById(R.id.tv301Names).setBackgroundColor(ltblue);
               }
            });
        }


    }

    public int computeDart(String string) {
        int score;
        if(string.equals("Single Bull")) {
            score =25;
        }else if(string.equals("Double Bull")) {
            score = 50;
        }else if(string.equals("miss")) {
            score = 0;
        }else{
            int multiplier = 0;
            String[] split = string.split("\\s");
            if(split[0].equals("single")){
                multiplier = 1;
            }else if(split[0].equals("double")){
                multiplier = 2;
            }else if(split[0].equals("triple")){
                multiplier = 3;
            }
            score = multiplier * Integer.parseInt(split[1]);
        }
        return score;
    }


    public float getRadius (float x, float y) {
        float radius = (float) Math.sqrt(x*x + y*y);
        // divide radius by scaleFactor to get scaled version
        radius = radius / scaleFactor;
        return radius;
    }

    public float getTheta (float x, float y) {
        float theta = (float) Math.toDegrees(Math.atan2(y,x));
        if(theta < 0) {
            theta += 360;
        }
        return theta;
    }

    public String getSector(float radius) {
        String string;
        if(radius <= 20.0) {
            string = "double_bull";
        }else if (radius > 20.0 && radius <= 40.0) {
            string = "single_bull";
        }else if (radius > 40.0 && radius <= 140.0) {
            string = "inner_sector";
        }else if (radius > 140.0 && radius <= 170.0) {
            string = "triple";
        }else if (radius > 170.0 && radius <= 245.0) {
            string = "outer_sector";
        }else if (radius > 245.0 && radius <= 275.0) {
            string = "double";
        }else{
            string = "miss";
        }
        return string;
    }

    public int getScore(float theta) {
        int score = 0;
        if(theta >= 351.0 || theta < 9.0) {
            score = 6;
        }else if (theta >= 9.0 && theta < 27.0) {
            score = 10;
        }else if (theta >= 27.0 && theta < 45.0) {
            score = 15;
        }else if (theta >= 45.0 && theta < 63.0) {
            score = 2;
        }else if (theta >= 63.0 && theta < 81.0) {
            score = 17;
        }else if (theta >= 81.0 && theta < 99.0) {
            score = 3;
        }else if (theta >= 99.0 && theta < 117.0) {
            score = 19;
        }else if (theta >= 117.0 && theta < 135.0) {
            score = 7;
        }else if (theta >= 135.0 && theta < 153.0) {
            score = 16;
        }else if (theta >= 153.0 && theta < 171.0) {
            score = 8;
        }else if (theta >= 171.0 && theta < 189.0) {
            score = 11;
        }else if (theta >= 189.0 && theta < 207.0) {
            score = 14;
        }else if (theta >= 207.0 && theta < 225.0) {
            score = 9;
        }else if (theta >= 225.0 && theta < 243.0) {
            score = 12;
        }else if (theta >= 243.0 && theta < 261.0) {
            score = 5;
        }else if (theta >= 261.0 && theta < 279.0) {
            score = 20;
        }else if (theta >= 279.0 && theta < 297.0) {
            score = 1;
        }else if (theta >= 297.0 && theta < 315.0) {
            score = 18;
        }else if (theta >= 315.0 && theta < 333.0) {
            score = 4;
        }else if (theta >= 333.0 && theta < 351.0) {
            score = 13;
        }
        return score;
    }
}