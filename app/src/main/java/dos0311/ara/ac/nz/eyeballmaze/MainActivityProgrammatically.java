package dos0311.ara.ac.nz.eyeballmaze;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dos0311.ara.ac.nz.eyeballmaze.Model.*;

public class MainActivityProgrammatically extends AppCompatActivity {
    private int size = 6;
    private Board board;
    private ImageView[][] imageViews = new ImageView[6][6];
    private int[][] imageSrcs = new int[6][6];

    private TextView stageIndicator;
    private TextView textViewForGoals;
    private TextView textViewForMovements;
    private TextView gamePlayTime;

    private Player eyeball;
    //  It means game is not finished yet if it is true.
    private Boolean gameIsOn;
    //  will be used for task 17, if user keep trying bad thing, popup warning with rule.
    private int errorCount = 0;

    //  for loading & saving game
    private int currentStage ;

    //  Time to launch the another activity
    private static int TIME_OUT = 1500;
    //  To store values into those array from DB.
    private Point[] movementHistoryArray = new Point[11];
    private String[] directionHistoryArray = new String[11];

    private Switch switchSoundOnOff;
    private MediaPlayer bgm;
    //    to notify whether game is been saved in firebase
    private boolean gameIsSaved;

    //    Extra View Feature 5, stopwatch
    private long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    private Handler handler;
    private int Seconds, Minutes, MilliSeconds ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_programmatic);
//        Indicator layout at the very top
        RelativeLayout indicator = findViewById(R.id.indicatorLayout);

        stageIndicator = new TextView(this);
        stageIndicator.setText(R.string.stage_one_choose);
        stageIndicator.setId(R.id.stageIndicator);

        RelativeLayout.LayoutParams paramsLeft = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        stageIndicator.setLayoutParams(paramsLeft);

        gamePlayTime = new TextView(this);
        gamePlayTime.setText(R.string.time_taken);
        gamePlayTime.setId(R.id.gamePlayTime);

        RelativeLayout.LayoutParams paramsRight = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        gamePlayTime.setLayoutParams(paramsRight);

        indicator.addView(stageIndicator);
        indicator.addView(gamePlayTime);
        
