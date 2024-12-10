package com.example.lab9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OpeningScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_opening_screen);

        Button startButton = findViewById(R.id.start_quiz_button);
        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(OpeningScreenActivity.this, QuizPageActivity.class);
            startActivity(intent);
        });
    }
}