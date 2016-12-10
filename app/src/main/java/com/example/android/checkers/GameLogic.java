package com.example.android.checkers;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.sql.SQLOutput;

/**
 * Created by evankaplan on 12/7/16.
 */

public class GameLogic {

    private Board board;
    private Square selectedSquare;
    private boolean squareSelected;
    private Player currentPlayer;
    private boolean justJumped;
    private Player player1;
    private Player player2;
    private boolean ableToDoubleJump = false;

    public GameLogic(ImageView piece1, ImageView piece2, ImageView piece3, ImageView piece4,
                     ImageView piece5, ImageView piece6, ImageView piece7, ImageView piece8,
                     ImageView piece9, ImageView piece10, ImageView piece11, ImageView piece12,
                     ImageView blackPiece1, ImageView blackPiece2, ImageView blackPiece3,
                     ImageView blackPiece4, ImageView blackPiece5, ImageView blackPiece6,
                     ImageView blackPiece7, ImageView blackPiece8, ImageView blackPiece9,
                     ImageView blackPiece10, ImageView blackPiece11, ImageView blackPiece12,
                     ImageView selectorSquare) {

        //System.out.println("TEST1");

        board = new Board(piece1, piece2, piece3, piece4,
                piece5, piece6, piece7, piece8, piece9, piece10, piece11,
                piece12, blackPiece1, blackPiece2, blackPiece3, blackPiece4,
                blackPiece5, blackPiece6, blackPiece7, blackPiece8, blackPiece9,
                blackPiece10, blackPiece11, blackPiece12, selectorSquare);

        selectedSquare = null;
        squareSelected = false;
        justJumped = false;
        currentPlayer = new Player("white");
        player1 = new Player("white");
        player1.setScore(12);
        player2 = new Player("black");
        player2.setScore(12);

    }

    public GameLogic(Board board, String currentPlayer) {
        this.board = new Board(board);

        selectedSquare = null;
        squareSelected = false;
        justJumped = false;
        this.currentPlayer = new Player(currentPlayer);
    }

