package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private ImageView bird, enemy1, enemy2, enemy3, coin1, coin2, heart1, heart2, heart3;
    private TextView textViewScore, textViewStartInfo;
    private ConstraintLayout constraintLayout;
    private boolean touchControl = false, beginControl = false;
    private Runnable runnable;
    private Handler handler;

    // positions and screen's dimensions
    int birdX, birdY, screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        bird = findViewById(R.id.imageViewBird);
        enemy1 = findViewById(R.id.imageViewEnemy1);
        enemy2 = findViewById(R.id.imageViewEnemy2);
        enemy3 = findViewById(R.id.imageViewEnemy3);
        coin1 = findViewById(R.id.imageViewCoin);
        coin2 = findViewById(R.id.imageViewCoin2);
        heart1 = findViewById(R.id.imageViewHeart1);
        heart2 = findViewById(R.id.imageViewHeart2);
        heart3 = findViewById(R.id.imageViewHeart3);
        textViewScore = findViewById(R.id.textViewScore);
        textViewStartInfo = findViewById(R.id.textViewStartInfo);
        constraintLayout = findViewById(R.id.constraintLayout);

        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                textViewStartInfo.setVisibility(View.INVISIBLE);
                if (!beginControl) {

                    beginControl = true;
                    // get screen's dimensions
                    screenWidth = (int) constraintLayout.getWidth();
                    screenHeight = (int) constraintLayout.getHeight();
                    // get bird's position
                    birdX = (int) bird.getX();
                    birdY = (int) bird.getY();

                    handler = new Handler();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            moveBirdTo();
                            handler.postDelayed(runnable, 20);
                        }
                    };
                    handler.post(runnable);
                } else {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        touchControl = true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        touchControl = false;
                    }
                }
                return true;
            }
        });
    }

    public void moveBirdTo() {
        if (touchControl) {
            birdY -= (screenHeight / 50);
        } else {
            birdY += (screenHeight / 50);
        }
        if (birdY <= 0) {
            birdY = 0;
        }
        if (birdY >= screenHeight - bird.getHeight()) {
            birdY = screenHeight - bird.getHeight();
        }
        bird.setY(birdY);
    }
}