package dos0311.ara.ac.nz.eyeballmaze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import dos0311.ara.ac.nz.eyeballmaze.Model.*;

public class MainActivity extends AppCompatActivity {
    private int size = 6;
    Board board = new Board(size);
    TextView[][] textViews = new TextView[6][6];
    TextView textViewForGoal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        for the goal textView
        textViewForGoal = findViewById(R.id.textView);

        //      first row
        textViews[0][0] = findViewById(R.id.textView2);
        textViews[0][1] = findViewById(R.id.textView3);
        textViews[0][2] = findViewById(R.id.textView4);
        textViews[0][3] = findViewById(R.id.textView5);
        textViews[0][4] = findViewById(R.id.textView6);
        textViews[0][5] = findViewById(R.id.textView7);
        //      second row
        textViews[1][0] = findViewById(R.id.textView8);
        textViews[1][1] = findViewById(R.id.textView9);
        textViews[1][2] = findViewById(R.id.textView10);
        textViews[1][3] = findViewById(R.id.textView11);
        textViews[1][4] = findViewById(R.id.textView12);
        textViews[1][5] = findViewById(R.id.textView13);

        //      third row
        textViews[2][0] = findViewById(R.id.textView14);
        textViews[2][1] = findViewById(R.id.textView15);
        textViews[2][2] = findViewById(R.id.textView16);
        textViews[2][3] = findViewById(R.id.textView17);
        textViews[2][4] = findViewById(R.id.textView18);
        textViews[2][5] = findViewById(R.id.textView19);

        //      fourth row
        textViews[3][0] = findViewById(R.id.textView20);
        textViews[3][1] = findViewById(R.id.textView21);
        textViews[3][2] = findViewById(R.id.textView22);
        textViews[3][3] = findViewById(R.id.textView23);
        textViews[3][4] = findViewById(R.id.textView24);
        textViews[3][5] = findViewById(R.id.textView25);

        //      fifth row
        textViews[4][0] = findViewById(R.id.textView26);
        textViews[4][1] = findViewById(R.id.textView27);
        textViews[4][2] = findViewById(R.id.textView28);
        textViews[4][3] = findViewById(R.id.textView29);
        textViews[4][4] = findViewById(R.id.textView30);
        textViews[4][5] = findViewById(R.id.textView31);

        //      sixth row
        textViews[5][0] = findViewById(R.id.textView32);
        textViews[5][1] = findViewById(R.id.textView33);
        textViews[5][2] = findViewById(R.id.textView34);
        textViews[5][3] = findViewById(R.id.textView35);
        textViews[5][4] = findViewById(R.id.textView36);
        textViews[5][5] = findViewById(R.id.textView37);
    }

//    Starting the game and always start with Stage 1.
    public void startGame(View view) {
        board.stageOneBoard();

        board.setGoal(0, 3);
        textViewForGoal.setText("Number of Goal(s) : " + board.numberOfGoals);
        for (int i =0; i < size; i++){
            for (int j = 0; j < size; j++){
                textViews[i][j].setText(board.map[i][j]);
            }
        }
    }

}
