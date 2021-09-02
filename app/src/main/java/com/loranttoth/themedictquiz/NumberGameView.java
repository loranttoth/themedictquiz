package com.loranttoth.themedictquiz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.loranttoth.themedictquiz.utils.Utils;

import java.util.Random;

public class NumberGameView extends View {


    Context context;
    NumberGameActivity parent;
    Paint paint;
    Paint painttimeline;
    Paint painttimefill;

    int a,b,c;
    String r1,r2,r3,r4;
    int level = 1;
    int point = 0;
    int gamedb = 0;

    String op11,op12;
    String op21,op22;
    String op31,op32;
    String op41,op42;
    int db11,db12,db13;
    int db21,db22,db23;
    int db31,db32,db33;
    int db41,db42,db43;

    String sres = "?";

    Bitmap bm1,bm2,bm3;

    Bitmap bmf1,bmf2,bmf3;

    TimerThread timerThread;

    int maxTime = 20;
    int counter = 60;
    int life = 20;

    int bw = 75;
    int bh = 45;

    int cw = 50;
    int ch = 50;

    boolean istimeoutmess = false;

    boolean isRnd = false;

    boolean isPractice = false;

    NumberGameInterface gameInterface;

    public NumberGameView(Context context) {
        super(context);
        init(context);
    }

