package com.example.flighttracker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightRepository {
    private AviationStackService apiService;

    public FlightRepository() {
        apiService = (AviationStackService) ApiClient.getApiService(); // <-- fixed line
    }

    public void getFlightData(String apiKey, String flightIata, FlightCallback callback) {
        Call<FlightResponse> call = apiService.getFlights(apiKey, flightIata);
        call.enqueue(new Callback<FlightResponse>() {
            @Override
            public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().data);
                } else {
                    callback.onFailure("No data available");
                }
            }

            @Override
            public void onFailure(Call<FlightResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface FlightCallback {
        void onSuccess(List<FlightData> flights);
        void onFailure(String error);
    }
}
