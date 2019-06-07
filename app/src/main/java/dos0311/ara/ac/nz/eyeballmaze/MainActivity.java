package dos0311.ara.ac.nz.eyeballmaze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dos0311.ara.ac.nz.eyeballmaze.Model.*;

public class MainActivity extends AppCompatActivity {
    private int size = 6;
    Board board = new Board(size);
    ImageView[][] imageViews = new ImageView[6][6];
    int[][] imageSrcs = new int[6][6];
    TextView textViewForGoal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        for the goal textView
        textViewForGoal = findViewById(R.id.textView);

        //      first row
        imageViews[0][0] = findViewById(R.id.imageView);
        imageViews[0][1] = findViewById(R.id.imageView2);
        imageViews[0][2] = findViewById(R.id.imageView3);
        imageViews[0][3] = findViewById(R.id.imageView4);
        imageViews[0][4] = findViewById(R.id.imageView5);
        imageViews[0][5] = findViewById(R.id.imageView6);

//        imageSrcs[0][0] = R.drawable.Empty_Block;
        //      second row
        imageViews[1][0] = findViewById(R.id.imageView7);
        imageViews[1][1] = findViewById(R.id.imageView8);
        imageViews[1][2] = findViewById(R.id.imageView9);
        imageViews[1][3] = findViewById(R.id.imageView10);
        imageViews[1][4] = findViewById(R.id.imageView11);
        imageViews[1][5] = findViewById(R.id.imageView12);

        //      third row
        imageViews[2][0] = findViewById(R.id.imageView13);
        imageViews[2][1] = findViewById(R.id.imageView14);
        imageViews[2][2] = findViewById(R.id.imageView15);
        imageViews[2][3] = findViewById(R.id.imageView17);
        imageViews[2][4] = findViewById(R.id.imageView17);
        imageViews[2][5] = findViewById(R.id.imageView18);

        //      fourth row
        imageViews[3][0] = findViewById(R.id.imageView19);
        imageViews[3][1] = findViewById(R.id.imageView20);
        imageViews[3][2] = findViewById(R.id.imageView21);
        imageViews[3][3] = findViewById(R.id.imageView22);
        imageViews[3][4] = findViewById(R.id.imageView23);
        imageViews[3][5] = findViewById(R.id.imageView24);

        //      fifth row
        imageViews[4][0] = findViewById(R.id.imageView25);
        imageViews[4][1] = findViewById(R.id.imageView26);
        imageViews[4][2] = findViewById(R.id.imageView27);
        imageViews[4][3] = findViewById(R.id.imageView28);
        imageViews[4][4] = findViewById(R.id.imageView29);
        imageViews[4][5] = findViewById(R.id.imageView30);

        //      sixth row
        imageViews[5][0] = findViewById(R.id.imageView31);
        imageViews[5][1] = findViewById(R.id.imageView32);
        imageViews[5][2] = findViewById(R.id.imageView33);
        imageViews[5][3] = findViewById(R.id.imageView34);
        imageViews[5][4] = findViewById(R.id.imageView35);
        imageViews[5][5] = findViewById(R.id.imageView36);
    }

//    Starting the game and always start with Stage 1.
    public void startGame(View view) {
        board.stageOneBoard();

        board.setGoal(0, 3);
        textViewForGoal.setText("Number of Goal(s) : " + board.numberOfGoals);
//        This must be for programatically setup, will do manual first
//        for (int i =0; i < size; i++){
//            for (int j = 0; j < size; j++){
//
//            }
//        }
    }

}
