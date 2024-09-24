package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NameActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button startQuizButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        nameInput = findViewById(R.id.nameInput);
        startQuizButton = findViewById(R.id.startQuizButton);

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = nameInput.getText().toString().trim();

                if (userName.isEmpty()) {
                    Toast.makeText(NameActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(NameActivity.this, MainActivity.class);
                    intent.putExtra("USER_NAME", userName);
                    startActivity(intent);
                    finish(); // Close the NameActivity
                }
            }
        });
    }
}
