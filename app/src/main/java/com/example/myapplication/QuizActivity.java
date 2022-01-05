package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private TextView questionCountTextView, question;
    private Button buttonA, buttonB, buttonC, buttonD, buttonNext, correctButton;

    private QuestionsDatabase questionsDatabase;
    private ArrayList<QuestionModel> questionsList;
    private ArrayList<Button> buttonsList;
    private Map<Integer, Button> questionMap;

    private int coinsEarned = 0, questionCount = 0;
    private boolean userAlreadyAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionCountTextView = findViewById(R.id.textViewQuestionCount);
        question = findViewById(R.id.textViewQuestion);

        buttonA = findViewById(R.id.buttonEarnCoinsOption1);
        buttonB = findViewById(R.id.buttonEarnCoinsOption2);
        buttonC = findViewById(R.id.buttonEarnCoinsOption3);
        buttonD = findViewById(R.id.buttonEarnCoinsOption4);
        buttonNext = findViewById(R.id.buttonEarnCoinsNext);

        questionMap = new HashMap<>();
        questionMap.put(1, buttonA);
        questionMap.put(2, buttonB);
        questionMap.put(3, buttonC);
        questionMap.put(4, buttonD);

        buttonsList = new ArrayList<>();
        buttonsList.add(buttonA);
        buttonsList.add(buttonB);
        buttonsList.add(buttonC);
        buttonsList.add(buttonD);

        questionsDatabase = new QuestionsDatabase(QuizActivity.this);
        questionsList = new QuestionsDAO().getRandomThreeQuestions(questionsDatabase);

        displayQuestion(questionCount);

        buttonA.setOnClickListener(v -> {
            buttonClickedAction(buttonA);
        });

        buttonB.setOnClickListener(v -> {
            buttonClickedAction(buttonB);
        });

        buttonC.setOnClickListener(v -> {
            buttonClickedAction(buttonC);
        });

        buttonD.setOnClickListener(v -> {
            buttonClickedAction(buttonD);
        });

        buttonNext.setOnClickListener(v -> {
            questionCount++;
            userAlreadyAnswered = false;
            if (questionCount == 3) {
                Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
                intent.putExtra("coinsEarned", coinsEarned);
                startActivity(intent);
            } else {
                displayQuestion(questionCount);
            }
        });
    }

    public void displayQuestion(int questionCount) {
        questionCountTextView.setText("Question: " + (questionCount + 1));
        question.setText(questionsList.get(questionCount).getQuestion());

        int rightButtonIndex = (int) ((Math.random() * 3) + 1);
        correctButton = questionMap.get(rightButtonIndex);
        questionMap.get(rightButtonIndex).setText(questionsList.get(questionCount).getAnswer());
        int index = 1;
        for (Button button : buttonsList) {
            if (button == correctButton) {
                continue;
            }
            if (index == 1) {
                button.setText(questionsList.get(questionCount).getWrongAnswer1());
            } else if (index == 2) {
                button.setText(questionsList.get(questionCount).getWrongAnswer2());
            } else {
                button.setText(questionsList.get(questionCount).getWrongAnswer3());
            }
            index++;
        }
    }

    public void displayCorrectMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder.setTitle("");
        builder.setMessage("Wrong! The correct answer is " + questionsList.get(questionCount).getAnswer());
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    public void displayAnswerIsCorrect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        builder.setTitle("");
        builder.setMessage("The answer is correct!");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

    public void buttonClickedAction(Button button) {
        if (!userAlreadyAnswered) {
            if (button == correctButton) {
                coinsEarned += 10;
                displayAnswerIsCorrect();
            } else {
                displayCorrectMessage();
            }
            userAlreadyAnswered = true;
        }
    }
}