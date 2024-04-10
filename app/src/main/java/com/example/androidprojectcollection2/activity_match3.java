package com.example.androidprojectcollection2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;



public class activity_match3 extends AppCompatActivity {

    Integer[] colors = new Integer[]{Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE};
    Button[][] buttons;
    Toast toast;
    int[][] position = new int[5][5];
    Button firstClickedButton = null;
    Button btnrestart;
    TextView scoreText;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match3);

        score = 0;

        buttons = new Button[][]{
                {findViewById(R.id.matchbtn1), findViewById(R.id.matchbtn2), findViewById(R.id.matchbtn3), findViewById(R.id.matchbtn4), findViewById(R.id.matchbtn5)},
                {findViewById(R.id.matchbtn6), findViewById(R.id.matchbtn7), findViewById(R.id.matchbtn8), findViewById(R.id.matchbtn9), findViewById(R.id.matchbtn10)},
                {findViewById(R.id.matchbtn11), findViewById(R.id.matchbtn12), findViewById(R.id.matchbtn13), findViewById(R.id.matchbtn14), findViewById(R.id.matchbtn15)},
                {findViewById(R.id.matchbtn16), findViewById(R.id.matchbtn17), findViewById(R.id.matchbtn18), findViewById(R.id.matchbtn19), findViewById(R.id.matchbtn20)},
                {findViewById(R.id.matchbtn21), findViewById(R.id.matchbtn22), findViewById(R.id.matchbtn23), findViewById(R.id.matchbtn24), findViewById(R.id.matchbtn25)}
        };

        btnrestart = findViewById(R.id.btnmatchRestart);
        btnrestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomize();
                score = 0;
                scoreText.setText("Score: " + score);
                firstClickedButton = null;
//                checkMatches();
            }
        });

        scoreText = findViewById(R.id.scoreText);

        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 5; b++) {
                final int row = a;
                final int col = b;
                buttons[row][col].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (firstClickedButton == null) {
                            firstClickedButton = (Button) view;
                        } else {
                            if (adjacent(firstClickedButton, (Button) view)) {
                                swap(firstClickedButton, (Button) view);
//                                checkMatches();
                            }
                            firstClickedButton = null;
                        }

                    }
                });
            }
        }

        randomize();
        checkMatches();
    }

    void randomize() {
        Random ran = new Random();
        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 5; b++) {
                int temp = ran.nextInt(4);
                position[a][b] = temp;
                buttons[a][b].setBackgroundColor(colors[temp]);
            }
        }


        checkMatches();
//        score = 0;

    }

    void swap(Button btn1, Button btn2) {
        int row1 = -1, col1 = -1, row2 = -1, col2 = -1;

        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 5; b++) {
                if (buttons[a][b] == btn1) {
                    row1 = a;
                    col1 = b;
                } else if (buttons[a][b] == btn2) {
                    row2 = a;
                    col2 = b;
                }
            }
        }

        int temp = position[row1][col1];
        position[row1][col1] = position[row2][col2];
        position[row2][col2] = temp;

        btn1.setBackgroundColor(colors[position[row1][col1]]);
        btn2.setBackgroundColor(colors[position[row2][col2]]);

        checkMatches();
    }

    public void checkMatches() {
        boolean matchHori = false;
        boolean matchVerti = false;
        int rowHori = -1, colHori = -1;  // To store the position for horizontal match
        int rowVerti = -1, colVerti = -1;  // To store the position for vertical match

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                int currentColor = position[row][col];
                if (currentColor == position[row][col + 1] && currentColor == position[row][col + 2]) {
                    matchHori = true;
                    rowHori = row;
                    colHori = col;

                }
            }
        }

        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 3; row++) {
                int currentColor = position[row][col];
                if (currentColor == position[row + 1][col] && currentColor == position[row + 2][col]) {
                    matchVerti = true;
                    rowVerti = row;
                    colVerti = col;

                }
            }
        }

        if (matchHori) {
            score++;
            scoreText.setText("Score: " + score);
            changeTiles(rowHori, colHori, rowHori, colHori + 1, rowHori, colHori + 2);
//            Toast.makeText(getApplicationContext(), "HORIZONTAL", Toast.LENGTH_SHORT).show();
        }

        if (matchVerti) {
            score++;
            scoreText.setText("Score: " + score);
            changeTiles(rowVerti, colVerti, rowVerti + 1, colVerti, rowVerti + 2, colVerti);
//            Toast.makeText(getApplicationContext(), "VERTICAL", Toast.LENGTH_SHORT).show();
        }

    }


    void changeTiles(int row1, int col1, int row2, int col2, int row3, int col3) {
        int tempColor1 = new Random().nextInt(4);
        int tempColor2 = new Random().nextInt(4);
        int tempColor3 = new Random().nextInt(4);

        position[row1][col1] = tempColor1;
        position[row2][col2] = tempColor2;
        position[row3][col3] = tempColor3;

        buttons[row1][col1].setBackgroundColor(colors[tempColor1]);
        buttons[row2][col2].setBackgroundColor(colors[tempColor2]);
        buttons[row3][col3].setBackgroundColor(colors[tempColor3]);

        checkMatches();
    }

    boolean adjacent(Button btn1, Button btn2) {
        int row1 = -1, col1 = -1, row2 = -1, col2 = -1;

        for (int a = 0; a < 5; a++) {
            for (int b = 0; b < 5; b++) {
                if (buttons[a][b] == btn1) {
                    row1 = a;
                    col1 = b;
                } else if (buttons[a][b] == btn2) {
                    row2 = a;
                    col2 = b;
                }
            }
        }

        if (((Math.abs(row1 - row2) == 1) && col1 == col2) || (Math.abs(col1 - col2) == 1) && row2 == row1) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Not Adjacent!", Toast.LENGTH_SHORT).show();
        }

        return false;
    }


}