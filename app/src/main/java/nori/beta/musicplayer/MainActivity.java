package nori.beta.musicplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import nori.beta.musicplayer.Class.BlurBuilder;

public class MainActivity extends Activity {
    /**<####_Norbert_####>
     *  image object that needet to change album cover for background and center imageView
     */

    private ImageView bg;       // blured backgroud of size of screen
    private ImageView cover;    // small image in center of activity that plays song
    private BlurBuilder blured; // class to blur image for background

    /**
     * <####_Norbert_####/>
     */

    /**<####_Krzysiek_####>
     * @param progressBar
     * Skalowanie sie progress bar do każdej piosenki i pokazuje postemp pioseniki
     * plus mozliwość przesuniecia uzywania go do przewijania pioseni po przez progresbar
     */
    private ProgressBar progressBar;


    private ImageButton
            listButton,             //Start framgent with list of songs
            likeButton,             //Add song to favorit
            progressButton,         //Change progress of song
            volumeButton,            //Change volum of sound
            loopButton,             //Loop one song, all song  or don't loop
            prevSongButton,         //Go to privius song
            backSongButton,         //Roll back a song
            play_pause_stopButton,  //on click do 1.play/2.paues/3.stop for all change icon
            frontSongButton,        //Roll forward a song
            nextSongButton;         //Go to next song

    private TextView title_band;    //Set title of song with name of band (Title-Name)
    private MediaPlayer player;


    /**
     * <####_Krzysiek_####/>
     */



    /**   <####_Norbert_####>
     *  Lists of files
     */
    ArrayList<File> mySongs; // list of music file
    ArrayList<FileInfo> songsInfo; //list of music file with extract information about them


    /**<####_Norbert_####/>
     *
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Image Part
        bg = (ImageView)findViewById(R.id.main_background);
        cover = (ImageView)findViewById(R.id.cover_image);
        blured =new BlurBuilder();
        //Buttons
        play_pause_stopButton=(ImageButton)findViewById(R.id.play_pause_stop_button);
        listButton=(ImageButton)findViewById(R.id.goList_button);
        likeButton=(ImageButton)findViewById(R.id.liked_button);
        progressButton=(ImageButton)findViewById(R.id.progress_button);
        volumeButton=(ImageButton)findViewById(R.id.volum_button);
        loopButton=(ImageButton)findViewById(R.id.loopsong_button);
        prevSongButton=(ImageButton)findViewById(R.id.prevSong_button);
        backSongButton=(ImageButton)findViewById(R.id.rollback_button);
        frontSongButton=(ImageButton)findViewById(R.id.rollfront_button);
        nextSongButton=(ImageButton)findViewById(R.id.next_Song);
        player = new MediaPlayer();


        //TextView
        //<####_To Do_####>

        //ProgressBarr
        //<####_To Do_####>

        //Lists
        mySongs = findSongs(Environment.getExternalStorageDirectory());
        songsInfo = new ArrayList<FileInfo>();

        /**
         * Change List of file to a list with file with information of that file
         */
        for(File f:mySongs){
            songsInfo.add(new FileInfo(f));
        }


        /**
         * Set image of song on background
         */
        for(int i=0;i<songsInfo.size();i++){


            // and to this if -||- || i = playedNow(int which song is played)
            if(songsInfo.get(i).getBackground()!= null){
                bg.setImageBitmap(blured.blur(this,songsInfo.get(i).getBackground()));
                cover.setImageBitmap(songsInfo.get(i).getBackground());
                Log.i("FileInfo.SetCover","Set cover of " + songsInfo.get(i).getName());

            }
        }

        // Here have u data of all file and chosen song and can u make to play song
        setButtons();






    }

    /**<####_Norbert_####>
     *
     * @param root
     * @return
     */
    private ArrayList<File> findSongs(File root) {
        ArrayList<File> al = new ArrayList<File>();
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
                    al.add(singleFile);
                    Log.e("FileInfo.GetSong",singleFile.getName().toString());
                }
            }
        }
        return al;
    }

    /**
     * <####_Norbert_####/>
     */
    /**
     * <####_KRZYSIEK_####/>
     */
    private void setButtons() {
        play_pause_stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)  {
                try {

                    player.reset();
                    player.setDataSource(songsInfo.get(0).getPath());
                    player.prepare();
                    player.start();
                }catch (IOException e) {
                    Log.v(getString(R.string.app_name), e.getMessage());
                }



            }
        });

    }

}