//        Create Maze
//        first row
        TableRow firstRow = findViewById(R.id.rowZero);

        ImageView imageview00 = new ImageView(this);
        imageview00.setId(R.id.imageView00);
        imageview00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview01 = new ImageView(this);
        imageview01.setId(R.id.imageView01);
        imageview01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview02 = new ImageView(this);
        imageview02.setId(R.id.imageView02);
        imageview02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview03 = new ImageView(this);
        imageview03.setId(R.id.imageView03);
        imageview03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview04 = new ImageView(this);
        imageview04.setId(R.id.imageView04);
        imageview04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview05 = new ImageView(this);
        imageview05.setId(R.id.imageView05);
        imageview05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        firstRow.addView(imageview00);
        firstRow.addView(imageview01);
        firstRow.addView(imageview02);
        firstRow.addView(imageview03);
        firstRow.addView(imageview04);
        firstRow.addView(imageview05);

        //        second row
        TableRow secondRow = findViewById(R.id.rowOne);

        ImageView imageview10 = new ImageView(this);
        imageview10.setId(R.id.imageView10);
        imageview10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview11 = new ImageView(this);
        imageview11.setId(R.id.imageView11);
        imageview11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview12 = new ImageView(this);
        imageview12.setId(R.id.imageView12);
        imageview12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview13 = new ImageView(this);
        imageview13.setId(R.id.imageView13);
        imageview13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview14 = new ImageView(this);
        imageview14.setId(R.id.imageView14);
        imageview14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview15 = new ImageView(this);
        imageview15.setId(R.id.imageView15);
        imageview15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        secondRow.addView(imageview10);
        secondRow.addView(imageview11);
        secondRow.addView(imageview12);
        secondRow.addView(imageview13);
        secondRow.addView(imageview14);
        secondRow.addView(imageview15);

        //        third row
        TableRow thirdRow = findViewById(R.id.rowTwo);

        ImageView imageview20 = new ImageView(this);
        imageview20.setId(R.id.imageView20);
        imageview20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview21 = new ImageView(this);
        imageview21.setId(R.id.imageView21);
        imageview21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview22 = new ImageView(this);
        imageview22.setId(R.id.imageView22);
        imageview22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview23 = new ImageView(this);
        imageview23.setId(R.id.imageView23);
        imageview23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview24 = new ImageView(this);
        imageview24.setId(R.id.imageView24);
        imageview24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview25 = new ImageView(this);
        imageview25.setId(R.id.imageView25);
        imageview25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        thirdRow.addView(imageview20);
        thirdRow.addView(imageview21);
        thirdRow.addView(imageview22);
        thirdRow.addView(imageview23);
        thirdRow.addView(imageview24);
        thirdRow.addView(imageview25);

        //        fourth row
        TableRow fourthRow = findViewById(R.id.rowThree);

        ImageView imageview30 = new ImageView(this);
        imageview30.setId(R.id.imageView30);
        imageview30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview31 = new ImageView(this);
        imageview31.setId(R.id.imageView31);
        imageview31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview32 = new ImageView(this);
        imageview32.setId(R.id.imageView32);
        imageview32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview33 = new ImageView(this);
        imageview33.setId(R.id.imageView33);
        imageview33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview34 = new ImageView(this);
        imageview34.setId(R.id.imageView34);
        imageview34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview35 = new ImageView(this);
        imageview35.setId(R.id.imageView35);
        imageview35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        fourthRow.addView(imageview30);
        fourthRow.addView(imageview31);
        fourthRow.addView(imageview32);
        fourthRow.addView(imageview33);
        fourthRow.addView(imageview34);
        fourthRow.addView(imageview35);

        //        fifth row
        TableRow fifthRow = findViewById(R.id.rowFour);

        ImageView imageview40 = new ImageView(this);
        imageview40.setId(R.id.imageView40);
        imageview40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview41 = new ImageView(this);
        imageview41.setId(R.id.imageView41);
        imageview41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview42 = new ImageView(this);
        imageview42.setId(R.id.imageView42);
        imageview42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview43 = new ImageView(this);
        imageview43.setId(R.id.imageView43);
        imageview43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview44 = new ImageView(this);
        imageview44.setId(R.id.imageView44);
        imageview44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview45 = new ImageView(this);
        imageview45.setId(R.id.imageView45);
        imageview45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        fifthRow.addView(imageview40);
        fifthRow.addView(imageview41);
        fifthRow.addView(imageview42);
        fifthRow.addView(imageview43);
        fifthRow.addView(imageview44);
        fifthRow.addView(imageview45);

        //        sixth row
        TableRow sixthRow = findViewById(R.id.rowFive);

        ImageView imageview50 = new ImageView(this);
        imageview50.setId(R.id.imageView50);
        imageview50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview51 = new ImageView(this);
        imageview51.setId(R.id.imageView51);
        imageview51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview52 = new ImageView(this);
        imageview52.setId(R.id.imageView52);
        imageview52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview53 = new ImageView(this);
        imageview53.setId(R.id.imageView53);
        imageview53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview54 = new ImageView(this);
        imageview54.setId(R.id.imageView54);
        imageview54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        ImageView imageview55 = new ImageView(this);
        imageview55.setId(R.id.imageView55);
        imageview55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToMove(v);
            }
        });

        sixthRow.addView(imageview50);
        sixthRow.addView(imageview51);
        sixthRow.addView(imageview52);
        sixthRow.addView(imageview53);
        sixthRow.addView(imageview54);
        sixthRow.addView(imageview55);

//        First functions column
        LinearLayout firstFunctions = findViewById(R.id.functionsOne);

        Button goBackButton = new Button(this);
        goBackButton.setText(R.string.go_back_one_movement);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackOneMove(v);
            }
        });

        Button saveGame = new Button(this);
        saveGame.setText(R.string.game_save);
        saveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentGame(v);
            }
        });

        Button loadGame = new Button(this);
        loadGame.setText(R.string.game_load);
        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCurrentGame(v);
            }
        });

        firstFunctions.addView(goBackButton);
        firstFunctions.addView(saveGame);
        firstFunctions.addView(loadGame);

