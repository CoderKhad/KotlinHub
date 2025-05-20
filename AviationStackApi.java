package com.example.flighttracker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AviationStackApi {
    @GET("flights")
    Call<FlightResponse> getFlights(@Query("access_key") String accessKey, @Query("flight_iata") String flightIata);
}

