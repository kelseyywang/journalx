package com.journalxapp.kelseywang.journalx;

/**
 * Created by kelseywang on 3/6/17.
 */

public class ListElement {
    private String q1;
    private String q2;
    private String month;
    private String drawableChar;
    private int color;

    public ListElement(String q1, String q2, String month, String drawableChar, int color) {
        this.q1 = q1;
        this.q2 = q2;
        this.month = month;
        this.drawableChar = drawableChar;
        this.color = color;
    }

    public String getQ1() {
        return q1;
    }

    public String getQ2() {
        return q2;
    }

    public String getMonth() {
        return month;
    }

    public String getDrawableChar() {
        return drawableChar;
    }

    public int getColor() {
        return color;
    }


}
