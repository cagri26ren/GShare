package com.example.gshare.Popup;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.Chat.ChatReturnedFragment;
import com.example.gshare.DBHelper;
import com.example.gshare.ModelClasses.ChatModel.Chat;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.R;

public class PopupTerminateDeal extends DialogFragment {

    Button yes;
    Button no;

    Chat chat;
    Notice notice;

    User noticeOwner;
    User customer;

    String email;
    String chatId;
    String noticeId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popup_terminate_deal, container, false);

        final Handler handler = new Handler();

        email = getArguments().getString("email");
        chatId = getArguments().getString("chatId");
        noticeId = getArguments().getString("noticeId");

        yes = view.findViewById(R.id.yesButtonConfirm);
        no = view.findViewById(R.id.noButtonConfirm);

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

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new DBHelper().getNoticeById(chat.getNotice().getId(), new DBHelper.DataStatus() {
                    @Override
                    public void dataIsLoaded(Object data) {
                        notice = (Notice)data;
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

            }
        },100);



        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("noticeId", noticeId);
                        bundle.putString("chatId",chatId);

                        chat.setStatus(Chat.RETURNED);
                        notice.setNoticeTaker(customer);
                        notice.setNoticeOwner(noticeOwner);
                        notice.finish();
                        chat.setNotice(notice);
                        DBHelper.updateNotice(notice);
                        DBHelper.updateChat(chat);

                        ChatReturnedFragment chatReturnedFragment = new ChatReturnedFragment();
                        chatReturnedFragment.setArguments(bundle);
                        getActivity().setContentView(R.layout.blank_layout);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_layout, chatReturnedFragment);
                        fragmentTransaction.commit();
                        getDialog().dismiss();

                    }
                },100);


            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }
}
