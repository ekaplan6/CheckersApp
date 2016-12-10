package com.example.android.checkers;

import android.widget.ImageView;

/**
 * Created by evankaplan on 12/7/16.
 */

public class Board {
    private Square[] board = new Square[64];
    private Square selectorSquare;
    private String winner;
    private int player1;
    private int player2;
    private int player1King;
    private int player2King;


    public Board(Board b) {
        Square[] bArray = b.getBoard();
        for (int i = 0; i < board.length; i++) {
            board[i] = new Square(bArray[i]);
        }
        selectorSquare = new Square(b.getSelectorSquare());
        winner = b.getWinner();
        player1 = b.getPlayer1();
        player2 = b.getPlayer2();
        player1King = b.getPlayer1King();
        player2King = b.getPlayer2King();
    }

    public Board(ImageView piece1, ImageView piece2, ImageView piece3, ImageView piece4,
                 ImageView piece5, ImageView piece6, ImageView piece7, ImageView piece8,
                 ImageView piece9, ImageView piece10, ImageView piece11, ImageView piece12,
                 ImageView blackPiece1, ImageView blackPiece2, ImageView blackPiece3,
                 ImageView blackPiece4, ImageView blackPiece5, ImageView blackPiece6,
                 ImageView blackPiece7, ImageView blackPiece8, ImageView blackPiece9,
                 ImageView blackPiece10, ImageView blackPiece11, ImageView blackPiece12,
                 ImageView selectorSquare) {

        this.selectorSquare = new Square(56, null, true, selectorSquare);
        player1 = 12;
        player2 = 12;
        player1King = 0;
        player2King = 0;

        for (int i = 0; i < 64; i++) {
            Piece piece;
            boolean blackSquare;
            ImageView imageView;
            if ((i%16 < 8 && (i%16)%2 == 1) || (i%16 > 7 && (i%16)%2 == 0)) {
                blackSquare = true;
                if (i <= 23) {
                    piece = new Piece("black");
                } else if (i <= 39) {
                    piece = null;
                } else {
                    piece = new Piece("white");
                }
            } else {
                blackSquare = false;
                piece = null;
            }

            switch (i) {
                case 56:
                    imageView = piece1;
                    break;
                case 58:
                    imageView = piece2;
                    break;
                case 60:
                    imageView = piece3;
                    break;
                case 62:
                    imageView = piece4;
                    break;
                case 49:
                    imageView = piece5;
                    break;
                case 51:
                    imageView = piece6;
                    break;
                case 53:
                    imageView = piece7;
                    break;
                case 55:
                    imageView = piece8;
                    break;
                case 40:
                    imageView = piece9;
                    break;
                case 42:
                    imageView = piece10;
                    break;
                case 44:
                    imageView = piece11;
                    break;
                case 46:
                    imageView = piece12;
                    break;
                case 1:
                    imageView = blackPiece1;
                    break;
                case 3:
                    imageView = blackPiece2;
                    break;
                case 5:
                    imageView = blackPiece3;
                    break;
                case 7:
                    imageView = blackPiece4;
                    break;
                case 8:
                    imageView = blackPiece5;
                    break;
                case 10:
                    imageView = blackPiece6;
                    break;
                case 12:
                    imageView = blackPiece7;
                    break;
                case 14:
                    imageView = blackPiece8;
                    break;
                case 17:
                    imageView = blackPiece9;
                    break;
                case 19:
                    imageView = blackPiece10;
                    break;
                case 21:
                    imageView = blackPiece11;
                    break;
                case 23:
                    imageView = blackPiece12;
                    break;
                default:
                    imageView = null;
                    break;
            }

            this.board[i] = new Square(i, piece, blackSquare, imageView);
        }
    }

    public Square getSquare(int i) {
        return board[i];
    }

    public Square getSelectorSquare() {
        return selectorSquare;
    }

    public void setSelectorSquare(Square square) {
        this.selectorSquare = square;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public Square[] getBoard() {
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

    public void setPlayer1King (int player1King) {
        this.player1King = player1King;
    }

    public void setPlayer2King(int player2King) {
        this.player2King = player2King;
    }
}
