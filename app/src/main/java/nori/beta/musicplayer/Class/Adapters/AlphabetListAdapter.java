package nori.beta.musicplayer.Class.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import nori.beta.musicplayer.FileInfo;
import nori.beta.musicplayer.R;

/**
 * Created by Lenowo G575 on 2016-11-17.
 */

public class AlphabetListAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflter;
    private TextView title;
    public ListView list;
    private List<FileInfo> fileinfolist;
    private List<FileInfo>  alphabetlist;

    private String[] alphabetTable = {
            "#","A","B","C","D",
            "E","F","G","H","I",
            "J","K","L","M","N",
            "O","P","Q","R","S",
            "T","U","W","X","Y",
            "Z","?"};

    //Setting alphabet list order
    private int stage = 0 ,index = 0;
    private FileInfo storage;
    private List<Filelist> storageList;



    //Now
    private FileInfo file;

    public AlphabetListAdapter(Context applicationContext, List<FileInfo> fileinfolist) {
        this.context = applicationContext;
        this.fileinfolist = fileinfolist;
        this.inflter = (LayoutInflater.from(applicationContext));
    }

    private void SetAlphabetList(List<FileInfo> fileinfolist) {

        /**
         * Method SetAlphabetList set in list the separator
         *
         * @param fileinfolist the list of all music file that are sorted
         * if file is start with next letter in alphabet change stage to next letter and add
         * a separator for file with a filelist that is a child of fileinfo .
         *
         * else add to storagelist
         */
        for(FileInfo f:fileinfolist){
            if(f.getName().startsWith(alphabetTable[stage+1].toLowerCase()) || f.getName().startsWith(alphabetTable[stage+1])){
                stage ++;
                storageList.add(new Filelist(null,true,alphabetTable[stage+1]));
                storageList.add((Filelist)f);
            }else{
                storageList.add((Filelist)f);
            }
            index ++;
        }



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
        return view;
    }
}

class Filelist extends FileInfo{


    /***
     * FileInfo:
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
