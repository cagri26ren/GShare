package com.example.gshare.Notice;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.DBHelper;
import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.Sort.Sort;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.Popup.PopupConfirmRemove;
import com.example.gshare.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NoticeEditLendingFragment extends Fragment implements View.OnClickListener{
    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;
    private StorageReference mStorageRef;

    Notice notice;
    String noticeId;
    int category;

    EditText name;
    EditText day;
    EditText g;
    EditText note;
    Button applyButton;
    ImageButton backButton;
    ImageButton deleteButton;
    ImageButton addImage;
    String email;

    //Category Buttons
    ImageButton category1;
    ImageButton category2;
    ImageButton category3;
    ImageButton category4;
    ImageButton category5;
    ImageButton category6;
    ImageButton category7;
    ImageButton category8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_edit_lending, container, false);

        Bundle bundle = getArguments();

        noticeId = bundle.getString("noticeId");
        email = bundle.getString("email");

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
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");


        name = view.findViewById(R.id.itemNameinput);
        day = view.findViewById(R.id.daysinput);
        g = view.findViewById(R.id.ginput);
        note = view.findViewById(R.id.noteinput);
        applyButton = view.findViewById(R.id.ApplyEditNoticeLending);
        backButton = view.findViewById(R.id.backButton);
        deleteButton = view.findViewById(R.id.imageButtonDelete2);
        addImage = view.findViewById(R.id.imageButton2);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
        name.setText(notice.getName());
        day.setText(notice.getDay() + "");
        g.setText(notice.getG()+"");
        note.setText(notice.getNote());
        category = notice.getCategory();

        Picasso.with(getActivity()).load(notice.getImageUrl()).fit().into(addImage);
            }
        },100);

        applyButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

        //Category buttons initialized
        category1 = (ImageButton) view.findViewById(R.id.transportButton);
        category2 = (ImageButton) view.findViewById(R.id.schoolButton);
        category3 = (ImageButton) view.findViewById(R.id.laptopButton);
        category4 = (ImageButton) view.findViewById(R.id.cameraButton);
        category5 = (ImageButton) view.findViewById(R.id.stationaryButton);
        category6 = (ImageButton) view.findViewById(R.id.petsButton);
        category7 = (ImageButton) view.findViewById(R.id.booksButton);
        category8 = (ImageButton) view.findViewById(R.id.othersButton);

        //Set click listeners for the category buttons
        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = Sort.TRANSPORT;
            }
        });
        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = Sort.EDUCATION;
            }
        });
        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = Sort.ELECTRONIC;
            }
        });
        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = Sort.LECTURE_NOTES;
            }
        });
        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = Sort.STATIONARY;
            }
        });
        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = Sort.PET;
            }
        });
        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = Sort.BOOKS;
            }
        });
        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = Sort.OTHER;
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()== R.id.ApplyEditNoticeLending) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
            notice.setG(Integer.parseInt(g.getText().toString()));//Only here usage is allowed
            notice.setDay(Integer.parseInt(day.getText().toString()));
            notice.setName(name.getText().toString());
            notice.setNote(note.getText().toString());
            notice.setCategory(category);
            DBHelper.updateNotice(notice);
                }
            },100);

            Bundle bundle = new Bundle();
            bundle.putString("email",email);
            bundle.putString("noticeId",noticeId);
            NoticeViewLending noticeViewLending = new NoticeViewLending();
            getActivity().setContentView(R.layout.fullyblanklayout);
            noticeViewLending.setArguments( bundle );
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_biglayout,noticeViewLending);
            fragmentTransaction.commit();
        }

        else if( v.getId() == R.id.backButton) {
            Bundle bundle = new Bundle();
            bundle.putString("email", email);
            bundle.putString("noticeId", noticeId);
            NoticeViewLending noticeViewLending = new NoticeViewLending();
            getActivity().setContentView(R.layout.fullyblanklayout);
            noticeViewLending.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_biglayout, noticeViewLending);
            fragmentTransaction.commit();
        }

        else if( v.getId() == R.id.imageButtonDelete2){
            Bundle bundle = new Bundle();
            bundle.putString("email", email);
            bundle.putString("noticeId", noticeId);

            PopupConfirmRemove popupRemoveByFragment = new PopupConfirmRemove();
            popupRemoveByFragment.setArguments(bundle);
            popupRemoveByFragment.show( getFragmentManager(), "RemovePopUp");
        }
    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType( "image/*" );
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( PICK_IMAGE_REQUEST == requestCode && resultCode == RESULT_OK && data != null && data.getData() != null ){
            mImageUri = data.getData();

            Picasso.with( getActivity() ).load( mImageUri ).into( addImage );
        }
    }

    private void uploadFile(){
        if(mImageUri != null){
            final StorageReference fileReference = mStorageRef.child( System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText( getActivity() , "Upload Succesfull" , Toast.LENGTH_LONG).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    notice.setImageUrl(uri.toString());
                                    DBHelper.updateNotice(notice);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText( getActivity() , e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        }
        else{
            Toast.makeText( getActivity() , "No file selected" , Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension( Uri uri ){
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType( cR.getType(uri));
    }
}
