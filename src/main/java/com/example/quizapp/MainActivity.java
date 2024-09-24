package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView userNameText, questionText, questionNumberText;
    private RadioGroup optionsGroup;
    private RadioButton option1, option2, option3, option4;
    private Button submitButton, nextButton, startAgainButton;
    private int currentQuestionIndex = 0;
    private int score = 0;

    // Questions and their respective answer options
    String[] questions = {
            "What is the capital of Australia?",
            "Which planet is known as the Red Planet?",
            "Who wrote the play 'Romeo and Juliet'?",
            "Which is the largest ocean in the world?",
            "Which animal is known as the King of the Jungle?",
            "What is the national currency of Japan?",
            "Who was the first person to walk on the moon?",
            "What is the largest mammal in the world?",
            "Which country gifted the Statue of Liberty to the USA?",
            "How many continents are there in the world?",
            "What is the hardest natural substance on Earth?",
            "Who invented the telephone?",
            "Which country is the Eiffel Tower located in?",
            "What is the smallest prime number?",
            "Which element is essential for the formation of bones and teeth?"
    };

    String[][] options = {
            {"Sydney", "Melbourne", "Canberra", "Brisbane"},
            {"Earth", "Venus", "Mars", "Jupiter"},
            {"Charles Dickens", "William Shakespeare", "Leo Tolstoy", "Mark Twain"},
            {"Atlantic Ocean", "Indian Ocean", "Pacific Ocean", "Arctic Ocean"},
            {"Tiger", "Elephant", "Lion", "Cheetah"},
            {"Yen", "Won", "Yuan", "Dollar"},
            {"Neil Armstrong", "Buzz Aldrin", "Yuri Gagarin", "John Glenn"},
            {"Elephant", "Blue Whale", "Giraffe", "Hippopotamus"},
            {"Spain", "Italy", "France", "Germany"},
            {"5", "6", "7", "8"},
            {"Gold", "Iron", "Diamond", "Silver"},
            {"Alexander Graham Bell", "Thomas Edison", "Nikola Tesla", "Guglielmo Marconi"},
            {"Italy", "Spain", "France", "Germany"},
            {"0", "1", "2", "3"},
            {"Iron", "Calcium", "Magnesium", "Potassium"}
    };

    String[] correctAnswers = {
            "Canberra", "Mars", "William Shakespeare", "Pacific Ocean", "Lion",
            "Yen", "Neil Armstrong", "Blue Whale", "France", "7",
            "Diamond", "Alexander Graham Bell", "France", "2", "Calcium"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameText = findViewById(R.id.userNameText);
        questionText = findViewById(R.id.questionText);
        questionNumberText = findViewById(R.id.questionNumberText);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);
        startAgainButton = findViewById(R.id.startAgainButton);

        // Get the name passed from NameActivity
        String userName = getIntent().getStringExtra("USER_NAME");
        userNameText.setText("Welcome, " + userName + "!");

        loadQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = optionsGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(MainActivity.this, "Please select an option!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedOption = findViewById(selectedId);
                String selectedAnswer = selectedOption.getText().toString();

                if (selectedAnswer.equals(correctAnswers[currentQuestionIndex])) {
                    score++;
                    Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                }

                submitButton.setEnabled(false);
                nextButton.setVisibility(View.VISIBLE);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    loadQuestion();
                    optionsGroup.clearCheck();
                    submitButton.setEnabled(true);
                    nextButton.setVisibility(View.GONE);
                } else {
                    showFinalScore();
                }
            }
        });

        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartQuiz();
            }
        });
    }

    // Shuffle the options and load the current question
    private void loadQuestion() {
        questionNumberText.setText("Question " + (currentQuestionIndex + 1));
        questionText.setText(questions[currentQuestionIndex]);

        // Shuffle the options for the current question
        List<String> shuffledOptions = new ArrayList<>();
        Collections.addAll(shuffledOptions, options[currentQuestionIndex]);
        Collections.shuffle(shuffledOptions); // Shuffling the options

        // Set shuffled options to the radio buttons
        option1.setText(shuffledOptions.get(0));
        option2.setText(shuffledOptions.get(1));
        option3.setText(shuffledOptions.get(2));
        option4.setText(shuffledOptions.get(3));
    }

    // Display final score and show the Start Again button
    private void showFinalScore() {
        questionText.setText("Your final score: " + score + "/" + questions.length);
        questionNumberText.setText("");
        optionsGroup.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        startAgainButton.setVisibility(View.VISIBLE); // Show the Start Again button
    }

    // Restart the quiz by returning to NameActivity
    private void restartQuiz() {
        Intent intent = new Intent(MainActivity.this, NameActivity.class);
        startActivity(intent);
        finish(); // Close MainActivity to prevent going back
    }
}
