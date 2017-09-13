package com.gomez.jeanpi.spotifymaterial.views;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gomez.jeanpi.spotifymaterial.R;
import com.gomez.jeanpi.spotifymaterial.models.ItemAlbum;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private List<ItemAlbum> items;
    private Context context;

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name;
        public TextView availability;

        public AlbumViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.album_photo);
            name = (TextView) v.findViewById(R.id.album_name);
            availability = (TextView) v.findViewById(R.id.album_availability);
        }
    }

    public AlbumAdapter(List<ItemAlbum> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.album_item, viewGroup, false);
        return new AlbumViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder viewHolder, int i) {
        final int position = i;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AlbumDetails.class);
                intent.putExtra("IMAGE_URL", items.get(position).getImages().get(0).getUrl());
                intent.putExtra("EXTERNAL_URL", items.get(position).getExternalUrls().getSpotify());
                context.startActivity(intent);
            }
        });
        Picasso.with(viewHolder.image.getContext()).load(items.get(i).getImages().get(0).getUrl()).into(viewHolder.image);
        viewHolder.name.setText(items.get(i).getName());
        if (items.get(i).getAvailableMarkets() != null && items.get(i).getAvailableMarkets().size() < 5) {
            viewHolder.availability.setText("Available in: "+ (items.get(i).getAvailableMarkets().toString()).substring(1, items.get(i).getAvailableMarkets().toString().length() - 1));
        }
    }
}