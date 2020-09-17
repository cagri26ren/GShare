package com.example.gshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.Chat.ChatFragment;

import com.example.gshare.Notice.ListViewAdapter;
import com.example.gshare.Notice.MyNoticesFragment;

import com.example.gshare.Profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This class is not final and will be changed by ONUR
 */
public class HomePageActivity extends AppCompatActivity {

    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        email = getIntent().getStringExtra("email");
        setContentView(R.layout.blank_layout);
        HomePageFragment fragmentForBeginning = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        fragmentForBeginning.setArguments(bundle);
        FragmentTransaction fragmentTransactionForBeginning = getSupportFragmentManager().beginTransaction();
        fragmentTransactionForBeginning.replace(R.id.main_layout, fragmentForBeginning);
        fragmentTransactionForBeginning.commit();

    }


}

