package com.earleydevelopmentwebdesign.tic_tac_connect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 0 = yellow and 1 = red
    int activePlayer = 0;
    boolean gameIsActive = true;
    //2 means unplayed position
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] gameWon = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};


    //The onclick dropIn method is applied via the layout activity_main.xml
    public void dropIn(View view){

        //specifying this empty view being clicked, no need to find any ID
        ImageView token = (ImageView) view;
        //tracking the space tapped by token tag number on xml
        int tappedToken = Integer.parseInt(token.getTag().toString());

        //Checking if the space tapped is open, if open set the token space to current active player
        if (gameState[tappedToken] == 2 && gameIsActive){
            gameState[tappedToken] = activePlayer;
            //animations applied to token piece
            //sets token initially offscreen
            token.setTranslationY(-1000f);
            //Checking whos turn and setting the img source for token
            if (activePlayer == 0){
                token.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else{
                token.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            //token animations
            token.animate()
                    .translationYBy(1000f)
                    .rotationBy(270f)
                    .setDuration(1000);

            for (int[] gameWon : gameWon) {
                if (gameState[gameWon[0]] == gameState[gameWon[1]] && gameState[gameWon[1]] == gameState[gameWon[2]] &&
                        gameState[gameWon[0]] != 2){

                    //Stops the game play
                    gameIsActive = false;

                    //setting the default winner as red
                    String winner = "Red";

                    if(gameState[gameWon[0]] == 0){
                        winner = "Yellow";
                    }

                    //Somebody won!
                    //accessing the text winner text box
                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    //setting winner message
                    winnerMessage.setText(winner + " is the winner!");
                    //access the desired layout by its id and setting its visibility to be displayed
                    LinearLayout layout = findViewById(R.id.playAgainLayout);
                    layout.setVisibility(View.VISIBLE);
               } else {

                    //create boolean for game over
                    boolean gameIsOver = true;

                    //looping through views(gameState)
                    for (int counterState : gameState){
                        // Checking if one view(gameState) is = 2 if so, game is not over
                        if (counterState == 2){
                            gameIsOver = false;
                        }

                    }
                    if (gameIsOver){
                        //Somebody won!
                        //accessing the text winner text box
                        TextView winnerMessage = findViewById(R.id.winnerMessage);
                        //setting winner message
                        winnerMessage.setText("Its a draw!");
                        //access the desired layout by its id and setting its visibility to be displayed
                        LinearLayout layout = findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                    }

                }
            }

        }


    }
    public void playAgain(View view){

        gameIsActive = true;

        LinearLayout layout = findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.INVISIBLE);
        //No need to state type of variable cause its already created on the dropIn method. Just giving it a value of 0 (reset)
        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout board = findViewById(R.id.board);
        //looping through and indentifying all views on the grid layout(board)
        for (int i = 0; i < board.getChildCount(); i++){
            //grabs all the views on the board and sets the img source to "0" (empty)
            ((ImageView) board.getChildAt(i)).setImageResource(0);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