    public NumberGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberGameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context c) {
        context = c;
        paint = new Paint();
        paint.setTypeface(Typeface.create("Arial", Typeface.BOLD));
        int spSize = 28;
        float scaledSizeInPixels = spSize * getResources().getDisplayMetrics().scaledDensity;
        paint.setTextSize(scaledSizeInPixels);

        painttimefill = new Paint();
        painttimefill.setColor(Color.parseColor("#ff0000"));
        Paint.Style style1 = Paint.Style.FILL;
        painttimefill.setStyle(style1);

        painttimeline = new Paint();
        painttimeline.setColor(Color.parseColor("#000000"));
        Paint.Style style2 = Paint.Style.STROKE;
        painttimeline.setStyle(style2);
    }

    public void initGame(boolean isRnd, boolean isPractice, NumberGameInterface gameInterface) {
        this.isRnd = isRnd;
        this.isPractice = isPractice;
        this.gameInterface = gameInterface;
        Random r = new Random();
        int a = r.nextInt(2);
        switch (a){
            case 0:
                bmf1 = BitmapFactory.decodeResource(getResources(), R.drawable.fruit_1);
                bmf2 = BitmapFactory.decodeResource(getResources(), R.drawable.fruit_2);
                bmf3 = BitmapFactory.decodeResource(getResources(), R.drawable.fruit_3);
                break;
            case 1:
                bmf1 = BitmapFactory.decodeResource(getResources(), R.drawable.food_1);
                bmf2 = BitmapFactory.decodeResource(getResources(), R.drawable.food_2);
                bmf3 = BitmapFactory.decodeResource(getResources(), R.drawable.food_3);
                break;
        }
        if (isRnd) {
            level = r.nextInt(4)+1;
        }
        makeValues();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#000000"));

        String x1 = (int)(Utils.eval(r1))+"";
        String x2 = (int)(Utils.eval(r2))+"";
        String x3 = (int)(Utils.eval(r3)) + "";

        int mw = getWidth()-20;
        int w3 = mw/7;
        int mh = getHeight() - 50;
        int h3 = (mh)/4;

        cw = w3-10;
        ch = h3-10;
        if (cw < ch)
            ch = cw;
        else
            cw = ch;


        bm1 = Bitmap.createScaledBitmap(bmf1, cw, ch, true);
        bm2 = Bitmap.createScaledBitmap(bmf2, cw, ch, true);
        bm3 = Bitmap.createScaledBitmap(bmf3, cw, ch, true);

        int cpx1 = 20+(w3/2-cw/2);
        int cpx2 = 20+w3+(w3/2-cw/2);
        int cpx3 = 20+w3+w3+(w3/2-cw/2);
        int cpx4 = 20+w3+w3+w3+(w3/2-cw/2);
        int cpx5 = 20+w3+w3+w3+w3+(w3/2-cw/2);
        int cpx6 = 20+w3+w3+w3+w3+w3+(w3/2-cw/2);
        int cpx7 = 20+w3+w3+w3+w3+w3+w3+(w3/2-cw/2);
        int cpx8 = 20+w3+w3+w3+w3+w3+w3+w3+(w3/2-cw/2);

        int cpy1 = 20+(h3/2-ch/2);
        int cpy2 = 20+h3+(h3/2-ch/2);
        int cpy3 = 20+h3+h3+(h3/2-ch/2);
        int cpy4 = 20+h3+h3+h3+(h3/2-ch/2);
        int cpy5 = getHeight()-10;

        int ch2 = ch/2;

        //level 1 is always
        if (db11 == 1)
            canvas.drawBitmap(bm1, cpx1, cpy1, paint);
        else{
            canvas.drawBitmap(bm1, cpx1-15, cpy1-15, paint);
            canvas.drawBitmap(bm1, cpx1+15, cpy1+15, paint);
        }
        drawTextCentered(op11, (cpx2+cpx3)/2, cpy1+ch2, paint, canvas, paint);
        if (db12 == 1)
            canvas.drawBitmap(bm1, cpx3, cpy1, paint);
        else{
            canvas.drawBitmap(bm1, cpx3-15, cpy1-15, paint);
            canvas.drawBitmap(bm1, cpx3+15, cpy1+15, paint);
        }
        drawTextCentered(op12, (cpx4+cpx5)/2, cpy1+ch2, paint, canvas, paint);
        if (db13 == 1)
            canvas.drawBitmap(bm1, cpx5, cpy1, paint);
        else if (db13 == 2){
            canvas.drawBitmap(bm1, cpx5-15, cpy1-15, paint);
            canvas.drawBitmap(bm1, cpx5+15, cpy1+15, paint);
        }
        drawTextCentered("=", (cpx6+cpx7)/2, cpy1+ch2, paint, canvas, paint);
        drawTextCentered(""+x1, (cpx7+cpx8)/2, cpy1+ch/2,paint,canvas,paint);

        if (level == 1){
            canvas.drawBitmap(bm1, cpx1, cpy2, paint);
            drawTextCentered("=", (cpx6+cpx7)/2, cpy2+ch2, paint, canvas, paint);
            drawTextCentered(""+sres, (cpx7+cpx8)/2,cpy2+ch2,paint,canvas,paint);
        }

        if (level >= 2) {
            if (db21 == 1)
                canvas.drawBitmap(bm1, cpx1, cpy2, paint);
            else{
                canvas.drawBitmap(bm1, cpx1-15, cpy2-15, paint);
                canvas.drawBitmap(bm1, cpx1+15, cpy2+15, paint);
            }
            drawTextCentered(op21, (cpx2 + cpx3) / 2, cpy2 + ch2, paint, canvas, paint);
            if (db22 == 1)
                canvas.drawBitmap(bm2, cpx3, cpy2, paint);
            else {
                canvas.drawBitmap(bm2, cpx3-15, cpy2-15, paint);
                canvas.drawBitmap(bm2, cpx3 + 15, cpy2 + 15, paint);
            }
            if (!op22.equals("")){
                drawTextCentered(op22, (cpx4 + cpx5) / 2, cpy2 + ch2, paint, canvas, paint);
                if (db23 == 1)
                    canvas.drawBitmap(bm2, cpx5, cpy2, paint);
                else {
                    canvas.drawBitmap(bm2, cpx5-15, cpy2-15, paint);
                    canvas.drawBitmap(bm2, cpx5 + 15, cpy2 + 15, paint);
                }
            }
            drawTextCentered("=", (cpx6+cpx7)/2, cpy2+ch2, paint, canvas, paint);
            drawTextCentered(""+x2, (cpx7+cpx8)/2, cpy2+ch2, paint,canvas,paint);
        }
        if (level == 2){
            canvas.drawBitmap(bm2, cpx1, cpy3, paint);
            drawTextCentered("=", (cpx6+cpx7)/2, cpy3+ch2, paint, canvas, paint);
            drawTextCentered("" + sres, (cpx7+cpx8)/2, cpy3+ch2, paint,canvas,paint);
        }
        if (level >= 3) {
            if (db31 == 1)
                canvas.drawBitmap(bm1, cpx1, cpy3, paint);
            else{
                canvas.drawBitmap(bm1, cpx1-15, cpy3-15, paint);
                canvas.drawBitmap(bm1, cpx1+15, cpy3+15, paint);
            }
            drawTextCentered(op31, (cpx2+cpx3)/2, cpy3+ch2, paint, canvas, paint);
            if (db32 == 1)
                canvas.drawBitmap(bm2, cpx3, cpy3, paint);
            else{
                canvas.drawBitmap(bm2, cpx3-15, cpy3-15, paint);
                canvas.drawBitmap(bm2, cpx3+15, cpy3+15, paint);
            }
            drawTextCentered(op32, (cpx4 + cpx5) / 2, cpy3 + ch2, paint, canvas, paint);
            if (db33 == 1)
                canvas.drawBitmap(bm3, cpx5, cpy3, paint);
            else{
                canvas.drawBitmap(bm3, cpx5-15, cpy3-15, paint);
                canvas.drawBitmap(bm3, cpx5+15, cpy3+15, paint);
            }
            drawTextCentered("=", (cpx6+cpx7)/2, cpy3+ch2, paint, canvas, paint);
            drawTextCentered(""+x3, (cpx7+cpx8)/2, cpy3+ch2,paint,canvas,paint);

        }
        if (level == 3){
            canvas.drawBitmap(bm3, cpx1, cpy4, paint);
            drawTextCentered("=", (cpx6+cpx7)/2, cpy4+ch2, paint, canvas, paint);
            drawTextCentered("" + sres, (cpx7+cpx8)/2, cpy4+ch2, paint,canvas,paint);
        }
        if (level >= 4) {
            if (db41 == 1)
                canvas.drawBitmap(bm1, cpx1, cpy4, paint);
            else{
                canvas.drawBitmap(bm1, cpx1-15, cpy4-15, paint);
                canvas.drawBitmap(bm1, cpx1+15, cpy4+15, paint);
            }
            drawTextCentered(op41, (cpx2+cpx3)/2, cpy4+ch2, paint, canvas, paint);
            if (db42 == 1)
                canvas.drawBitmap(bm2, cpx3, cpy4, paint);
            else{
                canvas.drawBitmap(bm2, cpx3-15, cpy4-15, paint);
                canvas.drawBitmap(bm2, cpx3+15, cpy4+15, paint);
            }
            drawTextCentered(op42, (cpx4+cpx5)/2, cpy4+ch2, paint,canvas,paint);
            if (db43 == 1)
                canvas.drawBitmap(bm3, cpx5, cpy4, paint);
            else {
                canvas.drawBitmap(bm3, cpx5-15, cpy4-15, paint);
                canvas.drawBitmap(bm3, cpx5+15, cpy4+15, paint);
            }
            drawTextCentered("=", (cpx6+cpx7)/2, cpy4+ch2, paint,canvas,paint);
            drawTextCentered(""+sres, (cpx7+cpx8)/2, cpy4+ch2,paint,canvas,paint);
        }

        int w = mw/3;
        int mh1 = (getHeight()-mh)-20;
        int h = mh1/4;

        bw = w-10;
        bh = h-10;

        int px1 = 10+(w/2-bw/2);
        int px2 = 10+w+(w/2-bw/2);
        int px3 = 10+w+w+(w/2-bw/2);

        int py1 = mh+10+(h/2-bh/2);
        int py2 = mh+10+h+(h/2-bh/2);
        int py3 = mh+10+h+h+(h/2-bh/2);
        int py4 = mh+10+h+h+h+(h/2-bh/2);


        int ww = getWidth()-60;
        float t2 = maxTime-counter;
        float fpc = ww/maxTime;
        RectF rt1 = new RectF();
        float len = t2*fpc;
        if (len > ww)
            len = ww;
        rt1.set(30,cpy5-25,30+len,cpy5-5);

        Rect rt2 = new Rect();
        rt2.set(30,cpy5-25,getWidth()-30,cpy5-5);

        canvas.drawRect(rt1,painttimefill);
        canvas.drawRect(rt2,painttimeline);


        if (istimeoutmess) {
            istimeoutmess = false;
//            parent.sendPoints(point);
//            showOkPopup(R.string.timeout,false);
        }
    }

    public static void drawTextCentered(String text, int x, int y, Paint paint, Canvas canvas, Paint textPaint) {
        int xPos = x - (int)(paint.measureText(text)/2);
        int yPos = (int) (y - ((textPaint.descent() + textPaint.ascent()) / 2)) ;

        canvas.drawText(text, xPos, yPos, textPaint);
    }

    public void makeValues(){
        if (life <= 0)
            return;
        db11 = 0;
        db12 = 0;
        db13 = 0;
        db21 = 0;
        db22 = 0;
        db23 = 0;
        db31 = 0;
        db32 = 0;
        db33 = 0;
        db41 = 0;
        db41 = 0;
        db43 = 0;
        op11 = "";
        op12 = "";
        op21 = "";
        op22 = "";
        op31 = "";
        op32 = "";
        op41 = "";
        op42 = "";

        int q;

        sres = "?";
        Random r = new Random();
        a = r.nextInt(10)+2;
        while (true) {
            b = r.nextInt(6) + 1;
            if (b != a)
                break;
        }
        while (true) {
            c = r.nextInt(4) + 1;
            if (c != a && c != b)
                break;
        }


        db11 = r.nextInt(2)+1;
        db12 = r.nextInt(2)+1;
        db13 = r.nextInt(2)+1;
        op11 = "+";
        op12 = "+";
        r1 = (db11*a)+op11+(db12*a)+op12+(db13*a);

        while (true) {

            db21 = r.nextInt(2) + 1;
            db22 = r.nextInt(2) + 1;
            db23 = r.nextInt(2) + 1;

            q = r.nextInt(2);
            switch (q) {
                case 0:
                    op21 = "+";
                    break;
                case 1:
                    op21 = "-";
                    break;
            }
            op22 = "";

            q = r.nextInt(4);
            switch (q) {
                case 0:
                    op22 = "+";
                    break;
                case 1:
                    op22 = "-";
                    break;
                case 2:
                case 3:
                    op22 = "";
                    break;
            }
            if ((!op21.equals(op22) && db22 != db23) ||
                    (op21.equals(op22)))
                break;
        }
        if (op22.equals(""))
            r2 = (db21*a)+op21+(db22*b);
        else
            r2 = (db21*a)+op21+(db22*b)+op22+(db23*b);


        db31 = r.nextInt(2)+1;
        db32 = r.nextInt(2)+1;
        db33 = r.nextInt(2)+1;

        q = r.nextInt(3);
        switch (q) {
            case 0:
                op31 = "+";
                break;
            case 1:
                op31 = "-";
                break;
            case 2:
                op31 = "*";
                break;
        }
        if (op31.equals("*"))
            q = r.nextInt(2);
        else
            q = r.nextInt(3);
        switch (q) {
            case 0:
                op32 = "+";
                break;
            case 1:
                op32 = "-";
                break;
            case 2:
                op32 = "*";
                break;
        }

        r3 = (db31*a)+op31+(db32*b)+op32+(db33*c);


        int ch;
        while (true) {
            db41 = r.nextInt(2)+1;
            db42 = r.nextInt(2)+1;
            db43 = r.nextInt(2)+1;
            q = r.nextInt(3);
            switch (q) {
                case 0:
                    op41 = "+";
                    break;
                case 1:
                    op41 = "-";
                    break;
                case 2:
                    op41 = "*";
                    break;
            }
            if (op41.equals("*"))
                q = r.nextInt(2);
            else
                q = r.nextInt(3);
            switch (q) {
                case 0:
                    op42 = "+";
                    break;
                case 1:
                    op42 = "-";
                    break;
                case 2:
                    op42 = "*";
                    break;
            }
            r4 = (db41*a)+op41+(db42*b)+op42+(db43*c);
            ch =(int)(Utils.eval(r4));
            if (ch > 0 && ch < 100)
                break;
        }

        if (isPractice) {
            int calc = -99999;
            if (level == 1){
                calc = a;
            }
            else if (level == 2){
                calc = b;
            }
            else if (level == 3){
                calc = c;
            }
            else if (level == 4){
                calc = (int)(Utils.eval(r4));
            }
            int[] vals = new int[4];
            int k = 0;
            boolean ok;
            for (int i = 0; i < 4; i++) {
                ok = true;
                while (ok) {
                    if ( calc < 20)
                        k = r.nextInt(20)+1;
                    else
                        k = r.nextInt(10)+(calc-10);
                    if (k != calc) {
                        ok = false;
                        for (int l = 0; l < i; l++) {
                            if (vals[l] == k)
                                ok = true;
                        }
                    }
                }
                vals[i] = k;
            }
            k = r.nextInt(4);
            vals[k] = calc;
            gameInterface.setNumbers(vals);
        }

        counter = maxTime;
        invalidate();
        startTimer();
    }

    public void checkResult(String res){
        int ires = -88888;
        try{
            ires = Integer.parseInt(res);
        }
        catch(Exception e){
            res = "?";
            return;
        }
        boolean ok = false;
        int calc = -99999;
        if (level == 1){
            calc = a;
        }
        else if (level == 2){
            calc = b;
        }
        else if (level == 3){
            calc = c;
        }
        else if (level == 4){
            calc = (int)(Utils.eval(r4));
        }
        ok = ires == calc;
        //sres = "?";
        if (ok){
            stopTimer();
            point+=(maxTime-counter)*level;
            this.gameInterface.showWinDialog(point);
            gamedb++;
            //if (gamedb % 10 == 0 && level < 4) {
            if (gamedb % 2 == 0 && level < 4) {
                level++;
                switch (level) {
                    case 2:
                        maxTime = 30;
                        break;
                    case 3:
                        maxTime = 40;
                        break;
                    case 4:
                        maxTime = 60;
                        break;
                }
            }
            else{

            }

        }
        else{
            life--;
            if (life == 0){
                stopTimer();
            }
        }
        invalidate();

    }

    public void setRes(String s){
        sres = s;
        invalidate();
    }

    public void counterRefresh(int counter){
        this.counter = counter;
        if (this.counter == 0)
            istimeoutmess = true;
        postInvalidate();
    }

    void startTimer(){
        stopTimer();
        timerThread = new TimerThread(this, counter);
        timerThread.setRunning(true);
        try {
            timerThread.start();
        }catch(Exception e){
        }
    }

    void stopTimer(){
        if (timerThread != null)
            timerThread.setRunning(false);
        boolean retry = true;
        while (retry) {
            try {
                if (timerThread != null)
                    timerThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        timerThread = null;
    }

    public void onPause(){
        stopTimer();
    }

    void showOkPopup(int mess, boolean islive){
        //parent.showOkPopup(mess,islive);
    }



    private static final int RGB_MASK = 0x00FFFFFF;

    public Bitmap invert(Bitmap original) {
        // Create mutable Bitmap to invert, argument true makes it mutable
        Bitmap inversion = original.copy(Bitmap.Config.ARGB_8888, true);

        // Get info about Bitmap
        int width = inversion.getWidth();
        int height = inversion.getHeight();
        int pixels = width * height;

        // Get original pixels
        int[] pixel = new int[pixels];
        inversion.getPixels(pixel, 0, width, 0, 0, width, height);

        // Modify pixels
        for (int i = 0; i < pixels; i++)
            pixel[i] ^= RGB_MASK;
        inversion.setPixels(pixel, 0, width, 0, 0, width, height);

        // Return inverted Bitmap
        return inversion;
    }

    public void buttonPressed(int val) {
        if (val >=0){
            if (!isPractice) {
                if (sres.equals("?"))
                    sres = "";
                if (sres.equals("0"))
                    sres = "";
                sres += val;
            }
            else {
                sres = val+"";
                checkResult(sres);
            }
            invalidate();
        }
        else if (val == -1){
            if (sres.length() > 0)
                sres = sres.substring(0,sres.length()-1);
            if (sres.length() == 0)
                sres = "?";
            invalidate();
        }
        else if (val == -2){
            checkResult(sres);
        }

    }

}
