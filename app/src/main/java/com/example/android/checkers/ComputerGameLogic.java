package com.example.android.checkers;


/**
 * Created by evankaplan on 12/7/16.
 */

public class ComputerGameLogic {

    private ComputerBoard board;
    private ComputerSquare selectedSquare;
    private boolean squareSelected;
    private Player currentPlayer;
    private boolean justJumped;
    private int player1;
    private int player2;
    private boolean ableToDoubleJump = false;


    public ComputerGameLogic () {

    }

    public ComputerGameLogic (ComputerBoard board, String currentPlayer) {
        this.board = new ComputerBoard(board);

        selectedSquare = null;
        squareSelected = false;
        justJumped = false;
        this.currentPlayer = new Player(currentPlayer);
        player1 = board.getPlayer1();
        player2 = board.getPlayer2();

    }

    public ComputerGameLogic (ComputerGameLogic gameLogic) {
        if (gameLogic.getBoard() != null) {
            board = new ComputerBoard(gameLogic.getBoard());
        } else {
            board = null;
        }
        if (gameLogic.getSelectedSquare() != null) {
            selectedSquare = new ComputerSquare(gameLogic.getSelectedSquare());
        } else {
            selectedSquare = null;
        }
        squareSelected = gameLogic.getSquareSelected();
        if (gameLogic.getCurrentPlayer() != null) {
            currentPlayer = new Player(gameLogic.getCurrentPlayer());
        } else {
            currentPlayer = null;
        }
        justJumped = gameLogic.getJustJumped();
        player1 = gameLogic.getPlayer1();
        player2 = gameLogic.getPlayer2();
        ableToDoubleJump = gameLogic.getAbleToDoubleJump();
    }

