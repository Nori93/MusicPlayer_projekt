package nori.beta.musicplayer.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import nori.beta.musicplayer.Class.Adapters.AlbumAdapter;
import nori.beta.musicplayer.Class.Adapters.SongsAdapter;
import nori.beta.musicplayer.MainActivity;
import nori.beta.musicplayer.R;
import nori.beta.musicplayer.Song;

public class PlayListFragment extends Fragment {

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
    FrameLayout album;
    FrameLayout artist;
    FrameLayout songs;
    FrameLayout playlist;

    ArrayList<Song> List;
    RelativeLayout relativeLayout;
    ListView listView;
    int indexOfSong;



    public void setList(ArrayList<Song> file){
        this.List = file;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SongsAdapter songsAdapter = new SongsAdapter(view.getContext(),List);
        final AlbumAdapter albumAdapter = new AlbumAdapter(view.getContext(),List);

        listView = (ListView)view.findViewById(R.id.ListView);
        listView.setAdapter(songsAdapter);
        album=(FrameLayout)view.findViewById(R.id.albums_btn);
        album.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listView.setAdapter(albumAdapter);
            }
        });
        songs=(FrameLayout)view.findViewById(R.id.songs_btn);
        songs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listView.setAdapter(songsAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                indexOfSong = position;
                ((MainActivity)getActivity()).setStage(position);

            }

        });
    }

    private void indexList(ArrayList<Song> songs){
        ArrayList<ListItem> index = new ArrayList<ListItem>();
        int i =0;
        for(Song s:songs){
            index.add(new ListItem(s,i));
            i++;
        }
    }


}


class ListItem{
    Song song;
    int id;
    ListItem(Song s, int i){
        this.song=s;
        this.id=i;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