    public boolean zoneClicked(int zone) {
        boolean out = false;
        Square square = board.getSquare(zone);
        if (square.getPiece() != null && selectedSquare != null &&
                selectedSquare.getPiece() != null && squareSelected //&&
                //!(selectedSquare.getPiece().getColor().equals(square.getPiece().getColor()))
                ) {
            squareSelected = false;
            selectedSquare = null;
        }
        if (square.isBlackSquare() && !(!squareSelected && square.getPiece() == null)) {
            if ((squareSelected && selectedSquare.getPiece() != null && !selectedSquare.getPiece().getKing()) || (square.getPiece() != null && !square.getPiece().getKing())) {
                if (justJumped) {
                    checkDoubleJumpConditions(square);
                }
                if (!squareSelected) {
                    if (square.isBlackSquare() && square.getPiece() != null &&
                            currentPlayer.getColor().equals(square.getPiece().getColor())) {
                        selectedSquare = square;
                        squareSelected = true;
                        moveSelectorSquare(selectedSquare.getZone());
                    }
                } else {
                    if (square.isBlackSquare() && square.getPiece() == null) {
                        if (currentPlayer.getColor().equals("white") &&
                                selectedSquare.getPiece() != null &&
                                selectedSquare.getPiece().getColor().equals("white")) {
                            if (((square.getZone() == (selectedSquare.getZone() - 9)) ||
                                    (square.getZone() == (selectedSquare.getZone() - 7)))) {
                                if (square.getZone() == (selectedSquare.getZone() - 9)) {
                                    movePiece(zone, -1, 1);
                                } else {
                                    movePiece(zone, 1, 1);
                                }
                                currentPlayer.setColor("black");
                                squareSelected = false;
                            } else if ((square.getZone() == (selectedSquare.getZone() - 14)) &&
                                    board.getSquare(selectedSquare.getZone() - 7) != null &&
                                    board.getSquare(selectedSquare.getZone() - 7).getPiece().getColor().equals("black")) {
                                out = jump(zone, 1, 1);
                                justJumped = true;
                                currentPlayer.setColor("black");
                                selectedSquare = board.getSquare(selectedSquare.getZone() - 14);
                                moveSelectorSquare(selectedSquare.getZone());
                                squareSelected = true;
                            } else if ((square.getZone() == (selectedSquare.getZone() - 18)) &&
                                    board.getSquare(selectedSquare.getZone() - 9) != null &&
                                    board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals("black")) {
                                out = jump(zone, -1, 1);
                                justJumped = true;
                                currentPlayer.setColor("black");
                                selectedSquare = board.getSquare(selectedSquare.getZone() - 18);
                                moveSelectorSquare(selectedSquare.getZone());
                                squareSelected = true;
                            }
                        } else if (currentPlayer.getColor().equals("black") &&
                                selectedSquare.getPiece() != null &&
                                selectedSquare.getPiece().getColor().equals("black")) {
                            if (((square.getZone() == (selectedSquare.getZone() + 9))
                                    || (square.getZone() == (selectedSquare.getZone() + 7)))) {
                                if (square.getZone() == (selectedSquare.getZone() + 9)) {
                                    movePiece(zone, 1, -1);
                                } else {
                                    movePiece(zone, -1, -1);
                                }
                                currentPlayer.setColor("white");
                                squareSelected = false;
                            } else if ((square.getZone() == (selectedSquare.getZone() + 14)) &&
                                    board.getSquare(selectedSquare.getZone() + 7) != null &&
                                    board.getSquare(selectedSquare.getZone() + 7).getPiece().getColor().equals("white")) {
                                out = jump(zone, -1, -1);
                                justJumped = true;
                                currentPlayer.setColor("white");
                                selectedSquare = board.getSquare(selectedSquare.getZone() + 14);
                                moveSelectorSquare(selectedSquare.getZone());
                                squareSelected = true;
                            } else if ((square.getZone() == (selectedSquare.getZone() + 18)) &&
                                    board.getSquare(selectedSquare.getZone() + 9) != null &&
                                    board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals("white")) {
                                out = jump(zone, 1, -1);
                                justJumped = true;
                                currentPlayer.setColor("white");
                                selectedSquare = board.getSquare(selectedSquare.getZone() + 18);
                                moveSelectorSquare(selectedSquare.getZone());
                                squareSelected = true;
                            }
                        }
                        //board.getSelectorSquare().getImageView().setAlpha(0.0f);
                    } else if (square.isBlackSquare() && square.getPiece() != null &&
                            currentPlayer.getColor().equals(square.getPiece().getColor())) {
                        selectedSquare = square;
                        squareSelected = true;
                        moveSelectorSquare(selectedSquare.getZone());
                    } else {
                        squareSelected = false;
                        board.getSelectorSquare().getImageView().setAlpha(0.0f);
                    }
                }
            } else {
                //System.out.println("HAVING A KINGLY MOVE");
                out = kinglyMove(square);
            }
            if (selectedSquare != null && selectedSquare.getPiece() != null && justJumped &&
                    !(selectedSquare.getPiece().getColor().equals(currentPlayer.getColor()))) {
                //System.out.println("TEST1");
                checkForAnotherJump();
            }
            if (!out) {
                out = checkForMoves();
            }
            return out;
        } else {
            if (selectedSquare != null && selectedSquare.getPiece() != null && justJumped &&
                    !(selectedSquare.getPiece().getColor().equals(currentPlayer.getColor()))) {
                //System.out.println("TEST2");
                checkForAnotherJump();
            }
            return false;
        }
    }

    private void movePiece(int zone, int i, int j) {
        Square newSquare = board.getSquare(zone);
        Square oldSquare = board.getSquare(selectedSquare.getZone());

        newSquare.setPiece(oldSquare.getPiece());
        newSquare.setImageView(oldSquare.getImageView());

        oldSquare.setPiece(null);
        oldSquare.setImageView(null);

        int leftMove = 123*i;
        int bottomMove = 123*j;


        ImageView iv = newSquare.getImageView();
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) iv.getLayoutParams();
        mlp.setMargins(mlp.leftMargin, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin + bottomMove);//all in pixels
        mlp.setMarginStart(mlp.getMarginStart() + leftMove);
        iv.setLayoutParams(mlp);

