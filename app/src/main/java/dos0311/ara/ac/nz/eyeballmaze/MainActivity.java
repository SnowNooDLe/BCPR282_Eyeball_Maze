package dos0311.ara.ac.nz.eyeballmaze;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import dos0311.ara.ac.nz.eyeballmaze.Model.*;

public class MainActivity extends AppCompatActivity {
    private int size = 6;
    Board board = new Board(size);
    ImageView[][] imageViews = new ImageView[6][6];
    int[][] imageSrcs = new int[6][6];
    TextView textViewForGoal;
    TextView testViewForMovements;
    Player eyeball;
//    It means game is not finished yet if it is true.
    private Boolean gameIsOn;
//    will be used for task 17, if user keep trying bad thing, popup warning with rule.
    private int errorCount = 0;

    Switch soundOnOffSwitch;
    MediaPlayer bgm, lost_case_sound, won_case_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundOnOffSwitch = (Switch) findViewById(R.id.switchSoundOnOff);
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

//        for the goal textView
        textViewForGoal = findViewById(R.id.textViewGoals);
        testViewForMovements = findViewById(R.id.textViewMovements);

        //      first row
        imageViews[0][0] = findViewById(R.id.imageView00);
        imageViews[0][1] = findViewById(R.id.imageView01);
        imageViews[0][2] = findViewById(R.id.imageView02);
        imageViews[0][3] = findViewById(R.id.imageView03);
        imageViews[0][4] = findViewById(R.id.imageView04);
        imageViews[0][5] = findViewById(R.id.imageView05);

        imageSrcs[0][0] = R.drawable.empty_block;
        imageSrcs[0][1] = R.drawable.empty_block;
        imageSrcs[0][2] = R.drawable.empty_block;
        imageSrcs[0][3] = R.drawable.red_flower;
        imageSrcs[0][4] = R.drawable.empty_block;
        imageSrcs[0][5] = R.drawable.empty_block;

        //      second row
        imageViews[1][0] = findViewById(R.id.imageView10);
        imageViews[1][1] = findViewById(R.id.imageView11);
        imageViews[1][2] = findViewById(R.id.imageView12);
        imageViews[1][3] = findViewById(R.id.imageView13);
        imageViews[1][4] = findViewById(R.id.imageView14);
        imageViews[1][5] = findViewById(R.id.imageView15);

        imageSrcs[1][0] = R.drawable.empty_block;
        imageSrcs[1][1] = R.drawable.cyan_cross;
        imageSrcs[1][2] = R.drawable.yellow_flower;
        imageSrcs[1][3] = R.drawable.yellow_diamond;
        imageSrcs[1][4] = R.drawable.green_cross;
        imageSrcs[1][5] = R.drawable.empty_block;

        //      third row
        imageViews[2][0] = findViewById(R.id.imageView20);
        imageViews[2][1] = findViewById(R.id.imageView21);
        imageViews[2][2] = findViewById(R.id.imageView22);
        imageViews[2][3] = findViewById(R.id.imageView23);
        imageViews[2][4] = findViewById(R.id.imageView24);
        imageViews[2][5] = findViewById(R.id.imageView25);

        imageSrcs[2][0] = R.drawable.empty_block;
        imageSrcs[2][1] = R.drawable.green_flower;
        imageSrcs[2][2] = R.drawable.red_diamond;
        imageSrcs[2][3] = R.drawable.green_star;
        imageSrcs[2][4] = R.drawable.yellow_diamond;
        imageSrcs[2][5] = R.drawable.empty_block;

        //      fourth row
        imageViews[3][0] = findViewById(R.id.imageView30);
        imageViews[3][1] = findViewById(R.id.imageView31);
        imageViews[3][2] = findViewById(R.id.imageView32);
        imageViews[3][3] = findViewById(R.id.imageView33);
        imageViews[3][4] = findViewById(R.id.imageView34);
        imageViews[3][5] = findViewById(R.id.imageView35);

        imageSrcs[3][0] = R.drawable.empty_block;
        imageSrcs[3][1] = R.drawable.red_flower;
        imageSrcs[3][2] = R.drawable.cyan_flower;
        imageSrcs[3][3] = R.drawable.red_star;
        imageSrcs[3][4] = R.drawable.green_flower;
        imageSrcs[3][5] = R.drawable.empty_block;

        //      fifth row
        imageViews[4][0] = findViewById(R.id.imageView40);
        imageViews[4][1] = findViewById(R.id.imageView41);
        imageViews[4][2] = findViewById(R.id.imageView42);
        imageViews[4][3] = findViewById(R.id.imageView43);
        imageViews[4][4] = findViewById(R.id.imageView44);
        imageViews[4][5] = findViewById(R.id.imageView45);

        imageSrcs[4][0] = R.drawable.empty_block;
        imageSrcs[4][1] = R.drawable.cyan_star;
        imageSrcs[4][2] = R.drawable.red_diamond;
        imageSrcs[4][3] = R.drawable.cyan_flower;
        imageSrcs[4][4] = R.drawable.cyan_diamond;
        imageSrcs[4][5] = R.drawable.empty_block;

        //      sixth row
        imageViews[5][0] = findViewById(R.id.imageView50);
        imageViews[5][1] = findViewById(R.id.imageView51);
        imageViews[5][2] = findViewById(R.id.imageView52);
        imageViews[5][3] = findViewById(R.id.imageView53);
        imageViews[5][4] = findViewById(R.id.imageView54);
        imageViews[5][5] = findViewById(R.id.imageView55);

