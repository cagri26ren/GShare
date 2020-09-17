package com.example.gshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gshare.ModelClasses.ChatModel.Message;

import java.util.ArrayList;

public class ChatAdapter extends ArrayAdapter<Message> {

    String email;
    Context context;
    ArrayList<Message> myMessages = new ArrayList<Message>();


    private ArrayList<String> notices;
    public ChatAdapter(@NonNull Context c, @NonNull ArrayList<Message> stringMessages , String email){

        super(c, R.layout.chatlayout,stringMessages);
        this.context = c;
        this.myMessages = stringMessages;
        this.email = email;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View row = null;
        if(position <= myMessages.size()){
        if(myMessages.get(position).getSender().getEmail().equals(email)){
            row = layoutInflater.inflate(R.layout.chatlayout,parent,false);
            TextView chatMessage = (TextView) row.findViewById(R.id.textViewChat);

            chatMessage.setText(myMessages.get(position).getSender().getUserName()+ "-\t" + myMessages.get(position).getMessage());
        }else{
            row = layoutInflater.inflate(R.layout.chatlayout,parent,false);
            TextView chatMessageOther = (TextView) row.findViewById(R.id.textViewChat);
            chatMessageOther.setText(myMessages.get(position).getSender().getUserName()+ "-\t" + myMessages.get(position).getMessage());
        }}
        return row;
    }

}
