package com.loranttoth.themedictquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class GameActivity extends Activity {

    GameSurfaceView fCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        Bundle b = getIntent().getExtras();
        int level = 1;
        if(b != null) {
            level = b.getInt("level");
        }
        fCanvas = new GameSurfaceView(this, level);
        fCanvas.setParent(this);
        setContentView(fCanvas);

    }

    public void showMess(String mess){
        Toast.makeText(getApplicationContext(), mess
                , Toast.LENGTH_LONG).show();

    }

    @Override
    protected  void onPause(){
        super.onPause();
        fCanvas.onPause();
        //finish();
        //this.fCanvas.thread.setRunning(false);
    }

    @Override
    protected  void onResume(){
        super.onResume();
        fCanvas.onResume();
        //finish();
        //this.fCanvas.thread.setRunning(true);
    }


    public void toMainScreen() {
        this.finish();
    }
}
