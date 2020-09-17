package com.example.gshare.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gshare.ModelClasses.ChatModel.Chat;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.R;

import java.util.ArrayList;

public class UserChatsAdapter extends ArrayAdapter<Chat> {
    Context context;
    ArrayList<Chat> chats;
    String userEmail;

    public UserChatsAdapter(Context c, ArrayList<Chat> chatsNew, String email){
        super(c, R.layout.chatlayout,chatsNew);
        context = c;
        chats = chatsNew;
        userEmail = email;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.chatlayout, parent,false);
        ImageView image = (ImageView) view.findViewById(R.id.imageChat);
        TextView text = (TextView) view.findViewById(R.id.textViewChat);
        //image.setImageResource(R.drawable.gshare_icon);
        if(chats.get(position).getNoticeOwner().getEmail().equals(userEmail)) {
            text.setText(chats.get(position).getCustomer().getUserName() + " - " + chats.get(position).getNotice().getName() + " - "+ typeConverter(chats.get(position).getNotice().getNoticeType()) + " - " +statusConverter(chats.get(position).getStatus()));
        }else{
            text.setText(chats.get(position).getNoticeOwner().getUserName() + " - " + chats.get(position).getNotice().getName() + " - " + typeConverter(chats.get(position).getNotice().getNoticeType()) + " - " + statusConverter(chats.get(position).getStatus()));
        }
        return view;
    }
    public static String statusConverter( int a ){
        if( a == Chat.NOT_AGREED ){
            return "Not Agreed";
        }
        else if( a == Chat.TERMINATED ){
            return "Terminated";
        }
        else if( a == Chat.AGREED ){
            return "Agreed";
        }
        else if( a == Chat.WAITING_FOR_RETURN ){
            return "Waiting For Return";
        }
        else if( a == Chat.RETURNED ){
            return "Returned";
        }
        return "";
    }
    public static String typeConverter( int a ){
        if( a == Notice.LEND_NOTICE ){
            return "Lend Notice";
        }
        if( a == Notice.BORROW_NOTICE ){
            return "Borrow Notice";
        }
        return "";
    }
}