        imageSrcs[5][0] = R.drawable.empty_block;
        imageSrcs[5][1] = R.drawable.empty_block;
        imageSrcs[5][2] = R.drawable.cyan_diamond;
        imageSrcs[5][3] = R.drawable.empty_block;
        imageSrcs[5][4] = R.drawable.empty_block;
        imageSrcs[5][5] = R.drawable.empty_block;

        startGame();
    }

//    Starting the game and always start with Stage 1.
    public void startGame() {
        gameIsOn = true;
        board.stageOneBoard();
//        Plan was setting Red Flower for goal but for some reason, if i set Red Flower for goal
//        and when eyeball gets to the point, app crashes.
        board.setGoal(2, 4);
        eyeball = new Player(5,2, board);

        textViewForGoal.setText("Number of Goal(s) : " + board.numberOfGoals);
        testViewForMovements.setText("Number of Movements : " + eyeball.getCurrentMoveCount());

        setGoalInMaze(2,4);
        setPlayerInMaze(5,2);
    }

    private void setPlayerInMaze(int row, int col) {
        Bitmap image1 = BitmapFactory.decodeResource(getResources(), imageSrcs[row][col]);
        Bitmap image2 = BitmapFactory.decodeResource(getResources(), R.drawable.eyeball_up);
        Bitmap mergedImages = createSingleImageFromMultipleImages(image1, image2);
        imageViews[row][col].setImageBitmap(mergedImages);
    }

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


    private int getLocationImageView(ImageView imageView) {
        // https://stackoverflow.com/questions/10137692/how-to-get-resource-name-from-resource-id
        String name = getResources().getResourceEntryName(imageView.getId());
        name = name.replace("imageView", "");
        return Integer.parseInt(name);
    }

    public void onClickToMove(View view) {
        if (checkGameIsOver()){
            ImageView nextImageView = (ImageView) view;

            String targetPosition = String.valueOf(getLocationImageView(nextImageView));
//        to get row and col values
            int targetRow = Character.digit(targetPosition.charAt(0), 10);
            int targetCol = Character.digit(targetPosition.charAt(1), 10);

            String currentPosition = eyeball.getCurrPosition();

            int currRow = Character.digit(currentPosition.charAt(0), 10);
            int currCol = Character.digit(currentPosition.charAt(1), 10);

//        debug purpose
//        Log.d("MYINT", "Current location row is : " + currRow);
//        Log.d("MYINT", "Current location col is : " + currCol);
//        Log.d("MYINT", "Target location row is : " + targetRow);
//        Log.d("MYINT", "Target location col is : " + targetCol);
//        Log.d("MYINT", "What is the result ? : " + eyeball.checkDestinationBlock(targetRow, targetCol));

            if (eyeball.checkDestinationBlock(targetRow, targetCol)){
//            Resetting current spot's image
                imageViews[currRow][currCol].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[currRow][currCol]));

                imageViews[targetRow][targetCol].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[targetRow][targetCol]));
                eyeball.setPlayer(targetRow, targetCol);

                Bitmap image1 = BitmapFactory.decodeResource(getResources(), imageSrcs[targetRow][targetCol]);
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
                imageViews[targetRow][targetCol].setImageBitmap(mergedImages);

//            movement increase
                eyeball.movementCountIncrease();
//            recording movement
                eyeball.recordMovementHistory(targetRow, targetCol);
//            recording direction
                eyeball.recordDirectionHisory();

//        updating movements display
                testViewForMovements.setText("Number of Movements : " + eyeball.getCurrentMoveCount());
            } else {
//                just telling what to do
                if (errorCount < 3){
                    warningMSG("You can only move to either same color or same shape to Eyeball's front, left or right");
//                    task 17, showing corresponding rule
                } else {
                    warningMSG("Based on the face of Eyeball, you can only go to your Front, Right or Left. And the tile you want to go, must be either same color or same shape to your Eyeball's current tile.");
                }
                errorCount++;
            }

            if (eyeball.checkWhetherBlockIsGoal()){
//                playing winning song
                won_case_sound = MediaPlayer.create(MainActivity.this,R.raw.won_sound);
                won_case_sound.start();
                textViewForGoal.setText("Number of Goal(s) : " + (board.numberOfGoals - 1));
                gameFinishedMSG("Congratulations ! You won the game !");
                gameIsOn = false;
            }

//           checking movements to decide whether game should be over or not
            if (eyeball.getCurrentMoveCount() > 10){
//                playing lost sound
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

    public void resetCurrentStageForDialog(){
        resetStage();
    }

    private void resetStage(){
//        resetting previous eyeball image
        gameIsOn = true;
        imageViews[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()]));
        eyeball.resetPlayer();
        setPlayerInMaze(eyeball.getStartingRow(), eyeball.getStartingCol());
        setGoalInMaze(2,4);
        testViewForMovements.setText("Number of Movements : " + eyeball.getCurrentMoveCount());
    }

    public void gameIsOverBadEnding(){
        gameIsOn = false;
        warningMSG("You couldn't make it within 10 movements, please try again !");
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

    private void gameFinishedMSG(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Restart",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        resetCurrentStageForDialog();
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

    public void goBackOneMove(View view){
        if (checkGameIsOver()){
            //        resetting current image without user.
            imageViews[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()].setImageBitmap(BitmapFactory.decodeResource(getResources(), imageSrcs[eyeball.getCurrRowPosition()][eyeball.getCurrColPosition()]));
//        go back one movement which will decrease number of movement, position as well
            eyeball.goBackOneMove();

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

//        update the number of movements as well
            testViewForMovements.setText("Number of Movements : " + eyeball.getCurrentMoveCount());
        }

    }

    private Boolean checkGameIsOver(){
        return gameIsOn;
    }
}