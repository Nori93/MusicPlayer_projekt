package nori.beta.musicplayer.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

import nori.beta.musicplayer.Class.BlurBuilder;
import nori.beta.musicplayer.MainActivity;
import nori.beta.musicplayer.R;

/**
 * Created by Lenowo G575 on 2016-12-13.
 */

public class PlayerFragment extends Fragment {



    //---------------------------------------------------------------Gui----------------------------

    /**<####_Norbert_####>
     *  image object that needet to change album cover for background and center imageView
     */
    private ImageView bg;       // blured backgroud of size of screen
    private ImageView cover;    // small image in center of activity that plays song


    /**<####_Krzysiek_####>
     * @param progressBar
     * Skalowanie sie progress bar do każdej piosenki i pokazuje postemp pioseniki
     * plus mozliwość przesuniecia uzywania go do przewijania pioseni po przez progresbar
     */
    private SeekBar
            progressBar;            // Creating seekbar that show progress of song and allow us scroll and rewind song


    private ImageButton
            listButton,             //Start framgent with list of songs
            likeButton,             //Add song to favorit
            randomButton,            //Change volum of sound
            loopButton,             //Loop one song, all song  or don't loop
            prevSongButton,         //Go to privius song
            play_pause_stopButton,  //on click do 1.play/2.paues/3.stop for all change icon
            nextSongButton;         //Go to next song

    static private TextView
            actualTime,
            songDuration,
            title_band;    //Set title of song with name of band (Title-Name)



    //--------------------------------------------------------------Engin---------------------------

