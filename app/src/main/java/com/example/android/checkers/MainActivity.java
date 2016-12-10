package com.example.android.checkers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    float[][][] allCoordinate = new float[2][2][2];
    float width;
    float height;

    ImageView piece1;
    ImageView piece2;
    ImageView piece3;
    ImageView piece4;
    ImageView piece5;
    ImageView piece6;
    ImageView piece7;
    ImageView piece8;
    ImageView piece9;
    ImageView piece10;
    ImageView piece11;
    ImageView piece12;

    ImageView blackpiece1;
    ImageView blackpiece2;
    ImageView blackpiece3;
    ImageView blackpiece4;
    ImageView blackpiece5;
    ImageView blackpiece6;
    ImageView blackpiece7;
    ImageView blackpiece8;
    ImageView blackpiece9;
    ImageView blackpiece10;
    ImageView blackpiece11;
    ImageView blackpiece12;

    ImageView selectorSquare;

    static GameLogic gameLogic;

    TextView whiteScore;
    TextView blackScore;
    boolean winner = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.checkerBoard);
        textView = (TextView) findViewById(R.id.show);
        whiteScore = (TextView) findViewById(R.id.whiteScore);
        blackScore = (TextView) findViewById(R.id.blackScore);

        piece1 = (ImageView) findViewById(R.id.whitePiece1);
        piece2 = (ImageView) findViewById(R.id.whitePiece2);
        piece3 = (ImageView) findViewById(R.id.whitePiece3);
        piece4 = (ImageView) findViewById(R.id.whitePiece4);
        piece5 = (ImageView) findViewById(R.id.whitePiece5);
        piece6 = (ImageView) findViewById(R.id.whitePiece6);
        piece7 = (ImageView) findViewById(R.id.whitePiece7);
        piece8 = (ImageView) findViewById(R.id.whitePiece8);
        piece9 = (ImageView) findViewById(R.id.whitePiece9);
        piece10 = (ImageView) findViewById(R.id.whitePiece10);
        piece11 = (ImageView) findViewById(R.id.whitePiece11);
        piece12 = (ImageView) findViewById(R.id.whitePiece12);

        blackpiece1 = (ImageView) findViewById(R.id.blackPiece1);
        blackpiece2 = (ImageView) findViewById(R.id.blackPiece2);
        blackpiece3 = (ImageView) findViewById(R.id.blackPiece3);
        blackpiece4 = (ImageView) findViewById(R.id.blackPiece4);
        blackpiece5 = (ImageView) findViewById(R.id.blackPiece5);
        blackpiece6 = (ImageView) findViewById(R.id.blackPiece6);
        blackpiece7 = (ImageView) findViewById(R.id.blackPiece7);
        blackpiece8 = (ImageView) findViewById(R.id.blackPiece8);
        blackpiece9 = (ImageView) findViewById(R.id.blackPiece9);
        blackpiece10 = (ImageView) findViewById(R.id.blackPiece10);
        blackpiece11 = (ImageView) findViewById(R.id.blackPiece11);
        blackpiece12 = (ImageView) findViewById(R.id.blackPiece12);

        selectorSquare = (ImageView) findViewById(R.id.selectedSquare);

        gameLogic = new GameLogic(piece1, piece2, piece3, piece4,
                piece5, piece6, piece7, piece8, piece9, piece10, piece11,
                piece12, blackpiece1, blackpiece2, blackpiece3, blackpiece4,
                blackpiece5, blackpiece6, blackpiece7, blackpiece8, blackpiece9,
                blackpiece10, blackpiece11, blackpiece12, selectorSquare);

        textView.setText(gameLogic.getPlayer().getColor() + "'s turn!");
        whiteScore.setText("White score is: " + Integer.toString(gameLogic.getPlayer1().getScore()));
        blackScore.setText("Black score is: " + Integer.toString(gameLogic.getPlayer2().getScore()));

        initializeCoord();

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    int zone = areaClicked(event);

                    //textView.setText(Integer.toString(zone));
                    //textView.setText(Float.toString(width));



                    if (zone >= 0 && zone <= 63) {
                        winner = gameLogic.zoneClicked(zone);
                    }



                    if (winner) {
                        if (gameLogic.getPlayer().getColor().equals("white")) {
                            getGameLogic().getBoard().setWinner("black");
                        } else {
                            getGameLogic().getBoard().setWinner("white");
                        }
                    }



                    textView.setText(gameLogic.getPlayer().getColor() + "'s turn!");
                    whiteScore.setText("White score is: " + Integer.toString(gameLogic.getPlayer1().getScore()));
                    blackScore.setText("Black score is: " + Integer.toString(gameLogic.getPlayer2().getScore()));

                    if (winner) {
                        startActivity(new Intent(getApplicationContext(), Activity2.class));
                    }

                    if (!winner && (!gameLogic.getAbleToDoubleJump() || !gameLogic.getJustJumped())) {
                        if (gameLogic.getPlayer().getColor().equals("black")) {
                            winner = gameLogic.computerMove();
                        }




                        if (winner) {
                            if (gameLogic.getPlayer().getColor().equals("white")) {
                                getGameLogic().getBoard().setWinner("black");
                            } else {
                                getGameLogic().getBoard().setWinner("white");
                            }
                        }


                        textView.setText(gameLogic.getPlayer().getColor() + "'s turn!");
                        whiteScore.setText("White score is: " + Integer.toString(gameLogic.getPlayer1().getScore()));
                        blackScore.setText("Black score is: " + Integer.toString(gameLogic.getPlayer2().getScore()));

                        if (winner) {
                            startActivity(new Intent(getApplicationContext(), Activity2.class));
                        }
                    }

                }
                return true;
            }
        });


        Button resetButton =  (Button) findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup.MarginLayoutParams mlp0 = (ViewGroup.MarginLayoutParams) selectorSquare.getLayoutParams();
                mlp0.setMargins(mlp0.leftMargin, mlp0.topMargin, mlp0.rightMargin, 239);//all in pixels
                mlp0.setMarginStart(7);
                selectorSquare.setLayoutParams(mlp0);
                selectorSquare.setAlpha(0.0f);

                ViewGroup.MarginLayoutParams mlp1 = (ViewGroup.MarginLayoutParams) piece1.getLayoutParams();
                mlp1.setMargins(mlp1.leftMargin, mlp1.topMargin, mlp1.rightMargin, 239);//all in pixels
                mlp1.setMarginStart(7);
                piece1.setLayoutParams(mlp1);
                piece1.setImageResource(R.drawable.whitepiece);
                piece1.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp2 = (ViewGroup.MarginLayoutParams) piece2.getLayoutParams();
                mlp2.setMargins(mlp2.leftMargin, mlp2.topMargin, mlp2.rightMargin, 239);//all in pixels
                mlp2.setMarginStart(253);
                piece2.setLayoutParams(mlp2);
                piece2.setImageResource(R.drawable.whitepiece);
                piece2.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp3 = (ViewGroup.MarginLayoutParams) piece3.getLayoutParams();
                mlp3.setMargins(mlp3.leftMargin, mlp3.topMargin, mlp3.rightMargin, 239);//all in pixels
                mlp3.setMarginStart(499);
                piece3.setLayoutParams(mlp3);
                piece3.setImageResource(R.drawable.whitepiece);
                piece3.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp4 = (ViewGroup.MarginLayoutParams) piece4.getLayoutParams();
                mlp4.setMargins(mlp4.leftMargin, mlp4.topMargin, mlp4.rightMargin, 239);//all in pixels
                mlp4.setMarginStart(745);
                piece4.setLayoutParams(mlp4);
                piece4.setImageResource(R.drawable.whitepiece);
                piece4.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp5 = (ViewGroup.MarginLayoutParams) piece5.getLayoutParams();
                mlp5.setMargins(mlp5.leftMargin, mlp5.topMargin, mlp5.rightMargin, 360);//all in pixels
                mlp5.setMarginStart(133);
                piece5.setLayoutParams(mlp5);
                piece5.setImageResource(R.drawable.whitepiece);
                piece5.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp6 = (ViewGroup.MarginLayoutParams) piece6.getLayoutParams();
                mlp6.setMargins(mlp6.leftMargin, mlp6.topMargin, mlp6.rightMargin, 360);//all in pixels
                mlp6.setMarginStart(379);
                piece6.setLayoutParams(mlp6);
                piece6.setImageResource(R.drawable.whitepiece);
                piece6.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp7 = (ViewGroup.MarginLayoutParams) piece7.getLayoutParams();
                mlp7.setMargins(mlp7.leftMargin, mlp7.topMargin, mlp7.rightMargin, 360);//all in pixels
                mlp7.setMarginStart(625);
                piece7.setLayoutParams(mlp7);
                piece7.setImageResource(R.drawable.whitepiece);
                piece7.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp8 = (ViewGroup.MarginLayoutParams) piece8.getLayoutParams();
                mlp8.setMargins(mlp8.leftMargin, mlp8.topMargin, mlp8.rightMargin, 360);//all in pixels
                mlp8.setMarginStart(871);
                piece8.setLayoutParams(mlp8);
                piece8.setImageResource(R.drawable.whitepiece);
                piece8.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp9 = (ViewGroup.MarginLayoutParams) piece9.getLayoutParams();
                mlp9.setMargins(mlp9.leftMargin, mlp9.topMargin, mlp9.rightMargin, 481);//all in pixels
                mlp9.setMarginStart(7);
                piece9.setLayoutParams(mlp9);
                piece9.setImageResource(R.drawable.whitepiece);
                piece9.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp10 = (ViewGroup.MarginLayoutParams) piece10.getLayoutParams();
                mlp10.setMargins(mlp10.leftMargin, mlp10.topMargin, mlp10.rightMargin, 481);//all in pixels
                mlp10.setMarginStart(253);
                piece10.setLayoutParams(mlp10);
                piece10.setImageResource(R.drawable.whitepiece);
                piece10.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp11 = (ViewGroup.MarginLayoutParams) piece11.getLayoutParams();
                mlp11.setMargins(mlp11.leftMargin, mlp11.topMargin, mlp11.rightMargin, 481);//all in pixels
                mlp11.setMarginStart(499);
                piece11.setLayoutParams(mlp11);
                piece11.setImageResource(R.drawable.whitepiece);
                piece11.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams mlp12 = (ViewGroup.MarginLayoutParams) piece12.getLayoutParams();
                mlp12.setMargins(mlp12.leftMargin, mlp12.topMargin, mlp12.rightMargin, 481);//all in pixels
                mlp12.setMarginStart(745);
                piece12.setLayoutParams(mlp12);
                piece12.setImageResource(R.drawable.whitepiece);
                piece12.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp1 = (ViewGroup.MarginLayoutParams) blackpiece1.getLayoutParams();
                bmlp1.setMargins(bmlp1.leftMargin, bmlp1.topMargin, bmlp1.rightMargin, 1095);//all in pixels
                bmlp1.setMarginStart(133);
                blackpiece1.setLayoutParams(bmlp1);
                blackpiece1.setImageResource(R.drawable.blackpiece);
                blackpiece1.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp2 = (ViewGroup.MarginLayoutParams) blackpiece2.getLayoutParams();
                bmlp2.setMargins(bmlp2.leftMargin, bmlp2.topMargin, bmlp2.rightMargin, 1095);//all in pixels
                bmlp2.setMarginStart(379);
                blackpiece2.setLayoutParams(bmlp2);
                blackpiece2.setImageResource(R.drawable.blackpiece);
                blackpiece2.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp3 = (ViewGroup.MarginLayoutParams) blackpiece3.getLayoutParams();
                bmlp3.setMargins(bmlp3.leftMargin, bmlp3.topMargin, bmlp3.rightMargin, 1095);//all in pixels
                bmlp3.setMarginStart(625);
                blackpiece3.setLayoutParams(bmlp3);
                blackpiece3.setImageResource(R.drawable.blackpiece);
                blackpiece3.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp4 = (ViewGroup.MarginLayoutParams) blackpiece4.getLayoutParams();
                bmlp4.setMargins(bmlp4.leftMargin, bmlp4.topMargin, bmlp4.rightMargin, 1095);//all in pixels
                bmlp4.setMarginStart(871);
                blackpiece4.setLayoutParams(bmlp4);
                blackpiece4.setImageResource(R.drawable.blackpiece);
                blackpiece4.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp5 = (ViewGroup.MarginLayoutParams) blackpiece5.getLayoutParams();
                bmlp5.setMargins(bmlp5.leftMargin, bmlp5.topMargin, bmlp5.rightMargin, 974);//all in pixels
                bmlp5.setMarginStart(7);
                blackpiece5.setLayoutParams(bmlp5);
                blackpiece5.setImageResource(R.drawable.blackpiece);
                blackpiece5.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp6 = (ViewGroup.MarginLayoutParams) blackpiece6.getLayoutParams();
                bmlp6.setMargins(bmlp6.leftMargin, bmlp6.topMargin, bmlp6.rightMargin, 974);//all in pixels
                bmlp6.setMarginStart(253);
                blackpiece6.setLayoutParams(bmlp6);
                blackpiece6.setImageResource(R.drawable.blackpiece);
                blackpiece6.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp7 = (ViewGroup.MarginLayoutParams) blackpiece7.getLayoutParams();
                bmlp7.setMargins(bmlp7.leftMargin, bmlp7.topMargin, bmlp7.rightMargin, 974);//all in pixels
                bmlp7.setMarginStart(499);
                blackpiece7.setLayoutParams(bmlp7);
                blackpiece7.setImageResource(R.drawable.blackpiece);
                blackpiece7.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp8 = (ViewGroup.MarginLayoutParams) blackpiece8.getLayoutParams();
                bmlp8.setMargins(bmlp8.leftMargin, bmlp8.topMargin, bmlp8.rightMargin, 974);//all in pixels
                bmlp8.setMarginStart(745);
                blackpiece8.setLayoutParams(bmlp8);
                blackpiece8.setImageResource(R.drawable.blackpiece);
                blackpiece8.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp9 = (ViewGroup.MarginLayoutParams) blackpiece9.getLayoutParams();
                bmlp9.setMargins(bmlp9.leftMargin, bmlp9.topMargin, bmlp9.rightMargin, 853);//all in pixels
                bmlp9.setMarginStart(133);
                blackpiece9.setLayoutParams(bmlp9);
                blackpiece9.setImageResource(R.drawable.blackpiece);
                blackpiece9.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp10 = (ViewGroup.MarginLayoutParams) blackpiece10.getLayoutParams();
                bmlp10.setMargins(bmlp10.leftMargin, bmlp10.topMargin, bmlp10.rightMargin, 853);//all in pixels
                bmlp10.setMarginStart(379);
                blackpiece10.setLayoutParams(bmlp10);
                blackpiece10.setImageResource(R.drawable.blackpiece);
                blackpiece10.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp11 = (ViewGroup.MarginLayoutParams) blackpiece11.getLayoutParams();
                bmlp11.setMargins(bmlp11.leftMargin, bmlp11.topMargin, bmlp11.rightMargin, 853);//all in pixels
                bmlp11.setMarginStart(625);
                blackpiece11.setLayoutParams(bmlp11);
                blackpiece11.setImageResource(R.drawable.blackpiece);
                blackpiece11.setAlpha(1.0f);

                ViewGroup.MarginLayoutParams bmlp12 = (ViewGroup.MarginLayoutParams) blackpiece12.getLayoutParams();
                bmlp12.setMargins(bmlp12.leftMargin, bmlp12.topMargin, bmlp12.rightMargin, 853);//all in pixels
                bmlp12.setMarginStart(871);
                blackpiece12.setLayoutParams(bmlp12);
                blackpiece12.setImageResource(R.drawable.blackpiece);
                blackpiece12.setAlpha(1.0f);

                gameLogic = new GameLogic(piece1, piece2, piece3, piece4,
                        piece5, piece6, piece7, piece8, piece9, piece10, piece11,
                        piece12, blackpiece1, blackpiece2, blackpiece3, blackpiece4,
                        blackpiece5, blackpiece6, blackpiece7, blackpiece8, blackpiece9,
                        blackpiece10, blackpiece11, blackpiece12, selectorSquare);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private int areaClicked(MotionEvent event) {
        float xValue = event.getX();
        float yValue = event.getY();

        //textView.setText(Float.toString(yValue));

        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                float x = allCoordinate[0][0][0] + width/8*j;
                float y = allCoordinate[0][0][1] + height/8*i;
                if (xValue >= x && xValue <= x + width/8 &&
                        yValue >= y && yValue <= y + width/8) {
                    return counter;
                }
                counter++;
            }
        }

        return 999;
    }

    private void initializeCoord() {
        float left = (float) 6.0197754;
        float right = (float) 983.91284;
        float top = (float) 237.23438;
        float bottom = (float)1213.6992;

        float[] cornerOfImage = new float[2];
        cornerOfImage[0] = left;
        cornerOfImage[1] = top;



        width = right - left;
        height = bottom - top;

        allCoordinate[0][0][0] = cornerOfImage[0];
        allCoordinate[0][0][1] = cornerOfImage[1];
        allCoordinate[0][1][0] = cornerOfImage[0] + width;
        allCoordinate[0][1][1] = cornerOfImage[1];
        allCoordinate[1][0][0] = cornerOfImage[0];
        allCoordinate[1][0][1] = cornerOfImage[1] + height;
        allCoordinate[1][1][0] = cornerOfImage[0] + width;
        allCoordinate[1][1][1] = cornerOfImage[1] + height;

    }

    public static GameLogic getGameLogic() {
        return gameLogic;
    }
}
