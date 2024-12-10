package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ScoreScreenActivity extends AppCompatActivity {
    // Inside ScoreScreenActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_score_screen);

        // Retrieve data passed from QuizPageActivity
        int score = getIntent().getIntExtra("score", 0);
        List<Question> questions = (List<Question>) getIntent().getSerializableExtra("questions");
        if (questions == null || questions.isEmpty()) {
            // Handle missing or empty questions list
            finish(); // Exit the activity gracefully
            return;
        }

        ArrayList<Integer> userAnswers = getIntent().getIntegerArrayListExtra("answers");

        // Display the score
        TextView scoreSummary = findViewById(R.id.score_summary);
        scoreSummary.setText("Таны оноо: " + score + "/" + questions.size());

        // Display the answers
        LinearLayout answersList = findViewById(R.id.answers_list);
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            int correctAnswerIndex = question.getCorrectAnswerIndex();
            int userAnswerIndex = userAnswers.get(i);

            // Create a TextView for each question's feedback
            TextView answerView = new TextView(this);
            String feedback = (i + 1) + ". " + question.getQuestionText() + "\n";
            feedback += "Таны хариулт: " + question.getAnswers()[userAnswerIndex] + "\n";
            feedback += "Зөв хариулт: " + question.getAnswers()[correctAnswerIndex] + "\n";

            // Color the feedback text based on correctness
            if (userAnswerIndex == correctAnswerIndex) {
                answerView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                answerView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }

            answerView.setText(feedback);
            answerView.setPadding(16, 16, 16, 16);
            answersList.addView(answerView);
        }

        // Back to dashboard button
        Button backToDashboardButton = findViewById(R.id.back_to_dashboard_button);
        // Set the button text to "Дахин эхлэх" (Restart)
        backToDashboardButton.setText("Дахин эхлэх");

        backToDashboardButton.setOnClickListener(v -> {
            // Restart the QuizPageActivity (start a new activity)
            Intent restartIntent = new Intent(ScoreScreenActivity.this, QuizPageActivity.class);
            startActivity(restartIntent); // Start the quiz activity again
            finish(); // Close the current ScoreScreenActivity
        });
    }
}
