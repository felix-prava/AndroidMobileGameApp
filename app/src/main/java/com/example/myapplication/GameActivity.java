package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private ImageView player, enemy1, enemy2, enemy3, coin1, coin2, heart1, heart2, heart3, shield, imageViewShieldTimer;
    private TextView textViewScore, textViewStartInfo, textViewLevel, textViewShieldTimer;
    private ConstraintLayout constraintLayout;
    private boolean touchControl = false, beginControl = false, firstSpeedUpgrade = false, secondSpeedUpgrade = false;
    private Runnable runnable, secondRunnable;
    private Handler handler, secondHandler;
    private SharedPreferences sharedPreferences;
    private int targetScore, level, difficulty, backgroundImage2, backgroundImage3, backgroundImage4, backgroundImage5;
    private Map<Integer, Integer> backgroundMap, playerMap;
    
    private boolean shieldActivated = false;
    
    // positions
    int playerX, enemy1X, enemy2X, enemy3X, coin1X, coin2X, shieldX;
    int playerY, enemy1Y, enemy2Y, enemy3Y, coin1Y, coin2Y, shieldY;

    // screen's dimensions
    int screenWidth, screenHeight;

    // remaining lives
    int lives = 3;

    // coins
    int score = 0;

    // players
    int player2, player3, player4, player5;

    // objects speed
    int enemyOneSpeed = 150, enemyTwoSpeed = 140, enemyThreeSpeed = 130, coinOneSpeed = 120, coinTwoSpeed = 110, shieldSpeed=115;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        targetScore = getIntent().getIntExtra("targetScore", 100);
        level = getIntent().getIntExtra("level", 1);

        sharedPreferences = this.getSharedPreferences("game", Context.MODE_PRIVATE);
        int playerSkin = sharedPreferences.getInt("playerSkin", 1);
        difficulty = sharedPreferences.getInt("difficulty", 2);
        if (difficulty == 1) {
            enemyOneSpeed += 27;
            enemyTwoSpeed += 27;
            enemyThreeSpeed += 27;
        } else if (difficulty == 3) {
            enemyOneSpeed -= 27;
            enemyTwoSpeed -= 27;
            enemyThreeSpeed -= 27;
        }

        player = findViewById(R.id.imageViewPlayer);
        initializeMaps();
        if (playerSkin > 1) {
            player.setImageDrawable(getResources().getDrawable(playerMap.get(playerSkin), getApplicationContext().getTheme()));
        }

        enemy1 = findViewById(R.id.imageViewEnemy1);
        enemy2 = findViewById(R.id.imageViewEnemy2);
        enemy3 = findViewById(R.id.imageViewEnemy3);
        shield = findViewById(R.id.imageViewShield);
        coin1 = findViewById(R.id.imageViewCoin);
        coin2 = findViewById(R.id.imageViewCoin2);
        heart1 = findViewById(R.id.imageViewHeart1);
        heart2 = findViewById(R.id.imageViewHeart2);
        heart3 = findViewById(R.id.imageViewHeart3);
        textViewScore = findViewById(R.id.textViewScore);
        textViewStartInfo = findViewById(R.id.textViewStartInfo);
        textViewLevel = findViewById(R.id.textViewLevel);
        textViewLevel.setText("Level " + level);
        textViewShieldTimer =findViewById(R.id.textViewShieldTimer);
        constraintLayout = findViewById(R.id.constraintLayoutGame);
        imageViewShieldTimer = findViewById(R.id.imageViewShieldTimer);

        int backgroundImage = (int) ((Math.random() * 4) + 1);
        if (backgroundImage > 1) {
            constraintLayout.setBackgroundResource(backgroundMap.get(backgroundImage));
        }

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
                    playerX = (int) player.getX();
                    playerY = (int) player.getY();

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
            playerY -= (screenHeight / 50);
        } else {
            playerY += (screenHeight / 50);
        }
        if (playerY <= 0) {
            playerY = 0;
        }
        if (playerY >= screenHeight - player.getHeight()) {
            playerY = screenHeight - player.getHeight();
        }
        player.setY(playerY);
    }

    public void enemyControl() {
        enemy1.setVisibility(View.VISIBLE);
        enemy2.setVisibility(View.VISIBLE);
        enemy3.setVisibility(View.VISIBLE);
        coin1.setVisibility(View.VISIBLE);
        coin2.setVisibility(View.VISIBLE);
        shield.setVisibility(View.VISIBLE);

        // For the second half of the game
        if (!firstSpeedUpgrade && score >= targetScore / 2) {
            enemyOneSpeed -= 17;
            enemyTwoSpeed -= 17;
            enemyThreeSpeed -= 17;
            firstSpeedUpgrade = true;
        }

        // For the last third of the game
        if (!secondSpeedUpgrade && score >= 2 * targetScore / 3) {
            enemyOneSpeed -= 22;
            enemyTwoSpeed -= 22;
            enemyThreeSpeed -= 22;
            secondSpeedUpgrade = true;
        }
        enemy1X = enemy1X - (screenWidth / enemyOneSpeed);
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

        enemy2X = enemy2X - (screenWidth / enemyTwoSpeed);
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

        enemy3X = enemy3X - (screenWidth / enemyThreeSpeed);
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

        coin1X = coin1X - (screenWidth / coinOneSpeed);
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

        coin2X = coin2X - (screenWidth / coinTwoSpeed);
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

        shieldX = shieldX - (screenWidth / shieldSpeed);
        if (shieldX < 0) {
            shieldX = screenWidth + 3000;
            shieldY = (int) Math.floor(Math.random() * screenHeight);

            if (shieldY <= 0) {
                shieldY = 0;
            }
            if (shieldY >= screenHeight - shield.getHeight()) {
                shieldY = screenHeight - shield.getHeight();
            }
        }
        shield.setX(shieldX);
        shield.setY(shieldY);
    }

    public void collisionControl() {
        int centerEnemy1X = enemy1X + enemy1.getWidth() / 2;
        int centerEnemy1Y = enemy1Y + enemy1.getHeight() / 2;
        if (centerEnemy1X >= playerX && centerEnemy1X <= playerX + player.getWidth() &&
            centerEnemy1Y >= playerY && centerEnemy1Y <= playerY + player.getHeight())
        {
            enemy1X = screenWidth + 200;
            if(!shieldActivated) lives--;;
        }

        int centerEnemy2X = enemy2X + enemy2.getWidth() / 2;
        int centerEnemy2Y = enemy2Y + enemy2.getHeight() / 2;
        if (centerEnemy2X >= playerX && centerEnemy2X <= playerX + player.getWidth() &&
                centerEnemy2Y >= playerY && centerEnemy2Y <= playerY + player.getHeight())
        {
            enemy2X = screenWidth + 200;
            if(!shieldActivated) lives--;;
        }

        int centerEnemy3X = enemy3X + enemy3.getWidth() / 2;
        int centerEnemy3Y = enemy3Y + enemy3.getHeight() / 2;
        if (centerEnemy3X >= playerX && centerEnemy3X <= playerX + player.getWidth() &&
                centerEnemy3Y >= playerY && centerEnemy3Y <= playerY + player.getHeight())
        {
            enemy3X = screenWidth + 200;
            if(!shieldActivated) lives--;;
        }

        int centerCoin1X = coin1X + coin1.getWidth() / 2;
        int centerCoin1Y = coin1Y + coin1.getHeight() / 2;
        if (centerCoin1X >= playerX && centerCoin1X <= playerX + player.getWidth() &&
                centerCoin1Y >= playerY && centerCoin1Y <= playerY + player.getHeight())
        {
            coin1X = screenWidth + 200;
            score += 10;
            textViewScore.setText("" + score);
        }

        int centerCoin2X = coin2X + coin2.getWidth() / 2;
        int centerCoin2Y = coin2Y + coin2.getHeight() / 2;
        if (centerCoin2X >= playerX && centerCoin2X <= playerX + player.getWidth() &&
                centerCoin2Y >= playerY && centerCoin2Y <= playerY + player.getHeight())
        {
            coin2X = screenWidth + 200;
            score += 10;
            textViewScore.setText("" + score);
        }

        int centerShieldX = shieldX + shield.getWidth() / 2;
        int centerShieldY = shieldY + shield.getHeight() / 2;
        if (centerShieldX >= playerX && centerShieldX <= playerX + player.getWidth() &&
                centerShieldY >= playerY && centerShieldY <= playerY + player.getHeight())
        {
            shieldX = screenWidth + 10000;
            shieldActivated = true;
            imageViewShieldTimer.setVisibility(View.VISIBLE);
            
            new CountDownTimer(6000, 1000) {
                public void onTick(long millisUntilFinished) {
                    textViewShieldTimer.setText("" + millisUntilFinished / 1000);
                }
                public void onFinish() {
                    shieldActivated = false;
                    imageViewShieldTimer.setVisibility(View.INVISIBLE);
                    textViewShieldTimer.setText("");
                }
            }.start();
        }

        if (lives > 0 && score < targetScore) {
            if (lives == 2) {
                heart1.setImageResource(R.drawable.black_heart);
            } else if (lives == 1) {
                heart2.setImageResource(R.drawable.black_heart);
            }
            handler.postDelayed(runnable, 20);
        } else if (score >= targetScore) {
            handler.removeCallbacks(runnable);
            constraintLayout.setEnabled(false);
            textViewStartInfo.setVisibility(View.VISIBLE);
            textViewStartInfo.setText("Congratulations! You won!");
            enemy1.setVisibility(View.INVISIBLE);
            enemy2.setVisibility(View.INVISIBLE);
            enemy3.setVisibility(View.INVISIBLE);
            coin1.setVisibility(View.INVISIBLE);
            coin2.setVisibility(View.INVISIBLE);
            shield.setVisibility(View.INVISIBLE);

            secondHandler = new Handler();
            secondRunnable = () -> {
                playerX = playerX + (screenWidth / 300);
                player.setX(playerX);
                player.setY(screenHeight / 2f);
                if (playerX <= screenWidth) {
                    secondHandler.postDelayed(secondRunnable, 20);
                } else {
                    secondHandler.removeCallbacks(secondRunnable);
                    Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                    intent.putExtra("score", score);
                    intent.putExtra("targetScore", targetScore);
                    intent.putExtra("level", level  );
                    startActivity(intent);
                    finish();
                }
            };
            secondHandler.post(secondRunnable);
        } else if (lives == 0) {
            handler.removeCallbacks(runnable);
            heart3.setImageResource(R.drawable.black_heart);
            Intent intent = new Intent(GameActivity.this, ResultActivity.class);
            intent.putExtra("score", score);
            intent.putExtra("targetScore", targetScore);
            intent.putExtra("level", level  );
            startActivity(intent);
            finish();
        }
    }

    private void initializeMaps() {
        // Background image map
        backgroundImage2 = R.drawable.background_image2;
        backgroundImage3 = R.drawable.background_image3;
        backgroundImage4 = R.drawable.background_image4;
        backgroundImage5 = R.drawable.background_image5;
        backgroundMap = new HashMap<>();
        backgroundMap.put(2, backgroundImage2);
        backgroundMap.put(3, backgroundImage3);
        backgroundMap.put(4, backgroundImage4);
        backgroundMap.put(5, backgroundImage5);

        // Player skin map
        player2 = R.drawable.player2;
        player3 = R.drawable.player3;
        player4 = R.drawable.player4;
        player5 = R.drawable.player5;
        playerMap = new HashMap<>();
        playerMap.put(2, player2);
        playerMap.put(3, player3);
        playerMap.put(4, player4);
        playerMap.put(5, player5);
    }
}
