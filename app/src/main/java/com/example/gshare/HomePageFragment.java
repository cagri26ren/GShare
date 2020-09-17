package com.example.gshare;

import android.app.Activity;

import android.content.Context;


import android.content.res.ColorStateList;

import android.graphics.Typeface;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.SearchView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.gshare.Chat.ChatFragment;
import com.example.gshare.ModelClasses.Location.LocationG;


import java.io.IOException;
import java.util.ArrayList;


import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.Sort.Sort;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.Notice.ContactPurpleNoticeFragment;
import com.example.gshare.Notice.ContactYellowNoticeFragment;
import com.example.gshare.Notice.CreatePurpleNoticeFragment;
import com.example.gshare.Notice.CreateYellowNoticeFragment;
import com.example.gshare.Notice.ListViewAdapter;
import com.example.gshare.Notice.MyNoticesFragment;
import com.example.gshare.Popup.PopupSortByFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.gshare.Profile.ProfileFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import android.graphics.Color;

public class HomePageFragment extends Fragment implements View.OnClickListener {

    public final static char LENDING_MODE = 'L';
    public final static char BORROWING_MODE = 'B';

    //THIS PART IS FOR SORTING ONLY
    public static boolean accepted = false;
    public static boolean newest = false;
    public static boolean alphabetical = false;
    public static boolean g = false;
    public static int min;
    public static int max;
    public static int categorySort = -1;


    User user;
    TextView gText;
    FloatingActionButton addNoticeButton;
    Button lendModeButton;
    Button borrowModeButton;
    Button button;
    ImageButton sortButton;
    String email;
    ArrayList<Notice> notices;

    ImageButton home;
    ImageButton map;
    ImageButton noticeNav;
    ImageButton chat;
    ImageButton profile;

    static char sortMode = LENDING_MODE;

    ListView listView = null;
    Context c = null;

    ListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page,container,false);


        email = getArguments().getString("email");
        Bundle bundle = getArguments();

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

        sortButton = view.findViewById(R.id.sortby_button);
        lendModeButton = view.findViewById(R.id.lendingSortiButton);
        borrowModeButton = view.findViewById(R.id.borrowingSortButton);
        addNoticeButton = view.findViewById(R.id.addNoticeButton);
        gText = view.findViewById(R.id.moneyTextView);

        home = view.findViewById(R.id.navigationHome);
        map = view.findViewById(R.id.navigationMap);
        noticeNav = view.findViewById(R.id.navigationMyNotices);
        chat = view.findViewById(R.id.navigationChat);
        profile = view.findViewById(R.id.navigationProfile);


        addNoticeButton.setOnClickListener(this);
        borrowModeButton.setOnClickListener(this);
        lendModeButton.setOnClickListener(this);
        sortButton.setOnClickListener(this);

        home.setOnClickListener(this);
        map.setOnClickListener(this);
        noticeNav.setOnClickListener(this);
        chat.setOnClickListener(this);
        profile.setOnClickListener(this);


        // just setting colors and fonts
        if( sortMode == BORROWING_MODE ) {
            lendModeButton.setBackgroundColor(Color.parseColor("#FFFF00"));
            borrowModeButton.setBackgroundColor(Color.parseColor("#B201D8"));
            addNoticeButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF681F")));
            lendModeButton.setTypeface(null, Typeface.BOLD_ITALIC);
            borrowModeButton.setTypeface(null, Typeface.NORMAL);
        }
        else{
            borrowModeButton.setBackgroundColor(Color.parseColor("#F000FF"));
            lendModeButton.setBackgroundColor(Color.parseColor("#FF9800") );
            addNoticeButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#492A4B")));
            lendModeButton.setTypeface(null, Typeface.NORMAL);
            borrowModeButton.setTypeface(null , Typeface.BOLD_ITALIC);
        }

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                gText.setText( user.getG() + "" );
            }
        },300);

        notices = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.list);
        new DBHelper().getAllNotices(new DBHelper.DataStatus() {
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

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notices = Sort.getShowables(notices);
                notices = Sort.randomize(notices);
                if( sortMode == BORROWING_MODE){
                    notices = Sort.getBorrowings(notices);
                }
                else{
                    notices = Sort.getLendings(notices);
                }
                if( accepted ){
                    notices = sort( notices , accepted, g,min,max,newest,alphabetical);
                    accepted = false;
                }
            }
        },100);

        /**
         * THIS PART IS ONLY FOR QUICK SORTING!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */
        ImageButton buttonTransport = (ImageButton) view.findViewById(R.id.transportButton);
        buttonTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                categorySort = Sort.TRANSPORT;
                refreshPage();
            }
        });
        ImageButton buttonEducation = (ImageButton) view.findViewById(R.id.schoolButton);
        buttonEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                categorySort = Sort.EDUCATION;
                refreshPage();
            }
        });
        ImageButton buttonElectronics = (ImageButton) view.findViewById(R.id.laptopButton);
        buttonElectronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                categorySort = Sort.ELECTRONIC;
                refreshPage();
            }
        });
        //Button should be changed later
        ImageButton buttonCamera = (ImageButton) view.findViewById(R.id.cameraButton);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                categorySort = Sort.OTHER;
                refreshPage();
            }
        });
        ImageButton buttonStationary = (ImageButton) view.findViewById(R.id.stationaryButton);
        buttonStationary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                categorySort = Sort.STATIONARY;
                refreshPage();
            }
        });
        ImageButton buttonPets = (ImageButton) view.findViewById(R.id.petsButton);
        buttonPets.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                categorySort = Sort.PET;
                refreshPage();
            }
        });
        //button should be changed later
        ImageButton buttonSports = (ImageButton) view.findViewById(R.id.othersButton);
        buttonSports.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                categorySort = Sort.ALL;
                refreshPage();
            }
        });
        if (categorySort == Sort.PET) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notices = Sort.sortByCategory(notices, Sort.PET);
                    categorySort = -1;
                }
            }, 100);
        }
        if (categorySort == Sort.ALL) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notices = Sort.sortByCategory(notices, Sort.ALL);
                    categorySort = -1;
                }
            }, 100);
        }
        if (categorySort == Sort.STATIONARY) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notices = Sort.sortByCategory(notices, Sort.STATIONARY);
                    categorySort = -1;
                }
            }, 100);
        }
        if (categorySort == Sort.OTHER) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notices = Sort.sortByCategory(notices, Sort.OTHER);
                    categorySort = -1;
                }
            }, 100);
        }
        if (categorySort == Sort.ELECTRONIC) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notices = Sort.sortByCategory(notices, Sort.ELECTRONIC);
                    categorySort = -1;
                }
            }, 100);
        }
        if (categorySort == Sort.TRANSPORT) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notices = Sort.sortByCategory(notices, Sort.TRANSPORT);
                    categorySort = -1;
                }
            }, 100);
        }
        if (categorySort == Sort.EDUCATION) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notices = Sort.sortByCategory(notices, Sort.EDUCATION);
                    categorySort = -1;
                }
            }, 100);
        }
        /**
         * ENDS HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new ListViewAdapter(c,  notices);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle args = new Bundle();
                        args.putString("noticeId",notices.get(position).getId());
                        args.putString("email",email);
                        getActivity().setContentView(R.layout.blank_layout);
                        if( sortMode == LENDING_MODE ) {
                            ContactYellowNoticeFragment fragmentForLending = new ContactYellowNoticeFragment();
                            fragmentForLending.setArguments(args);
                            FragmentTransaction fragmentTransactionForLending = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransactionForLending.replace(R.id.main_layout, fragmentForLending);
                            fragmentTransactionForLending.commit();
                        }
                        else{
                            ContactPurpleNoticeFragment fragmentForBorrowing = new ContactPurpleNoticeFragment();
                            fragmentForBorrowing.setArguments(args);
                            FragmentTransaction fragmentTransactionForLending = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransactionForLending.replace(R.id.main_layout, fragmentForBorrowing);
                            fragmentTransactionForLending.commit();
                        }
                    }
                });
            }

        }, 100);

        SearchView searchView = (SearchView) view.findViewById(R.id.homepageSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                return true;
            }

            public void callSearch(String query) {

                listView.setAdapter(adapter.update(notices));

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context con){
        super.onAttach(con);
        c = con;}

    @Override
    public void onClick(View v ){
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        switch ( v.getId() ){
            case R.id.addNoticeButton:
                if( sortMode == LENDING_MODE ){
                    CreateYellowNoticeFragment fragmentLend = new CreateYellowNoticeFragment();
                    getActivity().setContentView(R.layout.fullyblanklayout);
                    fragmentLend.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace( R.id.main_biglayout,fragmentLend);
                    fragmentTransaction.commit();
                }
                if( sortMode == BORROWING_MODE ){
                    CreatePurpleNoticeFragment fragmentBorrow = new CreatePurpleNoticeFragment();
                    getActivity().setContentView(R.layout.fullyblanklayout);
                    fragmentBorrow.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_biglayout,fragmentBorrow);
                    fragmentTransaction.commit();
                }
                break;

            case R.id.sortby_button:
                PopupSortByFragment popupSortByFragment = new PopupSortByFragment();
                popupSortByFragment.setArguments(bundle);
                popupSortByFragment.show( getFragmentManager(), "SortPopUp");
                break;

            case R.id.borrowingSortButton:
                sortMode = BORROWING_MODE;
                refreshPage();
                break;

            case R.id.lendingSortiButton:
                sortMode = LENDING_MODE;
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

    public ArrayList<Notice> sort( final ArrayList<Notice> list , final boolean accepted , final boolean g, final int min, final int max, final boolean newest, final boolean alphabetical ){

            ArrayList<Notice> notices = list;

            if( accepted ){
            if(sortMode==LENDING_MODE) {
                if (g) {
                    notices = Sort.sortByGInterval(notices, min, max);
                }
            }
            if(newest){
                notices = Sort.sortByPostTime(notices);
            }
            if(alphabetical){
                notices = Sort.sortByLexiography(notices);
            }
        }

        return notices;
    }

    public void refreshPage(){
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        HomePageFragment homePageFragment = new HomePageFragment();
        getActivity().setContentView(R.layout.fullyblanklayout);
        homePageFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_biglayout,homePageFragment);
        fragmentTransaction.commit();
    }




}