//        Second functions column
        LinearLayout secondFunctions = findViewById(R.id.functionsTwo);

        Button restartButton = new Button(this);
        restartButton.setText(R.string.game_restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetCurrentStageForButton(v);
            }
        });

        Button stageOneChooser = new Button(this);
        stageOneChooser.setText(R.string.stage_one_choose);
        stageOneChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameStageOneViaButton(v);
            }
        });

        Button stageTwoChooser = new Button(this);
        stageTwoChooser.setText(R.string.stage_two_choose);
        stageTwoChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameStageTwoViaButton(v);
            }
        });

        secondFunctions.addView(restartButton);
        secondFunctions.addView(stageOneChooser);
        secondFunctions.addView(stageTwoChooser);

//        Display infos area
        LinearLayout displayArea = findViewById(R.id.displayArea);

        textViewForGoals = new TextView(this);
        textViewForGoals.setText(R.string.number_of_goals);
        textViewForGoals.setId(R.id.textViewForGoals);

        textViewForMovements = new TextView(this);
        textViewForMovements.setText(R.string.number_of_movements);
        textViewForMovements.setId(R.id.textViewForMovements);

        switchSoundOnOff = new Switch(this);
        switchSoundOnOff.setText(R.string.sound_control);
        switchSoundOnOff.setTextOn("ON");
        switchSoundOnOff.setTextOff("OFF");

        //      Task 14, Display a GUI element to control sound on / off
        switchSoundOnOff.setId(R.id.switchSoundOnOff);

        displayArea.addView(textViewForGoals);
        displayArea.addView(textViewForMovements);
        displayArea.addView(switchSoundOnOff);

        startGameStageOne();
        soundSetup();
    }

    public void soundSetup(){
        switchSoundOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchSoundOnOff.isChecked()) {
                    // The toggle is enabled
                    bgm = MediaPlayer.create(MainActivityProgrammatically.this,R.raw.hypnotic_puzzle3);
                    bgm.setLooping(true);
                    bgm.start();
                } else {
                    // The toggle is disabled
                    bgm.stop();
                    bgm.release();
                }
            }
        });
    }

    //  Extra View Feature 3, user can select level by clicking this.
    public void stageOneSetup(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                String imageview = "imageView" + i + j;
                int resID = getResources().getIdentifier(imageview, "id", getPackageName());
                imageViews[i][j] = findViewById(resID);
                imageSrcs[i][j] = R.drawable.empty_block;
            }
        }
        //      first row
        imageSrcs[0][3] = R.drawable.red_flower;

        //        second row
        imageSrcs[1][1] = R.drawable.cyan_cross;
        imageSrcs[1][2] = R.drawable.yellow_flower;
        imageSrcs[1][3] = R.drawable.yellow_diamond;
        imageSrcs[1][4] = R.drawable.green_cross;

        //      Third row
        imageSrcs[2][1] = R.drawable.green_flower;
        imageSrcs[2][2] = R.drawable.red_star;
        imageSrcs[2][3] = R.drawable.green_star;
        imageSrcs[2][4] = R.drawable.yellow_diamond;

        //      fourth row
        imageSrcs[3][1] = R.drawable.red_flower;
        imageSrcs[3][2] = R.drawable.cyan_flower;
        imageSrcs[3][3] = R.drawable.red_star;
        imageSrcs[3][4] = R.drawable.green_flower;

        //        Fifth row
        imageSrcs[4][1] = R.drawable.cyan_star;
        imageSrcs[4][2] = R.drawable.red_diamond;
        imageSrcs[4][3] = R.drawable.cyan_flower;
        imageSrcs[4][4] = R.drawable.cyan_diamond;

        //        Sixth row
        imageSrcs[5][2] = R.drawable.cyan_diamond;

//      resetting the displayed image to new stage
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                imageViews[i][j].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[i][j]));
            }
        }

        board = new Board(size);
//        setup with stage one board.
        board.stageOneBoard();

