package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView bird, enemy1, enemy2, enemy3, coin, volumeUp;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bird = findViewById(R.id.birdFour);
        enemy1 = findViewById(R.id.birdOne);
        enemy2 = findViewById(R.id.birdTwo);
        enemy3 = findViewById(R.id.birdThree);
        coin = findViewById(R.id.coin);
        volumeUp = findViewById(R.id.volume);
        startButton = findViewById(R.id.startButton);
    }
}