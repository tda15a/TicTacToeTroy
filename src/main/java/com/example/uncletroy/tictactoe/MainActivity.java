package com.example.uncletroy.tictactoe;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private Integer[][] values = new Integer[3][3];

    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
                values[i][j] = 0;
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int index1 = getButtonIndex1(v);
        int index2 = getButtonIndex2(v);

        if(values[index1][index2] != 0){
            return;
        }

        if(player1Turn){
            //((Button) v).setText("x");
            ((Button) v).setBackgroundResource(R.drawable.melee);
            values[index1][index2] = 1;
        }else{
            //((Button) v).setText("o");
            ((Button) v).setBackgroundResource(R.drawable.smash4);
            values[index1][index2] = 2;
        }

        roundCount++;

        if(CheckForWin()){
            if(player1Turn){
                player1Wins();
            }else{
                player2Wins();
            }
        }else if(roundCount == 9){
            draw();
        }else{
            player1Turn = !player1Turn;
        }
    }

    private boolean CheckForWin(){

        for(int i = 0;i < 3;i++) {
            if(values[i][0].equals(values[i][1])
                    &&values[i][0].equals(values[i][2])
                    && !values[i][0].equals(0)){
                return true;
            }
        }
        for(int i = 0;i < 3;i++) {
            if(values[0][i].equals(values[1][i])
                    &&values[0][i].equals(values[2][i])
                    && !values[0][i].equals(0)){
                return true;
            }
        }
        if(values[0][0].equals(values[1][1])
                &&values[0][0].equals(values[2][2])
                && !values[0][0].equals(0)){
            return true;
        }
        if(values[0][2].equals(values[1][1])
                &&values[0][2].equals(values[2][0])
                && !values[0][2].equals(0)){
            return true;
        }
        return false;
    }

    private void player1Wins(){
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void player2Wins(){
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updatePointsText(){
        textViewPlayer1.setText("Player 1: "+player1Points);
        textViewPlayer2.setText("Player 2: "+player2Points);
    }
    private void resetBoard(){
        for(int i = 0;i < 3;i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setBackgroundResource(0);
                values[i][j] = 0;
            }
        }
        roundCount = 0;
        player1Turn = true;
    }
    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    private int getButtonIndex1(View v){
        int index = 0;
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++){
                if(v.getId() == buttons[i][j].getId()) {
                    return index;
                }
            }
            index++;
        }

        return -1;
    }

    private int getButtonIndex2(View v){
        int index;
        for(int i = 0;i < 3;i++){
            index = 0;
            for(int j = 0;j < 3;j++){
                if(v.getId() == buttons[i][j].getId()) {
                    return index;
                }
                index++;
            }
        }

        return -1;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {// when orientation changes, save
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
        outState.putInt("values1", values[0][0]);
        outState.putInt("values2", values[0][1]);
        outState.putInt("values3", values[0][2]);
        outState.putInt("values4", values[1][0]);
        outState.putInt("values5", values[1][1]);
        outState.putInt("values6", values[1][2]);
        outState.putInt("values7", values[2][0]);
        outState.putInt("values8", values[2][1]);
        outState.putInt("values9", values[2][2]);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {// load after orientation changes
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("Player1Turn");
        values[0][0] = savedInstanceState.getInt("values1");
        values[0][1] = savedInstanceState.getInt("values2");
        values[0][2] = savedInstanceState.getInt("values3");
        values[1][0] = savedInstanceState.getInt("values4");
        values[1][1] = savedInstanceState.getInt("values5");
        values[1][2] = savedInstanceState.getInt("values6");
        values[2][0] = savedInstanceState.getInt("values7");
        values[2][1] = savedInstanceState.getInt("values8");
        values[2][2] = savedInstanceState.getInt("values9");

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(values[i][j] == 1){
                    buttons[i][j].setBackgroundResource(R.drawable.melee);
                }else if(values[i][j] == 2){
                    buttons[i][j].setBackgroundResource(R.drawable.smash4);
                }
            }
        }
    }
}
