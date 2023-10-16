package com.jarmaleniza.seatwork2_midterms;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView resultTextView, hintTextView, attemptTextView, guessedNumbersTextView;
    private EditText inputEditText;
    private Button submitButton, resetButton;
    private LinearLayout mainLayout;
    private ArrayAdapter<String> guessedNumbersAdapter;
    private ArrayList<String> guessedNumbers = new ArrayList<>();
    private int target;
    private int attempts = 0;
    private boolean hasGuessed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);
        resultTextView = findViewById(R.id.tv_result);
        attemptTextView = findViewById(R.id.tv_attempt);
        hintTextView = findViewById(R.id.tv_hint);
        inputEditText = findViewById(R.id.et_input);
        submitButton = findViewById(R.id.btn_submit);
        resetButton = findViewById(R.id.btn_reset);
        guessedNumbersTextView = findViewById(R.id.tv_guessed_numbers);

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(3);
        inputEditText.setFilters(filters);

        target = new Random().nextInt(100) + 1;

        guessedNumbersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, guessedNumbers);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasGuessed) {
                    String inputText = inputEditText.getText().toString().trim();
                    if (!inputText.isEmpty()) {
                        try {
                            int guessedNumber = Integer.parseInt(inputText);
                            attempts++;

                            if (guessedNumber < target) {
                                hintTextView.setText("HINT: higher");
                            } else if (guessedNumber > target) {
                                hintTextView.setText("HINT: lower");
                            } else {
                                hasGuessed = true;
                                hintTextView.setText("You've guessed it correctly, number is " + target + " Gusto pa din kita IRISH");
                            }

                            attemptTextView.setText(getString(R.string.attempts, attempts));
                            resultTextView.setText(String.valueOf(guessedNumber));
                            guessedNumbers.add(inputText);

                            if (attempts >= 100) {
                                hasGuessed = true;
                                hintTextView.setText(getString(R.string.bad_luck, target));
                            }

                            inputEditText.setText("");

                            updateGuessedNumbersList();

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Please input a number first, tanga ako pero kay IRISH g lang", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    private void resetGame() {
        target = new Random().nextInt(100) + 1;
        attempts = 0;
        hasGuessed = false;
        hintTextView.setText(getString(R.string.hint));
        attemptTextView.setText(getString(R.string.attempts, 0));
        resultTextView.setText("000");
        inputEditText.setText("");
        guessedNumbers.clear();
        updateGuessedNumbersList();
    }

    private void updateGuessedNumbersList() {
        StringBuilder guessedNumbersText = new StringBuilder();

        for (String number : guessedNumbers) {
            guessedNumbersText.append(number).append(", ");
        }
        if (guessedNumbersText.length() > 0) {
            guessedNumbersText.setLength(guessedNumbersText.length() - 2);
        }

        guessedNumbersTextView.setText(guessedNumbersText.toString());
    }
}
