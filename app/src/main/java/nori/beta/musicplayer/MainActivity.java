package nori.beta.musicplayer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Environment;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import nori.beta.musicplayer.Class.DataSearchService;
import nori.beta.musicplayer.Fragment.PlayListFragment;
import nori.beta.musicplayer.Fragment.PlayerFragment;

public class MainActivity extends Activity {

    PlayListFragment playListFragment;
    PlayerFragment playerFragment;
    RelativeLayout fragemntBox;
    FragmentManager manager;
    FragmentTransaction transaction;

    ArrayList<Song> songsInfo; //list of music file with extract information about them
    DataSearchService dataSearchService; // Data Research Class



    /**<####_Norbert_####/>
     *
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSearchService = new DataSearchService(Environment.getExternalStorageDirectory());

    }
    @Override
    protected void onResume(){
        super.onResume();
        // init Database and rest of gui staff that need database
        initEngin();

    }


    private void initEngin(){
        dataSearchService = new DataSearchService(Environment.getExternalStorageDirectory());
        songsInfo = new ArrayList<Song>();
        songsInfo = dataSearchService.getDataList();

    }



    /**public ArrayList<Song> Search(File root) {
        ArrayList<Song> al = new ArrayList<Song>();
        File[] files = root.listFiles();

        /**
         * findSongs Search for music file in memory
         *
         * for each file in memory
         * 1.if is that file a folder , then  take all file then give it in method findSongs
         * and with requrency
         * 2.Else if that file end with .mp3 or .wav ,then add to list
         */
/**
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                al.addAll(Search(singleFile));
                //Log.e("findsongs","Folder");
            } else {
                if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")) {
                    al.add(new Song(singleFile));
                    Log.e("FileInfo.GetSong",singleFile.getName().toString());
                }
            }
        }
        return al;

    }*/

    //Set current Song
    public void setStage(int i){
        playerFragment.setSongIndex(i);
        manager.beginTransaction().remove(playListFragment).commit();
        playerFragment.playSong(i);
    }

    public void StartPlayListFragment(){
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        playListFragment = new PlayListFragment();
        playListFragment.setList(songsInfo);
        transaction.add(R.id.fragment_fullscreeen, playListFragment);
        transaction.commit();

    }

    public ArrayList<Song> GetSonges(){
        return dataSearchService.getDataList();
    }

    public Song getElement(int i){
        return dataSearchService.getDataList().get(i);
    }

    public int getSize(){
        return dataSearchService.getSize();
    }
/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("stage", stage);
        outState.putInt("index", currentSongIndex);
        outState.putInt("time",player.getCurrentPosition());
        outState.putBoolean("isLoop",isLoop);
        outState.putBoolean("isRandom",isRandom);
        outState.putBoolean("isPlaying",player.isPlaying());
        //player.stop();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        stage=1;
        initEngin();
        currentSongIndex = savedInstanceState.getInt("index");
        isLoop=savedInstanceState.getBoolean("isLoop");
        isRandom=savedInstanceState.getBoolean("isLoop");
        progressBar.setProgress(savedInstanceState.getInt("time"));
        updateProgressBar();
        setTitle_band(currentSongIndex);
        setBackground(currentSongIndex,view);
        if(savedInstanceState.getBoolean("isPlaying"))
            play_pause_stopButton.setImageResource(R.drawable.pause);

        player.seekTo(savedInstanceState.getInt("time"));

        super.onRestoreInstanceState(savedInstanceState);
    }*/

}
