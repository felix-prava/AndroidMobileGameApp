package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizResultActivity extends AppCompatActivity {

    private TextView coinsEarned;
    private Button goToMainMenu, quitGame;

    private SharedPreferences sharedPreferences;

    private int coinsWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        coinsEarned = findViewById(R.id.textViewQuizResult);

        goToMainMenu = findViewById(R.id.buttonQuizGoToMainMenu);
        quitGame = findViewById(R.id.buttonQuizQuitGame);

        coinsWon = getIntent().getIntExtra("coinsEarned", 0);
        coinsEarned.setText("You Won " + coinsWon + " Coins!");

        sharedPreferences = this.getSharedPreferences("game", Context.MODE_PRIVATE);
        int prevScore = sharedPreferences.getInt("coins", 0);
        sharedPreferences.edit().putInt("coins", coinsWon + prevScore).apply();
        sharedPreferences.edit().putBoolean("userCanPlay", false).apply();

        goToMainMenu.setOnClickListener(v -> {
            Intent intent = new Intent(QuizResultActivity.this, MainMenuActivity.class);
            startActivity(intent);
        });

        quitGame.setOnClickListener(v -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        });
    }
}