package com.example.gshare.Chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.ChatAdapter;
import com.example.gshare.DBHelper;
import com.example.gshare.HomePageActivity;
import com.example.gshare.ModelClasses.ChatModel.Chat;
import com.example.gshare.ModelClasses.ChatModel.Message;
import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.Popup.PopupDoYouAgreeFragment;
import com.example.gshare.Popup.PopupReport;
import com.example.gshare.Profile.ProfilePublicFragment;
import com.example.gshare.R;
//import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;

public class ChatReturnedFragment extends Fragment {

    HomePageActivity a;
    ListView listView;
    FirebaseListAdapter<Message> chatAdapter;
    String textBeSend;

    EditText editText;
    EditText editG;
    EditText editDay;
    TextView noticeName;
    Button userNumaAndSurname;
    TextView gText;

    Notice notice;
    Chat chat;

    User senderUser;
    User recieverUser;
    User itemOwner;
    User user;

    User noticeOwner;
    User customer;
    Notice noticeDB;
    ImageView noticeImage;
    DatabaseReference dbRef;

    String email;
    String chatId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = inflater.inflate(R.layout.fragment_chat_returned, container, false);

        email = getArguments().getString("email");
        chatId = getArguments().getString("chatId");
        dbRef = FirebaseDatabase.getInstance().getReference("chats");

