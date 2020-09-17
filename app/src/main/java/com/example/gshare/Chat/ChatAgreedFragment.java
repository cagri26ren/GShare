package com.example.gshare.Chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.example.gshare.Popup.PopupTerminateDeal;
import com.example.gshare.Profile.ProfilePublicFragment;
import com.example.gshare.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChatAgreedFragment extends Fragment {
    HomePageActivity a;
    ListView listView;
    FirebaseListAdapter<Message> chatAdapter;
    String textBeSend;

    EditText editText;
    EditText editG;
    EditText editDay;
    TextView noticeName;
    Button userNumaAndSurname;
    TextView timeLeft;
    TextView gText;
    ImageView noticeImage;

    Notice noticeDB;
    Notice notice;
    Chat chat;

    User recieverUser;
    User itemOwner;
    User user;
    User customer;
    User noticeOwner;


    String email;
    String noticeId;
    String chatId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_agreed, container, false);

        email = getArguments().getString("email");
        noticeId = getArguments().getString("noticeId");
        chatId = getArguments().getString("chatId");

        editG = view.findViewById(R.id.gEditText);
        editDay = view.findViewById(R.id.daysEditText);
        editText = view.findViewById(R.id.editText);
        noticeName = view.findViewById(R.id.itemName);
        userNumaAndSurname = view.findViewById(R.id.nameButton2);
        timeLeft = view.findViewById(R.id.timeLeftTextView);
        gText = view.findViewById(R.id.moneyTextView);
        noticeImage = view.findViewById(R.id.itemImageView);

        final Handler handler = new Handler();

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
                chat.setStatus(Chat.AGREED);
                noticeName.setText(notice.getName());
                editG.setText( notice.getG() + "" );
                editDay.setText( notice.getDay() + "");
                gText.setText( user.getG() + "");
                Picasso.with( getActivity() ).load( notice.getImageUrl() ).into( noticeImage );

                timeLeft.setText( notice.computeTimeLeftHours() + " hours left ");

                if( user.equals(customer) ) {
                    recieverUser = noticeOwner;
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
                    userNumaAndSurname.setText(recieverUser.getUserName());
                    if(notice.getNoticeType()== Notice.LEND_NOTICE){
                        itemOwner = noticeOwner;
                    }
                    if(notice.getNoticeType()==Notice.BORROW_NOTICE){
                        itemOwner = customer;
                    }
                }


                if( notice.computeTimeLeftForMilliSeconds() <= 0  ){
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("noticeId", noticeId);
                    bundle.putString("chatId", chatId);
                    chat.setStatus(Chat.WAITING_FOR_RETURN);
                    chat.setCustomer(customer);
                    chat.setNoticeOwner(noticeOwner);
                    notice.setNoticeOwner(noticeOwner);
                    notice.setNoticeTaker(customer);
                    DBHelper.updateChat(chat);
                    DBHelper.updateNotice(notice);

                    ChatDoneFragment chatDoneFragment = new ChatDoneFragment();
                    chatDoneFragment.setArguments(bundle);
                    getActivity().setContentView(R.layout.blank_layout);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_layout, chatDoneFragment);
                    fragmentTransaction.commit();
                }
            }
        },195);

        Button terminateButton = (Button) view.findViewById(R.id.terminateButton);
        terminateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.equals(itemOwner)){

                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("noticeId", noticeId);
                    bundle.putString("chatId",chatId);

                    PopupTerminateDeal terminatePopUp = new PopupTerminateDeal();
                    terminatePopUp.setArguments(bundle);
                    terminatePopUp.show(getFragmentManager(), "TerminatePopUp");

                }
            }
        });

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
    }}