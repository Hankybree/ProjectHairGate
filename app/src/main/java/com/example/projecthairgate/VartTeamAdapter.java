package com.example.projecthairgate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class VartTeamAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private final String [] namn;
    private final int [] porträtt;
    View view;

    public VartTeamAdapter(Context context, String[] namn, int[] porträtt) {
        this.context = context;
        this.namn = namn;
        this.porträtt = porträtt;
    }


    @Override
    public int getCount() {
        return namn.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)  {

            view = new View(context);
            view = layoutInflater.inflate(R.layout.vartteam_item, null);

            ImageView imageView = view.findViewById(R.id.vartteam_imageview);
            TextView textView = view.findViewById(R.id.vartteam_textview);
            imageView.setImageResource(porträtt[position]);
            textView.setText(namn[position]);
        }
        return view;

    }
}
