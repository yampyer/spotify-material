package com.gomez.jeanpi.spotifymaterial.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomez.jeanpi.spotifymaterial.R;

public class ArtistResultFragment extends Fragment {

    public ArtistResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_result, container, false);
    }
}
