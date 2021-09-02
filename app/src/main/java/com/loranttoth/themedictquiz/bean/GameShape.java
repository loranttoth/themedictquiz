package com.loranttoth.themedictquiz.bean;

public abstract class GameShape {
    public int cx;
    public int cy;
    public STATES state;
    public STATES state2;

    public Point[] koords;

    public String text;
    public String text2;

    public abstract boolean isTogether(GameShape gs);

}
