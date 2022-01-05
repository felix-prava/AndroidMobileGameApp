package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class QuestionsDatabase extends SQLiteOpenHelper {

    public QuestionsDatabase(@Nullable Context context) {
        super(context, "SMA_QuizGame.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS \"ITQuizGame\" (\n" +
                "\t\"question\"\tTEXT,\n" +
                "\t\"answer\"\tTEXT,\n" +
                "\t\"wrongAnswer1\"\tTEXT,\n" +
                "\t\"wrongAnswer2\"\tTEXT,\n" +
                "\t\"wrongAnswer3\"\tTEXT\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS ITQuizGame");
        onCreate(db);
    }
}
