package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class QuestionsDAO {

    public ArrayList<QuestionModel> getRandomThreeQuestions(QuestionsDatabase qd) {
        ArrayList<QuestionModel> modelArrayList = new ArrayList<>();
        SQLiteDatabase liteDatabase = qd.getWritableDatabase();
        Cursor cursor = liteDatabase.rawQuery("SELECT * FROM ITQuizGame ORDER BY RANDOM () LIMIT 3", null);

        int questionIndex = cursor.getColumnIndex("question");
        int answerIndex = cursor.getColumnIndex("answer");
        int wrongAnswer1Index = cursor.getColumnIndex("wrongAnswer1");
        int wrongAnswer2Index = cursor.getColumnIndex("wrongAnswer2");
        int wrongAnswer3Index = cursor.getColumnIndex("wrongAnswer3");

        while (cursor.moveToNext()) {
            QuestionModel model = new QuestionModel(cursor.getString(questionIndex),
                    cursor.getString(answerIndex),
                    cursor.getString(wrongAnswer1Index),
                    cursor.getString(wrongAnswer2Index),
                    cursor.getString(wrongAnswer3Index));
            modelArrayList.add(model);
        }

        return modelArrayList;
    }
}
