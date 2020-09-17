package com.example.gshare.Profile;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.Chat.ChatFragment;
import com.example.gshare.DBHelper;
import com.example.gshare.HomePageFragment;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.R;

import java.util.ArrayList;

public class ProfilePublicFragment extends Fragment {

    public static final int HOME_PAGE = 0;
    public static final int CHAT = 1;

    public static int comeWhere = 0;

    ImageButton backButton;
    TextView name;
    TextView username;
    TextView g;
    User ownerUser;
    User user;
    ArrayList<Notice> notices;
    String email;
    String userEmail;

    ListView listView;
    ProfileTransactionAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_public, container, false);
        Bundle bundle = getArguments();

        userEmail = bundle.getString("userEmail");
        email = bundle.getString("email");

        Handler handler = new Handler();
        new DBHelper().getUser(email , new DBHelper.DataStatus() {
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

        new DBHelper().getUser(userEmail , new DBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(Object data) {
                ownerUser = (User)data;
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

        new DBHelper().getAllUserNotices(userEmail,new DBHelper.DataStatus() {
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

        backButton = (ImageButton) view.findViewById(R.id.backButton);
        name = (TextView) view.findViewById(R.id.nameTextView);
        username = (TextView) view.findViewById(R.id.usernameTextView);
        g = view.findViewById(R.id.moneyTextView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                if( comeWhere == HOME_PAGE ){
                    bundle.putString("email",email);
                    getActivity().setContentView(R.layout.blank_layout);
                    HomePageFragment homePageFragment = new HomePageFragment();
                    homePageFragment.setArguments(bundle);
                    FragmentTransaction fragmentManagerForHomePage = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentManagerForHomePage.replace(R.id.main_layout, homePageFragment);
                    fragmentManagerForHomePage.commit();
                }
                else{
                    bundle.putString("email",email);
                    getActivity().setContentView(R.layout.blank_layout);
                    ChatFragment chatFragment = new ChatFragment();
                    chatFragment.setArguments(bundle);
                    FragmentTransaction fragmentManagerForHomePage = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentManagerForHomePage.replace(R.id.main_layout, chatFragment);
                    fragmentManagerForHomePage.commit();
                }
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                name.setText(ownerUser.getUserName());
                username.setText(ownerUser.getEmail());
                g.setText( user.getG() + "");
            }
        },100);

        listView = (ListView) view.findViewById(R.id.publicProfileList);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new ProfileTransactionAdapter( getContext(),notices );
                listView.setAdapter(adapter);
            }
        },100);


        return view;
    }
}
