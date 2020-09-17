package com.example.gshare.Profile;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.Chat.ChatFragment;
import com.example.gshare.DBHelper;
import com.example.gshare.HomePageFragment;
import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.Notice.MyNoticesFragment;

import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.Popup.PopupReport;
import com.example.gshare.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    User user;
    ImageButton editButton;
    ImageButton reportButton;
    TextView nameAndSurname;
    TextView username;
    TextView g;

    ImageButton home;
    ImageButton map;
    ImageButton noticeNav;
    ImageButton chat;
    ImageButton profile;

    ListView listView;
    ArrayList<Notice> notices;
    String email;
    String noticeId;
    ProfileTransactionAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle bundle = getArguments();
        email = bundle.getString("email");
        noticeId = bundle.getString("noticeId");

        new DBHelper().getUser( email , new DBHelper.DataStatus() {
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

        editButton = (ImageButton) view.findViewById(R.id.editProfileButton);
        nameAndSurname = (TextView) view.findViewById(R.id.nameTextView);
        username = (TextView) view.findViewById(R.id.usernameTextView);
        reportButton = view.findViewById(R.id.fileReportButton);

        home = view.findViewById(R.id.navigationHome);
        map = view.findViewById(R.id.navigationMap);
        noticeNav = view.findViewById(R.id.navigationMyNotices);
        chat = view.findViewById(R.id.navigationChat);
        profile = view.findViewById(R.id.navigationProfile);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                username.setText(user.getEmail());
                nameAndSurname.setText(user.getUserName());
            }
        },100);

        new DBHelper().getAllUserNotices(email,new DBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(Object data) {
                notices = ( ArrayList<Notice> )data;
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

        listView = (ListView) view.findViewById(R.id.listViewProfile);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
        adapter = new ProfileTransactionAdapter(getContext(),notices);
        listView.setAdapter(adapter);
            }
        },100);


        editButton.setOnClickListener(this);
        home.setOnClickListener(this);
        map.setOnClickListener(this);
        noticeNav.setOnClickListener(this);
        chat.setOnClickListener(this);
        profile.setOnClickListener(this);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email",email);

                PopupReport reportPopUp = new PopupReport();
                reportPopUp.setArguments(bundle);
                reportPopUp.show(getFragmentManager(), "ReportPopUp");
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        bundle.putString("noticeId",noticeId);
        switch (v.getId()) {
            case R.id.editProfileButton:
                ProfileEditFragment profileEditFragment = new ProfileEditFragment();
                profileEditFragment.setArguments(bundle);
                profileEditFragment.show(getFragmentManager(), "EditPopUp");
                break;

            case R.id.navigationHome:

                HomePageFragment fragment1 = new HomePageFragment();
                getActivity().setContentView(R.layout.fullyblanklayout);
                fragment1.setArguments(bundle);
                FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.main_biglayout,fragment1);
                fragmentTransaction1.commit();
                break;

            case R.id.navigationMap:

                HomePageFragment fragment2 = new HomePageFragment();
                fragment2.setArguments(bundle);
                FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.main_layout, fragment2);
                fragmentTransaction2.commit();
                break;

            case R.id.navigationMyNotices:

                MyNoticesFragment fragment3 = new MyNoticesFragment();
                getActivity().setContentView(R.layout.fullyblanklayout);
                fragment3.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_biglayout,fragment3);
                fragmentTransaction.commit();
                break;

            case R.id.navigationChat:

                ChatFragment fragment4 = new ChatFragment();
                getActivity().setContentView(R.layout.fullyblanklayout);
                fragment4.setArguments(bundle);
                FragmentTransaction fragmentTransaction4 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction4.replace(R.id.main_biglayout,fragment4);
                fragmentTransaction4.commit();
                break;

            case R.id.navigationProfile:

                ProfileFragment fragment5 = new ProfileFragment();
                getActivity().setContentView(R.layout.fullyblanklayout);
                fragment5.setArguments(bundle);
                FragmentTransaction fragmentTransaction5 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction5.replace(R.id.main_biglayout,fragment5);
                fragmentTransaction5.commit();
                break;
        }

    }
}
