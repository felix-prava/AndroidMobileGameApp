package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity {

    private Button skinOne, skinTwo, skinThree, skinFour, skinFive;
    private TextView totalCoins;
    private SharedPreferences sharedPreferences;

    private int playerTwo, playerThree, playerFour, playerFive, coins;
    private final int targetSkinTwo = 100, getTargetSkinThree = 1000, targetSkinFour = 10000, targetSkinFive = 50000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        sharedPreferences = this.getSharedPreferences("game", Context.MODE_PRIVATE);
        coins = sharedPreferences.getInt("coins", 0);
        playerTwo = sharedPreferences.getInt("skinTwo", 0);
        playerThree = sharedPreferences.getInt("skinThree", 0);
        playerFour = sharedPreferences.getInt("skinFour", 0);
        playerFive = sharedPreferences.getInt("skinFive", 0);

        totalCoins = findViewById(R.id.shopCoins);
        skinOne = findViewById(R.id.buttonShop1);
        skinTwo = findViewById(R.id.buttonShop2);
        skinThree = findViewById(R.id.buttonShop3);
        skinFour = findViewById(R.id.buttonShop4);
        skinFive = findViewById(R.id.buttonShop5);

        totalCoins.setText("Coins: " + coins);

        skinOne.setOnClickListener(v -> {
            sharedPreferences.edit().putInt("playerSkin", 1).apply();
            skinIsChangedMessage();
        });

        skinTwo.setOnClickListener(v -> {
            if (playerTwo > 0) {
                sharedPreferences.edit().putInt("playerSkin", 2).apply();
                skinIsChangedMessage();
            } else {
                if (coins >= targetSkinTwo) {
                    sharedPreferences.edit().putInt("playerSkin", 2).apply();
                    sharedPreferences.edit().putInt("skinTwo", 1).apply();
                    coins -= targetSkinTwo;
                    totalCoins.setText("Coins: " + coins);
                    sharedPreferences.edit().putInt("coins", coins).apply();
                    skinTwo.setText("Select");
                    skinIsChangedMessage();
                } else {
                    needMoreCoinsMessage(targetSkinTwo);
                }
            }
        });

        skinThree.setOnClickListener(v -> {
            if (playerThree > 0) {
                sharedPreferences.edit().putInt("playerSkin", 3).apply();
                skinIsChangedMessage();
            } else {
                if (coins >= getTargetSkinThree) {
                    sharedPreferences.edit().putInt("playerSkin", 3).apply();
                    sharedPreferences.edit().putInt("skinThree", 1).apply();
                    coins -= getTargetSkinThree;
                    totalCoins.setText("Coins: " + coins);
                    sharedPreferences.edit().putInt("coins", coins).apply();
                    skinThree.setText("Select");
                    skinIsChangedMessage();
                } else {
                    needMoreCoinsMessage(getTargetSkinThree);
                }
            }
        });

        skinFour.setOnClickListener(v -> {
            if (playerFour > 0) {
                sharedPreferences.edit().putInt("playerSkin", 4).apply();
                skinIsChangedMessage();
            } else {
                if (coins >= targetSkinFour) {
                    sharedPreferences.edit().putInt("playerSkin", 4).apply();
                    sharedPreferences.edit().putInt("skinFour", 1).apply();
                    coins -= targetSkinFour;
                    totalCoins.setText("Coins: " + coins);
                    sharedPreferences.edit().putInt("coins", coins).apply();
                    skinFour.setText("Select");
                    skinIsChangedMessage();
                } else {
                    needMoreCoinsMessage(targetSkinFour);
                }
            }
        });

        skinFive.setOnClickListener(v -> {
            if (playerFour > 0) {
                sharedPreferences.edit().putInt("playerSkin", 5).apply();
                skinIsChangedMessage();
            } else {
                if (coins >= targetSkinFive) {
                    sharedPreferences.edit().putInt("playerSkin", 5).apply();
                    sharedPreferences.edit().putInt("skinFive", 1).apply();
                    coins -= targetSkinFive;
                    totalCoins.setText("Coins: " + coins);
                    sharedPreferences.edit().putInt("coins", coins).apply();
                    skinFive.setText("Select");
                    skinIsChangedMessage();
                } else {
                    needMoreCoinsMessage(targetSkinFive);
                }
            }
        });

        if (playerTwo > 0) {
            skinTwo.setText("Select");
        }
        if (playerThree > 0) {
            skinThree.setText("Select");
        }
        if (playerFour > 0) {
            skinFour.setText("Select");
        }
        if (playerFive > 0) {
            skinFive.setText("Select");
        }


    }

    public void skinIsChangedMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);
        builder.setTitle("Player Updated");
        builder.setMessage("The New Player Was Selected");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    public void needMoreCoinsMessage(int targetCoins) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShopActivity.this);
        builder.setTitle("Not Enough Coins");
        builder.setMessage("You Need " + targetCoins + " Coins To Buy This Skin");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }
}