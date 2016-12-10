package com.example.android.checkers;

import java.util.ArrayList;

/**
 * Created by evankaplan on 12/8/16.
 */

public class BoardInt {
    private ComputerBoard board;
    private int i;
    private int firstClick;
    private int secondClick;
    private ArrayList<Integer> array;

    public BoardInt(ComputerBoard b, int i, int firstClick, int secondClick) {
        board = new ComputerBoard(b);
        this.i = i;
        this.firstClick = firstClick;
        this.secondClick = secondClick;
        array = new ArrayList<>();
    }

    public BoardInt(ComputerBoard b, int i, int firstClick, int secondClick, int newZone, ArrayList<Integer> arr) {
        board = new ComputerBoard(b);
        this.i = i;
        this.firstClick = firstClick;
        this.secondClick = secondClick;
        array = new ArrayList<>();
        for (int j = 0; j <= arr.size() - 1; j++) {
            array.add(arr.get(j));
        }
        array.add(newZone);
    }

    public BoardInt(BoardInt boardInt) {
        board = new ComputerBoard(boardInt.getBoard());
        i = boardInt.getI();
        firstClick = boardInt.getFirstClick();
        secondClick = boardInt.getSecondClick();
        array = new ArrayList<>();
        for (int j = 0; j <= boardInt.getArray().size() - 1; j++) {
            array.add(boardInt.getArray().get(j));
        }
    }

    public BoardInt(BoardInt boardInt, int newZone) {
        array = new ArrayList<>();
        for (int j = 0; j <= boardInt.getArray().size() - 1; j++) {
            array.add(boardInt.getArray().get(j));
        }
        array.add(newZone);
        board = new ComputerBoard(boardInt.getBoard());
        i = boardInt.getI();
        firstClick = boardInt.getFirstClick();
        secondClick = boardInt.getSecondClick();
    }

    public ComputerBoard getBoard() {
        return board;
    }

    public int getI() {
        return i;
    }

    public int getFirstClick() {
        return firstClick;
    }

    public int getSecondClick() {
        return secondClick;
    }

    public ArrayList<Integer> getArray() {
        return array;
    }
}
