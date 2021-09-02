package com.loranttoth.themedictquiz.bean;

public class Shape6 extends GameShape {

    public Shape6(int cx, int cy, String text) {
        this.cx = cx;
        this.cy = cy;
        this.text = text;
        this.state = STATES.NORMAL;
        this.state2 = STATES.NORMAL;
    }

    @Override
    public boolean isTogether(GameShape gs) {
        boolean b = false;

        if (cy == gs.cy && (cx == gs.cx-2 || cx == gs.cx+2))
            b = true;
        if (cx == gs.cx-1 && (cy == gs.cy-1 || cy == gs.cy+1))
            b = true;
        if (cx == gs.cx+1 && (cy == gs.cy-1 || cy == gs.cy+1))
            b = true;

        return b;
    }

}
