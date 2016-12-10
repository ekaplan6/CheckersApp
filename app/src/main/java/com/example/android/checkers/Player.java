package com.example.android.checkers;

/**
 * Created by evankaplan on 12/7/16.
 */

public class Player {
    private String color;
    private int score;

    public Player(String color) {
        this.color = color;
    }

    public Player(Player player) {
        color = player.getColor();
        score = player.getScore();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
