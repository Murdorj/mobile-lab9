package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizPageActivity extends AppCompatActivity {

    private TextView questionText, timerText, scoreText;
    private RadioGroup answersGroup;
    private Button submitButton;

    private String[] questions = {
            "What is the capital of France?",
            "What is the capital of Germany?",
            "What is the capital of Spain?",
            "What is the capital of Italy?",
            "What is the capital of UK?",
            "What is the capital of USA?",
            "What is the capital of Canada?",
            "What is the capital of Australia?",
            "What is the capital of Japan?",
            "What is the capital of China?",
            "What is the capital of Russia?",
            "What is the capital of India?",
            "What is the capital of Brazil?",
            "What is the capital of South Africa?",
            "What is the capital of Argentina?",
            "What is the capital of Egypt?",
            "What is the capital of Saudi Arabia?",
            "What is the capital of Mexico?",
            "What is the capital of Thailand?",
            "What is the capital of South Korea?"
    };

    private String[][] answers = {
            {"Berlin", "Madrid", "Paris", "Rome"},
            {"Berlin", "Madrid", "Paris", "Rome"},
            {"Berlin", "Madrid", "Paris", "Rome"},
            {"Berlin", "Madrid", "Paris", "Rome"},
            {"Berlin", "Madrid", "Paris", "London"},
            {"Washington", "Ottawa", "Canberra", "Tokyo"},
            {"Berlin", "Ottawa", "Paris", "Rome"},
            {"Canberra", "Madrid", "Paris", "Rome"},
            {"Seoul", "Beijing", "Tokyo", "Bangkok"},
            {"Berlin", "Beijing", "Paris", "Rome"},
            {"Moscow", "Berlin", "Paris", "Rome"},
            {"Berlin", "New Delhi", "Paris", "Rome"},
            {"Brasília", "Madrid", "Paris", "Rome"},
            {"Cape Town", "Madrid", "Paris", "Rome"},
            {"Berlin", "Buenos Aires", "Paris", "Rome"},
            {"Cairo", "Madrid", "Paris", "Rome"},
            {"Riyadh", "Madrid", "Paris", "Rome"},
            {"Mexico City", "Madrid", "Paris", "Rome"},
            {"Bangkok", "Madrid", "Paris", "Rome"},
            {"Seoul", "Madrid", "Paris", "Rome"}
    };

    private int[] correctAnswerIndexes = {
            2, 0, 1, 3, 3, 0, 1, 0, 2, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0
    };

    private List<Integer> selectedQuestions; // To hold random questions
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int timeLeft = 10; // 10 seconds for each question

    private ArrayList<Integer> userAnswers = new ArrayList<>(); // To store the user's answers
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quiz_page);

        // Initialize UI elements
        questionText = findViewById(R.id.question_text);
        timerText = findViewById(R.id.timer_text);
        scoreText = findViewById(R.id.score_text);
        answersGroup = findViewById(R.id.answers_group);
        submitButton = findViewById(R.id.submit_button);

        // Randomly select 5 questions
        selectedQuestions = getRandomQuestions();

        // Display the first question
        loadQuestion();

        // Start the timer
        startTimer();

        // Handle submit button click
        submitButton.setOnClickListener(v -> {
            // Check answer
            int selectedId = answersGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            // Stop timer on submit
            timer.cancel();

            // Get the selected answer index
            int selectedIndex = answersGroup.indexOfChild(findViewById(selectedId));

            // Add the selected answer to userAnswers
            userAnswers.add(selectedIndex);

            if (selectedIndex == correctAnswerIndexes[selectedQuestions.get(currentQuestionIndex)]) {
                score += 1; // Increment score for correct answer
                scoreText.setText("Score: " + score);
                Toast.makeText(this, "Correct! +10 points", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Incorrect. The correct answer is: " +
                                answers[selectedQuestions.get(currentQuestionIndex)][correctAnswerIndexes[selectedQuestions.get(currentQuestionIndex)]],
                        Toast.LENGTH_LONG).show();
            }

            // Move to the next question or end quiz
            currentQuestionIndex++;
            if (currentQuestionIndex < selectedQuestions.size()) {
                loadQuestion();
                resetTimer();
            } else {
                endQuiz();
            }
        });
    }

    private List<Integer> getRandomQuestions() {
        List<Integer> questionIndexes = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            questionIndexes.add(i);
        }
        Collections.shuffle(questionIndexes);
        return questionIndexes.subList(0, 5);
    }

    private void loadQuestion() {
        // Set question and answers dynamically
        int questionIndex = selectedQuestions.get(currentQuestionIndex);
        questionText.setText(questions[questionIndex]);
        ((RadioButton) findViewById(R.id.answer_1)).setText(answers[questionIndex][0]);
        ((RadioButton) findViewById(R.id.answer_2)).setText(answers[questionIndex][1]);
        ((RadioButton) findViewById(R.id.answer_3)).setText(answers[questionIndex][2]);
        ((RadioButton) findViewById(R.id.answer_4)).setText(answers[questionIndex][3]);
    }

    private void startTimer() {
        // Timer for each question
        timer = new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft--;
                timerText.setText("Time Left: " + timeLeft + "s");
            }

            @Override
            public void onFinish() {
                Toast.makeText(QuizPageActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                currentQuestionIndex++;
                if (currentQuestionIndex < selectedQuestions.size()) {
                    loadQuestion();
                    resetTimer();
                } else {
                    endQuiz();
                }
            }
        }.start();
    }

    private void resetTimer() {
        timeLeft = 10; // Reset to 10 seconds
        timerText.setText("Time Left: " + timeLeft + "s");
        answersGroup.clearCheck();
        startTimer(); // Restart timer for the next question
    }

    private int calculateScore(ArrayList<Integer> userAnswers, int[] correctAnswerIndexes) {
        int score = 0;
        for (int i = 0; i < userAnswers.size(); i++) {
            if (userAnswers.get(i) == correctAnswerIndexes[i]) {
                score += 1; // 10 points for each correct answer
            }
        }
        return score;
    }

    private void endQuiz() {
        // Stop the timer if it's still running
        if (timer != null) {
            timer.cancel();
        }

        // Create a list of Question objects to pass to ScoreScreenActivity
        List<Question> questionList = new ArrayList<>();
        for (int i = 0; i < selectedQuestions.size(); i++) {
            int questionIndex = selectedQuestions.get(i);
            Question question = new Question(questions[questionIndex], answers[questionIndex], correctAnswerIndexes[questionIndex]);
            questionList.add(question);
        }

        // Show a Toast with the final score
        Toast.makeText(this, "Quiz Over! Final Score: " + score, Toast.LENGTH_LONG).show();

        // Pass the final score and the list of Question objects to the next activity
        Intent intent = new Intent(QuizPageActivity.this, ScoreScreenActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("questions", (Serializable) questionList); // Pass the list of Question objects as Serializable
        intent.putIntegerArrayListExtra("answers", userAnswers); // Pass the user's answers
        startActivity(intent);
    }

}
