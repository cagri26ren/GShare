package com.example.gshare;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gshare.ModelClasses.ChatModel.Chat;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.User.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DBHelper {
    public static User userToReturn;

    private FirebaseDatabase ref;
    private DatabaseReference refUser;
    private DatabaseReference refNotices;
    private DatabaseReference refChats;

    private User user;
    private Chat chat;
    private ArrayList<Notice> allNotices;
    private ArrayList<Notice> allUserNotices;
    private ArrayList<Chat> allUserChats;
    private Notice notice;

    public DBHelper(){
        ref = FirebaseDatabase.getInstance();
        refUser = ref.getReference("users");
        refNotices = ref.getReference("notices");
        refChats = ref.getReference("chats");

        allNotices = new ArrayList<>();
        allUserNotices = new ArrayList<>();
        allUserChats = new ArrayList<>();
    }

    public void getUser( final String email , final DataStatus dataStatus ){
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = null;
                for( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    User userIn = keyNode.getValue(User.class);
                    if( userIn.getEmail().equals(email) ){
                        user = userIn;
                    }
                }
                dataStatus.dataIsLoaded( user );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAllNotices( final DataStatus dataStatus ){
        refNotices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allNotices.clear();
                for( DataSnapshot keyNode : dataSnapshot.getChildren() ){
                    Notice notice = keyNode.getValue( Notice.class );
                    allNotices.add( notice );
                }
                dataStatus.dataIsLoaded( allNotices );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAllUserNotices( final String email ,final DataStatus dataStatus ){
        refNotices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allUserNotices.clear();
                for( DataSnapshot keyNode : dataSnapshot.getChildren() ){
                    Notice notice = keyNode.getValue( Notice.class );
                    if( notice.getNoticeOwner().getEmail().equals(email)) {
                        allUserNotices.add(notice);
                    }
                }
                dataStatus.dataIsLoaded( allUserNotices );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getAllUserChats( final String email ,final DataStatus dataStatus ){
        refChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allUserChats.clear();
                for( DataSnapshot keyNode : dataSnapshot.getChildren() ){
                    Chat chat = keyNode.getValue( Chat.class );
                    if( chat.getNoticeOwner().getEmail().equals(email) || chat.getCustomer().getEmail().equals(email)) {
                        allUserChats.add(chat);
                    }
                }
                dataStatus.dataIsLoaded( allUserChats );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getNoticeById (final String noticeId , final DataStatus dataStatus ){
        refNotices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notice = null;
                for( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Notice noticeIn = keyNode.getValue(Notice.class);
                    if( noticeIn.getId().equals(noticeId) ){
                        notice = noticeIn;
                    }
                }
                dataStatus.dataIsLoaded( notice );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getChatById (final String chatId , final DataStatus dataStatus ){
        refChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chat = null;
                for( DataSnapshot keyNode : dataSnapshot.getChildren()){
                    Chat chatIn = keyNode.getValue(Chat.class);
                    if( chatIn.getChatId().equals(chatId) ){
                        chat = chatIn;
                    }
                }
                dataStatus.dataIsLoaded( chat );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static void updateUser( User user ){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getId());
        databaseReference.setValue(user);
    }
    public static void updateNotice( Notice notice ){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notices").child(notice.getId());
        databaseReference.setValue(notice);
    }

    public static void deleteNotice( String noticeId ){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("notices").child(noticeId);
        databaseReference.removeValue();
    }
    public static void updateChat( Chat chat ){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chats").child(chat.getChatId());
        databaseReference.setValue(chat);
    }

    public interface DataStatus{
        void dataIsLoaded( Object data  );
        void dataIsInserted();
        void dataIsUpdated();
        void dataIsDeleted();
    }


}
