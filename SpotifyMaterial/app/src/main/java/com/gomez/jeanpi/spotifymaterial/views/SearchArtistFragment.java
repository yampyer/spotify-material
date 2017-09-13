package com.gomez.jeanpi.spotifymaterial.views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomez.jeanpi.spotifymaterial.R;

public class SearchArtistFragment extends Fragment {

    public SearchArtistFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_artist, container, false);
    }
}
