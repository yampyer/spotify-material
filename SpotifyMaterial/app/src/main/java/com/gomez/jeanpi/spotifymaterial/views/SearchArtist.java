package com.gomez.jeanpi.spotifymaterial.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gomez.jeanpi.spotifymaterial.R;
import com.gomez.jeanpi.spotifymaterial.api.ServerAPI;
import com.gomez.jeanpi.spotifymaterial.api.SpotifyAPI;
import com.gomez.jeanpi.spotifymaterial.models.Album;
import com.gomez.jeanpi.spotifymaterial.models.Artist;
import com.gomez.jeanpi.spotifymaterial.models.ItemArtist;
import com.gomez.jeanpi.spotifymaterial.utils.TokenManagement;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

public class SearchArtist extends AppCompatActivity {

    private SpotifyAPI serverAPI;
    private TokenManagement tokenManagement;
    private String token;
    private static final String CLIENT_ID = "e8491bd0b31646a9bc629bd1a1c85569";
    private TextView name;
    private TextView followers;
    private ImageView artistPhoto;
    private ProgressBar popularity;
    private View results;
    private RecyclerView albums;
    private AlbumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText querySearch = (EditText) findViewById(R.id.search_input);
        name = (TextView) findViewById(R.id.artist_name);
        followers = (TextView) findViewById(R.id.artist_followers);
        artistPhoto = (ImageView) findViewById(R.id.artist_image);
        popularity = (ProgressBar) findViewById(R.id.artist_popularity);
        albums = (RecyclerView)findViewById(R.id.albums);
        results = (View) findViewById(R.id.result);
        results.setVisibility(View.INVISIBLE);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        albums.setLayoutManager(llm);

        Button search = (Button) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!querySearch.getText().toString().equals("")) {
                    getArtist(querySearch.getText().toString());
                } else {
                    Toast.makeText(SearchArtist.this, "You must enter an artist name in order to search", Toast.LENGTH_SHORT).show();
                }
            }
        });

        serverAPI = ServerAPI.getInstance();

        tokenManagement = TokenManagement.getInstance(getApplicationContext());

        // Check if user is already logged in or not
        if (tokenManagement.isLoggedIn()) {
            HashMap<String, String> user = tokenManagement.getToken();

            //token
            token = user.get(TokenManagement.KEY_TOKEN);
        } else {
            login();
        }

        checkForUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        // ... your own onResume implementation
        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

    private void getArtist(final String query) {
        Call<Artist> getArtist = serverAPI.getArtist("Bearer " + token, query, "artist");
        getArtist.enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {

                if (response.isSuccessful() && response.body().getArtists().getTotal() != 0) {
                    results.setVisibility(View.VISIBLE);
                    ItemArtist artist = response.body().getArtists().getItems().get(0);
                    name.setText(artist.getName());
                    String followersText = artist.getFollowers().getTotal() + " followers";
                    followers.setText(followersText);
                    popularity.setMax(100);
                    popularity.setProgress(artist.getPopularity());
                    Picasso.with(getApplicationContext()).load(artist.getImages().get(0).getUrl()).into(artistPhoto);

                    Call<Album> getAlbums = serverAPI.getAlbums("Bearer " + token, artist.getId(), "album");
                    getAlbums.enqueue(new Callback<Album>() {
                        @Override
                        public void onResponse(Call<Album> call, Response<Album> response) {
                            adapter = new AlbumAdapter(response.body().getItems(), getApplicationContext());
                            albums.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<Album> call, Throwable t) {
                            Log.e("error", t.getMessage());
                            Toast.makeText(SearchArtist.this, "Try again later", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (response.message().equals("Unauthorized")) {
                    Toast.makeText(SearchArtist.this, "Token expired, please try again", Toast.LENGTH_SHORT).show();
                    login();
                } else {
                    Log.e("error", response.message());
                    Toast.makeText(SearchArtist.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {
                Log.e("error", t.getMessage());
                Toast.makeText(SearchArtist.this, "Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login() {
        // Request code will be used to verify if result comes from the login activity. Can be set to any integer.
        final int REQUEST_CODE = 1337;
        final String REDIRECT_URI = "https://spotify.com/callback";

        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (AuthenticationClient.getResponse(resultCode, intent).getAccessToken() != null) {
            token = AuthenticationClient.getResponse(resultCode, intent).getAccessToken();
            if (tokenManagement.isLoggedIn()) {
                tokenManagement.updateToken(token);
            } else {
                tokenManagement.createLoginSession(token);
            }
        }

        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                case TOKEN:
                    token = response.getAccessToken();
                    if (tokenManagement.isLoggedIn()) {
                        tokenManagement.updateToken(token);
                    } else {
                        tokenManagement.createLoginSession(token);
                    }
                    break;

                case ERROR:
                    Toast.makeText(SearchArtist.this, "Error while trying to login, try again later", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    Toast.makeText(SearchArtist.this, "You need to login in order to use the application", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
