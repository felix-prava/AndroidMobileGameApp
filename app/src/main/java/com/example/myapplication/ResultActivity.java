package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView textViewResultInfo, textViewMyScore, textViewHighestScore;
    private Button playAgainButton;
    private int score;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewResultInfo = findViewById(R.id.textViewResultInfo);
        textViewHighestScore = findViewById(R.id.textViewHighestScore);
        textViewMyScore = findViewById(R.id.textViewMyScore);
        playAgainButton = findViewById(R.id.buttonPlayAgain);

        score = getIntent().getIntExtra("score", 0);
        textViewMyScore.setText("Your Score: " + score);

        sharedPreferences = this.getSharedPreferences("score", Context.MODE_PRIVATE);
        int highestScore = sharedPreferences.getInt("highestScore", 0);

        if (score >= 200) {
            textViewResultInfo.setText("You Won The Game!");
            textViewHighestScore.setText("Highest Score: " + score);
            sharedPreferences.edit().putInt("highestScore", score).apply();
        } else if (score >= highestScore) {
            textViewResultInfo.setText("Sorry, You Lost..");
            textViewHighestScore.setText("Highest Score: " + score);
            sharedPreferences.edit().putInt("highestScore", score).apply();
        } else {
            textViewResultInfo.setText("Sorry, You Lost..");
            textViewHighestScore.setText("Highest Score: " + highestScore);
        }

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
        builder.setTitle("Test Title");
        builder.setMessage("Are You Sure You Want To Quit The Game?");
        builder.setCancelable(false);
        builder.setNegativeButton("Quit Game", (dialog, which) -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        });
        builder.setPositiveButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }
}