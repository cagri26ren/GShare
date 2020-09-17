package com.example.gshare.Profile;

import android.content.Context;
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

public class ProfileTransactionAdapter extends ArrayAdapter<Notice> {
    Context c;
    ArrayList<Notice> transactedNotices;
    public ProfileTransactionAdapter(Context con,ArrayList<Notice> notices){
        super(con, R.layout.fragment_notice_with_comment_borrowing,notices);
        c = con;
        transactedNotices = notices;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_notice_with_comment_borrowing,parent,false);
        TextView text = (TextView) view.findViewById(R.id.textViewItemName);
        TextView days = (TextView) view.findViewById(R.id.daysTextViewTransacted);
                text.setText(transactedNotices.get(position).getName());
                days.setText(transactedNotices.get(position).getDay() + "");
        return view;
    }

}
