package dos0311.ara.ac.nz.eyeballmaze;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import dos0311.ara.ac.nz.eyeballmaze.Model.*;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity {
    private int size = 6;
    private Board board;
    private ImageView[][] imageViews = new ImageView[6][6];
    private int[][] imageSrcs = new int[6][6];
    private TextView textViewForGoal, textViewForMovements;

    private Player eyeball;
//  It means game is not finished yet if it is true.
    private Boolean gameIsOn;
//  will be used for task 17, if user keep trying bad thing, popup warning with rule.
    private int errorCount = 0;

//  for loading & saving game
    private int currentStage, currentNumOfMovements, currentNumOfGoals, currentEyeballRowPosition, currentEyeballColPosition;
    private String currentDirection;
    private Point[] currentMovementHistory;
    private String[] currentDirectionHistory;
//  Time to launch the another activity
    private static int TIME_OUT = 1500;

    private Switch soundOnOffSwitch;
    private MediaPlayer bgm, lost_case_sound, won_case_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//      Task 3, Manually create a GUI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//      Task 14, Display a GUI element to control sound on / off
        soundOnOffSwitch = findViewById(R.id.switchSoundOnOff);
        bgm = MediaPlayer.create(MainActivity.this,R.raw.hellomrmyyesterday);
        soundOnOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    bgm = MediaPlayer.create(MainActivity.this,R.raw.hellomrmyyesterday);
                    bgm.start();
                } else {
                    // The toggle is disabled
                    bgm.stop();
                    bgm.release();
                }
            }
        });

//      for the goal textView
        textViewForGoal = findViewById(R.id.textViewGoals);
        textViewForMovements = findViewById(R.id.textViewMovements);
//      As when game starts, it will always start with stage 1
        startGameStageOne();
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

//      Task 12. Display the number of goals to do
        textViewForGoal.setText("Number of Goal(s): " + board.getGoals());
//       Task 13. Display move counts
        textViewForMovements.setText("Number of Movements: "  + eyeball.getCurrentMoveCount());

//        for image
        setGoalInMaze(0,3);
        setPlayerInMaze(5,2);
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

//      Task 12. Display the number of goals to do
        textViewForGoal.setText("Number of Goal(s): " + board.getGoals());
//       Task 13. Display move counts
        textViewForMovements.setText("Number of Movements: "  + eyeball.getCurrentMoveCount());

//      for image
        setGoalInMaze(0,3);
        setPlayerInMaze(5,2);
    }

//    Starting the game with stage 1 but when it starts, always run stage one first
    public void startGameStageOne() {
        gameIsOn = true;
        currentStage = 1;
        stageOneSetup();
    }
//    Starting the game with stage 2
    public void startGameStageTwo(){
        gameIsOn = true;
        currentStage = 2;
        stageTwoSetup();
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

//              updating movements display
                textViewForMovements.setText("Number of Movements: " + eyeball.getCurrentMoveCount());
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
//               Task 15, playing winning song
                won_case_sound = MediaPlayer.create(MainActivity.this,R.raw.won_sound);
                won_case_sound.start();
                textViewForGoal.setText("Number of Goal(s): " + (board.getGoals() - 1));
                gameFinishedMSG("Congratulations ! You won the game !");
                gameIsOn = false;
            }

//           checking movements to decide whether game should be over or not
            if (eyeball.getCurrentMoveCount() > 10){
//               Task 16, playing lost sound
                lost_case_sound = MediaPlayer.create(MainActivity.this,R.raw.lost_sound);
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
        imageViews[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()]));
        eyeball.resetPlayer();
        setPlayerInMaze(eyeball.getStartingRow(), eyeball.getStartingCol());
        setGoalInMaze(0,3);
        textViewForMovements.setText("Number of Movements: " + eyeball.getCurrentMoveCount());
    }

//    Task 18 & Task 20,  Display dialogue with options after player character has lost
    public void gameIsOverBadEnding(){
        gameIsOn = false;
        gameFinishedMSG("You couldn't make it within 10 movements, please try again !");
    }

    private void warningMSG(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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
                textViewForMovements.setText("Number of Movements: " + eyeball.getCurrentMoveCount());
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
            currentNumOfMovements = eyeball.getCurrentMoveCount();
            currentNumOfGoals = board.getGoals();
            currentDirection = eyeball.getCurrentDirection();
            currentEyeballRowPosition = eyeball.getCurrRowPosition();
            currentEyeballColPosition = eyeball.getCurrColPosition();
            currentMovementHistory = eyeball.getMovementHistory();
            currentDirectionHistory = eyeball.getDirectionHistory();

        } else {
            warningMSG("Game is finished :) No need to save");
        }
    }

//    Task 1, Button for loading a maze
    public void loadCurrentGame(View view){
        if (checkGameIsOver() && currentNumOfMovements != NULL){
            //        resetting current image without user.
            imageViews[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()]));

            eyeball.setCurrentNumOfMovements(currentNumOfMovements);
            board.setNumOfGoals(currentNumOfGoals);
            eyeball.setCurrentDirection(currentDirection);
            eyeball.setCurrentRowPosition(currentEyeballRowPosition);
            eyeball.setCurrentColPosition(currentEyeballColPosition);
            eyeball.setMovementHistory(currentMovementHistory);
            eyeball.setDirectionHistory(currentDirectionHistory);

//            actual movement happening,
            movementHappening();

//        update the number of movements & Goal as well
            textViewForMovements.setText("Number of Movements: " + eyeball.getCurrentMoveCount());
            textViewForGoal.setText(R.string.number_of_goals + board.getGoals());

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
}
