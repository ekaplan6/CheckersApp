package com.example.android.checkers;

/**
 * Created by evankaplan on 12/7/16.
 */

public class Piece {
    private String color;
    private boolean king;


    public Piece(String color) {
        this(color, false);
    }

    public Piece(String color, boolean king) {
        this.color = color;
        this.king = king;

    }

    public String getColor() {
        return color;
    }

    public void setKing(boolean king) {
        this.king = king;
    }

    public boolean getKing() {
        return king;
    }

}
