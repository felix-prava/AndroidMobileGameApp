package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SelectLevelActivity extends AppCompatActivity {

    private Button buttonLevel1, buttonLevel2, buttonLevel3, buttonLevel4, buttonLevel5;
    private Button buttonLevel6, buttonLevel7, buttonLevel8, buttonLevel9, buttonLevel10;
    private SharedPreferences sharedPreferences;
    private ArrayList<Button> buttonArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);

        sharedPreferences = this.getSharedPreferences("game", Context.MODE_PRIVATE);
        int level = sharedPreferences.getInt("level", 1);

        buttonLevel1 = findViewById(R.id.buttonLevel1);
        buttonLevel2 = findViewById(R.id.buttonLevel2);
        buttonLevel3 = findViewById(R.id.buttonLevel3);
        buttonLevel4 = findViewById(R.id.buttonLevel4);
        buttonLevel5 = findViewById(R.id.buttonLevel5);
        buttonLevel6 = findViewById(R.id.buttonLevel6);
        buttonLevel7 = findViewById(R.id.buttonLevel7);
        buttonLevel8 = findViewById(R.id.buttonLevel8);
        buttonLevel9 = findViewById(R.id.buttonLevel9);
        buttonLevel10 = findViewById(R.id.buttonLevel10);
        buttonArrayList = new ArrayList<>();
        buttonArrayList.add(buttonLevel1);
        buttonArrayList.add(buttonLevel2);
        buttonArrayList.add(buttonLevel3);
        buttonArrayList.add(buttonLevel4);
        buttonArrayList.add(buttonLevel5);
        buttonArrayList.add(buttonLevel6);
        buttonArrayList.add(buttonLevel7);
        buttonArrayList.add(buttonLevel8);
        buttonArrayList.add(buttonLevel9);
        buttonArrayList.add(buttonLevel10);

        unlockLevels(level);

        buttonLevel1.setOnClickListener(v -> {
            Intent intent = new Intent(SelectLevelActivity.this, MainActivity.class);
            intent.putExtra("targetScore", 100);
            intent.putExtra("level", 1  );
            startActivity(intent);
        });

        buttonLevel2.setOnClickListener(v -> {
            if (level >= 2) {
                Intent intent = new Intent(SelectLevelActivity.this, MainActivity.class);
                intent.putExtra("targetScore", 150);
                intent.putExtra("level", 2  );
                startActivity(intent);
            } else {
                levelIsLocked(1);
            }
        });

        buttonLevel3.setOnClickListener(v -> {
            if (level >= 3) {
                Intent intent = new Intent(SelectLevelActivity.this, MainActivity.class);
                intent.putExtra("targetScore", 200);
                intent.putExtra("level", 3 );
                startActivity(intent);
            } else {
                levelIsLocked(2);
            }
        });

        buttonLevel4.setOnClickListener(v -> {
            if (level >= 4) {
                Intent intent = new Intent(SelectLevelActivity.this, MainActivity.class);
                intent.putExtra("targetScore", 250);
                intent.putExtra("level", 4 );
                startActivity(intent);
            } else {
                levelIsLocked(3);
            }
        });

        buttonLevel5.setOnClickListener(v -> {
            if (level >= 5) {
                Intent intent = new Intent(SelectLevelActivity.this, MainActivity.class);
                intent.putExtra("targetScore", 300);
                intent.putExtra("level", 5 );
                startActivity(intent);
            } else {
                levelIsLocked(4);
            }
        });

        buttonLevel6.setOnClickListener(v -> {
            if (level >= 6) {
                Intent intent = new Intent(SelectLevelActivity.this, MainActivity.class);
                intent.putExtra("targetScore", 350);
                intent.putExtra("level", 6 );
                startActivity(intent);
            } else {
                levelIsLocked(5);
            }
        });

        buttonLevel7.setOnClickListener(v -> {
            if (level >= 7) {
                Intent intent = new Intent(SelectLevelActivity.this, MainActivity.class);
                intent.putExtra("targetScore", 400);
                intent.putExtra("level", 7 );
                startActivity(intent);
            } else {
                levelIsLocked(5);
            }
        });

        buttonLevel8.setOnClickListener(v -> {
            if (level >= 8) {
                Intent intent = new Intent(SelectLevelActivity.this, MainActivity.class);
                intent.putExtra("targetScore", 450);
                intent.putExtra("level", 8 );
                startActivity(intent);
            } else {
                levelIsLocked(7);
            }
        });

        buttonLevel9.setOnClickListener(v -> {
            if (level >= 9) {
                Intent intent = new Intent(SelectLevelActivity.this, MainActivity.class);
                intent.putExtra("targetScore", 500);
                intent.putExtra("level", 9 );
                startActivity(intent);
            } else {
                levelIsLocked(8);
            }
        });

        buttonLevel10.setOnClickListener(v -> {
            if (level >= 10) {
                Intent intent = new Intent(SelectLevelActivity.this, MainActivity.class);
                intent.putExtra("targetScore", 10000);
                intent.putExtra("level", 10 );
                startActivity(intent);
            } else {
                levelIsLocked(9);
            }
        });
    }

    private void unlockLevels(int level) {
        for (int i = 0; i < level; i++) {
            Button button = buttonArrayList.get(i);
            button.setBackgroundColor(getResources().getColor(R.color.teal_700));
        }
    }

    public void levelIsLocked(int level) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SelectLevelActivity.this);
        builder.setTitle("Level Unlocked");
        builder.setMessage("You must first finish level " + level);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }
}