        checkForKing(newSquare);
    }

    private boolean jump(int zone, int x, int y) {
        Square newSquare = board.getSquare(zone);
        Square oldSquare = board.getSquare(selectedSquare.getZone());

        newSquare.setPiece(oldSquare.getPiece());
        newSquare.setImageView(oldSquare.getImageView());

        oldSquare.setPiece(null);
        oldSquare.setImageView(null);

        Square middleSquare;
        if (y > 0) {
            if (x > 0) {
                middleSquare = board.getSquare(oldSquare.getZone() - 7);
            } else {
                middleSquare = board.getSquare(oldSquare.getZone() - 9);
            }
        } else {
            if (x > 0) {
                middleSquare = board.getSquare(oldSquare.getZone() + 9);
            } else {
                middleSquare = board.getSquare(oldSquare.getZone() + 7);
            }
        }
        String color = middleSquare.getPiece().getColor();
        Boolean king = middleSquare.getPiece().getKing();
        middleSquare.setPiece(null);
        middleSquare.getImageView().setAlpha(0.0f);
        middleSquare.setImageView(null);

        if (color.equals("white")) {
            player1.setScore(player1.getScore() - 1);
            board.setPlayer1(board.getPlayer1() - 1);
            if (king) {
                board.setPlayer1King(board.getPlayer1King() - 1);
            }
        } else {
            player2.setScore(player2.getScore() - 1);
            board.setPlayer2(board.getPlayer2() - 1);
            if (king) {
                board.setPlayer2King(board.getPlayer2King() - 1);
            }
        }

        int leftMove = 123 * x * 2;
        int bottomMove = 123 * y * 2;


        ImageView iv = newSquare.getImageView();
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) iv.getLayoutParams();
        mlp.setMargins(mlp.leftMargin, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin + bottomMove);//all in pixels
        mlp.setMarginStart(mlp.getMarginStart() + leftMove);
        iv.setLayoutParams(mlp);

        checkForKing(newSquare);

        if (player1.getScore() == 0) {
            winner(player2);
            return true;
        } else if (player2.getScore() == 0) {
            winner(player1);
            return true;
        }

        return false;
    }

    private void checkDoubleJumpConditions(Square square) {
        if (currentPlayer.getColor().equals("black")) {//white person just jumped
            //System.out.println("Getting Closer");
            if (square.getPiece() == null && (((square.getZone() == (selectedSquare.getZone() - 18)) &&
                    board.getSquare(selectedSquare.getZone() - 9) != null &&
                    board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals("black")) ||
                    ((square.getZone() == (selectedSquare.getZone() - 14)) &&
                            board.getSquare(selectedSquare.getZone() - 7) != null &&
                            board.getSquare(selectedSquare.getZone() - 7).getPiece().getColor().equals("black")))) {
                squareSelected = true;
                moveSelectorSquare(selectedSquare.getZone());
                currentPlayer.setColor("white");
                //System.out.println("Why isn't this working?");
            } else {
                justJumped = false;
                //System.out.println(":(");
            }
        } else { //black person just jumped
            //able to jump again
            if (square.getPiece() == null && (((square.getZone() == (selectedSquare.getZone() + 18)) &&
                    board.getSquare(selectedSquare.getZone() + 9) != null &&
                    board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals("white")) ||
                    ((square.getZone() == (selectedSquare.getZone() + 14)) &&
                            board.getSquare(selectedSquare.getZone() + 7) != null &&
                            board.getSquare(selectedSquare.getZone() + 7).getPiece().getColor().equals("white")))) {
                squareSelected = true;
                moveSelectorSquare(selectedSquare.getZone());
                currentPlayer.setColor("black");
            } else {
                justJumped = false;
            }
        }
    }

    private void checkForKing(Square square) {
        int zone = square.getZone();
        if (!square.getPiece().getKing()) {
            if (square.getPiece().getColor().equals("white")) {
                switch (zone) {
                    case 1:
                        kingIt(square);
                        break;
                    case 3:
                        kingIt(square);
                        break;
                    case 5:
                        kingIt(square);
                        break;
                    case 7:
                        kingIt(square);
                        break;
                }
            } else {
                switch (zone) {
                    case 56:
                        kingIt(square);
                        break;
                    case 58:
                        kingIt(square);
                        break;
                    case 60:
                        kingIt(square);
                        break;
                    case 62:
                        kingIt(square);
                        break;
                }
            }
        }
    }

    private void kingIt(Square square) {
        square.getPiece().setKing(true);
        if (square.getPiece().getColor().equals("white")) {
            square.getImageView().setImageResource(R.drawable.whiteking);
            board.setPlayer1King(board.getPlayer1King() + 1);
        } else {
            square.getImageView().setImageResource(R.drawable.blackking);
            board.setPlayer2King(board.getPlayer2King() + 1);
        }

    }

    private boolean kinglyMove(Square square) {
        boolean out = false;
        int zone = square.getZone();
        if (justJumped) {
            checkDoubleJumpConditionsForKing(square);
        }
        if (!squareSelected) {
            if (square.isBlackSquare() && square.getPiece() != null &&
                    currentPlayer.getColor().equals(square.getPiece().getColor())) {
                selectedSquare = square;
                squareSelected = true;
                moveSelectorSquare(selectedSquare.getZone());
            }
        } else {
            if (square.isBlackSquare() && square.getPiece() == null) {
                if (currentPlayer.getColor().equals("white") && selectedSquare.getPiece().getColor().equals("white")) {
                    if (((square.getZone() == (selectedSquare.getZone() - 9)) ||
                            (square.getZone() == (selectedSquare.getZone() - 7)))) {
                        if (square.getZone() == (selectedSquare.getZone() - 9)) {
                            movePiece(zone, -1, 1);
                        } else {
                            movePiece(zone, 1, 1);
                        }
                        currentPlayer.setColor("black");
                        squareSelected = false;
                    } else if (((square.getZone() == (selectedSquare.getZone() + 9)) ||
                            (square.getZone() == (selectedSquare.getZone() + 7)))) {
                        if (square.getZone() == (selectedSquare.getZone() + 9)) {
                            movePiece(zone, 1, -1);
                        } else {
                            movePiece(zone, -1, -1);
                        }
                        currentPlayer.setColor("black");
                        squareSelected = false;
                    } else if ((square.getZone() == (selectedSquare.getZone() - 14)) &&
                            board.getSquare(selectedSquare.getZone() - 7) != null &&
                            board.getSquare(selectedSquare.getZone() - 7).getPiece().getColor().equals("black")) {
                        out = jump(zone, 1, 1);
                        justJumped = true;
                        currentPlayer.setColor("black");
                        selectedSquare = board.getSquare(selectedSquare.getZone() - 14);
                        moveSelectorSquare(selectedSquare.getZone());
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() - 18)) &&
                            board.getSquare(selectedSquare.getZone() - 9) != null &&
                            board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals("black")) {
                        out = jump(zone, -1, 1);
                        justJumped = true;
                        currentPlayer.setColor("black");
                        selectedSquare = board.getSquare(selectedSquare.getZone() - 18);
                        moveSelectorSquare(selectedSquare.getZone());
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() + 14)) &&
                            board.getSquare(selectedSquare.getZone() + 7) != null &&
                            board.getSquare(selectedSquare.getZone() + 7).getPiece().getColor().equals("black")) {
                        out = jump(zone, -1, -1);
                        justJumped = true;
                        currentPlayer.setColor("black");
                        selectedSquare = board.getSquare(selectedSquare.getZone() + 14);
                        moveSelectorSquare(selectedSquare.getZone());
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() + 18)) &&
                            board.getSquare(selectedSquare.getZone() + 9) != null &&
                            board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals("black")) {
                        out = jump(zone, 1, -1);
                        justJumped = true;
                        currentPlayer.setColor("black");
                        selectedSquare = board.getSquare(selectedSquare.getZone() + 18);
                        moveSelectorSquare(selectedSquare.getZone());
                        squareSelected = true;
                    }
                } else if (currentPlayer.getColor().equals("black") && selectedSquare.getPiece().getColor().equals("black")) {
                    if (((square.getZone() == (selectedSquare.getZone() - 9)) ||
                            (square.getZone() == (selectedSquare.getZone() - 7)))) {
                        if (square.getZone() == (selectedSquare.getZone() - 9)) {
                            movePiece(zone, -1, 1);
                        } else {
                            movePiece(zone, 1, 1);
                        }
                        currentPlayer.setColor("white");
                        squareSelected = false;
                    } else if (((square.getZone() == (selectedSquare.getZone() + 9)) ||
                            (square.getZone() == (selectedSquare.getZone() + 7)))) {
                        if (square.getZone() == (selectedSquare.getZone() + 9)) {
                            movePiece(zone, 1, -1);
                        } else {
                            movePiece(zone, -1, -1);
                        }
                        currentPlayer.setColor("white");
                        squareSelected = false;
                    } else if ((square.getZone() == (selectedSquare.getZone() - 14)) &&
                            board.getSquare(selectedSquare.getZone() - 7) != null &&
                            board.getSquare(selectedSquare.getZone() - 7).getPiece().getColor().equals("white")) {
                        out = jump(zone, 1, 1);
                        justJumped = true;
                        currentPlayer.setColor("white");
                        selectedSquare = board.getSquare(selectedSquare.getZone() - 14);
                        moveSelectorSquare(selectedSquare.getZone());
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() - 18)) &&
                            board.getSquare(selectedSquare.getZone() - 9) != null &&
                            board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals("white")) {
                        out = jump(zone, -1, 1);
                        justJumped = true;
                        currentPlayer.setColor("white");
                        selectedSquare = board.getSquare(selectedSquare.getZone() - 18);
                        moveSelectorSquare(selectedSquare.getZone());
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() + 14)) &&
                            board.getSquare(selectedSquare.getZone() + 7) != null &&
                            board.getSquare(selectedSquare.getZone() + 7).getPiece().getColor().equals("white")) {
                        out = jump(zone, -1, -1);
                        justJumped = true;
                        currentPlayer.setColor("white");
                        selectedSquare = board.getSquare(selectedSquare.getZone() + 14);
                        moveSelectorSquare(selectedSquare.getZone());
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() + 18)) &&
                            board.getSquare(selectedSquare.getZone() + 9) != null &&
                            board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals("white")) {
                        out = jump(zone, 1, -1);
                        justJumped = true;
                        currentPlayer.setColor("white");
                        selectedSquare = board.getSquare(selectedSquare.getZone() + 18);
                        moveSelectorSquare(selectedSquare.getZone());
                        squareSelected = true;
                    }
                }
            }
        }
        return out;
    }

    private void checkDoubleJumpConditionsForKing(Square square) {
        if (currentPlayer.getColor().equals("black")) {//white person just jumped
            if (square.getPiece() == null && (((square.getZone() == (selectedSquare.getZone() - 18))
                    && board.getSquare(selectedSquare.getZone() - 9) != null
                    && board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals("black"))
                    || ((square.getZone() == (selectedSquare.getZone() - 14))
                    && board.getSquare(selectedSquare.getZone() - 7) != null
                    && board.getSquare(selectedSquare.getZone() - 7).getPiece().getColor().equals("black"))
                    || ((square.getZone() == (selectedSquare.getZone() + 18))
                    && board.getSquare(selectedSquare.getZone() + 9) != null
                    && board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals("black"))
                    || ((square.getZone() == (selectedSquare.getZone() + 14))
                    && board.getSquare(selectedSquare.getZone() + 7) != null
                    && board.getSquare(selectedSquare.getZone() + 7).getPiece().getColor().equals("black")))) {
                squareSelected = true;
                moveSelectorSquare(selectedSquare.getZone());
                currentPlayer.setColor("white");
            } else {
                justJumped = false;
            }
        } else { //black person just jumped
            //able to jump again
            if (square.getPiece() == null && (((square.getZone() == (selectedSquare.getZone() - 18))
                    && board.getSquare(selectedSquare.getZone() - 9) != null
                    && board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals("white"))
                    || ((square.getZone() == (selectedSquare.getZone() - 14))
                    && board.getSquare(selectedSquare.getZone() - 7) != null
                    && board.getSquare(selectedSquare.getZone() - 7).getPiece().getColor().equals("white"))
                    || ((square.getZone() == (selectedSquare.getZone() + 18))
                    && board.getSquare(selectedSquare.getZone() + 9) != null
                    && board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals("white"))
                    || ((square.getZone() == (selectedSquare.getZone() + 14))
                    && board.getSquare(selectedSquare.getZone() + 7) != null
                    && board.getSquare(selectedSquare.getZone() + 7).getPiece().getColor().equals("white")))) {
                squareSelected = true;
                moveSelectorSquare(selectedSquare.getZone());
                currentPlayer.setColor("black");
            } else {
                justJumped = false;
            }
        }
    }


    public Player getPlayer() {
        return currentPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private void moveSelectorSquare(int i) {
        ImageView iv = board.getSelectorSquare().getImageView();

        int fromLeft = (i%8) * 123;
        int fromBottom = (7 - (i/8)) * 121;

        ViewGroup.MarginLayoutParams mlp1 = (ViewGroup.MarginLayoutParams) iv.getLayoutParams();
        mlp1.setMargins(mlp1.leftMargin, mlp1.topMargin, mlp1.rightMargin, 239 + fromBottom);//all in pixels
        mlp1.setMarginStart(7 + fromLeft);
        iv.setLayoutParams(mlp1);
        iv.setAlpha(0.5f);
    }

    private void winner(Player player) {
        if (player.getColor().equals("white")) {
            board.setWinner("white");
        } else {
            board.setWinner("black");
        }
    }

    public Board getBoard() {
        return board;
    }

    public boolean checkForMoves() {
        for (Square square: board.getBoard()) {
            if (square.isBlackSquare() && square.getPiece() != null
                    && square.getPiece().getColor().equals(currentPlayer.getColor())) {
                if (!square.getPiece().getKing()) { //for all players non kings
                    if (currentPlayer.getColor().equals("white")) {//white nonkings
                        if (square.getZone() - 9 >= 0 &&
                                board.getSquare(square.getZone() - 9).isBlackSquare() &&
                                board.getSquare(square.getZone() - 9).getPiece() == null) {
                            //System.out.println("1");
                            return false;
                        }
                        if (square.getZone() - 7 >= 0 &&
                                board.getSquare(square.getZone() - 7).isBlackSquare() &&
                                board.getSquare(square.getZone() - 7).getPiece() == null) {
                            //System.out.println("2");
                            return false;
                        }
                        if (square.getZone() - 18 >= 0 &&
                                board.getSquare(square.getZone() - 18).isBlackSquare() &&
                                board.getSquare(square.getZone() - 18).getPiece() == null &&
                                board.getSquare(square.getZone() - 9).getPiece() != null &&
                                !board.getSquare(square.getZone() - 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                            //System.out.println("3");
                            return false;
                        }
                        if (square.getZone() - 14 >= 0 &&
                                board.getSquare(square.getZone() - 14).isBlackSquare() &&
                                board.getSquare(square.getZone() - 14).getPiece() == null &&
                                board.getSquare(square.getZone() - 7).getPiece() != null &&
                                !board.getSquare(square.getZone() - 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                            //System.out.println("4");
                            return false;
                        }
                    } else {//black nonkings
                        if (square.getZone() + 9 <= 63 &&
                                board.getSquare(square.getZone() + 9).isBlackSquare() &&
                                board.getSquare(square.getZone() + 9).getPiece() == null) {
                            //System.out.println("5");
                            return false;
                        }
                        if (square.getZone() + 7 <= 63 &&
                                board.getSquare(square.getZone() + 7).isBlackSquare() &&
                                board.getSquare(square.getZone() + 7).getPiece() == null) {
                            //System.out.println("6");
                            return false;
                        }
                        if (square.getZone() + 18 <= 63 &&
                                board.getSquare(square.getZone() + 18).isBlackSquare() &&
                                board.getSquare(square.getZone() + 18).getPiece() == null &&
                                board.getSquare(square.getZone() + 9).getPiece() != null &&
                                !board.getSquare(square.getZone() + 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                            //System.out.println("7");
                            return false;
                        }
                        if (square.getZone() + 14 <= 63 &&
                                board.getSquare(square.getZone() + 14).isBlackSquare() &&
                                board.getSquare(square.getZone() + 14).getPiece() == null &&
                                board.getSquare(square.getZone() + 7).getPiece() != null &&
                                !board.getSquare(square.getZone() + 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                            //System.out.println("8");
                            return false;
                        }
                    }
                } else { //for all players kings
                    if (square.getZone() - 9 >= 0 &&
                            board.getSquare(square.getZone() - 9).isBlackSquare() &&
                            board.getSquare(square.getZone() - 9).getPiece() == null) {
                        //System.out.println("1");
                        return false;
                    }
                    if (square.getZone() - 7 >= 0 &&
                            board.getSquare(square.getZone() - 7).isBlackSquare() &&
                            board.getSquare(square.getZone() - 7).getPiece() == null) {
                        //System.out.println("2");
                        return false;
                    }
                    if (square.getZone() - 18 >= 0 &&
                            board.getSquare(square.getZone() - 18).isBlackSquare() &&
                            board.getSquare(square.getZone() - 18).getPiece() == null &&
                            board.getSquare(square.getZone() - 9).getPiece() != null &&
                            !board.getSquare(square.getZone() - 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                        //System.out.println("3");
                        return false;
                    }
                    if (square.getZone() - 14 >= 0 &&
                            board.getSquare(square.getZone() - 14).isBlackSquare() &&
                            board.getSquare(square.getZone() - 14).getPiece() == null &&
                            board.getSquare(square.getZone() - 7).getPiece() != null &&
                            !board.getSquare(square.getZone() - 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                        //System.out.println("4");
                        return false;
                    }
                    if (square.getZone() + 9 <= 63 &&
                            board.getSquare(square.getZone() + 9).isBlackSquare() &&
                            board.getSquare(square.getZone() + 9).getPiece() == null) {
                        //System.out.println("5");
                        return false;
                    }
                    if (square.getZone() + 7 <= 63 &&
                            board.getSquare(square.getZone() + 7).isBlackSquare() &&
                            board.getSquare(square.getZone() + 7).getPiece() == null) {
                        //System.out.println("6");
                        return false;
                    }
                    if (square.getZone() + 18 <= 63 &&
                            board.getSquare(square.getZone() + 18).isBlackSquare() &&
                            board.getSquare(square.getZone() + 18).getPiece() == null &&
                            board.getSquare(square.getZone() + 9).getPiece() != null &&
                            !board.getSquare(square.getZone() + 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                        //System.out.println("7");
                        return false;
                    }
                    if (square.getZone() + 14 <= 63 &&
                            board.getSquare(square.getZone() + 14).isBlackSquare() &&
                            board.getSquare(square.getZone() + 14).getPiece() == null &&
                            board.getSquare(square.getZone() + 7).getPiece() != null &&
                            !board.getSquare(square.getZone() + 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                        //System.out.println("8");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean computerMove() {

        boolean out = false;
        BoardTree boardTree = new BoardTree(new ComputerBoard(board));
        BoardInt boardInt = boardTree.findBestMove();

        zoneClicked(boardInt.getFirstClick());

        int j;

        if (boardInt.getArray().size() == 0) {//no double jumps
            //for (j = 0; j < 1000000000; j++) {}
            return zoneClicked(boardInt.getSecondClick());
        } else {
            //SystemClock.sleep(4000);
            //for (j = 0; j < 1000000000; j++) {}
            zoneClicked(boardInt.getSecondClick());
        }

        for (int i = 0; i < boardInt.getArray().size(); i++) {
            //SystemClock.sleep(4000);
            //for (j = 0; j < 1000000000; j++) {}
            out = zoneClicked(boardInt.getArray().get(i));
        }

        return out;



    }

    public boolean getAbleToDoubleJump() {
        return ableToDoubleJump;
    }

    public boolean getJustJumped() {
        return justJumped;
    }

    private void checkForAnotherJump() {
        if (selectedSquare.getPiece().getKing()) {
            if (((selectedSquare.getZone() - 14 >= 0 &&
                    board.getSquare(selectedSquare.getZone() - 14).isBlackSquare() &&
                    board.getSquare(selectedSquare.getZone() - 14).getPiece() == null &&
                    board.getSquare(selectedSquare.getZone() - 7).getPiece() != null &&
                    !board.getSquare(selectedSquare.getZone() - 7).getPiece().getColor().equals(selectedSquare.getPiece().getColor())) ||
                    (selectedSquare.getZone() - 18 >= 0 &&
                            board.getSquare(selectedSquare.getZone() - 18).isBlackSquare() &&
                            board.getSquare(selectedSquare.getZone() - 18).getPiece() == null &&
                            board.getSquare(selectedSquare.getZone() - 9).getPiece() != null &&
                            !board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals(selectedSquare.getPiece().getColor()))) ||
                    ((selectedSquare.getZone() + 14 <= 63 &&
                            board.getSquare(selectedSquare.getZone() + 14).isBlackSquare() &&
                            board.getSquare(selectedSquare.getZone() + 14).getPiece() == null &&
                            board.getSquare(selectedSquare.getZone() + 7).getPiece() != null &&
                            !board.getSquare(selectedSquare.getZone() + 7).getPiece().getColor().equals(selectedSquare.getPiece().getColor())) ||
                            (selectedSquare.getZone() + 18 <= 63 &&
                                    board.getSquare(selectedSquare.getZone() + 18).isBlackSquare() &&
                                    board.getSquare(selectedSquare.getZone() + 18).getPiece() == null &&
                                    board.getSquare(selectedSquare.getZone() + 9).getPiece() != null &&
                                    !board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals(selectedSquare.getPiece().getColor())))) {
                ableToDoubleJump = true;
            } else {
                ableToDoubleJump = false;
            }
        } else {
            if (currentPlayer.getColor().equals("black")) {//white person just jumped
                if ((selectedSquare.getZone() - 14 >= 0 &&
                        board.getSquare(selectedSquare.getZone() - 14).isBlackSquare() &&
                        board.getSquare(selectedSquare.getZone() - 14).getPiece() == null &&
                        board.getSquare(selectedSquare.getZone() - 7).getPiece() != null &&
                        !board.getSquare(selectedSquare.getZone() - 7).getPiece().getColor().equals(selectedSquare.getPiece().getColor())) ||
                        (selectedSquare.getZone() - 18 >= 0 &&
                                board.getSquare(selectedSquare.getZone() - 18).isBlackSquare() &&
                                board.getSquare(selectedSquare.getZone() - 18).getPiece() == null &&
                                board.getSquare(selectedSquare.getZone() - 9).getPiece() != null &&
                                !board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals(selectedSquare.getPiece().getColor()))) {
                    ableToDoubleJump = true;
                } else {
                    ableToDoubleJump = false;
                }
            } else { //black person just jumped
                if ((selectedSquare.getZone() + 14 <= 63 &&
                        board.getSquare(selectedSquare.getZone() + 14).isBlackSquare() &&
                        board.getSquare(selectedSquare.getZone() + 14).getPiece() == null &&
                        board.getSquare(selectedSquare.getZone() + 7).getPiece() != null &&
                        !board.getSquare(selectedSquare.getZone() + 7).getPiece().getColor().equals(selectedSquare.getPiece().getColor())) ||
                        (selectedSquare.getZone() + 18 <= 63 &&
                                board.getSquare(selectedSquare.getZone() + 18).isBlackSquare() &&
                                board.getSquare(selectedSquare.getZone() + 18).getPiece() == null &&
                                board.getSquare(selectedSquare.getZone() + 9).getPiece() != null &&
                                !board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals(selectedSquare.getPiece().getColor()))) {
                    ableToDoubleJump = true;
                } else {
                    ableToDoubleJump = false;
                }
            }
        }
    }
}