//        setting the goal, eyeball's position
        board.setGoal(0, 3);
        eyeball = new Player(5,2, board);

        textViewSetup();
//        for image
        setGoalInMaze(0,3);
        setPlayerInMaze(5,2);

//        Timer part
        resetTimer();
        startTimer();

    }

    public void stageTwoSetup(){
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                String imageview = "imageView" + i + j;
                int resID = getResources().getIdentifier(imageview, "id", getPackageName());
                imageViews[i][j] = findViewById(resID);
                imageSrcs[i][j] = R.drawable.empty_block;
            }
        }

//      allocate images into imageSrces
        imageSrcs[0][3] = R.drawable.red_flower;

        imageSrcs[1][1] = R.drawable.cyan_cross;
        imageSrcs[1][2] = R.drawable.cyan_flower;
        imageSrcs[1][3] = R.drawable.cyan_diamond;
        imageSrcs[1][4] = R.drawable.green_cross;

        imageSrcs[2][1] = R.drawable.green_flower;
        imageSrcs[2][2] = R.drawable.red_star;
        imageSrcs[2][3] = R.drawable.green_star;
        imageSrcs[2][4] = R.drawable.yellow_flower;

        imageSrcs[3][1] = R.drawable.red_flower;
        imageSrcs[3][2] = R.drawable.green_diamond;
        imageSrcs[3][3] = R.drawable.red_star;
        imageSrcs[3][4] = R.drawable.yellow_star;

        imageSrcs[4][1] = R.drawable.green_cross;
        imageSrcs[4][2] = R.drawable.red_diamond;
        imageSrcs[4][3] = R.drawable.cyan_flower;
        imageSrcs[4][4] = R.drawable.green_diamond;

        imageSrcs[5][2] = R.drawable.cyan_diamond;

//        resetting the displayed image to new stage
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                imageViews[i][j].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[i][j]));
            }
        }

        board = new Board(size);

        board.stageTwoBoard();

        board.setGoal(0, 3);
        eyeball = new Player(5,2, board);

        textViewSetup();

//      for image
        setGoalInMaze(0,3);
        setPlayerInMaze(5,2);

//        Timer part
        resetTimer();
        startTimer();
    }

    //    Starting the game with stage 1 but when it starts, always run stage one first
    public void startGameStageOne() {
        gameIsOn = true;
        gameIsSaved = false;
//        Extra View Feature 4, stage indicator
        stageIndicator.setText(R.string.stage_one_choose);
        currentStage = 1;
        stageOneSetup();
    }
    //    Starting the game with stage 2
    public void startGameStageTwo(){
        gameIsOn = true;
        gameIsSaved = false;
//        Extra View Feature 4, stage indicator
        stageIndicator.setText(R.string.stage_two_choose);
        currentStage = 2;
        stageTwoSetup();
    }

    public void textViewSetup(){
//      Task 12. Display the number of goals to do
        textViewForGoals.setText(getString(R.string.number_of_goals, board.getGoals()));
//       Task 13. Display move counts
        textViewForMovements.setText(getString(R.string.number_of_movements, eyeball.getCurrentMoveCount()));

    }

    //    Extra View Feature 3, choose stages via button click
    public void startGameStageOneViaButton(View view){
        startGameStageOne();
    }

    public void startGameStageTwoViaButton(View view){
        startGameStageTwo();
    }

    //    Task 5, Display image of player character
    private void setPlayerInMaze(int row, int col) {
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), imageSrcs[row][col]);
        Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.eyeball_up);
        Bitmap mergedImages = createSingleImageFromMultipleImages(image1, image2);
        imageViews[row][col].setImageBitmap(mergedImages);
    }

    //        Task 6, Display Goal(s)
    private void setGoalInMaze(int row, int col) {
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), imageSrcs[row][col]);
        Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.goal);
        Bitmap mergedImages = createSingleImageFromMultipleImages(image1, image2);
        imageViews[row][col].setImageBitmap(mergedImages);
    }

    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage) {
        Bitmap result = Bitmap.createBitmap(secondImage.getWidth(), secondImage.getHeight(), secondImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, 10, 10, null);
        return result;
    }


    private String getLocationImageView(ImageView imageView) {
        // https://stackoverflow.com/questions/10137692/how-to-get-resource-name-from-resource-id
        String name = getResources().getResourceEntryName(imageView.getId());
        name = name.replace("imageView", "");
        return name;
    }

    public void setImageAtTarget(int targetRow, int targetCol){
        imageViews[targetRow][targetCol].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[targetRow][targetCol]));
    }

    public void onClickToMove(View view) {
        if (checkGameIsOver()){
            ImageView nextImageView = (ImageView) view;

            String targetPosition = getLocationImageView(nextImageView);
//          to get row and col values
            final int targetRow = Character.digit(targetPosition.charAt(0), 10);
            final int targetCol = Character.digit(targetPosition.charAt(1), 10);

            String currentPosition = eyeball.getCurrPosition();

            int currRow = Character.digit(currentPosition.charAt(0), 10);
            int currCol = Character.digit(currentPosition.charAt(1), 10);

            if (eyeball.checkDestinationBlock(targetRow, targetCol)){
//              Resetting current spot's image
                imageViews[currRow][currCol].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[currRow][currCol]));

                imageViews[targetRow][targetCol].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[targetRow][targetCol]));
                eyeball.setPlayer(targetRow, targetCol);

                movementHappening();

