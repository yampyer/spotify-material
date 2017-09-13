package com.gomez.jeanpi.spotifymaterial.views;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gomez.jeanpi.spotifymaterial.R;
import com.squareup.picasso.Picasso;

public class AlbumDetails extends AppCompatActivity {

    private String imageUrl;
    private String externalUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageUrl = getIntent().getStringExtra("IMAGE_URL");
        externalUrl = getIntent().getStringExtra("EXTERNAL_URL");

        ImageView albumPhoto = (ImageView) findViewById(R.id.album_image);
        Picasso.with(this).load(imageUrl).into(albumPhoto);
        Button openExternal = (Button) findViewById(R.id.open_external_url);
        openExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(externalUrl));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
