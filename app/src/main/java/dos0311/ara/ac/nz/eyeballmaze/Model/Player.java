package dos0311.ara.ac.nz.eyeballmaze.Model;

import android.graphics.Point;

import java.util.Arrays;

public class Player {
    private String direction;
    //	to determine current coordinate for player
    private int rowPosition;
    private int colPosition;
    private int countMovement;
    //	Store those value for reset game.
    private int startRow;
    private int startCol;
    Board board;
    public Point[] movementHistory = new Point[500];
    public String[] directionHistory = new String[500];
    private boolean gameIsOver;



    //	Feature 6 where actually happen to create Eyeball
    public Player(int startingRow, int startingCol, Board board) {
//		default direction u for Up,
//		directions are l for Left, r for Right, d for Down
        this.direction = "u";
        this.countMovement = 0;
        this.startRow = startingRow;
        this.startCol = startingCol;
        this.rowPosition = startingRow;
        this.colPosition = startingCol;
        this.board = board;
        this.movementHistory[0] = new Point(startingRow, startingCol);
        this.directionHistory[0] = this.direction;
        this.gameIsOver = false;

    }

    //	set player at certain coordinate
//	actual method to move eyeball.
    public void setPlayer(int row, int col) {
        this.rowPosition = row;
        this.colPosition = col;
    }

    //	Where actual movement happens
    public void moveEyeball(int row, int col){
//		checking whether it's valid tile to go
        if (this.checkDestinationBlock(row, col)) {
//			it is a valid tile to go, lets move !
            this.setPlayer(row, col);
//			we are moving so +1 for movement count
            this.movementCountIncrease();
//			adding to history, in terms of coordinate.
            this.movementHistory[this.countMovement] = new Point(this.rowPosition, this.colPosition);
//			adding to history, in terms of coordinate.
            this.directionHistory[this.countMovement] = this.getCurrentDirection();
        }
        this.checkWhetherBlockIsGoal();
    }

    //	checking whether its a goal or not, For feature 21
    private void checkWhetherBlockIsGoal() {
        if (this.getCurrCoordinates().equals(board.goal[0])) {
            board.end = System.currentTimeMillis();
            this.gameIsOver = true;
        }

    }

//    new code for assignment part 2
    public void recordMovementHistory(int row, int col){
        this.movementHistory[this.countMovement] = new Point(row, col);
    }

//    new code for assignment part 2
    public void recordDirectionHisory(){
        this.directionHistory[this.countMovement] = this.getCurrentDirection();
    }


    //	Feature 14
    public Boolean playerFailed() {
//		means still in progress
        Boolean playerStat = false;
        if (this.countMovement > 50) {
            playerStat = true;
        }
        return playerStat;
    }

    //  to find player's position
//    original code
//    public String getCurrPosition() {
//        String result = board.map[this.rowPosition][this.colPosition];
//        return result;
//    }
//    new code cuz need different form our return value
    public String getCurrPosition() {
        String result = Integer.toString(this.rowPosition) + Integer.toString(this.colPosition);
        return result;
    }

    private Point getCurrCoordinates() {
        Point result = new Point(this.rowPosition, this.colPosition);
        return result;
    }

    //	Feature 2
    public void goBackOneMove() {
//		decrease one to see previous movement index value
        this.movementCountDecrease();
//		and load it from history, x and y in here means row, col as I used Point to store as x,y coordinate
        this.setPlayer(this.movementHistory[this.countMovement].x, this.movementHistory[this.countMovement].y);
        this.direction = this.directionHistory[this.countMovement];
    }

    //	Feature 4, reset current maze -> reset eyeball location and some values
//	as resetting current maze, maze will not be changed.
//	will be joint later in controller part
    public void resetPlayer() {
        this.setPlayer(this.startRow, this.startCol);
        this.direction = "u";
        this.countMovement = 0;
    }

    //	Feature 9, movement count
    public void movementCountIncrease() {
        this.countMovement++;
    }
    //	for go back move once.
    private void movementCountDecrease() {
        this.countMovement--;
    }

    public int getCurrentMoveCount() {
        return this.countMovement;
    }

