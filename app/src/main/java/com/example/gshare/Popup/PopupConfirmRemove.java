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

import com.example.gshare.DBHelper;
import com.example.gshare.Notice.MyNoticesFragment;
import com.example.gshare.Notice.NoticeEditBorrowingFragment;
import com.example.gshare.R;

public class PopupConfirmRemove extends DialogFragment {

    Button yes;
    Button no;

    String email;
    String noticeId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popup_confirm_delete, container, false);

        yes = view.findViewById(R.id.yesButtonConfirm);
        no = view.findViewById(R.id.noButtonConfirm);
        email = getArguments().getString("email");
        noticeId = getArguments().getString("noticeId");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DBHelper.deleteNotice(noticeId);
                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("noticeId", noticeId);

                        MyNoticesFragment fragment = new MyNoticesFragment();
                        getActivity().setContentView(R.layout.fullyblanklayout);
                        fragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_biglayout,fragment);
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


