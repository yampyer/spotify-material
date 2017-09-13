package com.gomez.jeanpi.spotifymaterial.api;

import java.util.List;

import com.gomez.jeanpi.spotifymaterial.models.Album;
import com.gomez.jeanpi.spotifymaterial.models.Artist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpotifyAPI {

    @GET("search")
    Call<Artist> getArtist(
            @Header("Authorization") String token,
            @Query("query") String query,
            @Query("type") String type
    );

    @GET("artists/{id}/albums")
    Call<Album> getAlbums(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Query("album_type") String type
    );

}