    //	method for feature 19 & 20
    public String getCurrentDirection() {
        return this.direction;
    }

    //	Feature 19, turning left
    private void turningLeft() {
        String currentDirection = this.getCurrentDirection();
        switch (currentDirection) {
            case "u":
                this.direction = "l";
                break;
            case "l":
                this.direction = "d";
                break;
            case "d":
                this.direction = "r";
                break;
            case "r":
                this.direction = "u";
                break;
        }
    }
    //	Feature 20, turning right
    private void turningRight() {
        String currentDirection = this.getCurrentDirection();
        switch (currentDirection) {
            case "u":
                this.direction = "r";
                break;
            case "r":
                this.direction = "d";
                break;
            case "d":
                this.direction = "l";
                break;
            case "l":
                this.direction = "u";
                break;
        }
    }

    //	Feature 13, destination block validator
    public Boolean checkDestinationBlock(int targetRow, int targetCol) {
        Boolean status = false;
        String destinationValidaeResult = "";
//		to avoid magic number in condition statement
        int rowCoordinate = 0;
        int colCoordinate = 1;
        String[] destinationSpot = board.map[targetRow][targetCol].split("\\|");
        String[] currentSpot = board.map[this.rowPosition][this.colPosition].split("\\|");

        if (this.direction == "u") {
            destinationValidaeResult = facingUpMovement(targetRow, targetCol, destinationSpot, currentSpot, rowCoordinate, colCoordinate);
        } else if (this.direction == "l") {
            destinationValidaeResult = facingLeftMovement(targetRow, targetCol, destinationSpot, currentSpot, rowCoordinate, colCoordinate);
        } else if (this.direction == "d") {
            destinationValidaeResult = facingDownMovement(targetRow, targetCol, destinationSpot, currentSpot, rowCoordinate, colCoordinate);
        } else if (this.direction == "r") {
            destinationValidaeResult = facingRightMovement(targetRow, targetCol, destinationSpot, currentSpot, rowCoordinate, colCoordinate);
        } else {
//			debug purpose
            System.out.println("SOMETHING GONE WRONG CRAZY");
        }

        if (Arrays.asList("forward", "left", "right").contains(destinationValidaeResult)) {
            status = true;
        }
        switch (destinationValidaeResult) {
            case "left":
                this.turningLeft();
                break;
            case "right":
                this.turningRight();
                break;
        }

        return status;
    }
    //	Feature 10 ~ 12, depends on player's facing direction, do move forward, left, right
    private String facingUpMovement(int targetRow, int targetCol, String[] destinationSpot, String[] currentSpot, int rowCoordinate, int colCoordinate) {
        Boolean status = false;
        String result = "meh";
        if (this.rowPosition < targetRow) {
            result = "You cannot go back";
//			not on same column neither row, means diagonal direction which is not allowed
        } else if (this.colPosition != targetCol && this.rowPosition != targetRow) {
            result = "You can only go vertically straight or horizontally straight";
//			moving forward
        } else if (this.colPosition == targetCol && this.rowPosition > targetRow){
            status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
            if (status) {
                result = "forward";
            }
//			moving either left or right here
        } else if (this.colPosition != targetCol && this.rowPosition == targetRow) {
//			right
            if (this.colPosition < targetCol) {
                status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
                if (status) {
                    result = "right";
                }
//				left
            } else {
                status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
                if (status) {
                    result = "left";
                }
            }
        } else if (this.colPosition == targetCol && this.rowPosition == targetRow) {
            result = "You cannot move to where you are";
        } else {
//			debug purpose
            System.out.println("SOMETHING IS NOT RIGHT");
        }
        return result;
    }

