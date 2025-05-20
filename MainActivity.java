package com.example.flighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText flightInput;
    Button trackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flightInput = findViewById(R.id.flight_input);
        trackButton = findViewById(R.id.track_button);

        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flightNumber = flightInput.getText().toString().trim();
                if (flightNumber.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a flight number", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, FlightDetailsActivity.class);
                    intent.putExtra("FLIGHT_NUMBER", flightNumber);
                    startActivity(intent);
                }
            }
        });
    }
}