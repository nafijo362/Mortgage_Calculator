package com.example.mortgagecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private double amountMortgage, interestRate, amortizationPeriod;
    private EditText mortgageInput, interestInput, periodInput;
    private Button calculateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize the views
        mortgageInput = findViewById(R.id.MortgageAmount);
        interestInput = findViewById(R.id.Interest);
        periodInput = findViewById(R.id.AmortizationPeriod);
        calculateButton = findViewById(R.id.calculate);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mortgageInput.getText().toString().isEmpty() ||
                        interestInput.getText().toString().isEmpty() ||
                        periodInput.getText().toString().isEmpty()) {

                    // Show a Toast message to inform the user
                    Toast.makeText(MainActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    return; // Exit the method if inputs are not valid
                }

               try {
                   amountMortgage = Double.parseDouble(mortgageInput.getText().toString());
                   interestRate = Double.parseDouble(interestInput.getText().toString());
                   amortizationPeriod = Integer.parseInt(periodInput.getText().toString());

                   double monthlyPayment = calculateMortgage(amountMortgage, interestRate, (int) amortizationPeriod);

                   // Create an Intent to start the ResultActivity
                   Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                   // Pass the monthly payment as a string
                   intent.putExtra("monthlyPayment", String.format("Monthly Payment: $%.2f", monthlyPayment));

                   // Start the ResultActivity
                   startActivity(intent);
               }catch(NumberFormatException e) {
                   Toast.makeText(MainActivity.this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show();
               }
            }


            private double calculateMortgage(double principal, double annualRate, int periodYears) {
                double monthlyRate = (annualRate / 100) / 12;
                int numberOfPayments = periodYears * 12;
                return (principal * monthlyRate * Math.pow(1 + monthlyRate, numberOfPayments)) /
                        (Math.pow(1 + monthlyRate, numberOfPayments) - 1);
            }
        });
    }

}
