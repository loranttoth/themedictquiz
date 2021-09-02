package com.loranttoth.themedictquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

import static android.content.pm.ActivityInfo.*;

public class MainActivity extends Activity {

    int gameSizeX;
    int gameSizeY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        setBtnEvents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void showMess(String mess){
        Toast.makeText(getApplicationContext(), mess
                , Toast.LENGTH_LONG).show();

    }


    protected void setBtnEvents() {
        View bar = (View) findViewById(R.id.pr_bar);
        bar.setVisibility(View.INVISIBLE);
        final MainActivity self = this;

        Button btn_practice = (Button) findViewById(R.id.button_practice);

        btn_practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaiting();
                Intent intent = new Intent(self, GameActivity.class);
                Bundle b = new Bundle();
                int level = new Random().nextInt(7)+1;
                b.putInt("level", level);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        Button btn_settings = (Button) findViewById(R.id.button_settings);

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaiting();
                Intent intent = new Intent(self, NumberGameActivity.class);
                Bundle b = new Bundle();
                b.putInt("level", -2);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        View bar = (View) findViewById(R.id.pr_bar);
        bar.setVisibility(View.INVISIBLE);
    }



    void showWaiting() {
        View bar = (View) findViewById(R.id.pr_bar);
        bar.setVisibility(View.VISIBLE);
    }
}
