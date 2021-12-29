package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private Button playGame, selectLevel, selectDifficulty, viewHighScore, addCoins, goToShop;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        playGame = findViewById(R.id.playGameButton);
        selectLevel = findViewById(R.id.selectLevelButton);
        selectDifficulty = findViewById(R.id.selectDifficultyButton);
        viewHighScore = findViewById(R.id.viewHighScoreButton);
        addCoins = findViewById(R.id.addCoinsButton);
        goToShop = findViewById(R.id.goToShopButton);

        playGame.setOnClickListener(v -> {
            sharedPreferences = this.getSharedPreferences("game", Context.MODE_PRIVATE);
            int level = sharedPreferences.getInt("level", 1);

            Intent intent = new Intent(MainMenuActivity.this, MainActivity.class);
            if (level < 10) {
                intent.putExtra("targetScore", level * 50 + 50);
            } else {
                intent.putExtra("targetScore", 10000);
            }
            intent.putExtra("level", level  );
            startActivity(intent);
        });

        selectLevel.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, SelectLevelActivity.class);
            startActivity(intent);
        });

        selectDifficulty.setOnClickListener(v -> {
            // TO DO
        });

        viewHighScore.setOnClickListener(v -> {
            sharedPreferences = this.getSharedPreferences("game", Context.MODE_PRIVATE);
            int highestScore = sharedPreferences.getInt("highestScore", 0);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
            builder.setTitle("Test Title");
            builder.setMessage("Highest Score: " + highestScore);
            builder.setCancelable(false);
            builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
            builder.create().show();
        });

        addCoins.setOnClickListener(v -> {
            // TO DO
        });

        goToShop.setOnClickListener(v -> {
            // TO DO
        });
    }
}