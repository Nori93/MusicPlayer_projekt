package nori.beta.musicplayer.Class.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import nori.beta.musicplayer.R;
import nori.beta.musicplayer.Song;

/**
 * Created by Lenowo G575 on 2016-11-17.
 */

public class SongsAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflter;
    private TextView title;
    private List<Song> fileinfolist;
    private List<Song>  alphabetlist;

    private String[] alphabetTable = {
            "#","A","B","C","D",
            "E","F","G","H","I",
            "J","K","L","M","N",
            "O","P","Q","R","S",
            "T","U","W","X","Y",
            "Z","?"};

    //Setting alphabet list order
    private int stage = 0 ,index = 0;
    private Song storage;
    private List<Filelist> storageList;



    //Now
    private Song file;

    public SongsAdapter(Context applicationContext, List<Song> fileinfolist) {
        this.context = applicationContext;
        this.fileinfolist = fileinfolist;
        this.inflter = (LayoutInflater.from(applicationContext));
    }



    @Override
    public int getCount() {
        return alphabetTable.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.adapter_alphabet, null);
        title=(TextView)view.findViewById(R.id.alph_title);



        if(fileinfolist.get(i).getTitle() == "none" || fileinfolist.get(i).getArtist() == "none")
        {

            title.setText(fileinfolist.get(i).getName());
        }else{
        title.setText(fileinfolist.get(i).getTitle() +" - "+ fileinfolist.get(i).getArtist());
        }

        return view;
    }
}

class Filelist extends Song {


    /***
     * Song:
     * .Cover - Okładki płyty w Bitmap
     * .Artist - Wykonawca
     * .Title - Tytuł utworu
     * .Album - Tytuł albumu
     * .Genre - Gatunek muzyki
     * <p>
     * FileInfoError:
     * .Cover - bark okładki
     * .Artist - brak wykonawcy
     * .Title - brak tytułu
     * .Album - brak albumu
     * .Genre - brak informacji o Gatuneku muzyki
     *
     * @param file
     */

    boolean Header = false;
    String text_head;

    public Filelist(File file,boolean head,String text) {
        super(file);
        this.Header = head;
        this.text_head = text;
    }

    @Override
    public String getName() {
        if(Header)
        {
            return  text_head;
        }
        else
        {
        return super.getName();
        }
    }
}
