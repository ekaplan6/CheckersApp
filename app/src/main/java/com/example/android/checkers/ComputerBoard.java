package com.example.android.checkers;

import android.widget.ImageView;

/**
 * Created by evankaplan on 12/7/16.
 */

public class ComputerBoard {
    private ComputerSquare[] board = new ComputerSquare[64];
    private ComputerSquare selectorSquare;
    private String winner;
    private int player1;
    private int player2;
    private int player1King;
    private int player2King;


    public ComputerBoard(ComputerBoard b) {
        ComputerSquare[] bArray = b.getBoard();
        for (int i = 0; i < board.length; i++) {
            board[i] = new ComputerSquare(bArray[i]);
        }
        selectorSquare = new ComputerSquare(b.getSelectorSquare());
        winner = b.getWinner();
        player1 = b.getPlayer1();
        player2 = b.getPlayer2();
        player1King = b.getPlayer1King();
        player2King = b.getPlayer2King();
    }

    public ComputerBoard(Board b) {
        Square[] bArray = b.getBoard();
        for (int i = 0; i < board.length; i++) {
            board[i] = new ComputerSquare(bArray[i]);
        }
        selectorSquare = new ComputerSquare(b.getSelectorSquare());
        winner = b.getWinner();
        player1 = b.getPlayer1();
        player2 = b.getPlayer2();
        player1King = b.getPlayer1King();
        player2King = b.getPlayer2King();
    }

    public ComputerSquare getSquare(int i) {
        return board[i];
    }

    public ComputerSquare getSelectorSquare() {
        return selectorSquare;
    }

    public void setSelectorSquare(ComputerSquare square) {
        this.selectorSquare = square;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public ComputerSquare[] getBoard() {
        return board;
    }

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public void setPlayer1(int player1) {
        this.player1 = player1;
    }

    public void setPlayer2(int player2) {
        this.player2 = player2;
    }

    public int getPlayer1King() {
        return player1King;
    }

    public int getPlayer2King() {
        return player2King;
    }

    public void setPlayer1King(int player1King) {
        this.player1King = player1King;
    }

    public void setPlayer2King(int player2King) {
        this.player2King = player2King;
    }
}
