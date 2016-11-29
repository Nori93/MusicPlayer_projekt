package nori.beta.musicplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Time;

/**
 * Created by Lenowo G575 on 2016-11-17.
 */
public class FileInfo {
    String artist,title,album,genre,filename;
    Bitmap cover;
    Time time;
    MediaMetadataRetriever mmr;
    String Path;
    File file;

    /***
     *
     *       FileInfo:
     *       .Cover - Okładki płyty w Bitmap
     *       .Artist - Wykonawca
     *       .Title - Tytuł utworu
     *       .Album - Tytuł albumu
     *       .Genre - Gatunek muzyki
     *
     *       FileInfoError:
     *       .Cover - bark okładki
     *       .Artist - brak wykonawcy
     *       .Title - brak tytułu
     *       .Album - brak albumu
     *       .Genre - brak informacji o Gatuneku muzyki
     */


    protected FileInfo(File file){
        this.Path = file.getPath();
        this.filename = file.getName();
        mmr = new MediaMetadataRetriever();
        mmr.setDataSource(Path);

        /**
         * Set all information of file
         *
         */

        byte[] bytes = mmr.getEmbeddedPicture();
        if(bytes != null){

            InputStream is = new ByteArrayInputStream(mmr.getEmbeddedPicture());
            this.cover = BitmapFactory.decodeStream(is);
            Log.e("FileInfo.Cover","Cover found at Path" + Path);
        }else{
            Log.e("FileInfoError.NoCover","No cover of" + Path);
        }
        artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        if (artist !=null){
            Log.i("FileInfo.Artist",artist + "on path:" + Path);
        }
        else{
            artist = "none";
            Log.e("FileInfoError.NoArtist","No artist at" + Path);
        }

        title= mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        if (title !=null){
            Log.i("FileInfo.Title",title + "on path:" + Path);
        }
        else{
            title = "none";
            Log.e("FileInfoError.NoTitle","No title at" + Path);
        }

        album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        if (album !=null){
            Log.i("FileInfo.Album",album + "on path:" + Path);
        }
        else{
            album = "none";
            Log.e("FileInfoError.NoAlbum","No album at" + Path);
        }

        genre = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
        if (genre !=null){
            Log.i("FileInfo.Genre",genre + "on path:" + Path);
        }
        else{
            genre = "none";
            Log.e("FileInfoError.NoGener","No genre at" + Path);
        }

    }
    public Bitmap getBackground(){
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return filename;
    }

    public String getPath() {
        return Path;
    }
}
