package com.example.gshare.Notice;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.Chat.ChatFragment;
import com.example.gshare.DBHelper;
import com.example.gshare.HomePageFragment;
import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.Sort.Sort;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.Profile.ProfileFragment;
import com.example.gshare.R;


import java.util.ArrayList;

import static com.example.gshare.HomePageFragment.BORROWING_MODE;

public class MyNoticesFragment extends Fragment implements View.OnClickListener {


    public final static char LENDING_MODE = 'L';
    public final static char BORROWING_MODE = 'B';
    public static char sortMode = LENDING_MODE;

    Context c;

    Button lendingMode;
    Button borrowingMode;
    TextView gView;

    ListView listView;
    MyNoticesAdapter adapter;
    Bundle bundle;

    String email;
    User user;
    ArrayList<Notice> notices;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_my_notices, container, false);


        bundle = getArguments();
        email = bundle.getString("email");

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

        listView = (ListView) view.findViewById(R.id.listmynotices);

        lendingMode = view.findViewById(R.id.lendingButton);
        borrowingMode = view.findViewById(R.id.borrowingButton);
        gView = view.findViewById(R.id.moneyTextView);

        ImageButton homeButton = (ImageButton)view.findViewById(R.id.navigationHome);
        ImageButton mapButton = (ImageButton)view.findViewById(R.id.navigationMap);
        ImageButton myNoticesButton = (ImageButton)view.findViewById(R.id.navigationMyNotices);
        ImageButton chatButton = (ImageButton)view.findViewById(R.id.navigationChat);
        ImageButton profileButton = (ImageButton)view.findViewById(R.id.navigationProfile);

        homeButton.setOnClickListener(this);
        mapButton.setOnClickListener(this);
        myNoticesButton.setOnClickListener(this);
        chatButton.setOnClickListener(this);
        profileButton.setOnClickListener(this);
        lendingMode.setOnClickListener(this);
        borrowingMode.setOnClickListener(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gView.setText(user.getG() + "");
            }
        }, 100);

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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notices = Sort.removeOvers(notices);
                notices = Sort.sortByPostTime(notices);
                if( sortMode == BORROWING_MODE){
                    notices = Sort.getBorrowings(notices);
                }
                else{
                    notices = Sort.getLendings(notices);
                }
            }
        },100);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
        adapter = new MyNoticesAdapter(c, notices);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if(notices.get(position).getNoticeType() == Notice.LEND_NOTICE){
                   NoticeViewLending fragment = new NoticeViewLending();
                   bundle.putString("email",email);
                   bundle.putString("noticeId",notices.get(position).getId());
                    getActivity().setContentView(R.layout.fullyblanklayout);
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_biglayout,fragment);
                   transaction.commit();
               } else{
                    NoticeViewBorrowing fragment = new NoticeViewBorrowing();
                    bundle.putString("email",email);
                    bundle.putString("noticeId",notices.get(position).getId());
                    getActivity().setContentView(R.layout.fullyblanklayout);
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_biglayout,fragment);
                    transaction.commit();
                }
            }
        });
            }
        },100);

        return view;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("email", email);

        switch (v.getId()) {
            case R.id.lendingButton:
                sortMode = LENDING_MODE;
                refreshPage();
                break;
            case R.id.borrowingButton:
                sortMode = BORROWING_MODE;
                refreshPage();
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

    @Override
    public void onAttach (Context con){
        super.onAttach(con);
        c = con;
    }
    public void refreshPage(){
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        MyNoticesFragment myNoticesFragment = new MyNoticesFragment();
        getActivity().setContentView(R.layout.fullyblanklayout);
        myNoticesFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_biglayout,myNoticesFragment);
        fragmentTransaction.commit();
    }

}