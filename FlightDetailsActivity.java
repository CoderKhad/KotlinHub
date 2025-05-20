package com.example.flighttracker;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FlightDetailsActivity extends AppCompatActivity {

    TextView flightInfoText;
    ImageView statusImage;
    ProgressBar loadingSpinner;
    String API_KEY = "a352c93980b956c2bd93d2da08a2e6e2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_details);

        flightInfoText = findViewById(R.id.flight_info);
        statusImage = findViewById(R.id.status_image);
        loadingSpinner = findViewById(R.id.loading_spinner);

        String flightNumber = getIntent().getStringExtra("FLIGHT_NUMBER");

        fetchFlightInfo(flightNumber);
    }

    private void fetchFlightInfo(String flightNumber) {
        loadingSpinner.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                URL url = new URL("http://api.aviationstack.com/v1/flights?access_key=" + API_KEY + "&flight_iata=" + flightNumber);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                runOnUiThread(() -> {
                    loadingSpinner.setVisibility(View.GONE);
                    parseAndDisplay(response.toString());
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    loadingSpinner.setVisibility(View.GONE);
                    Toast.makeText(this, "Error fetching data. Please check your connection or try a different flight.", Toast.LENGTH_LONG).show();
                });
                e.printStackTrace();
            }
        }).start();
    }

    private void parseAndDisplay(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            if (dataArray.length() > 0) {
                JSONObject flight = dataArray.getJSONObject(0);
                String airline = flight.getJSONObject("airline").getString("name");
                String departure = flight.getJSONObject("departure").getString("airport");
                String arrival = flight.getJSONObject("arrival").getString("airport");
                String status = flight.getString("flight_status");

                String display = "Airline: " + airline +
                        "\nDeparture: " + departure +
                        "\nArrival: " + arrival +
                        "\nStatus: " + status;

                flightInfoText.setText(display);

                // Load status image
                if (status.equalsIgnoreCase("active")) {
                    Glide.with(this).load(R.drawable.active).into(statusImage);
                } else if (status.equalsIgnoreCase("landed")) {
                    Glide.with(this).load(R.drawable.landed).into(statusImage);
                } else {
                    Glide.with(this).load(R.drawable.unknown).into(statusImage);
                }
            } else {
                flightInfoText.setText("No flight information found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            flightInfoText.setText("Error parsing data.");
        }
    }
}