//              movement increase
                eyeball.movementCountIncrease();
//              recording movement
                eyeball.recordMovementHistory(targetRow, targetCol);
//              recording direction
                eyeball.recordDirectionHisory();


//              updating textviews display
                textViewForMovements.setText(getString(R.string.number_of_movements, eyeball.getCurrentMoveCount()));
            } else {
//                Extra View Feature 2, getting X sign on the block that user cannot go.
                Bitmap image1 = BitmapFactory.decodeResource(getResources(), imageSrcs[targetRow][targetCol]);
                Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.illegal);
                Bitmap mergedImages = createSingleImageFromMultipleImages(image1, image2);
                imageViews[targetRow][targetCol].setImageBitmap(mergedImages);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setImageAtTarget(targetRow, targetCol);
                    }
                }, TIME_OUT);

//                just telling what to do
                if (errorCount < 3){
                    warningMSG("You cannot move to there.");
//                    Task 17, showing corresponding rule
                } else {
                    warningMSG("Based on the face of Eyeball, you can only go to your Front, Right or Left. And the tile you want to go, must be either same color or same shape to your Eyeball's current tile.");
                }
                errorCount++;
            }

            if (eyeball.checkWhetherBlockIsGoal()){
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
//               Task 15, playing winning song
                MediaPlayer won_case_sound = MediaPlayer.create(this, R.raw.won_sound);
                won_case_sound.start();
                textViewForGoals.setText(getString(R.string.number_of_goals, board.getGoals()-1));
                gameFinishedMSG("Congratulations ! You won the game ! \nIt took you " + Minutes +"Mins " + Seconds + "." + MilliSeconds + "Secs");
                gameIsOn = false;

            }