    private BlurBuilder blured; // class to blur image for background
    static private MediaPlayer player; // Player that play music
    static private int stage=0,currentSongIndex = 0 ; //Stage show us if application is newly open or if music were already played. Index show which song from list is already playing
    private Handler mHandler=new Handler(); //Handler that help with refreshing progressBar
    private Utilities utils; //Change seconds into min + sec
    private boolean isLoop; //Show if song is looped or no
    private boolean isRandom; //Show if next song should be random
    private View view;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }


    private void initGUI(View v){
        // Image Part
        bg = (ImageView)v.findViewById(R.id.main_background);
        cover = (ImageView)v.findViewById(R.id.cover_image);


        //Buttons
        play_pause_stopButton=(ImageButton)v.findViewById(R.id.play_pause_stop_button);
        listButton=(ImageButton)v.findViewById(R.id.goList_button);
        likeButton=(ImageButton)v.findViewById(R.id.liked_button);
        randomButton=(ImageButton)v.findViewById(R.id.random_button);
        loopButton=(ImageButton)v.findViewById(R.id.loopsong_button);
        prevSongButton=(ImageButton)v.findViewById(R.id.prevSong_button);
        nextSongButton=(ImageButton)v.findViewById(R.id.next_Song);

        //TextView
        //<####_To Do_####>
        title_band=(TextView)v.findViewById(R.id.title_TextView);
        actualTime=(TextView)v.findViewById(R.id.actualTimeTextView);
        songDuration=(TextView)v.findViewById(R.id.songDurationTextView);

        //ProgressBarr
        //<####_To Do_####>
        progressBar=(SeekBar)v.findViewById(R.id.progressBar);
        //progressBar.setOnSeekBarChangeListener(this);

    }

    private void initEngin(){
        /**
         * Change List of file to a list with file with information of that file
         */

        if(player==null)
            player = new MediaPlayer();
        utils=new Utilities();
        isLoop=false;
        isRandom=false;
        blured =new BlurBuilder();

        // init list button
        listButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).StartPlayListFragment();
            }
        });

    }

    private void setButtons() {
        play_pause_stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Play/Pause song button clicked
                if (!player.isPlaying()) { //If player isnt playing now
                    if(stage==0) //And if app was just opened
                        playSong(currentSongIndex); //play song with index (for new app it's 0 so 1st song from list)
                    player.start();
                    stage=1;

                    play_pause_stopButton.setImageResource(R.drawable.pause); //Change image of button to "pause" image
                    updateProgressBar(); //Start updating progress bar
                }
                else
                {
                    player.pause(); //Pause playing song
                    play_pause_stopButton.setImageResource(R.drawable.play); //Change image of button to "play" image
                }
            }
        });
        loopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //LoopButton clicked
                if(isLoop) //Change isLoop value to opposite
                    isLoop=false;
                else
                    isLoop=true;
            }
        });
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Same as above
                if(isRandom)
                    isRandom=false;
                else
                    isRandom=true;
            }
        });
        prevSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Clicked button to move to prev song
                if(currentSongIndex!=0) { //If it isnt 1st song from list just decrease index by 1 and start playing new song and change index
                    currentSongIndex--;
                    playSong(currentSongIndex);
                }
                else //If it's 1st song from list start playing last song from list and change index
                {
                    currentSongIndex=0;
                    playSong(currentSongIndex);
                }
            }
        });
        nextSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Clicked button to move to next song
                if(currentSongIndex<((MainActivity)getActivity()).getSize()-1) { //If it isn't last song from list just start playing next song from it and change index/
                    currentSongIndex++;
                    playSong(currentSongIndex);
                }
                else //If it is last song from list start playing 1st song from list and change index
                {
                    currentSongIndex=((MainActivity)getActivity()).getSize()-1;
                    playSong(currentSongIndex);
                }
            }
        });
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //Using progress bar to scrolling song

            @Override
            public void onStopTrackingTouch(SeekBar progressBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar progressBar) {

            }

            @Override
            public void onProgressChanged(SeekBar progressBar, int progress, boolean fromUser) { //When user move progress bar song go to moment that user choosed
                if(player != null && fromUser){
                    player.seekTo(progress * 1000);
                }
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) { //When song ended playing
                if(isLoop) //If song is looped play same song again
                {
                    playSong(currentSongIndex);
                }
                else if(isRandom) //If shuffling is choosed play random song from list
                {
                    Random rand = new Random();
                    currentSongIndex = rand.nextInt((((MainActivity)getActivity()).getSize() - 1) - 0 + 1) + 0;
                    playSong(currentSongIndex);
                }
                else if(currentSongIndex<((MainActivity)getActivity()).getSize()-1) {
                    currentSongIndex++;
                    playSong(currentSongIndex);
                }
                else {
                    currentSongIndex=0;
                    playSong(currentSongIndex);
                }


            }
        });


    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100); //Updating progressBar every 100ms
    }

    /**
     *
     * Komentarz krzysiek
     * @param index
     */
    public void playSong(int index)
    {
        try {
            player.reset();
            player.setDataSource(((MainActivity)getActivity()).getElement(index).getPath()); //Getting song with proper index from list
            player.prepare();
            player.start(); //Playing prepared song
            // Displaying Song title
            String songTitle = ((MainActivity)getActivity()).getElement(index).getTitle();
            String songArtist = ((MainActivity)getActivity()).getElement(index).getArtist();
            title_band.setText(songArtist + " - " +songTitle); //Getting title of song and pasting it into TextView
            title_band.setTextColor(Color.WHITE);
            setBackground(index,view);
            // Changing Button Image to pause image
            play_pause_stopButton.setImageResource(R.drawable.pause);

            // set Progress bar values
            progressBar.setProgress(0);
            progressBar.setMax(player.getDuration()/1000);

            // Updating progress bar
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    // NA RAZIE FAZA EKSPERYMENTALNE
    //


    public void setTitle_band(int i)
    {
        String songTitle = ((MainActivity)getActivity()).getElement(i).getTitle();
        String songArtist = ((MainActivity)getActivity()).getElement(i).getArtist();
        title_band.setText(songArtist + " - " +songTitle); //Getting title of song and pasting it into TextView
        title_band.setTextColor(Color.WHITE);
    }

    private void setBackground(int i,View v){
        //setting the back image and cover image to the chosen song
        if(((MainActivity)getActivity()).getElement(i).getBackground()!= null){
            bg.setImageBitmap(blured.blur(v.getContext(),((MainActivity)getActivity()).getElement(i).getBackground()));
            cover.setImageBitmap(((MainActivity)getActivity()).getElement(i).getBackground());
            Log.i("FileInfo.SetCover","Set cover of " + ((MainActivity)getActivity()).getElement(i).getName());
        }

    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() { //Updating time of song and progressbar
            long totalDuration = player.getDuration();
            long currentDuration = player.getCurrentPosition();
            songDuration.setText(""+utils.milliSecondsToTimer(totalDuration)); //Setting duration of song in TextView
            actualTime.setText(""+utils.milliSecondsToTimer(currentDuration)); //Setting actual time of song in TextView

            // Updating progress bar
            int mCurrentPosition = player.getCurrentPosition() / 1000;
            progressBar.setProgress(mCurrentPosition);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);

        }
    };

    public void setSongIndex(int songIndex) {
        this.currentSongIndex = songIndex;
    }
}
