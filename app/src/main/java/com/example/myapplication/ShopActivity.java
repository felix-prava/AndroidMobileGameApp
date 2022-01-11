package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity {

    private Button buttonSkinOne, buttonSkinTwo, buttonSkinThree, buttonSkinFour, buttonSkinFive;
    private TextView totalCoins;
    private SharedPreferences sharedPreferences;

    private int playerTwo, playerThree, playerFour, playerFive, coins;
    private final int targetSkinTwo = 100, targetSkinThree = 1000, targetSkinFour = 10000, targetSkinFive = 50000;

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
        buttonSkinOne = findViewById(R.id.buttonShop1);
        buttonSkinTwo = findViewById(R.id.buttonShop2);
        buttonSkinThree = findViewById(R.id.buttonShop3);
        buttonSkinFour = findViewById(R.id.buttonShop4);
        buttonSkinFive = findViewById(R.id.buttonShop5);

        totalCoins.setText("Coins: " + coins);

        buttonSkinOne.setOnClickListener(v -> {
            sharedPreferences.edit().putInt("playerSkin", 1).apply();
            skinIsChangedMessage();
        });

        buttonSkinTwo.setOnClickListener(v -> {
            boolean skinIsChanged = skinIsChanged(playerTwo, targetSkinTwo, buttonSkinTwo, "skinTwo", 2);
            if (skinIsChanged) {
                playerTwo = 1;
            }
        });

        buttonSkinThree.setOnClickListener(v -> {
            boolean skinIsChanged = skinIsChanged(playerThree, targetSkinThree, buttonSkinThree, "skinThree", 3);
            if (skinIsChanged) {
                playerThree = 1;
            }
        });

        buttonSkinFour.setOnClickListener(v -> {
            boolean skinIsChanged = skinIsChanged(playerFour, targetSkinFour, buttonSkinFour, "skinFour", 4);
            if (skinIsChanged) {
                playerFour = 1;
            }
        });

        buttonSkinFive.setOnClickListener(v -> {
            boolean skinIsChanged = skinIsChanged(playerFive, targetSkinFive, buttonSkinFive, "skinFive", 5);
            if (skinIsChanged) {
                playerFive = 1;
            }
        });

        if (playerTwo > 0) {
            buttonSkinTwo.setText("Select");
        }
        if (playerThree > 0) {
            buttonSkinThree.setText("Select");
        }
        if (playerFour > 0) {
            buttonSkinFour.setText("Select");
        }
        if (playerFive > 0) {
            buttonSkinFive.setText("Select");
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

    public boolean skinIsChanged(int playerWasPurchased, int targetCoins, Button button, String skinName, int skinIndex) {
        if (playerWasPurchased > 0) {
            sharedPreferences.edit().putInt("playerSkin", skinIndex).apply();
            skinIsChangedMessage();
            return true;
        } else {
            if (coins >= targetCoins) {
                sharedPreferences.edit().putInt("playerSkin", skinIndex).apply();
                sharedPreferences.edit().putInt(skinName, 1).apply();
                coins -= targetCoins;
                totalCoins.setText("Coins: " + coins);
                sharedPreferences.edit().putInt("coins", coins).apply();
                button.setText("Select");
                skinIsChangedMessage();
                return true;
            } else {
                needMoreCoinsMessage(targetCoins);
                return false;
            }
        }
    }
}