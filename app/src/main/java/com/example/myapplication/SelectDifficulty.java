package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class SelectDifficulty extends AppCompatActivity {

    private Button buttonEasyDifficulty, buttonMediumDifficulty, buttonHardDifficulty;
    private SharedPreferences sharedPreferences;
    private ArrayList<Button> buttonArrayList;
    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty);

        sharedPreferences = this.getSharedPreferences("game", Context.MODE_PRIVATE);
        difficulty = sharedPreferences.getInt("difficulty", 2);
        buttonArrayList = new ArrayList<>();

        buttonEasyDifficulty = findViewById(R.id.buttonEasyDifficulty);
        buttonMediumDifficulty = findViewById(R.id.buttonMediumDifficulty);
        buttonHardDifficulty = findViewById(R.id.buttonHardDifficulty);

        buttonArrayList.add(buttonEasyDifficulty);
        buttonArrayList.add(buttonMediumDifficulty);
        buttonArrayList.add(buttonHardDifficulty);

        updateButtons(difficulty);


        buttonEasyDifficulty.setOnClickListener(v -> {
            difficulty = 1;
            sharedPreferences.edit().putInt("difficulty", difficulty).apply();
            updateButtons(difficulty);
            displayMessage("You are playing on easy mode. You should be fine!");
        });

        buttonMediumDifficulty.setOnClickListener(v -> {
            difficulty = 2;
            sharedPreferences.edit().putInt("difficulty", difficulty).apply();
            updateButtons(difficulty);
            displayMessage("Medium difficulty.. for sure you have some skills!");
        });

        buttonHardDifficulty.setOnClickListener(v -> {
            difficulty = 3;
            sharedPreferences.edit().putInt("difficulty", difficulty).apply();
            updateButtons(difficulty);
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

    public void updateButtons(int difficulty) {
        for (int i = 1; i < 4; i++) {
            if (i == difficulty) {
                buttonArrayList.get(i - 1).setBackgroundColor(getResources().getColor(R.color.teal_700));
            } else {
                buttonArrayList.get(i - 1).setBackgroundColor(getResources().getColor(R.color.purple_500));
            }
        }
    }
}