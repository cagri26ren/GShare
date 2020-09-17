package com.example.gshare.Notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.R;

import java.util.ArrayList;

public class MyNoticesAdapter extends ArrayAdapter<Notice> {
    View row;
    Context c;
    ArrayList<Notice> noticesTransacted;
    private ArrayList<Notice> notices;
    public MyNoticesAdapter(@NonNull Context c, @NonNull ArrayList<Notice> notices){
        super(c, R.layout.noticelayout,notices);
        this.c = c;
        this.noticesTransacted = notices;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(!noticesTransacted.get(position).isOver()) {
            if(noticesTransacted.get(position).getNoticeType() == Notice.LEND_NOTICE) {
                row = layoutInflater.inflate(R.layout.fragment_notice_small_lending, parent, false);
                TextView itemG = (TextView) row.findViewById(R.id.price);
                itemG.setText("G: " + noticesTransacted.get(position).getG());
            }else{
                row = layoutInflater.inflate(R.layout.fragment_notice_small_lending, parent, false);
            }
            TextView itemName = (TextView) row.findViewById(R.id.textViewItemName);
            TextView itemDay = (TextView) row.findViewById(R.id.daysTextViewTransacted);
            itemName.setText(noticesTransacted.get(position).getName());
            itemDay.setText(noticesTransacted.get(position).getDay() + "");

        }else{
            if(noticesTransacted.get(position).getNoticeType() == Notice.LEND_NOTICE) {
                row = layoutInflater.inflate(R.layout.fragment_notice_small_deletable_lending, parent, false);
                TextView itemGDeletable = (TextView) row.findViewById(R.id.priceDeletable);
                itemGDeletable.setText("G: " + noticesTransacted.get(position).getG());
            }else{
                row = layoutInflater.inflate(R.layout.fragment_notice_small_deletable_borrowing, parent, false);
            }
            TextView itemNameDeletable = (TextView) row.findViewById(R.id.textViewDeletable);
            TextView itemDayDeletable = (TextView) row.findViewById(R.id.daysTextViewDeletable);
            itemNameDeletable.setText(noticesTransacted.get(position).getName());
            itemDayDeletable.setText(noticesTransacted.get(position).getDay() + "");
            ImageButton deleteButton = (ImageButton) row.findViewById(R.id.deleteNoticeButton);
            deleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    noticesTransacted.remove(noticesTransacted.get(position));
                    //disactive it from database

                }

            });

        }
        return row;
    }

    }
