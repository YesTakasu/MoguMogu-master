package jp.ac.asojuku.mogumogu;

import android.os.Handler;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Button[][] buttonMogu = new  Button[4][4];
    private TextView textViewTime;
    private  int gameTime = 30;
    private Timer timer;
    private Handler handler = new Handler();
    private  TextView scoreText;
    private  int score;
    private  Button moguraNow;
    private RelativeLayout gameOver;
    private Timer moguraTimer = null;
    private TextView replayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMogu[0][0] = (Button)findViewById(R.id.button1A);
        buttonMogu[0][1] = (Button)findViewById(R.id.button1B);
        buttonMogu[0][2] = (Button)findViewById(R.id.button1C);
        buttonMogu[0][3] = (Button)findViewById(R.id.button1D);

        buttonMogu[1][0] = (Button)findViewById(R.id.button2A);
        buttonMogu[1][1] = (Button)findViewById(R.id.button2B);
        buttonMogu[1][2] = (Button)findViewById(R.id.button2C);
        buttonMogu[1][3] = (Button)findViewById(R.id.button2D);

        buttonMogu[2][0] = (Button)findViewById(R.id.button3A);
        buttonMogu[2][1] = (Button)findViewById(R.id.button3B);
        buttonMogu[2][2] = (Button)findViewById(R.id.button3C);
        buttonMogu[2][3] = (Button)findViewById(R.id.button3D);

        buttonMogu[3][0] = (Button)findViewById(R.id.button4A);
        buttonMogu[3][1] = (Button)findViewById(R.id.button4B);
        buttonMogu[3][2] = (Button)findViewById(R.id.button4C);
        buttonMogu[3][3] = (Button)findViewById(R.id.button4D);

        textViewTime = (TextView)findViewById(R.id.textViewTime);

        score = 0;
        scoreText = (TextView)findViewById(R.id.textViewScore);

        gameOver = (RelativeLayout)findViewById(R.id.game_over_container);

        replayText = (TextView)findViewById(R.id.replay_text);

        moguraNow = null;

        for (int row = 0; row < buttonMogu.length; row++){
            for (int col = 0; col < buttonMogu[0].length; col++){
                buttonMogu[row][col].setOnClickListener(moguClicked);
            }
        }

        gameOver.setOnClickListener(gameOverClicked);


    }

    private void printMole(){
        if (moguraNow != null){
            moguraNow.setBackgroundResource(R.drawable.bg_button);
        }
        int row = (int)(Math.random()*4);
        int col = (int)(Math.random()*4);
        buttonMogu[row][col].setBackgroundResource(R.drawable.mogura_button);

        moguraNow = buttonMogu[row][col];

        if (moguraTimer != null){
            moguraTimer.cancel();
        }

        long delay = (long)(Math.random() * (1000 -10) + 100);
        moguraTimer = new Timer(true);
        moguraTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        printMole();
                    }
                });
            }
        },delay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //printMole();
        gameStart();
    }

    private  void gameStart(){
        replayText.setVisibility(View.INVISIBLE);
        printMole();

        if (timer != null){
            return;
        }

        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameTime--;
                        if (gameTime <= 0){
                            gameOver();
                        }
                        textViewTime.setText(Integer.toString(gameTime));
                    }
                });
            }
        },1000,1000);
    }

    private  void  gameOver(){
        timer.cancel();
        timer = null;

        moguraTimer.cancel();
        moguraTimer = null;

        gameOver.setVisibility(View.VISIBLE);

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        replayText.setVisibility(View.VISIBLE);
                    }
                });
            }
        },2000);

    }

    private View.OnClickListener moguClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v != moguraNow){
                return;
            }
            score++;
            scoreText.setText(String.valueOf(score));

            printMole();
        }
    };
    private  View.OnClickListener gameOverClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (replayText.getVisibility() != View.VISIBLE){
                return;
            }
            gameOver.setVisibility(View.INVISIBLE);
            score = 0;
            scoreText.setText(String.valueOf(score));
            gameTime=30;
            gameStart();
        }
    };
    System.out.println("wadataito");
    System.out.println("wadataito");
    System.out.println("wadataito");

}
