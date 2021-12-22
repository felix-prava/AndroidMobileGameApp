package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private ImageView bird, enemy1, enemy2, enemy3, coin1, coin2, heart1, heart2, heart3;
    private TextView textViewScore, textViewStartInfo;
    private ConstraintLayout constraintLayout;
    private boolean touchControl = false, beginControl = false;
    private Runnable runnable;
    private Handler handler;

    // positions
    int birdX, enemy1X, enemy2X, enemy3X, coin1X, coin2X;
    int birdY, enemy1Y, enemy2Y, enemy3Y, coin1Y, coin2Y;

    // screen's dimensions
    int screenWidth, screenHeight;

    // remaining lives
    int lives = 3;

    // coins
    int score = 0;

    @SuppressLint("ClickableViewAccessibility")
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
                    runnable = () -> {
                        moveBirdTo();
                        enemyControl();
                        collisionControl();
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

    public void enemyControl() {
        enemy1.setVisibility(View.VISIBLE);
        enemy2.setVisibility(View.VISIBLE);
        enemy3.setVisibility(View.VISIBLE);
        coin1.setVisibility(View.VISIBLE);
        coin2.setVisibility(View.VISIBLE);

        enemy1X = enemy1X - (screenWidth / 150);
        if (enemy1X < 0) {
            enemy1X = screenWidth + 200;
            enemy1Y = (int) Math.floor(Math.random() * screenHeight);

            if (enemy1Y <= 0) {
                enemy1Y = 0;
            }
            if (enemy1Y >= screenHeight - enemy1.getHeight()) {
                enemy1Y = screenHeight - enemy1.getHeight();
            }
        }
        enemy1.setX(enemy1X);
        enemy1.setY(enemy1Y);

        enemy2X = enemy2X - (screenWidth / 140);
        if (enemy2X < 0) {
            enemy2X = screenWidth + 200;
            enemy2Y = (int) Math.floor(Math.random() * screenHeight);

            if (enemy2Y <= 0) {
                enemy1Y = 0;
            }
            if (enemy2Y >= screenHeight - enemy2.getHeight()) {
                enemy2Y = screenHeight - enemy2.getHeight();
            }
        }
        enemy2.setX(enemy2X);
        enemy2.setY(enemy2Y);

        enemy3X = enemy3X - (screenWidth / 130);
        if (enemy3X < 0) {
            enemy3X = screenWidth + 200;
            enemy3Y = (int) Math.floor(Math.random() * screenHeight);

            if (enemy3Y <= 0) {
                enemy3Y = 0;
            }
            if (enemy3Y >= screenHeight - enemy3.getHeight()) {
                enemy3Y = screenHeight - enemy3.getHeight();
            }
        }
        enemy3.setX(enemy3X);
        enemy3.setY(enemy3Y);

        coin1X = coin1X - (screenWidth / 120);
        if (coin1X < 0) {
            coin1X = screenWidth + 200;
            coin1Y = (int) Math.floor(Math.random() * screenHeight);

            if (coin1Y <= 0) {
                coin1Y = 0;
            }
            if (coin1Y >= screenHeight - coin1.getHeight()) {
                coin1Y = screenHeight - coin1.getHeight();
            }
        }
        coin1.setX(coin1X);
        coin1.setY(coin1Y);

        coin2X = coin2X - (screenWidth / 110);
        if (coin2X < 0) {
            coin2X = screenWidth + 200;
            coin2Y = (int) Math.floor(Math.random() * screenHeight);

            if (coin2Y <= 0) {
                coin2Y = 0;
            }
            if (coin2Y >= screenHeight - coin2.getHeight()) {
                coin2Y = screenHeight - coin2.getHeight();
            }
        }
        coin2.setX(coin2X);
        coin2.setY(coin2Y);
    }

    public void collisionControl() {
        int centerEnemy1X = enemy1X + enemy1.getWidth() / 2;
        int centerEnemy1Y = enemy1Y + enemy1.getHeight() / 2;
        if (centerEnemy1X >= birdX && centerEnemy1X <= birdX + bird.getWidth() &&
            centerEnemy1Y >= birdY && centerEnemy1Y <= birdY + bird.getHeight())
        {
            enemy1X = screenWidth + 200;
            lives--;;
        }

        int centerEnemy2X = enemy2X + enemy2.getWidth() / 2;
        int centerEnemy2Y = enemy2Y + enemy2.getHeight() / 2;
        if (centerEnemy2X >= birdX && centerEnemy2X <= birdX + bird.getWidth() &&
                centerEnemy2Y >= birdY && centerEnemy2Y <= birdY + bird.getHeight())
        {
            enemy2X = screenWidth + 200;
            lives--;;
        }

        int centerEnemy3X = enemy3X + enemy3.getWidth() / 2;
        int centerEnemy3Y = enemy3Y + enemy3.getHeight() / 2;
        if (centerEnemy3X >= birdX && centerEnemy3X <= birdX + bird.getWidth() &&
                centerEnemy3Y >= birdY && centerEnemy3Y <= birdY + bird.getHeight())
        {
            enemy3X = screenWidth + 200;
            lives--;;
        }

        int centerCoin1X = coin1X + coin1.getWidth() / 2;
        int centerCoin1Y = coin1Y + coin1.getHeight() / 2;
        if (centerCoin1X >= birdX && centerCoin1X <= birdX + bird.getWidth() &&
                centerCoin1Y >= birdY && centerCoin1Y <= birdY + bird.getHeight())
        {
            coin1X = screenWidth + 200;
            textViewScore.setText("" + score);
            score += 10;
        }

        int centerCoin2X = coin2X + coin2.getWidth() / 2;
        int centerCoin2Y = coin2Y + coin2.getHeight() / 2;
        if (centerCoin2X >= birdX && centerCoin2X <= birdX + bird.getWidth() &&
                centerCoin2Y >= birdY && centerCoin2Y <= birdY + bird.getHeight())
        {
            coin2X = screenWidth + 200;
            textViewScore.setText("" + score);
            score += 10;
        }

        if (lives > 0 && score < 200) {
            if (lives == 2) {
                heart1.setImageResource(R.drawable.black_heart);
            } else if (lives == 1) {
                heart2.setImageResource(R.drawable.black_heart);
            }
            handler.postDelayed(runnable, 20);
        } else if (score >= 200) {
            handler.removeCallbacks(runnable);
        } else if (lives == 0) {
            handler.removeCallbacks(runnable);
            heart3.setImageResource(R.drawable.black_heart);
            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }
    }
}