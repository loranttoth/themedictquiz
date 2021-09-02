package com.loranttoth.themedictquiz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.loranttoth.themedictquiz.bean.GameShape;
import com.loranttoth.themedictquiz.bean.Point;
import com.loranttoth.themedictquiz.bean.STATES;
import com.loranttoth.themedictquiz.bean.Shape6;
import com.loranttoth.themedictquiz.utils.Utils;
import com.loranttoth.themedictquiz.utils.Words;

/**
 * TODO: document your custom view class.
 */
public class GameSurfaceView extends SurfaceView implements
        SurfaceHolder.Callback{

    private Bitmap mBitmap;
    private Canvas mCanvas;
    Context context;
    Paint linePaint;
    Paint linePaint2;
    Paint normalPaint;
    Paint actPaint;
    Paint usedPaint;
    Paint prevPaint;
    Paint textPaint;
    Paint textPaintBold;
    GameActivity parent;

    GameThread thread;

    static final int BEFOREINIT = -1;
    static final int TOSTART = 0;
    static final int STARTED = 1;

    Random random = new Random();

    int gameState = BEFOREINIT;

    int h, w, cw;

    int b_s;

    ArrayList<GameShape> history;

    ArrayList<GameShape> fields;

    int lastX = 0;
    int lastY = 0;

    int max_row = 7;

    int level;

    private final Rect textBounds = new Rect(); //don't new this up in a draw method

    ArrayList<GameShape> aktPath;

    boolean isWon;
    boolean isTimeOut;

    final String abc_en = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    String aktWord;
    String aktWordTemp;

    String sTime = "";
    long dTime = 0;
    long dTime2 = 0;
    long aktTime = 0;
    Words words;

    public GameSurfaceView(Context c, int level) {
        super(c);
        context = c;
        this.level = level;
        words = new Words();
        switch (level) {
            case 1:
                max_row = 5;
                break;
            case 2:
                max_row = 7;
                break;
            case 3:
                max_row = 9;
                break;
            case 4:
                max_row = 11;
                break;
            case 5:
                max_row = 13;
                break;
            case 6:
                max_row = 15;
                break;
            case 7:
                max_row = 17;
                break;
        }
        getHolder().addCallback(this);

        thread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    synchronized  void initGame() {
        gameState = TOSTART;

        if (mBitmap == null) {
            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            mBitmap = Bitmap.createBitmap(w, h, conf);
            mCanvas = new Canvas(mBitmap);
        }

        String s1 = abc_en;
        boolean ok = false;


        normalPaint = new Paint();
        normalPaint.setColor(Color.parseColor("#999999"));
        normalPaint.setStyle(Paint.Style.FILL);

        actPaint = new Paint();
        actPaint.setColor(Color.parseColor("#ff0000"));
        actPaint.setStyle(Paint.Style.FILL);

        usedPaint = new Paint();
        usedPaint.setColor(Color.parseColor("#cccccc"));
        usedPaint.setStyle(Paint.Style.FILL);

        prevPaint = new Paint();
        prevPaint.setColor(Color.parseColor("#0000ff"));
        prevPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#ffffff"));
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);

        linePaint2 = new Paint();
        linePaint2.setColor(Color.parseColor("#00ffff"));
        linePaint2.setStrokeWidth(10);
        linePaint2.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#777777"));

        int fsize = 60;
        while (!ok) {
            textPaint.setTextSize(fsize);
            textPaint.getTextBounds(s1, 0, s1.length(), textBounds);
            if (textBounds.right > w) {
                fsize--;
            }
            else {
                ok = true;
            }
        }

        textPaint.setTextAlign(Paint.Align.CENTER);

        textPaintBold = new Paint();
        textPaintBold.setColor(Color.parseColor("#777777"));
        textPaintBold.setTextSize(48);
        textPaintBold.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        textPaintBold.setTextAlign(Paint.Align.CENTER);




        fields = new ArrayList<>();
        history = new ArrayList<>();

        b_s = h / 29;

        int x1 = 0;
        int akt_x;
        int y1 = 0;
        int max_db = 1;
        int akt_db;
        int val;
        String s;
        GameShape shape;
 /*       for (int i = 0; i < max_row; i++) {
            akt_db = 0;
            akt_x = x1;
            while (akt_db < max_db) {
                shape = new Shape6(akt_x, y1, "");
                shape.text = "";
                fields.add(shape);
                akt_x+=2;
                akt_db++;
            }
            y1++;
            if (y1 <= max_row / 2) {
                x1--;
                max_db++;
            }
            else {
                x1++;
                max_db--;
            }

        }
*/
        for (int i = 0; i < max_row; i++) {
            akt_db = 0;
            if (y1 % 2 == 0) {
                x1 = -6;
                max_db = 7;
            }
            else {
                x1 = -7;
                max_db = 8;
            }
            akt_x = x1;
            while (akt_db < max_db) {
                shape = new Shape6(akt_x, y1, "");
                shape.text = "";
                fields.add(shape);
                akt_x+=2;
                akt_db++;
            }
            y1++;
        }

        //fields.get(fields.size()-1).state = STATES.ACT;
        //history.add(fields.get(fields.size()-1));

        GameShape aktshape;
        aktshape = fields.get(fields.size()-1);
        aktshape.text = "0";

        Iterator<GameShape> iterator = fields.iterator();
        while (iterator.hasNext()) {
            aktshape = iterator.next();
            makeKoords6(aktshape);
        }
        makePath();

        iterator = fields.iterator();
        while (iterator.hasNext()) {
            aktshape = iterator.next();
            if (aktshape.state2 == STATES.NORMAL) {
                val = random.nextInt(26);
                s = abc_en.charAt(val) + "";
                while (isLetterUsed(aktshape, s)) {
                    val = random.nextInt(26);
                    s = abc_en.charAt(val) + "";
                }
                aktshape.text = s;
            }
        }

        isWon = false;
        isTimeOut = false;

        sTime = "000:000";
        dTime = 0;
        aktTime = System.currentTimeMillis();
        gameState = STARTED;



    }

    public void setParent(GameActivity parent) {
        this.parent = parent;
    }

    public void render(Canvas canvas) {
        h = getHeight();
        w = getWidth();
        cw = w/2;


        if (!isWon && !isTimeOut) {
            long t1 = System.currentTimeMillis();
            dTime = t1 - aktTime;
            dTime2 = 60000 - dTime;
            int mm = (int) (dTime2 / 1000);
            if (mm == 0) {
                isTimeOut = true;
                sTime = "Time: " + String.format("%03d", mm);
            }
            else {
                sTime = "Time: " + String.format("%03d", mm);
            }
        }

        if (w > 0 && h > 0 && gameState == BEFOREINIT) {
            this.initGame();
            return;
        }
        if (gameState == TOSTART) {
            return;
        }

        mCanvas.drawColor(Color.parseColor("#ffffff"));

        Path path = new Path();

        Paint paint = new Paint();

        GameShape aktshape;
        int cx;
        int cy;
        //aktshape = fields.get(0);
        //aktshape.text = sum+"";
        String word_0 = "";
        Iterator<GameShape> it1 = history.iterator();
        while (it1.hasNext()) {
            aktshape = it1.next();
            word_0 += aktshape.text;
        }

        if (aktWord.equals(word_0)) {
            //parent.showMess("Congratulations! You won!");
            isWon = true;
        }
        Iterator<GameShape> iterator = fields.iterator();
        while (iterator.hasNext()) {
            aktshape = iterator.next();


            cx = (cw) + (aktshape.cx * b_s);
            cy = (int)((aktshape.cy +1) * b_s * 1.5) + 150;

            //mCanvas.drawCircle(cx, cy, 20, aktPaint);
            path.reset();


            path.moveTo(aktshape.koords[0].x, aktshape.koords[0].y);
            for (int i = 1; i < aktshape.koords.length; i++) {
                path.lineTo(aktshape.koords[i].x, aktshape.koords[i].y);
            }
            path.lineTo(aktshape.koords[0].x, aktshape.koords[0].y);
            switch (aktshape.state) {
                case NORMAL:
                    paint = normalPaint;
                    break;
                case ACT:
                    paint = actPaint;
                    break;
                case USED:
                    paint = usedPaint;
                    break;
            }
            //if (history.size() > 1) {
            //    GameShape previosField = history.get(history.size() - 2);
            //    if (aktshape == previosField)
            //        paint = prevPaint;
            //}

            mCanvas.drawPath(path, paint);
            mCanvas.drawPath(path, linePaint);
            drawTextCentered(mCanvas, textPaintBold, aktshape.text, cx, cy);

        }

        for (int i = 0; i < history.size()-1; i++) {
            aktshape = history.get(i);
            path.reset();
            cx = (cw) + (aktshape.cx * b_s);
            cy = (int)((aktshape.cy +1) * b_s * 1.5) + 150;
            path.moveTo(cx, cy);
            aktshape = history.get(i+1);
            cx = (cw) + (aktshape.cx * b_s);
            cy = (int)((aktshape.cy +1) * b_s * 1.5) + 150;
            path.lineTo(cx, cy);
            mCanvas.drawPath(path, linePaint2);
        }

        if (isWon) {
            drawTextCentered(mCanvas, textPaint, "WON!", cw, 120);
        }
        else if (isTimeOut) {
            drawTextCentered(mCanvas, textPaint, "Time Out!", cw, 120);
        }
        else {
            drawTextCentered(mCanvas, textPaint, aktWord, cw, 50);
            drawTextCenteredPattern(mCanvas, textPaint, sTime+"", "Time: 000",cw, 120);
        }

        drawTextCentered(mCanvas, textPaint, word_0, cw, h - 100);



        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    void upTouch(float x, float y){
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();

        if (!isWon && !isTimeOut) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //history.clear();
                    searchFirstField((int) x, (int) y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    searchActField((int) x, (int) y, true);
                    break;
                case MotionEvent.ACTION_UP:
                    searchActField((int) x, (int) y, false);
                    break;
            }
        }
        lastX = x;
        lastY = y;
        return true;
    }

    public void reDraw() {
        Canvas canvas = getHolder().lockCanvas();
        render(canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (thread != null && thread.getState() == Thread.State.TERMINATED)
        {
            return;
            //thread = new FishThread(getHolder(), this);
        }
        //creating lines

        reDraw();
        thread.setRunning(true);
        try {
            thread.start();
        }catch(Exception e){
            parent.showMess("err: "+e.getLocalizedMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void onPause(){
        //parent.showMess("stop fishing...");
        if (thread != null)
            thread.setRunning(false);
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                parent.showMess("error: " + e.getLocalizedMessage());
            }
        }
        thread = null;
    }

    public void onResume() {
        //parent.showMess("stop fishing...");
        if (thread != null)
            thread.setRunning(true);
        else
            thread = new GameThread(getHolder(), this);
    }

    public static int[] getBitmapPixels(Bitmap bitmap, int x, int y, int width, int height) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), x, y,
                width, height);
        final int[] subsetPixels = new int[width * height];
        for (int row = 0; row < height; row++) {
            System.arraycopy(pixels, (row * bitmap.getWidth()),
                    subsetPixels, row * width, width);
        }
        return subsetPixels;
    }

    void makeKoords6(GameShape shape) {
        int cx = (cw) + (shape.cx * b_s);
        int cy = (int)((shape.cy +1) * b_s * 1.5) + 150;

        shape.koords = new Point[6];
        int x,y;

        //1. point top
        x = cx;
        y = (cy - (int)(b_s));
        shape.koords[0] = new Point(x,y);

        //2. point right up
        x = (cx + b_s);
        y = (cy - (int)(b_s / 2));
        shape.koords[1] = new Point(x,y);

        //3. point right down
        x = (cx + b_s);
        y = (cy + (int)(b_s / 2));
        shape.koords[2] = new Point(x,y);

        //4. point down
        x = cx;
        y = (cy + (int)(b_s));
        shape.koords[3] = new Point(x,y);

        //5. point left down
        x = (cx - b_s);
        y = (cy + (int)(b_s / 2));
        shape.koords[4] = new Point(x,y);

        //6. point left up
        x = (cx - b_s);
        y = (cy - (int)(b_s / 2));
        shape.koords[5] = new Point(x,y);
    }

    void searchFirstField(int x, int y) {
        GameShape shape, shape1;
        Iterator<GameShape> iterator = fields.iterator();
        boolean ok = false;
        while (iterator.hasNext())
        {
            shape = iterator.next();
            if (Utils.isInside(shape.koords, shape.koords.length, new Point(x, y))) {
                if (shape.state == STATES.NORMAL) {
                    if (history.size() == 1) {
                        GameShape aktField = history.get(history.size()-1);
                        if (shape != aktField && !shape.isTogether(aktField)) {
                            aktField.state = STATES.NORMAL;
                            history.clear();
                            ok = true;
                        }
                    }
                    else if (history.size() > 0) {
                        GameShape aktField = history.get(history.size()-1);
                        if (shape != aktField && shape.isTogether(aktField)) {
                            ok = true;
                        }
                        else {
                            history.clear();
                            Iterator<GameShape> it1 = fields.iterator();
                            while (it1.hasNext()) {
                                shape1 = it1.next();
                                shape1.state = STATES.NORMAL;
                            }
                            ok = true;
                        }
                    }
                    else {
                        ok = true;
                    }
                    if (ok) {
                        shape.state = STATES.ACT;
                        history.add(shape);
                    }
                    break;
                }
            }
        }
    }

    void searchActField(int x, int y, boolean isUp) {
        if (history.size() == 0) {
            return;
        }
        GameShape shape, shape2;
        GameShape last = fields.get(fields.size()-1);
        GameShape aktField = history.get(history.size()-1);
        GameShape previosField = null;
        if (history.size() > 1)
            previosField = history.get(history.size()-2);
        else
            previosField = last;
        Iterator<GameShape> iterator = fields.iterator();
        while (iterator.hasNext()) {
            shape = iterator.next();
            if (Utils.isInside(shape.koords, shape.koords.length, new Point(x, y))) {
                //if (shape.equals(first) || shape.equals(last)) {
                //if (shape.equals(last)) {
                    //break;
                //}
                //else {
                    if (shape != aktField && shape.isTogether(aktField)) {
                        if (shape.state == STATES.NORMAL) {

                            Iterator<GameShape> iterator2 = fields.iterator();
                            while (iterator2.hasNext()) {
                                shape2 = iterator2.next();
                                if (shape2.state == STATES.ACT)
                                    shape2.state = STATES.USED;
                            }

                            shape.state = STATES.ACT;
                            history.add(shape);
                            aktField.state = STATES.USED;
                        }
                        else if (shape == previosField && isUp) {
                            aktField.state = STATES.NORMAL;
                            previosField.state = STATES.ACT;
                            history.remove(history.size()-1);
                        }
                    }

                //}

            }
        }

    }

    public void drawTextCentered(Canvas canvas, Paint paint, String text, float cx, float cy){
        paint.getTextBounds(text, 0, text.length(), textBounds);
        canvas.drawText(text, cx, cy - textBounds.exactCenterY(), paint);
    }

    public void drawTextCenteredPattern(Canvas canvas, Paint paint, String text, String pattern, float cx, float cy){
        paint.getTextBounds(pattern, 0, pattern.length(), textBounds);
        canvas.drawText(text, cx, cy - textBounds.exactCenterY(), paint);
    }

    private GameShape getShapeByIndx(int x, int y) {
        GameShape ret = null;
        GameShape shape = null;
        Iterator<GameShape> iterator = fields.iterator();

        while (iterator.hasNext()) {
            shape = iterator.next();
            if (shape.cx == x && shape.cy == y) {
                ret = shape;
                break;
            }
        }
        return ret;
    }

    private void makePath() {
        GameShape shape;
        boolean ok = false;
        int indx;
        while (!ok) {
            indx = random.nextInt(words.words.size());
            aktWord = words.words.get(indx).toUpperCase();
            if (aktWord.length() > 5)
                ok = true;
        }
        ok = false;
        aktWordTemp = "";
        while (!ok) {
            aktPath = new ArrayList<GameShape>();
            aktWordTemp = "";
            Iterator<GameShape> iterator = fields.iterator();
            while (iterator.hasNext()) {
                shape = iterator.next();
                shape.state2 = STATES.NORMAL;
                shape.text2 = "";
                //shape.state = STATES.NORMAL;
            }
            indx = random.nextInt(fields.size());
            GameShape shape1 = fields.get(indx);   //the last field on the bottom
            shape1.state2 = STATES.USED;
            aktWordTemp = aktWord.substring(0,1);
            shape1.text2 = aktWord.substring(0,1);
            aktPath.add(shape1);
            if (addNewShapeToPath(shape1)) {
                ok = true;
            }
        }
        Iterator<GameShape> iterator = fields.iterator();
        while (iterator.hasNext()) {
            shape = iterator.next();
            if (shape.state2 == STATES.USED) {
                shape.text = shape.text2;
            }
            //shape.state = STATES.NORMAL;
        }
    }

    private  boolean addNewShapeToPath(GameShape gs) {
        GameShape gs1;
        ArrayList<GameShape> nums = new ArrayList<>();
        gs1 = getShapeByIndx(gs.cx - 1, gs.cy - 1);
        if (gs1 != null && gs1.state2 == STATES.NORMAL)
            nums.add(gs1);
        gs1 = getShapeByIndx(gs.cx + 1, gs.cy - 1);
        if (gs1 != null && gs1.state2 == STATES.NORMAL)
            nums.add(gs1);
        //gs1 = getShapeByIndx(gs.cx - 1, gs.cy + 1);
        //if (gs1 != null && gs1.state2 == STATES.NORMAL)
         //   nums.add(gs1);
      //  gs1 = getShapeByIndx(gs.cx + 1, gs.cy + 1);
      //  if (gs1 != null && gs1.state2 == STATES.NORMAL)
      //      nums.add(gs1);
        gs1 = getShapeByIndx(gs.cx - 2, gs.cy);
        if (gs1 != null && gs1.state2 == STATES.NORMAL)
            nums.add(gs1);
        gs1 = getShapeByIndx(gs.cx + 2, gs.cy);
        if (gs1 != null && gs1.state2 == STATES.NORMAL)
            nums.add(gs1);

        if (nums.size() == 0)
            return false;
        else {
            int rnd = random.nextInt(nums.size());
            gs1 = nums.get(rnd);
            gs1.text2 = aktWord.substring(aktPath.size(),aktPath.size()+1);
            aktPath.add(gs1);
            aktWordTemp += gs1.text2;
            gs1.state2 = STATES.USED;
            if (aktWord.equals(aktWordTemp)) {
                return true;
            }
            return (addNewShapeToPath(gs1));
        }
        //return false;
    }


    private  boolean isLetterUsed(GameShape gs, String s) {
        GameShape gs1;
        gs1 = getShapeByIndx(gs.cx - 1, gs.cy - 1);
        if (gs1 != null && gs1.text.equals(s))
            return true;
        gs1 = getShapeByIndx(gs.cx + 1, gs.cy - 1);
        if (gs1 != null && gs1.text.equals(s))
            return true;
        gs1 = getShapeByIndx(gs.cx - 1, gs.cy + 1);
        if (gs1 != null && gs1.text.equals(s))
            return true;
        gs1 = getShapeByIndx(gs.cx + 1, gs.cy + 1);
        if (gs1 != null && gs1.text.equals(s))
            return true;
        gs1 = getShapeByIndx(gs.cx - 2, gs.cy);
        if (gs1 != null && gs1.text.equals(s))
            return true;
        gs1 = getShapeByIndx(gs.cx + 2, gs.cy);
        if (gs1 != null && gs1.text.equals(s))
            return true;
        return false;
    }


}