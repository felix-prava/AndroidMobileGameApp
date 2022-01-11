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

    private int coins;
    private boolean playerTwoWasPurchased, playerThreeWasPurchased, playerFourWasPurchased, playerFiveWasPurchased;
    private final int targetSkinTwo = 100, targetSkinThree = 1000, targetSkinFour = 10000, targetSkinFive = 50000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        sharedPreferences = this.getSharedPreferences("game", Context.MODE_PRIVATE);
        coins = sharedPreferences.getInt("coins", 0);
        playerTwoWasPurchased = sharedPreferences.getBoolean("playerNumberTwo", false);
        playerThreeWasPurchased = sharedPreferences.getBoolean("playerNumberThree", false);
        playerFourWasPurchased = sharedPreferences.getBoolean("playerNumberFour", false);
        playerFiveWasPurchased = sharedPreferences.getBoolean("playerNumberFive", false);

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
            boolean skinIsChanged = skinIsChanged(playerTwoWasPurchased, targetSkinTwo, buttonSkinTwo, "playerNumberTwo", 2);
            if (skinIsChanged) {
                playerTwoWasPurchased = true;
            }
        });

        buttonSkinThree.setOnClickListener(v -> {
            boolean skinIsChanged = skinIsChanged(playerThreeWasPurchased, targetSkinThree, buttonSkinThree, "playerNumberThree", 3);
            if (skinIsChanged) {
                playerThreeWasPurchased = true;
            }
        });

        buttonSkinFour.setOnClickListener(v -> {
            boolean skinIsChanged = skinIsChanged(playerFourWasPurchased, targetSkinFour, buttonSkinFour, "playerNumberFour", 4);
            if (skinIsChanged) {
                playerFourWasPurchased = true;
            }
        });

        buttonSkinFive.setOnClickListener(v -> {
            boolean skinIsChanged = skinIsChanged(playerFiveWasPurchased, targetSkinFive, buttonSkinFive, "playerNumberFive", 5);
            if (skinIsChanged) {
                playerFiveWasPurchased = true;
            }
        });

        if (playerTwoWasPurchased) {
            buttonSkinTwo.setText("Select");
        }
        if (playerThreeWasPurchased) {
            buttonSkinThree.setText("Select");
        }
        if (playerFourWasPurchased) {
            buttonSkinFour.setText("Select");
        }
        if (playerFiveWasPurchased) {
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

    public boolean skinIsChanged(boolean playerWasPurchased, int targetCoins, Button button, String skinName, int skinIndex) {
        if (playerWasPurchased) {
            sharedPreferences.edit().putInt("playerSkin", skinIndex).apply();
            skinIsChangedMessage();
            return true;
        } else {
            if (coins >= targetCoins) {
                sharedPreferences.edit().putInt("playerSkin", skinIndex).apply();
                sharedPreferences.edit().putBoolean(skinName, true).apply();
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