//           checking movements to decide whether game should be over or not
            int movement_limit = 10;
            if (eyeball.getCurrentMoveCount() > movement_limit){
//               Task 16, playing lost sound
                MediaPlayer lost_case_sound = MediaPlayer.create(this, R.raw.lost_sound);
                lost_case_sound.start();
                gameIsOverBadEnding();
            }
        } else {
            warningMSG("Game is finished, please restart the game if you want to");
        }
    }

    //    one for button, one for dialog
    public void resetCurrentStageForButton(View view){
        resetStage();
    }

    public void resetCurrentStage(){
        resetStage();
    }

    public void goToStageTwo(){
        startGameStageTwo();
    }

    //    Task 7, Button for restarting the current maze
    private void resetStage(){
//        resetting previous eyeball image
        gameIsOn = true;
        errorCount = 0;
        imageViews[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()]));
        if (currentStage == 1){
            resetStageOne();
        } else if (currentStage == 2) {
            resetStageTwo();
        }
        eyeball.resetPlayer();
        setPlayerInMaze(eyeball.getStartingRow(), eyeball.getStartingCol());

        textViewForMovements.setText(getString(R.string.number_of_movements, eyeball.getCurrentMoveCount()));
        textViewForGoals.setText(getString(R.string.number_of_goals, board.getGoals()));
        //        Timer part
        resetTimer();
        startTimer();
    }
    private void resetStageOne(){
//        board.setGoal(0,3);
        setGoalInMaze(0,3);
    }
    private void resetStageTwo(){
//        board.setGoal(0,3);
        setGoalInMaze(0,3);
    }


    //    Task 18 & Task 20,  Display dialogue with options after player character has lost
    public void gameIsOverBadEnding(){
        gameIsOn = false;
        gameFinishedMSG("You couldn't make it within 10 movements, please try again !");
    }

    private void warningMSG(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    //    Task 18 & Task 19,  Display dialogue with options after player character has won
//    as its combined for Task 18 ~ 20, depends on the message, can be for won or lost
    private void gameFinishedMSG(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
//      Local variable just for this dialog
        String title;

        //      As I only designed for first two stages,
//      when it gets to second stage, only option will be restart
        if (currentStage == 1){
            title = "Go to stage 2";
        } else {
            title = "Restart !";
        }

        alertDialogBuilder.setPositiveButton(title,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (currentStage == 1){
                            goToStageTwo();

                        } else{
                            resetCurrentStage();
                        }

                    }
                });

        alertDialogBuilder.setNegativeButton("Close",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    //    Extra View Feature 1, go back one movement
    public void goBackOneMove(View view){
        if (checkGameIsOver()){
//            To check whether use is at starting point or not
            if (eyeball.getCurrRowPosition() == eyeball.getStartRowPosition()
                    && eyeball.getCurrColPosition() == eyeball.getStartColPosition()){
                warningMSG("You are at starting point, cannot go back.");
            } else {
//              resetting current image without user.
                imageViews[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()]));
//              go back one movement which will decrease number of movement, position as well
                eyeball.goBackOneMove();

                movementHappening();

//              update the number of movements as well
                textViewForMovements.setText(getString(R.string.number_of_movements, eyeball.getCurrentMoveCount()));
            }

        } else {
            warningMSG("It only works when game is not finished");
        }

    }

    private Boolean checkGameIsOver(){
        return gameIsOn;
    }

    //    Task 2, Button for saving a maze
    public void saveCurrentGame(View view){
        if (checkGameIsOver()){
            if (eyeball.getCurrentMoveCount() == 0){
//          Add extra view 5, when it's at starting point, notify user that they don't have to instead of not doing anything
                warningMSG("You are still at starting point, no need to save");
            } else {
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference movements = database.getReference("movements");
                movements.setValue(eyeball.getCurrentMoveCount());

                DatabaseReference goals = database.getReference("goals");
                goals.setValue(board.getGoals());

                DatabaseReference directions = database.getReference("directions");
                directions.setValue(eyeball.getCurrentDirection());

                DatabaseReference currentRowPosition = database.getReference("currentRowPosition");
                currentRowPosition.setValue(eyeball.getCurrRowPosition());

                DatabaseReference currentColPosition = database.getReference("currentColPosition");
                currentColPosition.setValue(eyeball.getCurrColPosition());


                DatabaseReference movementHistory = database.getReference("movementHistory");
                DatabaseReference movementRef = movementHistory.child("History");

                Map<String, Point> moveHistory = new HashMap<>();
                for (int i = 0; i <= eyeball.getCurrentMoveCount(); i++){
                    moveHistory.put("Spot " + i, (eyeball.getMovementHistory()[i]));
                }
                movementRef.setValue(moveHistory);


                DatabaseReference directionHistory = database.getReference("directionHistory");

                DatabaseReference directionRef = directionHistory.child("History");

                Map<String, String> directHistory = new HashMap<>();
                for (int i = 0; i <= eyeball.getCurrentMoveCount(); i++){
                    directHistory.put("Spot " + i, eyeball.getDirectionHistory()[i]);
                }
                directionRef.setValue(directHistory);

                gameIsSaved = true;
            }

        } else {
            warningMSG("Game is finished :) No need to save");
        }
    }

    public void loadingDataFromDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

