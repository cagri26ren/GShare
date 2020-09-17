package com.example.gshare.Notice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.Chat.ChatNotAgreedFragment;
import com.example.gshare.DBHelper;
import com.example.gshare.HomePageFragment;
import com.example.gshare.ModelClasses.ChatModel.Chat;
import com.example.gshare.ModelClasses.ChatModel.ChatCollection;
import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.Sort.Sort;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.Profile.ProfilePublicFragment;
import com.example.gshare.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Fix the DBHElper parts when it is added
 */
public class ContactPurpleNoticeFragment extends Fragment implements View.OnClickListener {

    Notice notice;
    User user;
    User noticeOwner;
    ArrayList<Chat> chatsArrayList;

    TextView title;
    TextView days;
    TextView note;
    Button contactButton;
    Button goToUser;
    ImageButton backButton;
    ImageView addImage;
    ImageView categoryImageView;

    String email;
    String noticeId;
    String userEmail;

    DatabaseReference databaseChats;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noticecontactpurple, container, false);

        Bundle bundle = getArguments();
        Handler handler = new Handler();
        noticeId = bundle.getString("noticeId");
        email = bundle.getString("email");
        databaseChats = FirebaseDatabase.getInstance().getReference("chats");

        new DBHelper().getNoticeById(noticeId, new DBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(final Object data) {
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
        new DBHelper().getAllUserChats(email, new DBHelper.DataStatus() {
            @Override
            public void dataIsLoaded(Object data) {
                chatsArrayList = (ArrayList<Chat>)data;
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
                new DBHelper().getUser(notice.getNoticeOwner().getEmail(), new DBHelper.DataStatus() {
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


        categoryImageView = view.findViewById(R.id.categoryImageView);
        title = view.findViewById(R.id.noticeTitle);
        days = view.findViewById(R.id.noticedays);
        note = view.findViewById(R.id.noticenote);
        contactButton = view.findViewById(R.id.contactButton);
        backButton = view.findViewById(R.id.backButton);
        goToUser = view.findViewById(R.id.UserButton);
        addImage = view.findViewById(R.id.NoticePhoto);

        //Set the category picture according to the category of the notice
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notice.setNoticeOwner(noticeOwner);
                userEmail = notice.getNoticeOwner().getEmail();

                if(notice.getCategory() == Sort.TRANSPORT){
                    categoryImageView.setImageResource(R.drawable.ic_car_black_24dp);
                }
                else if (notice.getCategory() == Sort.EDUCATION){
                    categoryImageView.setImageResource(R.drawable.ic_school_black_24dp);
                }
                else if(notice.getCategory() == Sort.ELECTRONIC)
                    categoryImageView.setImageResource(R.drawable.ic_laptop_black_24dp);
                else if(notice.getCategory() == Sort.LECTURE_NOTES)
                    categoryImageView.setImageResource(R.drawable.ic_photo_camera_black_24dp);
                else if(notice.getCategory() == Sort.STATIONARY)
                    categoryImageView.setImageResource(R.drawable.ic_scissors_black_24dp);
                else if(notice.getCategory() == Sort.PET)
                    categoryImageView.setImageResource(R.drawable.ic_pets_black_24dp);
                else if(notice.getCategory() == Sort.BOOKS)
                    categoryImageView.setImageResource(R.drawable.bookiconforhome);
                else if(notice.getCategory() == Sort.OTHER)
                    categoryImageView.setImageResource(R.drawable.img_350691);

                title.setText(notice.getName());
                days.setText(notice.getDay() + "");
                note.setText(notice.getNote());
                goToUser.setText( "Go to " + notice.getNoticeOwner().getUserName() + " 's profile");
                Picasso.with(getActivity()).load(notice.getImageUrl()).fit().into(addImage);
                DBHelper.updateNotice(notice);
            }
        },200);

        contactButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        goToUser.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.contactButton ) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Chat chat = new Chat(notice, notice.getNoticeOwner(), user, "");
                    ChatCollection chatCollection = new ChatCollection(chatsArrayList);
                    if (chat.getCustomer().equals(chat.getNoticeOwner())) {
                        Toast.makeText(getContext(), "You can't contact yourself", Toast.LENGTH_LONG);
                    } else {

                        if (!chatCollection.getAllChat().contains(chat)) {
                            addChat(chat);
                            Bundle bundle = new Bundle();
                            bundle.putString("email", email);
                            bundle.putString("chatId", chat.getChatId());
                            ChatNotAgreedFragment fragmentChatNotAgreed = new ChatNotAgreedFragment();
                            getActivity().setContentView(R.layout.fullyblanklayout);
                            fragmentChatNotAgreed.setArguments(bundle);
                            FragmentTransaction fragmentManagerForNotAgreedChat = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentManagerForNotAgreedChat.replace(R.id.main_biglayout, fragmentChatNotAgreed);
                            fragmentManagerForNotAgreedChat.commit();
                        }
                    }
                }
            }, 100);
        }

        if (v.getId() == R.id.backButton){
            Bundle bundle2 = new Bundle();
            bundle2.putString("email", email);
            HomePageFragment homePageFragment = new HomePageFragment();
            homePageFragment.setArguments(bundle2);
            FragmentTransaction fragmentManagerForHomePage = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentManagerForHomePage.replace(R.id.main_layout, homePageFragment);
            fragmentManagerForHomePage.commit();
        }

        if( v.getId() == R.id.userButton){
            ProfilePublicFragment.comeWhere = ProfilePublicFragment.HOME_PAGE;
            Bundle bundle3 = new Bundle();
            bundle3.putString( "email",email);
            bundle3.putString("noticeId",noticeId);
            bundle3.putString("userEmail",userEmail);
            ProfilePublicFragment profilePublicFragment = new ProfilePublicFragment();
            getActivity().setContentView(R.layout.fullyblanklayout);
            profilePublicFragment.setArguments(bundle3);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_biglayout,profilePublicFragment);
            fragmentTransaction.commit();
        }
    }
    public void addChat( Chat chat ){
        String id = databaseChats.push().getKey();
        chat.setChatId(id);
        databaseChats.child(id).setValue(chat);
    }

}