    private String facingLeftMovement(int targetRow, int targetCol, String[] destinationSpot, String[] currentSpot, int rowCoordinate, int colCoordinate) {
        String result = "meh";
        boolean status = false;
        if (this.colPosition < targetCol) {
            result = "You cannot go back";
//			not on same column neither row, means diagonal direction which is not allowed
        } else if (this.colPosition != targetCol && this.rowPosition != targetRow) {
            result = "You can only go vertically straight or horizontally straight";
//			moving forward
        } else if (this.colPosition > targetCol && this.rowPosition == targetRow){
            status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
            if (status) {
                result = "forward";
            }
//			moving either left or right here
        } else if (this.colPosition == targetCol && this.rowPosition != targetRow) {
//			left
            if (this.rowPosition < targetRow) {
                status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
                if (status) {
                    result = "left";
                }
//				right
            } else {
                status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
                if (status) {
                    result = "right";
                }
            }
        } else if (this.colPosition == targetCol && this.rowPosition == targetRow) {
            result = "You cannot move to where you are";
        } else {
//			Debug purpose
            System.out.println("SOMETHING IS NOT RIGHT");
        }
        return result;
    }

    private String facingDownMovement(int targetRow, int targetCol, String[] destinationSpot, String[] currentSpot, int rowCoordinate, int colCoordinate) {
        Boolean status = false;
        String result = "meh";
        if (this.rowPosition > targetRow) {
            result = "You cannot go back";
//			not on same column neither row, means diagonal direction which is not allowed
        } else if (this.colPosition != targetCol && this.rowPosition != targetRow) {
            result = "You can only go vertically straight or horizontally straight";
//			moving forward
        } else if (this.colPosition == targetCol && this.rowPosition < targetRow){
            status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
            if (status) {
                result = "forward";
            }
//			moving either left or right here
        } else if (this.colPosition != targetCol && this.rowPosition == targetRow) {
//			left
            if (this.colPosition < targetCol) {
                status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
                if (status) {
                    result = "left";
                }
//				right
            } else {
                status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
                if (status) {
                    result = "right";
                }
            }
        } else if (this.colPosition == targetCol && this.rowPosition == targetRow) {
            result = "You cannot move to where you are";
        } else {
//			debug purpose
            System.out.println("SOMETHING IS NOT RIGHT");
        }
        return result;
    }

    private String facingRightMovement(int targetRow, int targetCol, String[] destinationSpot, String[] currentSpot, int rowCoordinate, int colCoordinate) {
        Boolean status = false;
        String result = "meh";
        if (this.colPosition > targetCol) {
            result = "You cannot go back";
//			not on same column neither row, means diagonal direction which is not allowed
        } else if (this.colPosition != targetCol && this.rowPosition != targetRow) {
            result = "You can only go vertically straight or horizontally straight";
//			moving forward
        } else if (this.colPosition < targetCol && this.rowPosition == targetRow){
            status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
            if (status) {
                result = "forward";
            }
//			moving either left or right here
        } else if (this.colPosition == targetCol && this.rowPosition != targetRow) {
//			left
            if (this.rowPosition > targetRow) {
                status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
                if (status) {
                    result = "left";
                }
//				right
            } else {
                status = decisionMaker(currentSpot, destinationSpot, rowCoordinate, colCoordinate);
                if (status) {
                    result = "right";
                }
            }
        } else if (this.colPosition == targetCol && this.rowPosition == targetRow) {
            result = "You cannot move to where you are";
        } else {
//			debug purpose
            System.out.println("SOMETHING IS NOT RIGHT");
        }
        return result;
    }

    private Boolean decisionMaker(String[] currentSpot, String[] destinationSpot, int rowCoordinate, int colCoordinate) {
        Boolean result = false;
        if (this.checkDestinationBlockSameColour(currentSpot[rowCoordinate], destinationSpot[rowCoordinate])
                || this.checkDestinationBlockSameShape(currentSpot[colCoordinate], destinationSpot[colCoordinate])) {
            result = true;
        }
        return result;
    }
    //	Feature 15, check whether destination block has same colour
    private Boolean checkDestinationBlockSameColour(String currColour, String destColour) {
        Boolean result = false;
        if (currColour.equals(destColour)) {
            result = true;
        }
        return result;
    }
    //	Feature 16, check whether destination block has same shape
    private Boolean checkDestinationBlockSameShape(String currShape, String destShape) {
        Boolean result = false;
        if (currShape.equals(destShape)) {
            result = true;
        }
        return result;
    }
}
