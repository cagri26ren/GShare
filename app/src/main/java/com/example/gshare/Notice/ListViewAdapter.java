package com.example.gshare.Notice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gshare.ModelClasses.NoticeModel.Notice;

import com.example.gshare.R;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Notice> {

    Context context;
    ArrayList<Notice> myNotices;

    public ListViewAdapter(@NonNull Context c,@NonNull ArrayList<Notice> notices){
        super(c, R.layout.noticelayout,notices);
        this.context = c;
        this.myNotices = notices;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = layoutInflater.inflate(R.layout.noticelayout,parent,false);
        TextView  productName = row.findViewById(R.id.productName);
        TextView  productDesription = row.findViewById(R.id.productDesription);
        TextView  gamouth = row.findViewById(R.id.gamouth);

        productName.setText(myNotices.get(position).getName());
        productDesription.setText(myNotices.get(position).getNote()+ "");
        gamouth.setText(myNotices.get(position).getG() + "");
        return row;
    }
    public ListViewAdapter update(ArrayList<Notice> noticeTries){
        ListViewAdapter adapterNew = new ListViewAdapter(this.context,noticeTries);
        return adapterNew;
    }
}











