package com.loranttoth.themedictquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class NumberGameActivity extends Activity implements NumberGameInterface {

    int level = 1;

    View view;
    NumberGameView gameView;

    int[] vals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_number_game);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            level = b.getInt("level");
        }

        String s = "Practice Mode";

        TextView header = (TextView) findViewById(R.id.practiceHeaderText);
        header.setText(s);


        view = getLayoutInflater().inflate(R.layout.number_game_view, null);
        gameView = (NumberGameView) view.findViewWithTag("numberGameView");
        gameView.initGame(true, true, this);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layoutprcalc);

        layout.addView(view);

        Button b1 = (Button) findViewById(R.id.buttonpr1);
        b1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.buttonPressed(vals[0]);
            }
        });
        Button b2 = (Button) findViewById(R.id.buttonpr2);
        b2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.buttonPressed(vals[1]);
            }
        });
        Button b3 = (Button) findViewById(R.id.buttonpr3);
        b3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.buttonPressed(vals[2]);
            }
        });
        Button b4 = (Button) findViewById(R.id.buttonpr4);
        b4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.buttonPressed(vals[3]);
            }
        });

    }

    @Override
    public void setNumbers(int[] arr) {
        vals = arr;
        Button b1 = (Button) findViewById(R.id.buttonpr1);
        b1.setText(arr[0]+"");
        Button b2 = (Button) findViewById(R.id.buttonpr2);
        b2.setText(arr[1]+"");
        Button b3 = (Button) findViewById(R.id.buttonpr3);
        b3.setText(arr[2]+"");
        Button b4 = (Button) findViewById(R.id.buttonpr4);
        b4.setText(arr[3]+"");
    }

    @Override
    public void showWinDialog(int stars) {

    }
}