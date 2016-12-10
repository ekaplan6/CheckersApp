package com.example.android.checkers;

import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by evankaplan on 12/7/16.
 */

public class ComputerSquare {
    private final int zone;
    private Piece piece;
    private final boolean blackSquare;

    public ComputerSquare (ComputerSquare s) {
        zone = s.getZone();
        if (s.getPiece() == null) {
            piece = null;
        } else {
            piece = new Piece(s.getPiece().getColor(), s.getPiece().getKing());
        }
        blackSquare = s.isBlackSquare();
    }

    public ComputerSquare (Square s) {
        zone = s.getZone();
        if (s.getPiece() == null) {
            piece = null;
        } else {
            piece = new Piece(s.getPiece().getColor(), s.getPiece().getKing());
        }
        blackSquare = s.isBlackSquare();
    }

    public ComputerSquare(int zone, Piece p, boolean blackSquare, ImageView imageView) {
        this.zone = zone;
        if (p == null) {
            piece = p;
        } else {
            piece = new Piece(p.getColor());
        }
        //piece = p;
        this.blackSquare = blackSquare;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isBlackSquare() {
        return blackSquare;
    }

    public int getZone() {
        return zone;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

//    public void setZone(int i) {
//        this.zone = i;
//    }
}
