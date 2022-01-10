package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView player, enemy1, enemy2, enemy3, coin, volume;
    private LinearLayout layout;
    private Button startButton;
    private Animation animation;
    private MediaPlayer mediaPlayer;
    private SharedPreferences sharedPreferences;
    private boolean status = false;
    private int backgroundImage2, backgroundImage3, backgroundImage4, backgroundImage5;
    private Map<Integer, Integer> backgroundMap, playerMap;

    // players
    private int player2, player3, player4, player5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = findViewById(R.id.birdFour);
        enemy1 = findViewById(R.id.birdOne);
        enemy2 = findViewById(R.id.birdTwo);
        enemy3 = findViewById(R.id.birdThree);
        coin = findViewById(R.id.coin);
        volume = findViewById(R.id.volume);
        startButton = findViewById(R.id.startButton);

        animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_animation);
        player.setAnimation(animation);
        enemy1.setAnimation(animation);
        enemy2.setAnimation(animation);
        enemy3.setAnimation(animation);
        coin.setAnimation(animation);

        initializeMaps();

        sharedPreferences = this.getSharedPreferences("game", Context.MODE_PRIVATE);
        int playerSkin = sharedPreferences.getInt("playerSkin", 1);
        if (playerSkin > 1) {
            player.setImageDrawable(getResources().getDrawable(playerMap.get(playerSkin), getApplicationContext().getTheme()));
        }

        layout = findViewById(R.id.linearLayoutGame);
        int backgroundImage = (int) ((Math.random() * 4) + 1);
        if (backgroundImage > 1) {
            layout.setBackgroundResource(backgroundMap.get(backgroundImage));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.never_gonna_give_you_up_original);
        mediaPlayer.start();

        volume.setOnClickListener(v -> {
            if (!status) {
                mediaPlayer.setVolume(0, 0);
                volume.setImageResource(R.drawable.volume_off);
                status = true;
            } else {
                mediaPlayer.setVolume(1, 1);
                volume.setImageResource(R.drawable.volume_up);
                status = false;
            }
        });

        startButton.setOnClickListener(v -> {
            mediaPlayer.reset();
            volume.setImageResource(R.drawable.volume_up);
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            int targetScore = getIntent().getIntExtra("targetScore", 100);
            int level = getIntent().getIntExtra("level", 1);
            intent.putExtra("targetScore", targetScore);
            intent.putExtra("level", level  );
            startActivity(intent);
        });
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