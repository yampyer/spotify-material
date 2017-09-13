package com.gomez.jeanpi.spotifymaterial.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerAPI {
    private static SpotifyAPI instance = null;
    private static final String BASE_URL = "https://api.spotify.com/v1/";

    public static SpotifyAPI getInstance() {
        if(instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            instance = retrofit.create(SpotifyAPI.class);
        }
        return instance;
    }
}
