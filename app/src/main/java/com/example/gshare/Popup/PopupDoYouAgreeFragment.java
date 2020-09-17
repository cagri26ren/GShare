package com.example.gshare.Popup;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.Chat.ChatAgreedFragment;
import com.example.gshare.Chat.ChatNotAgreedFragment;
import com.example.gshare.DBHelper;
import com.example.gshare.ModelClasses.ChatModel.Chat;
import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PopupDoYouAgreeFragment extends DialogFragment implements View.OnClickListener {

    Notice notice;

    Button yes;
    Button no;
    TextView gValue;
    TextView returnDateValue;
    Chat chat;
    Context con;

    String email;
    String userEmail;
    String chatId;

    User user;
    User noticeOwner;
    User customer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popup_doyouagree, container, false);

        yes = view.findViewById(R.id.agreeButton);
        no = view.findViewById(R.id.dontAgreeButton);
        gValue = view.findViewById(R.id.G);
        returnDateValue = view.findViewById(R.id.returndate);

        email = getArguments().getString("email");
        userEmail = getArguments().getString("userEmail");
        chatId = getArguments().getString("chatId");

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
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new DBHelper().getNoticeById(chat.getNotice().getId(), new DBHelper.DataStatus() {
                    @Override
                    public void dataIsLoaded(Object data) {
                        notice = (Notice) data;
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
                new DBHelper().getUser(email, new DBHelper.DataStatus() {
                    @Override
                    public void dataIsLoaded(Object data) {
                        user = (User) data;
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
                new DBHelper().getUser(chat.getNoticeOwner().getEmail(), new DBHelper.DataStatus() {
                    @Override
                    public void dataIsLoaded(Object data) {
                        noticeOwner = (User) data;
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
                new DBHelper().getUser(chat.getCustomer().getEmail(), new DBHelper.DataStatus() {
                    @Override
                    public void dataIsLoaded(Object data) {
                        customer = (User) data;
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
                notice.setNoticeOwner(noticeOwner);
                notice.setNoticeTaker(customer);
                chat.setCustomer(customer);
                chat.setNoticeOwner(noticeOwner);
                chat.setNotice(notice);
                gValue.setText(notice.getTempG() + "" );
                returnDateValue.setText("Return " + notice.getTempDay() + " days later "  );
                DBHelper.updateChat(chat);
            }
        },200);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);


        return view;

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.agreeButton) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (user.equals(customer)) {
                        chat.setPressedCustomer(true);
                    } else {
                        chat.setPressedOwner(true);
                    }
                    if (chat.isPressedCustomer() && chat.isPressedOwner()) {
                        if (notice.getNoticeType() == Notice.BORROW_NOTICE) {
                            try {
                                notice.setG(notice.getTempG());
                                notice.setDay(notice.getTempDay());
                                notice.agreeOnBorrowNotice(customer, notice.getG());
                            } catch (IllegalArgumentException e) {
                                Toast.makeText(getContext(), "Notice owner does not have enough money", Toast.LENGTH_LONG).show();
                            }
                        }
                        if (notice.getNoticeType() == Notice.LEND_NOTICE) {
                            try {
                                notice.setG(notice.getTempG());
                                notice.setDay(notice.getTempDay());
                                notice.agreeOnLendNotice(customer);
                            } catch (IllegalArgumentException e) {
                                Toast.makeText(getContext(), "Notice taker does not have enough money", Toast.LENGTH_LONG).show();
                            }
                            chat.setNotice(notice);
                            chat.setStatus(Chat.AGREED);
                            DBHelper.updateNotice(notice);
                            chat.setNoticeOwner(notice.getNoticeOwner());
                            chat.setCustomer(notice.getNoticeTaker());
                            DBHelper.updateUser(notice.getNoticeOwner());
                            DBHelper.updateUser(notice.getNoticeTaker());
                            DBHelper.updateChat(chat);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("chatId", chatId);
                        bundle.putString("userEmail", userEmail);
                        getActivity().setContentView(R.layout.fullyblanklayout);
                        ChatAgreedFragment fragment = new ChatAgreedFragment();
                        fragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.main_biglayout, fragment);
                        fragmentTransaction1.commit();
                        getDialog().dismiss();
                    }
                    DBHelper.updateChat(chat);
                }
            }, 200);
        }
        if( v.getId() == R.id.dontAgreeButton) {
            getDialog().dismiss();
        }


    }

    public static String getReturnDate( long dayMilli ) {
        long currentDateTime = System.currentTimeMillis();
        Date currentDate = new Date(currentDateTime + dayMilli);
        DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
        return df.format(currentDate);
    }



}