    public boolean zoneClicked(int zone) {

        boolean out = false;
        ComputerSquare square = board.getSquare(zone);
        if (square.getPiece() != null && selectedSquare != null &&
                selectedSquare.getPiece() != null && squareSelected &&
                !(selectedSquare.getPiece().getColor().equals(square.getPiece().getColor()))) {
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
                                squareSelected = true;
                            } else if ((square.getZone() == (selectedSquare.getZone() - 18)) &&
                                    board.getSquare(selectedSquare.getZone() - 9) != null &&
                                    board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals("black")) {
                                out = jump(zone, -1, 1);
                                justJumped = true;
                                currentPlayer.setColor("black");
                                selectedSquare = board.getSquare(selectedSquare.getZone() - 18);
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
                                squareSelected = true;
                            } else if ((square.getZone() == (selectedSquare.getZone() + 18)) &&
                                    board.getSquare(selectedSquare.getZone() + 9) != null &&
                                    board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals("white")) {

                                out = jump(zone, 1, -1);

                                justJumped = true;
                                currentPlayer.setColor("white");
                                selectedSquare = board.getSquare(selectedSquare.getZone() + 18);
                                squareSelected = true;
                            }
                        }
                        //board.getSelectorSquare().getImageView().setAlpha(0.0f);
                    } else if (square.isBlackSquare() && square.getPiece() != null &&
                            currentPlayer.getColor().equals(square.getPiece().getColor())) {
                        selectedSquare = square;
                        squareSelected = true;
                    } else {
                        squareSelected = false;
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
        ComputerSquare newSquare = board.getSquare(zone);
        ComputerSquare oldSquare = board.getSquare(selectedSquare.getZone());

        newSquare.setPiece(oldSquare.getPiece());

        oldSquare.setPiece(null);

        checkForKing(newSquare);
    }

    private boolean jump(int zone, int x, int y) {
        ComputerSquare newSquare = board.getSquare(zone);
        ComputerSquare oldSquare = board.getSquare(selectedSquare.getZone());

        newSquare.setPiece(oldSquare.getPiece());

        oldSquare.setPiece(null);

        ComputerSquare middleSquare;
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


        if (color.equals("white")) {
            player1--;
            board.setPlayer1(player1);
            if (king) {
                board.setPlayer1King(board.getPlayer1King() - 1);
            }
        } else {
            player2--;
            board.setPlayer2(player2);
            if (king) {
                board.setPlayer2King(board.getPlayer2King() - 1);
            }
        }

        checkForKing(newSquare);

        //this is where it is bugging out
        if (player1 == 0) {
            winner("black");
            return true;
        } else if (player2 == 0) {
            winner("white");
            return true;
        }

        return false;
    }

    private void checkDoubleJumpConditions(ComputerSquare square) {
        if (currentPlayer.getColor().equals("black")) {//white person just jumped
            //System.out.println("Getting Closer");
            if (square.getPiece() == null && (((square.getZone() == (selectedSquare.getZone() - 18)) &&
                    board.getSquare(selectedSquare.getZone() - 9) != null &&
                    board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals("black")) ||
                    ((square.getZone() == (selectedSquare.getZone() - 14)) &&
                            board.getSquare(selectedSquare.getZone() - 7) != null &&
                            board.getSquare(selectedSquare.getZone() - 7).getPiece().getColor().equals("black")))) {
                squareSelected = true;
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
                currentPlayer.setColor("black");
            } else {
                justJumped = false;
            }
        }
    }

    private void checkForKing(ComputerSquare square) {
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

    private void kingIt(ComputerSquare square) {
        square.getPiece().setKing(true);
        if (square.getPiece().getColor().equals("white")) {
            board.setPlayer1King(board.getPlayer1King() + 1);
        } else {
            board.setPlayer2King(board.getPlayer2King() + 1);
        }
    }

    private boolean kinglyMove(ComputerSquare square) {
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
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() - 18)) &&
                            board.getSquare(selectedSquare.getZone() - 9) != null &&
                            board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals("black")) {
                        out = jump(zone, -1, 1);
                        justJumped = true;
                        currentPlayer.setColor("black");
                        selectedSquare = board.getSquare(selectedSquare.getZone() - 18);
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() + 14)) &&
                            board.getSquare(selectedSquare.getZone() + 7) != null &&
                            board.getSquare(selectedSquare.getZone() + 7).getPiece().getColor().equals("black")) {
                        out = jump(zone, -1, -1);
                        justJumped = true;
                        currentPlayer.setColor("black");
                        selectedSquare = board.getSquare(selectedSquare.getZone() + 14);
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() + 18)) &&
                            board.getSquare(selectedSquare.getZone() + 9) != null &&
                            board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals("black")) {
                        out = jump(zone, 1, -1);
                        justJumped = true;
                        currentPlayer.setColor("black");
                        selectedSquare = board.getSquare(selectedSquare.getZone() + 18);
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
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() - 18)) &&
                            board.getSquare(selectedSquare.getZone() - 9) != null &&
                            board.getSquare(selectedSquare.getZone() - 9).getPiece().getColor().equals("white")) {
                        out = jump(zone, -1, 1);
                        justJumped = true;
                        currentPlayer.setColor("white");
                        selectedSquare = board.getSquare(selectedSquare.getZone() - 18);
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() + 14)) &&
                            board.getSquare(selectedSquare.getZone() + 7) != null &&
                            board.getSquare(selectedSquare.getZone() + 7).getPiece().getColor().equals("white")) {
                        out = jump(zone, -1, -1);
                        justJumped = true;
                        currentPlayer.setColor("white");
                        selectedSquare = board.getSquare(selectedSquare.getZone() + 14);
                        squareSelected = true;
                    } else if ((square.getZone() == (selectedSquare.getZone() + 18)) &&
                            board.getSquare(selectedSquare.getZone() + 9) != null &&
                            board.getSquare(selectedSquare.getZone() + 9).getPiece().getColor().equals("white")) {
                        out = jump(zone, 1, -1);
                        justJumped = true;
                        currentPlayer.setColor("white");
                        selectedSquare = board.getSquare(selectedSquare.getZone() + 18);
                        squareSelected = true;
                    }
                }
            }
        }
        return out;
    }

    private void checkDoubleJumpConditionsForKing(ComputerSquare square) {
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
                currentPlayer.setColor("black");
            } else {
                justJumped = false;
            }
        }
    }


    public Player getPlayer() {
        return currentPlayer;
    }

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }


    private void winner(String player) {
        if (player.equals("white")) {
            board.setWinner("white");
        } else {
            board.setWinner("black");
        }
    }

    public ComputerBoard getBoard() {
        return board;
    }

    public boolean checkForMoves() {
        for (ComputerSquare square: board.getBoard()) {
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



        BoardTree boardTree = new BoardTree(new ComputerBoard(board));
        BoardInt boardInt = boardTree.findBestMove();

        //System.out.println("Move from: " + boardInt.getFirstClick());
        //System.out.println("To: " + boardInt.getSecondClick());
        //System.out.println("selectedSquare is: " + selectedSquare.getZone());
        //System.out.println("squareSelected is: " + squareSelected);
        //System.out.println("currentPlayer is: " + currentPlayer.getColor());
        //System.out.println("justJumped is: " + justJumped + "\n");

        zoneClicked(boardInt.getFirstClick());
        return zoneClicked(boardInt.getSecondClick());
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

    public ComputerSquare getSelectedSquare() {
        return selectedSquare;
    }

    public boolean getSquareSelected() {
        return squareSelected;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

//    private void initScore() {
//        for (ComputerSquare square: board.getBoard()) {
//            if (square.isBlackSquare() && square.getPiece() != null) {
//                if (square.getPiece().getColor().equals("white")) {
//                    player1++;
//                } else {
//                    player2++;
//                }
//            }
//        }
//    }
}
