package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class SelectDifficulty extends AppCompatActivity {

    private Button buttonEasyDifficulty, buttonMediumDifficulty, buttonHardDifficulty;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty);

        sharedPreferences = this.getSharedPreferences("game", Context.MODE_PRIVATE);

        buttonEasyDifficulty = findViewById(R.id.buttonEasyDifficulty);
        buttonMediumDifficulty = findViewById(R.id.buttonMediumDifficulty);
        buttonHardDifficulty = findViewById(R.id.buttonHardDifficulty);

        buttonEasyDifficulty.setOnClickListener(v -> {
            sharedPreferences.edit().putInt("difficulty", 1).apply();
            displayMessage("You are playing on easy mode. You should be fine!");
        });

        buttonMediumDifficulty.setOnClickListener(v -> {
            sharedPreferences.edit().putInt("difficulty", 2).apply();
            displayMessage("Medium difficulty.. for sure you have some skills!");
        });

        buttonHardDifficulty.setOnClickListener(v -> {
            sharedPreferences.edit().putInt("difficulty", 3).apply();
            displayMessage("You are one of the best players! Good luck..you'll need it!");
        });
    }

    public void displayMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SelectDifficulty.this);
        builder.setTitle("");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }
}