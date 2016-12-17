package nori.beta.musicplayer.Class.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nori.beta.musicplayer.R;
import nori.beta.musicplayer.Song;

/**
 * Created by Lenowo G575 on 2016-12-12.
 */

public class AlbumAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflter;
    private ImageView image;
    private TextView title;
    private List<Song> fileinfolist;

    //Setting alphabet list order
    private int stage = 0 ,index = 0;
    private Song storage;
    private List<Filelist> storageList;



    //Now
    private Song file;

    public AlbumAdapter(Context applicationContext, List<Song> fileinfolist) {
        this.context = applicationContext;
        this.fileinfolist = fileinfolist;
        this.inflter = (LayoutInflater.from(applicationContext));
    }



    @Override
    public int getCount() {
        return fileinfolist.size();
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
        view = inflter.inflate(R.layout.adapter_album, null);
        image = (ImageView)view.findViewById(R.id.cover_list_item);
        title = (TextView)view.findViewById(R.id.title_list_item);


            try {
            title.setText(fileinfolist.get(i).getName());
            image.setImageBitmap(fileinfolist.get(i).getBackground());}catch (Exception e){
            Log.e("AlbumAdapter",e.getMessage());}


        return view;
    }
}

