package nori.beta.musicplayer.Class;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import nori.beta.musicplayer.Song;

/**
 * Created by Lenowo G575 on 2016-12-13.
 */

public class DataSearchService {

    private ArrayList<Song> Songs;

    public DataSearchService(File root){
        Songs = findSongs(root);
    }

    private ArrayList<Song> findSongs(File root) {
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

        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                al.addAll(findSongs(singleFile));
                //Log.e("findsongs","Folder");
            } else {
                if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")) {
                    al.add(new Song(singleFile));
                    Log.e("FileInfo.GetSong",singleFile.getName().toString());
                }
            }
        }
        return al;
    }


    public ArrayList<Song> getDataList() {
        return Songs;
    }

    public int getSize() {
        return Songs.size();
    }
}