        new DBHelper().getChatById(chatId, new DBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(Object data) {
                chat = (Chat)data;
            }

            @Override
            public void dataIsInserted() {

            }

            @Override
            public void dataIsUpdated() {

            }

            @Override
            public void dataIsDeleted() {

            }
        });
        /**
         * Setting users
         */
        new DBHelper().getUser(email, new DBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(Object data) {
                user = (User)data;
            }

            @Override
            public void dataIsInserted() {

            }

            @Override
            public void dataIsUpdated() {

            }

            @Override
            public void dataIsDeleted() {

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                new DBHelper().getUser(chat.getNoticeOwner().getEmail(), new DBHelper.DataStatus() {
                    @Override
                    public void dataIsLoaded(Object data) {
                        noticeOwner = (User)data;
                    }

                    @Override
                    public void dataIsInserted() {

                    }

                    @Override
                    public void dataIsUpdated() {

                    }

                    @Override
                    public void dataIsDeleted() {

                    }
                });
                chat.setNoticeOwner(noticeOwner);
                new DBHelper().getUser(chat.getCustomer().getEmail(), new DBHelper.DataStatus() {
                    @Override
                    public void dataIsLoaded(Object data) {
                        customer = (User)data;
                    }

                    @Override
                    public void dataIsInserted() {

                    }

                    @Override
                    public void dataIsUpdated() {

                    }

                    @Override
                    public void dataIsDeleted() {

                    }
                });
                chat.setCustomer(customer);
            }
        },100);
        /**
         * Ends here
         */
        editG = view.findViewById(R.id.gEditText);
        editDay = view.findViewById(R.id.daysEditText);
        editText = view.findViewById(R.id.editText);
        noticeName = view.findViewById(R.id.itemName);
        userNumaAndSurname = (Button)view.findViewById(R.id.nameButton2);

        gText = view.findViewById(R.id.moneyTextView);
        noticeImage = view.findViewById(R.id.itemImageView);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new DBHelper().getNoticeById(chat.getNotice().getId(), new DBHelper.DataStatus() {
                    @Override
                    public void dataIsLoaded(Object data) {
                        noticeDB = (Notice) data;
                    }

                    @Override
                    public void dataIsInserted() {

                    }

                    @Override
                    public void dataIsUpdated() {

                    }

                    @Override
                    public void dataIsDeleted() {

                    }
                });
            }
        },100);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                chat.setNotice(noticeDB);
                notice = chat.getNotice();
                chat.setStatus(Chat.NOT_AGREED);
                noticeName.setText(notice.getName());
                editG.setText( notice.getG() + "" );
                editDay.setText( notice.getDay() + "");
                gText.setText( user.getG() + "");
                Picasso.with( getActivity() ).load( notice.getImageUrl() ).into( noticeImage );


                if( user.equals(customer) ) {
                    recieverUser = noticeOwner;
                    senderUser = customer;
                    userNumaAndSurname.setText(recieverUser.getUserName());
                    if(notice.getNoticeType()==Notice.BORROW_NOTICE){
                        itemOwner = customer;
                    }
                    if(notice.getNoticeType()==Notice.LEND_NOTICE){
                        itemOwner = noticeOwner;
                    }
                }
                if( user.equals(noticeOwner ) ) {
                    recieverUser = customer;
                    senderUser = noticeOwner;
                    userNumaAndSurname.setText(recieverUser.getUserName());
                    if(notice.getNoticeType()== Notice.LEND_NOTICE){
                        itemOwner = noticeOwner;
                    }
                    if(notice.getNoticeType()==Notice.BORROW_NOTICE){
                        itemOwner = customer;
                    }
                }

            }
        },195);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if( !((notice.getTempDay() + "" ).equals(editDay.getText().toString())) || !((notice.getTempG() + "" ).equals(editG.getText().toString())) ){
                    chat.setPressedOwner(false);
                    chat.setPressedCustomer(false);
                }
                if( notice.getTempG() == 0 ){
                    notice.setTempG(notice.getG());
                }
                if( notice.getTempDay() == 0){
                    notice.setTempDay( notice.getDay() );
                }
                chat.setNoticeOwner(noticeOwner);
                chat.setCustomer(customer);
                chat.setStatus(Chat.RETURNED);
                DBHelper.updateChat(chat);
            }
        },200);

        userNumaAndSurname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putString("userEmail", recieverUser.getEmail());
                        bundle.putString("email",email);
                        ProfilePublicFragment.comeWhere = ProfilePublicFragment.CHAT;
                        chat.setNoticeOwner(noticeOwner);
                        chat.setCustomer(customer);
                        DBHelper.updateNotice(notice);
                        DBHelper.updateChat(chat);

                        ProfilePublicFragment publicProfile = new ProfilePublicFragment();
                        publicProfile.setArguments(bundle);
                        getActivity().setContentView(R.layout.blank_layout);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_layout, publicProfile);
                        fragmentTransaction.commit();
                    }
                },200);
            }

        });


        listView = (ListView) view.findViewById(R.id.chatListView);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if( chat.getAllMessage() == null ){
                    chat.setAllMessage( new ArrayList<Message>());
                }
            }
        },200);
        ImageButton buttonSend = (ImageButton) view.findViewById(R.id.imageButtonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textBeSend = editText.getText().toString();
                        Message message = null;
                        message = new Message(textBeSend, recieverUser, user);

                        textBeSend = message.getMessage();
                        message.setMessage(textBeSend);

                        if (!textBeSend.matches("")) {
                            chat.getAllMessage().add(message);
                            chat.setNoticeOwner(noticeOwner);
                            chat.setCustomer(customer);
                            DBHelper.updateChat(chat);
                            editText.setText("");
                        }
                    }
                },200);
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatAdapter = new FirebaseListAdapter<Message>(a, Message.class,R.layout.fragment_single_message, FirebaseDatabase.getInstance().getReference().child("chats").child(chatId).child("allMessage"))
                {
                    @Override
                    protected void populateView(View view, Message message, int i) {
                        TextView messageString;
                        TextView date;
                        date = view.findViewById(R.id.dateTime);
                        messageString = view.findViewById(R.id.messageString);

                        messageString.setText(  message.getSender().getUserName() + "-\t" + message.getMessage());
                        date.setText(" ( " + message.getTime() + " ) ");
                    }
                };
                listView.setAdapter(chatAdapter);
            }
        },200);


        return view;
    }

    public void onAttach(Context con) {
        super.onAttach(con);

        if (con instanceof Activity) {
            a = (HomePageActivity) con;
        }
    }

    public static User getStranger( String email , Chat chat ){
        if( chat.getNoticeOwner().getEmail().equals(email)){
            return chat.getCustomer();
        }
        return chat.getNoticeOwner();
    }
}



