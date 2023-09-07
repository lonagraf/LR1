package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     private LinearLayout board;
     private ArrayList<Button> squares = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View.OnClickListener listener = (view)->{
            Button btn = (Button) view;
            if(!btn.getText().toString().equals("")) return;
            if(GameInfo.isTurn) {
                btn.setText(GameInfo.firstSymbol);
                int [] comb = calcWinnPositions(GameInfo.firstSymbol);
                if(comb != null) {
                    Toast.makeText(getApplicationContext(),
                        "winner is "+GameInfo.firstSymbol,
                        Toast.LENGTH_LONG).show();
                btn.setBackgroundTintList(ContextCompat.getColorStateList(
                        getApplicationContext(),
                        R.color.green));}
            }
            else {
                btn.setText(GameInfo.secondSymbol);
                int [] comb = calcWinnPositions(GameInfo.secondSymbol);
                if(comb != null) {Toast.makeText(getApplicationContext(),
                            "winner is " + GameInfo.secondSymbol,
                            Toast.LENGTH_LONG).show();
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(
                            getApplicationContext(),
                            R.color.green));
                }

            }
            GameInfo.isTurn = !GameInfo.isTurn;
        };
        setContentView(R.layout.activity_main);
        board = findViewById(R.id.board);
        generateBoard(3,3,board);
        setListenerToSquares(listener);

    }
    private void initClearBoardBtn()
    {
        Button clearBtn = findViewById(R.id.clear_board_value);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText (
                    getApplicationContext(),"New game",Toast.LENGTH_LONG).show();
                for(Button square: squares) square.setText("");
            }
        });

    }
    public void generateBoard(int rowCount, int columnCount, LinearLayout board){
        for (int row = 0; row < rowCount; row++) {
            LinearLayout rowContainer = generateRow(columnCount);
            board.addView(rowContainer);
        }
    }
    private void setListenerToSquares(View.OnClickListener listener){
        for(int i = 0; i < squares.size(); i++)
            squares.get(i).setOnClickListener(listener);

    }
    private LinearLayout generateRow(int squareCount){
        LinearLayout rowContainer = new LinearLayout(getApplicationContext());
        rowContainer.setOrientation(LinearLayout.HORIZONTAL);
        rowContainer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        for (int square = 0; square < squareCount; square++){
            Button button = new Button(getApplicationContext());
            button.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                            getApplicationContext(),
                            R.color.pink));
            button.setWidth(convertToPixel(50));
            button.setHeight(convertToPixel(90));
            rowContainer.addView(button);
            squares.add(button);

        }
        return rowContainer;
    }

    public int convertToPixel(int digit){
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int)(digit * density + 0.5);
    }
    public int [] calcWinnPositions(String symbol){
        for(int i = 0; i < GameInfo.winCombination.length; i++){
            boolean findComb = true;
            for (int j = 0; j < GameInfo.winCombination[0].length; j++){
                int index = GameInfo.winCombination[i][j];
                if (!squares.get(index).getText().toString().equals(symbol)){
                    findComb = false;
                    break;
                }
            }
            if(findComb) return GameInfo.winCombination[i];
        }
        return null;
    }
}