//            Number of Movements
        DatabaseReference movements = database.getReference("movements");
        // Attach a listener to read the data at our posts reference
        movements.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer movementsFromDB = dataSnapshot.getValue(Integer.class);
                eyeball.setCurrentNumOfMovements(movementsFromDB);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
//            For number of goals
        DatabaseReference goals = database.getReference("goals");
        // Attach a listener to read the data at our posts reference
        goals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer goalsFromDB = dataSnapshot.getValue(Integer.class);
                board.setNumOfGoals(goalsFromDB);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
//            For Direction
        DatabaseReference directions = database.getReference("directions");
        // Attach a listener to read the data at our posts reference
        directions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String directionsFromDB = dataSnapshot.getValue(String.class);
                eyeball.setCurrentDirection(directionsFromDB);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
//            For Row Position
        DatabaseReference currentRowPosition = database.getReference("currentRowPosition");
        // Attach a listener to read the data at our posts reference
        currentRowPosition.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer currentRowPositionFromDB = dataSnapshot.getValue(Integer.class);
                eyeball.setCurrentRowPosition(currentRowPositionFromDB);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
//            For Col Position
        DatabaseReference currentColPosition = database.getReference("currentColPosition");
        // Attach a listener to read the data at our posts reference
        currentColPosition.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer currentColPositionFromDB = dataSnapshot.getValue(Integer.class);
                eyeball.setCurrentColPosition(currentColPositionFromDB);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

//            For movement history
        final DatabaseReference movementHistory = database.getReference("movementHistory");
        DatabaseReference childOfMovementHistory = movementHistory.child("History");
        // Attach a listener to read the data at our posts reference
        childOfMovementHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Point movementHistoryFromDB = ds.getValue(Point.class);
                    movementHistoryArray[i] = movementHistoryFromDB;
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        eyeball.setMovementHistory(movementHistoryArray);

//            For direction history
        DatabaseReference directionHistory = database.getReference("directionHistory");
        DatabaseReference childOfDirectionHistory = directionHistory.child("History");
        // Attach a listener to read the data at our posts reference
        childOfDirectionHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String directionHistoryFromDB = ds.getValue(String.class);
                    directionHistoryArray[i] = directionHistoryFromDB;
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        eyeball.setDirectionHistory(directionHistoryArray);
    }
    public void restSetupAfterLoad(){
//            actual movement happening,
        movementHappening();

//        update the number of movements & Goal as well
        textViewForMovements.setText(getString(R.string.number_of_movements, eyeball.getCurrentMoveCount()));
        textViewForGoals.setText(getString(R.string.number_of_goals, board.getGoals()));
    }

    //    Task 1, Button for loading a maze
    public void loadCurrentGame(View view){
        if (checkGameIsOver() && gameIsSaved){
            //        resetting current image without user.
            imageViews[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()]));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    restSetupAfterLoad();
                }
            }, TIME_OUT);

            loadingDataFromDB();
        } else {
            warningMSG("You can only load the game when its not finished or been saved before");
        }
    }

    public void movementHappening(){
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), imageSrcs[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()]);
        Bitmap image2 = null;

        switch (eyeball.getCurrentDirection()){
            case "u":
                image2 = BitmapFactory.decodeResource(getResources(), R.drawable.eyeball_up);
                break;
            case "l":
                image2 = BitmapFactory.decodeResource(getResources(), R.drawable.eyeball_left);
                break;
            case "d":
                image2 = BitmapFactory.decodeResource(getResources(), R.drawable.eyeball_down);
                break;
            case "r":
                image2 = BitmapFactory.decodeResource(getResources(), R.drawable.eyeball_right);
                break;
        }

        Bitmap mergedImages = createSingleImageFromMultipleImages(image1, image2);
        imageViews[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()].setImageBitmap(mergedImages);
    }
    public void startTimer(){
        handler = new Handler();
        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
    }

    public void resetTimer(){
        MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;

        gamePlayTime.setText(R.string.time_taken);
    }

//    Extra View Features 5 time taken for game.
    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            gamePlayTime.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }
    };
}
