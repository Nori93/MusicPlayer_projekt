package nori.beta.musicplayer.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nori.beta.musicplayer.R;

public class Playlist extends Fragment {

    /**<####_Norbert_####>
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     *
     *
     *
     */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
