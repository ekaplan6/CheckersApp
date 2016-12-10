package com.example.android.checkers;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by evankaplan on 12/8/16.
 */

public class BoardTree {
    private ComputerBoard board;
    private List<BoardInt> boardListLayer1 = new ArrayList<>();
    private List<BoardInt> boardListLayerTempBlack = new ArrayList<>();
    private List<BoardInt> boardListLayerTempWhite = new ArrayList<>();
    private List<BoardInt> boardListLayerFinal = new ArrayList<>();
    boolean test = false;
    boolean skip = false;

    private int counter = 0;

    public BoardTree(ComputerBoard b) {

        board = new ComputerBoard(b);
        constructor(board);

        boardListLayerTempWhite = getNextLayer(boardListLayer1, "white");
        boardListLayerTempBlack = getNextLayer(boardListLayerTempWhite, "black");
        boardListLayerTempWhite = getNextLayer(boardListLayerTempBlack, "white");
        //boardListLayerTempBlack = getNextLayer(boardListLayerTempWhite, "black");

        boardListLayerFinal = boardListLayerTempWhite;

    }

    private void constructor(ComputerBoard board1){

        for (ComputerSquare square: board1.getBoard()) {
            recurse(square, board1, new ComputerGameLogic(), false);
        }
    }

    private void recurse(ComputerSquare square, ComputerBoard board1, ComputerGameLogic gameLogic, boolean canDoubleJump) {
        boolean canDoubleJumpTemp = canDoubleJump;
        ComputerGameLogic gameLogicTemp = new ComputerGameLogic(gameLogic);


        if (square.isBlackSquare() && square.getPiece() != null
                && square.getPiece().getColor().equals("black")) {
            if (!square.getPiece().getKing()) { //for all players non kings
                if (!canDoubleJump && square.getZone() + 9 <= 63 &&
                        board1.getSquare(square.getZone() + 9).isBlackSquare() &&
                        board1.getSquare(square.getZone() + 9).getPiece() == null) {
                    gameLogic = new ComputerGameLogic(board1, "black");
                    gameLogic.zoneClicked(square.getZone());
                    gameLogic.zoneClicked(square.getZone() + 9);//////changes square.Piece to null
                    boardListLayer1.add(new BoardInt(gameLogic.getBoard(),
                            counter, square.getZone(), square.getZone() + 9));
                    counter++;
                }
                //System.out.println("TEST2");
                if (!canDoubleJump && square.getZone() + 7 <= 63 &&
                        board1.getSquare(square.getZone() + 7).isBlackSquare() &&
                        board1.getSquare(square.getZone() + 7).getPiece() == null) {
                    gameLogic = new ComputerGameLogic(board1, "black");
                    gameLogic.zoneClicked(square.getZone());
                    gameLogic.zoneClicked(square.getZone() + 7);
                    boardListLayer1.add(new BoardInt(gameLogic.getBoard(),
                            counter, square.getZone(), square.getZone() + 7));
                    counter++;
                }
                //System.out.println("TEST3");
                if (square.getZone() + 18 <= 63 &&
                        board1.getSquare(square.getZone() + 18).isBlackSquare() &&
                        board1.getSquare(square.getZone() + 18).getPiece() == null &&
                        board1.getSquare(square.getZone() + 9).getPiece() != null &&
                        !board1.getSquare(square.getZone() + 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                    if (!canDoubleJumpTemp) {
                        gameLogicTemp = new ComputerGameLogic(board1, "black");
                        gameLogicTemp.zoneClicked(square.getZone());
                        gameLogicTemp.zoneClicked(square.getZone() + 18);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(),
                                counter, square.getZone(), square.getZone() + 18));
                    } else {
                        gameLogicTemp.zoneClicked(square.getZone() + 18);
                        BoardInt temp = boardListLayer1.remove(boardListLayer1.size() - 1);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                temp.getSecondClick(), square.getZone() + 18, temp.getArray()));
                    }
                    if (gameLogicTemp.getAbleToDoubleJump()) {
                        recurse(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new ComputerBoard(gameLogicTemp.getBoard()), new ComputerGameLogic(gameLogicTemp), true);
                    } else {
                        counter++;
                    }
                    gameLogicTemp = new ComputerGameLogic(gameLogic);
                    canDoubleJumpTemp = canDoubleJump;
                }
                //System.out.println("TEST4");
                if (square.getZone() + 14 <= 63 &&//////////
                        board1.getSquare(square.getZone() + 14).isBlackSquare() &&
                        board1.getSquare(square.getZone() + 14).getPiece() == null &&
                        board1.getSquare(square.getZone() + 7).getPiece() != null &&
                        !board1.getSquare(square.getZone() + 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                    if (!canDoubleJumpTemp) {
                        gameLogicTemp = new ComputerGameLogic(board1, "black");
                        gameLogicTemp.zoneClicked(square.getZone());
                        gameLogicTemp.zoneClicked(square.getZone() + 14);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(),
                                counter, square.getZone(), square.getZone() + 14));
                    } else {
                        gameLogicTemp.zoneClicked(square.getZone() + 14);
                        BoardInt temp = boardListLayer1.remove(boardListLayer1.size() - 1);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                temp.getSecondClick(), square.getZone() + 14, temp.getArray()));
                    }
                    if (gameLogicTemp.getAbleToDoubleJump()) {
                        recurse(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new ComputerBoard(gameLogicTemp.getBoard()), new ComputerGameLogic(gameLogicTemp), true);
                    } else {
                        counter++;
                    }
                    gameLogicTemp = new ComputerGameLogic(gameLogic);
                    canDoubleJumpTemp = canDoubleJump;
                }
                //System.out.println("TEST5");
            } else { //for all players kings
                //System.out.println("TEST6");
                if (!canDoubleJump && square.getZone() - 9 >= 0 &&
                        board1.getSquare(square.getZone() - 9).isBlackSquare() &&
                        board1.getSquare(square.getZone() - 9).getPiece() == null) {
                    gameLogic = new ComputerGameLogic(board1, "black");
                    gameLogic.zoneClicked(square.getZone());
                    gameLogic.zoneClicked(square.getZone() - 9);
                    boardListLayer1.add(new BoardInt(gameLogic.getBoard(),
                            counter, square.getZone(), square.getZone() - 9));
                    counter++;
                }
                //System.out.println("TEST7");
                if (!canDoubleJump && square.getZone() - 7 >= 0 &&
                        board1.getSquare(square.getZone() - 7).isBlackSquare() &&
                        board1.getSquare(square.getZone() - 7).getPiece() == null) {
                    gameLogic = new ComputerGameLogic(board1, "black");
                    gameLogic.zoneClicked(square.getZone());
                    gameLogic.zoneClicked(square.getZone() - 7);
                    boardListLayer1.add(new BoardInt(gameLogic.getBoard(),
                            counter, square.getZone(), square.getZone() - 7));
                    counter++;
                }
                //System.out.println("TEST8");
                if (square.getZone() - 18 >= 0 &&
                        board1.getSquare(square.getZone() - 18).isBlackSquare() &&
                        board1.getSquare(square.getZone() - 18).getPiece() == null &&
                        board1.getSquare(square.getZone() - 9).getPiece() != null &&
                        !board1.getSquare(square.getZone() - 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                    if (!canDoubleJumpTemp) {
                        gameLogicTemp = new ComputerGameLogic(board1, "black");
                        gameLogicTemp.zoneClicked(square.getZone());
                        gameLogicTemp.zoneClicked(square.getZone() - 18);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(),
                                counter, square.getZone(), square.getZone() - 18));
                    } else {
                        gameLogicTemp.zoneClicked(square.getZone() - 18);
                        BoardInt temp = boardListLayer1.remove(boardListLayer1.size() - 1);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                temp.getSecondClick(), square.getZone() - 18, temp.getArray()));
                    }
                    if (gameLogicTemp.getAbleToDoubleJump()) {
                        recurse(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new ComputerBoard(gameLogicTemp.getBoard()), new ComputerGameLogic(gameLogicTemp), true);
                    } else {
                        counter++;
                    }
                    gameLogicTemp = new ComputerGameLogic(gameLogic);
                    canDoubleJumpTemp = canDoubleJump;
                }
                //System.out.println("TEST9");
                if (square.getZone() - 14 >= 0 &&
                        board1.getSquare(square.getZone() - 14).isBlackSquare() &&
                        board1.getSquare(square.getZone() - 14).getPiece() == null &&
                        board1.getSquare(square.getZone() - 7).getPiece() != null &&
                        !board1.getSquare(square.getZone() - 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                    if (!canDoubleJumpTemp) {
                        gameLogicTemp = new ComputerGameLogic(board1, "black");
                        gameLogicTemp.zoneClicked(square.getZone());
                        gameLogicTemp.zoneClicked(square.getZone() - 14);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(),
                                counter, square.getZone(), square.getZone() -14));
                    } else {
                        gameLogicTemp.zoneClicked(square.getZone() - 14);
                        BoardInt temp = boardListLayer1.remove(boardListLayer1.size() - 1);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                temp.getSecondClick(), square.getZone() - 14, temp.getArray()));
                    }
                    if (gameLogicTemp.getAbleToDoubleJump()) {
                        recurse(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new ComputerBoard(gameLogicTemp.getBoard()), new ComputerGameLogic(gameLogicTemp), true);
                    } else {
                        counter++;
                    }
                    gameLogicTemp = new ComputerGameLogic(gameLogic);
                    canDoubleJumpTemp = canDoubleJump;
                }
                if (!canDoubleJump && square.getZone() + 9 <= 63 &&
                        board1.getSquare(square.getZone() + 9).isBlackSquare() &&
                        board1.getSquare(square.getZone() + 9).getPiece() == null) {
                    gameLogic = new ComputerGameLogic(board1, "black");
                    gameLogic.zoneClicked(square.getZone());
                    gameLogic.zoneClicked(square.getZone() + 9);
                    boardListLayer1.add(new BoardInt(gameLogic.getBoard(),
                            counter, square.getZone(), square.getZone() + 9));
                    counter++;
                }
                if (!canDoubleJump && square.getZone() + 7 <= 63 &&
                        board1.getSquare(square.getZone() + 7).isBlackSquare() &&
                        board1.getSquare(square.getZone() + 7).getPiece() == null) {
                    gameLogic = new ComputerGameLogic(board1, "black");
                    gameLogic.zoneClicked(square.getZone());
                    gameLogic.zoneClicked(square.getZone() + 7);
                    boardListLayer1.add(new BoardInt(gameLogic.getBoard(),
                            counter, square.getZone(), square.getZone() + 7));
                    counter++;
                }
                if (square.getZone() + 18 <= 63 &&
                        board1.getSquare(square.getZone() + 18).isBlackSquare() &&
                        board1.getSquare(square.getZone() + 18).getPiece() == null &&
                        board1.getSquare(square.getZone() + 9).getPiece() != null &&
                        !board1.getSquare(square.getZone() + 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                    if (!canDoubleJumpTemp) {
                        gameLogicTemp = new ComputerGameLogic(board1, "black");
                        gameLogicTemp.zoneClicked(square.getZone());
                        gameLogicTemp.zoneClicked(square.getZone() + 18);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(),
                                counter, square.getZone(), square.getZone() + 18));
                    } else {
                        gameLogicTemp.zoneClicked(square.getZone() + 18);
                        BoardInt temp = boardListLayer1.remove(boardListLayer1.size() - 1);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                temp.getSecondClick(), square.getZone() + 18, temp.getArray()));
                    }
                    if (gameLogicTemp.getAbleToDoubleJump()) {
                        recurse(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new ComputerBoard(gameLogicTemp.getBoard()), new ComputerGameLogic(gameLogicTemp), true);
                    } else {
                        counter++;
                    }
                    gameLogicTemp = new ComputerGameLogic(gameLogic);
                    canDoubleJumpTemp = canDoubleJump;
                }
                if (square.getZone() + 14 <= 63 &&
                        board1.getSquare(square.getZone() + 14).isBlackSquare() &&
                        board1.getSquare(square.getZone() + 14).getPiece() == null &&
                        board1.getSquare(square.getZone() + 7).getPiece() != null &&
                        !board1.getSquare(square.getZone() + 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                    if (!canDoubleJumpTemp) {
                        gameLogicTemp = new ComputerGameLogic(board1, "black");
                        gameLogicTemp.zoneClicked(square.getZone());
                        gameLogicTemp.zoneClicked(square.getZone() + 14);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(),
                                counter, square.getZone(), square.getZone() + 14));
                    } else {
                        gameLogicTemp.zoneClicked(square.getZone() + 14);
                        BoardInt temp = boardListLayer1.remove(boardListLayer1.size() - 1);
                        boardListLayer1.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                temp.getSecondClick(), square.getZone() + 14, temp.getArray()));
                    }
                    if (gameLogicTemp.getAbleToDoubleJump()) {
                        recurse(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new ComputerBoard(gameLogicTemp.getBoard()), new ComputerGameLogic(gameLogicTemp), true);
                    } else {
                        counter++;
                    }
                    gameLogicTemp = new ComputerGameLogic(gameLogic);
                    canDoubleJumpTemp = canDoubleJump;
                }
            }
        }
    }

    private List<BoardInt> getNextLayer(List<BoardInt> boardListLayerIn, String color) {
        //create the next layer of boards
        List<BoardInt> boardListListLayerOut = new ArrayList<>();
        for (BoardInt board1 : boardListLayerIn) {
            if (board1.getBoard().getWinner() == null) {
                for (ComputerSquare square : board1.getBoard().getBoard()) {

                    recurse2(square, board1, color, boardListListLayerOut, new ComputerGameLogic(board1.getBoard(), color), false);

                }
            } else { //board has a winner
                boardListListLayerOut.add(new BoardInt(board1));
            }
        }
        return  boardListListLayerOut;

    }

    private void recurse2(ComputerSquare square, BoardInt board1, String color,
                          List<BoardInt> boardListListLayerOut, ComputerGameLogic gameLogic, boolean canDoubleJump) {

        boolean canDoubleJumpTemp = canDoubleJump;
        ComputerGameLogic gameLogicTemp = new ComputerGameLogic(gameLogic);


        if (test) {
            skip = true;
        }
        test = true;
        if (square.isBlackSquare() && square.getPiece() != null
                && square.getPiece().getColor().equals(color)) {
            if (!square.getPiece().getKing()) { //for all players non kings
                if (color.equals("white")) {
                    if (!canDoubleJump && square.getZone() - 9 >= 0 &&
                            board1.getBoard().getSquare(square.getZone() - 9).isBlackSquare() &&
                            board1.getBoard().getSquare(square.getZone() - 9).getPiece() == null) {
                        //System.out.println("TEST1");
                        gameLogic = new ComputerGameLogic(board1.getBoard(), color);
                        gameLogic.zoneClicked(square.getZone());
                        gameLogic.zoneClicked(square.getZone() - 9);
                        boardListListLayerOut.add(new BoardInt(gameLogic.getBoard(),
                                board1.getI(), square.getZone(), square.getZone() - 9));
                    }
                    if (!canDoubleJump && square.getZone() - 7 >= 0 &&
                            board1.getBoard().getSquare(square.getZone() - 7).isBlackSquare() &&
                            board1.getBoard().getSquare(square.getZone() - 7).getPiece() == null) {
                        //System.out.println("TEST2");
                        gameLogic = new ComputerGameLogic(board1.getBoard(), color);
                        gameLogic.zoneClicked(square.getZone());
                        gameLogic.zoneClicked(square.getZone() - 7);
                        boardListListLayerOut.add(new BoardInt(gameLogic.getBoard(),
                                board1.getI(), square.getZone(), square.getZone() - 7));
                    }
                    if (square.getZone() - 18 >= 0 &&
                            board1.getBoard().getSquare(square.getZone() - 18).isBlackSquare() &&
                            board1.getBoard().getSquare(square.getZone() - 18).getPiece() == null &&
                            board1.getBoard().getSquare(square.getZone() - 9).getPiece() != null &&
                            !board1.getBoard().getSquare(square.getZone() - 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                        if (!canDoubleJumpTemp) {
                            gameLogicTemp = new ComputerGameLogic(board1.getBoard(), color);
                            gameLogicTemp.zoneClicked(square.getZone());
                            gameLogicTemp.zoneClicked(square.getZone() - 18);
                            boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(),
                                    board1.getI(), square.getZone(), square.getZone() - 18));
                        } else {
                            gameLogicTemp.zoneClicked(square.getZone() - 18);
                            BoardInt temp = boardListListLayerOut.remove(boardListListLayerOut.size() - 1);
                            boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                    temp.getSecondClick(), square.getZone() - 18, temp.getArray()));
                        }
                        if (gameLogicTemp.getAbleToDoubleJump() && !skip) {
                            recurse2(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new BoardInt(board1), color, boardListListLayerOut, new ComputerGameLogic(gameLogicTemp), true);
                        }
                        gameLogicTemp = new ComputerGameLogic(gameLogic);
                        canDoubleJumpTemp = canDoubleJump;
                    }
                    if (square.getZone() - 14 >= 0 &&
                            board1.getBoard().getSquare(square.getZone() - 14).isBlackSquare() &&
                            board1.getBoard().getSquare(square.getZone() - 14).getPiece() == null &&
                            board1.getBoard().getSquare(square.getZone() - 7).getPiece() != null &&
                            !board1.getBoard().getSquare(square.getZone() - 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                        if (!canDoubleJumpTemp) {
                            gameLogicTemp = new ComputerGameLogic(board1.getBoard(), color);
                            gameLogicTemp.zoneClicked(square.getZone());
                            gameLogicTemp.zoneClicked(square.getZone() - 14);
                            boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(),
                                    board1.getI(), square.getZone(), square.getZone() - 14));
                        } else {
                            gameLogicTemp.zoneClicked(square.getZone() - 14);
                            BoardInt temp = boardListListLayerOut.remove(boardListListLayerOut.size() - 1);
                            boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                    temp.getSecondClick(), square.getZone() - 14, temp.getArray()));
                        }
                        if (gameLogicTemp.getAbleToDoubleJump() && !skip) {
                            recurse2(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new BoardInt(board1), color, boardListListLayerOut, new ComputerGameLogic(gameLogicTemp), true);
                        }
                        gameLogicTemp = new ComputerGameLogic(gameLogic);
                        canDoubleJumpTemp = canDoubleJump;
                    }
                } else {
                    if (!canDoubleJump && square.getZone() + 9 <= 63 &&
                            board1.getBoard().getSquare(square.getZone() + 9).isBlackSquare() &&
                            board1.getBoard().getSquare(square.getZone() + 9).getPiece() == null) {
                        //System.out.println("TEST5");
                        gameLogic = new ComputerGameLogic(board1.getBoard(), color);
                        gameLogic.zoneClicked(square.getZone());
                        gameLogic.zoneClicked(square.getZone() + 9);
                        boardListListLayerOut.add(new BoardInt(gameLogic.getBoard(),
                                board1.getI(), square.getZone(), square.getZone() + 9));
                    }
                    if (!canDoubleJump && square.getZone() + 7 <= 63 &&
                            board1.getBoard().getSquare(square.getZone() + 7).isBlackSquare() &&
                            board1.getBoard().getSquare(square.getZone() + 7).getPiece() == null) {
                        //System.out.println("TEST6");
                        gameLogic = new ComputerGameLogic(board1.getBoard(), color);
                        gameLogic.zoneClicked(square.getZone());
                        gameLogic.zoneClicked(square.getZone() + 7);
                        boardListListLayerOut.add(new BoardInt(gameLogic.getBoard(),
                                board1.getI(), square.getZone(), square.getZone() + 7));
                    }
                    if (square.getZone() + 18 <= 63 &&
                            board1.getBoard().getSquare(square.getZone() + 18).isBlackSquare() &&
                            board1.getBoard().getSquare(square.getZone() + 18).getPiece() == null &&
                            board1.getBoard().getSquare(square.getZone() + 9).getPiece() != null &&
                            !board1.getBoard().getSquare(square.getZone() + 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                        if (!canDoubleJumpTemp) {
                            gameLogicTemp = new ComputerGameLogic(board1.getBoard(), color);
                            gameLogicTemp.zoneClicked(square.getZone());
                            gameLogicTemp.zoneClicked(square.getZone() + 18);
                            boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(),
                                    board1.getI(), square.getZone(), square.getZone() + 18));
                        } else {
                            gameLogicTemp.zoneClicked(square.getZone() + 18);
                            BoardInt temp = boardListListLayerOut.remove(boardListListLayerOut.size() - 1);
                            boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                    temp.getSecondClick(), square.getZone() + 18, temp.getArray()));
                        }
                        if (gameLogicTemp.getAbleToDoubleJump() && !skip) {
                            recurse2(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new BoardInt(board1), color, boardListListLayerOut, new ComputerGameLogic(gameLogicTemp), true);
                        }
                        gameLogicTemp = new ComputerGameLogic(gameLogic);
                        canDoubleJumpTemp = canDoubleJump;
                    }
                    if (square.getZone() + 14 <= 63 &&
                            board1.getBoard().getSquare(square.getZone() + 14).isBlackSquare() &&
                            board1.getBoard().getSquare(square.getZone() + 14).getPiece() == null &&
                            board1.getBoard().getSquare(square.getZone() + 7).getPiece() != null &&
                            !board1.getBoard().getSquare(square.getZone() + 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                        if (!canDoubleJumpTemp) {
                            gameLogicTemp = new ComputerGameLogic(board1.getBoard(), color);
                            gameLogicTemp.zoneClicked(square.getZone());
                            gameLogicTemp.zoneClicked(square.getZone() + 14);
                            boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(),
                                    board1.getI(), square.getZone(), square.getZone() + 14));
                        } else {
                            gameLogicTemp.zoneClicked(square.getZone() + 14);
                            BoardInt temp = boardListListLayerOut.remove(boardListListLayerOut.size() - 1);
                            boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                    temp.getSecondClick(), square.getZone() + 14, temp.getArray()));
                        }
                        if (gameLogicTemp.getAbleToDoubleJump() && !skip) {
                            recurse2(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new BoardInt(board1), color, boardListListLayerOut, new ComputerGameLogic(gameLogicTemp), true);
                        }
                        gameLogicTemp = new ComputerGameLogic(gameLogic);
                        canDoubleJumpTemp = canDoubleJump;
                    }
                }
            } else { //for all players kings
                if (!canDoubleJump && square.getZone() - 9 >= 0 &&
                        board1.getBoard().getSquare(square.getZone() - 9).isBlackSquare() &&
                        board1.getBoard().getSquare(square.getZone() - 9).getPiece() == null) {
                    //System.out.println("TEST9");
                    gameLogic = new ComputerGameLogic(board1.getBoard(), color);
                    gameLogic.zoneClicked(square.getZone());
                    gameLogic.zoneClicked(square.getZone() - 9);
                    boardListListLayerOut.add(new BoardInt(gameLogic.getBoard(),
                            board1.getI(), square.getZone(), square.getZone() - 9));
                }
                if (!canDoubleJump && square.getZone() - 7 >= 0 &&
                        board1.getBoard().getSquare(square.getZone() - 7).isBlackSquare() &&
                        board1.getBoard().getSquare(square.getZone() - 7).getPiece() == null) {
                    //System.out.println("TEST10");
                    gameLogic = new ComputerGameLogic(board1.getBoard(), color);
                    gameLogic.zoneClicked(square.getZone());
                    gameLogic.zoneClicked(square.getZone() - 7);
                    boardListListLayerOut.add(new BoardInt(gameLogic.getBoard(),
                            board1.getI(), square.getZone(), square.getZone() - 7));
                }
                if (square.getZone() - 18 >= 0 &&
                        board1.getBoard().getSquare(square.getZone() - 18).isBlackSquare() &&
                        board1.getBoard().getSquare(square.getZone() - 18).getPiece() == null &&
                        board1.getBoard().getSquare(square.getZone() - 9).getPiece() != null &&
                        !board1.getBoard().getSquare(square.getZone() - 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                    if (!canDoubleJumpTemp) {
                        gameLogicTemp = new ComputerGameLogic(board1.getBoard(), color);
                        gameLogicTemp.zoneClicked(square.getZone());
                        gameLogicTemp.zoneClicked(square.getZone() - 18);
                        boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(),
                                board1.getI(), square.getZone(), square.getZone() - 18));
                    } else {
                        gameLogicTemp.zoneClicked(square.getZone() - 18);
                        BoardInt temp = boardListListLayerOut.remove(boardListListLayerOut.size() - 1);
                        boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                temp.getSecondClick(), square.getZone() - 18, temp.getArray()));
                    }
                    if (gameLogicTemp.getAbleToDoubleJump() && !skip) {
                        recurse2(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new BoardInt(board1), color, boardListListLayerOut, new ComputerGameLogic(gameLogicTemp), true);
                    }
                    gameLogicTemp = new ComputerGameLogic(gameLogic);
                    canDoubleJumpTemp = canDoubleJump;
                }
                if (square.getZone() - 14 >= 0 &&
                        board1.getBoard().getSquare(square.getZone() - 14).isBlackSquare() &&
                        board1.getBoard().getSquare(square.getZone() - 14).getPiece() == null &&
                        board1.getBoard().getSquare(square.getZone() - 7).getPiece() != null &&
                        !board1.getBoard().getSquare(square.getZone() - 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                    if (!canDoubleJumpTemp) {
                        gameLogicTemp = new ComputerGameLogic(board1.getBoard(), color);
                        gameLogicTemp.zoneClicked(square.getZone());
                        gameLogicTemp.zoneClicked(square.getZone() - 14);
                        boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(),
                                board1.getI(), square.getZone(), square.getZone() - 14));
                    } else {
                        gameLogicTemp.zoneClicked(square.getZone() - 14);
                        BoardInt temp = boardListListLayerOut.remove(boardListListLayerOut.size() - 1);
                        boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                temp.getSecondClick(), square.getZone() - 14, temp.getArray()));
                    }
                    if (gameLogicTemp.getAbleToDoubleJump() && !skip) {
                        recurse2(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new BoardInt(board1), color, boardListListLayerOut, new ComputerGameLogic(gameLogicTemp), true);
                    }
                    gameLogicTemp = new ComputerGameLogic(gameLogic);
                    canDoubleJumpTemp = canDoubleJump;
                }
                if (!canDoubleJump && square.getZone() + 9 <= 63 &&
                        board1.getBoard().getSquare(square.getZone() + 9).isBlackSquare() &&
                        board1.getBoard().getSquare(square.getZone() + 9).getPiece() == null) {
                    //System.out.println("TEST13");
                    gameLogic = new ComputerGameLogic(board1.getBoard(), color);
                    gameLogic.zoneClicked(square.getZone());
                    gameLogic.zoneClicked(square.getZone() + 9);
                    boardListListLayerOut.add(new BoardInt(gameLogic.getBoard(),
                            board1.getI(), square.getZone(), square.getZone() + 9));
                }
                if (!canDoubleJump && square.getZone() + 7 <= 63 &&
                        board1.getBoard().getSquare(square.getZone() + 7).isBlackSquare() &&
                        board1.getBoard().getSquare(square.getZone() + 7).getPiece() == null) {
                    //System.out.println("TEST14");
                    gameLogic = new ComputerGameLogic(board1.getBoard(), color);
                    gameLogic.zoneClicked(square.getZone());
                    gameLogic.zoneClicked(square.getZone() + 7);
                    boardListListLayerOut.add(new BoardInt(gameLogic.getBoard(),
                            board1.getI(), square.getZone(), square.getZone() + 7));
                }
                if (square.getZone() + 18 <= 63 &&
                        board1.getBoard().getSquare(square.getZone() + 18).isBlackSquare() &&
                        board1.getBoard().getSquare(square.getZone() + 18).getPiece() == null &&
                        board1.getBoard().getSquare(square.getZone() + 9).getPiece() != null &&
                        !board1.getBoard().getSquare(square.getZone() + 9).getPiece().getColor().equals(square.getPiece().getColor())) {
                    if (!canDoubleJumpTemp) {
                        gameLogicTemp = new ComputerGameLogic(board1.getBoard(), color);
                        gameLogicTemp.zoneClicked(square.getZone());
                        gameLogicTemp.zoneClicked(square.getZone() + 18);
                        boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(),
                                board1.getI(), square.getZone(), square.getZone() + 18));
                    } else {
                        gameLogicTemp.zoneClicked(square.getZone() + 18);
                        BoardInt temp = boardListListLayerOut.remove(boardListListLayerOut.size() - 1);
                        boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                temp.getSecondClick(), square.getZone() + 18, temp.getArray()));
                    }
                    if (gameLogicTemp.getAbleToDoubleJump() && !skip) {
                        recurse2(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new BoardInt(board1), color, boardListListLayerOut, new ComputerGameLogic(gameLogicTemp), true);
                    }
                    gameLogicTemp = new ComputerGameLogic(gameLogic);
                    canDoubleJumpTemp = canDoubleJump;
                }
                if (square.getZone() + 14 <= 63 &&
                        board1.getBoard().getSquare(square.getZone() + 14).isBlackSquare() &&
                        board1.getBoard().getSquare(square.getZone() + 14).getPiece() == null &&
                        board1.getBoard().getSquare(square.getZone() + 7).getPiece() != null &&
                        !board1.getBoard().getSquare(square.getZone() + 7).getPiece().getColor().equals(square.getPiece().getColor())) {
                    if (!canDoubleJumpTemp) {
                        gameLogicTemp = new ComputerGameLogic(board1.getBoard(), color);
                        gameLogicTemp.zoneClicked(square.getZone());
                        gameLogicTemp.zoneClicked(square.getZone() + 14);
                        boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(),
                                board1.getI(), square.getZone(), square.getZone() + 14));
                    } else {
                        gameLogicTemp.zoneClicked(square.getZone() + 14);
                        BoardInt temp = boardListListLayerOut.remove(boardListListLayerOut.size() - 1);
                        boardListListLayerOut.add(new BoardInt(gameLogicTemp.getBoard(), temp.getI(), temp.getFirstClick(),
                                temp.getSecondClick(), square.getZone() + 14, temp.getArray()));
                    }
                    if (gameLogicTemp.getAbleToDoubleJump() && !skip) {
                        recurse2(new ComputerSquare(gameLogicTemp.getSelectedSquare()), new BoardInt(board1), color, boardListListLayerOut, new ComputerGameLogic(gameLogicTemp), true);
                    }
                    gameLogicTemp = new ComputerGameLogic(gameLogic);
                    canDoubleJumpTemp = canDoubleJump;
                }
            }
        }
    }

    public BoardInt findBestMove() {
        int[] totalCounter = new int[boardListLayer1.size()];
        int[] boardCounter = new int[boardListLayer1.size()];

        for (BoardInt boardInt: boardListLayerFinal) {
            int currentBoardType = boardInt.getI();
            int currentBoardTotal = 0;
            if (boardInt.getBoard().getPlayer2() > board.getPlayer2()) {
                currentBoardTotal = currentBoardTotal + 1;
            }
            if (boardInt.getBoard().getPlayer2() < board.getPlayer2()) {
                currentBoardTotal = currentBoardTotal - 1;
            }
            if (boardInt.getBoard().getPlayer2King() > board.getPlayer2King()) {
                currentBoardTotal = currentBoardTotal + 5;
            }
            if (boardInt.getBoard().getPlayer2King() < board.getPlayer2King()) {
                currentBoardTotal = currentBoardTotal - 5;
            }


            if (boardInt.getBoard().getPlayer1() > board.getPlayer1()) {
                currentBoardTotal = currentBoardTotal - 1;
            }
            if (boardInt.getBoard().getPlayer1() < board.getPlayer1()) {
                currentBoardTotal = currentBoardTotal + 1;
            }
            if (boardInt.getBoard().getPlayer1King() > board.getPlayer1King()) {
                currentBoardTotal = currentBoardTotal - 5;
            }
            if (boardInt.getBoard().getPlayer1King() < board.getPlayer1King()) {
                currentBoardTotal = currentBoardTotal + 5;
            }

            if (boardInt.getBoard().getWinner() != null) {
                if (boardInt.getBoard().getWinner().equals("black")) {
                    currentBoardTotal = currentBoardType + 10;
                } else {
                    currentBoardTotal = currentBoardType - 10;
                }
            }
            totalCounter[currentBoardType] = totalCounter[currentBoardType] + currentBoardTotal;
            boardCounter[currentBoardType] = boardCounter[currentBoardType] + 1;
        }

        double[] avg = new double[boardListLayer1.size()];
        for (int i = 0; i < boardListLayer1.size(); i++) {
            avg[i] = (double) totalCounter[i] / (double) boardCounter[i];
        }

        int index = 0;
        double max = avg[0];
        int i = 1;
        while (i <= boardListLayer1.size() - 1) {
                if (avg[i] > max) {
                    index = i;
                    max = avg[i];
                }
            i++;
        }
        return boardListLayer1.get(index);
    }

}
