package com.example.android.checkers;

import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by evankaplan on 12/7/16.
 */

public class Square {
    private final int zone;
    private Piece piece;
    private final boolean blackSquare;
    private ImageView imageView;

    public Square (Square s) {
        zone = s.getZone();
        if (s.getPiece() == null) {
            piece = null;
        } else {
            piece = new Piece(s.getPiece().getColor(), s.getPiece().getKing());
        }
        blackSquare = s.isBlackSquare();
        imageView = s.getImageView();
    }

    public Square(int zone, Piece p, boolean blackSquare, ImageView imageView) {
        this.zone = zone;
        if (p == null) {
            piece = p;
        } else {
            piece = new Piece(p.getColor());
        }
        //piece = p;
        this.blackSquare = blackSquare;
        this.imageView = imageView;
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

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

//    public void setZone(int i) {
//        this.zone = i;
//    }
}
