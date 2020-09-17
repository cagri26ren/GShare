package com.example.gshare.Notice;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.DBHelper;
import com.example.gshare.HomePageFragment;
import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.Sort.Sort;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class CreatePurpleNoticeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;
    private StorageReference mStorageRef;

    EditText itemName;
    EditText day;
    EditText note;
    Button addNotice;
    ImageButton addImage;
    ImageButton back;
    DatabaseReference databaseNotices;
    User currentUser;

   Notice notice = null;

    int category;

    //Category Buttons
    ImageButton category1;
    ImageButton category2;
    ImageButton category3;
    ImageButton category4;
    ImageButton category5;
    ImageButton category6;
    ImageButton category7;
    ImageButton category8;

    String email;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_createnoticepurple, container,false);
        itemName = view.findViewById(R.id.itemNameinput);
        day = view.findViewById(R.id.daysinput);
        note = view.findViewById(R.id.noteinput);
        addImage = view.findViewById(R.id.imageButton2);

        addNotice = view.findViewById(R.id.PublishButtonPurple);
        back = view.findViewById(R.id.backButton);
        addNotice.setOnClickListener(this);
        back.setOnClickListener(this);

        email = getArguments().getString("email");

        currentUser = DBHelper.userToReturn;
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");


        //Category buttons initialized
        category1 = (ImageButton) view.findViewById(R.id.transportButton);
        category2 = (ImageButton) view.findViewById(R.id.schoolButton);
        category3 = (ImageButton) view.findViewById(R.id.laptopButton);
        category4 = (ImageButton) view.findViewById(R.id.cameraButton);
        category5 = (ImageButton) view.findViewById(R.id.stationaryButton);
        category6 = (ImageButton) view.findViewById(R.id.petsButton);
        category7 = (ImageButton) view.findViewById(R.id.booksButton);
        category8 = (ImageButton) view.findViewById(R.id.othersButton);

        //Initialize the catgory as OTHER in case the user doesnt choose.
        category = Sort.OTHER;

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

        databaseNotices = FirebaseDatabase.getInstance().getReference("notices");

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        return view;

    }


    @Override
    public void onClick(View v) {//FIX AT FINAL
        Bundle bundle = getArguments();
        if ( v.getId() == R.id.PublishButtonPurple ) {
           try {
               notice = new Notice(itemName.getText().toString(), Integer.parseInt(day.getText().toString()), note.getText().toString(), category,
                      currentUser, new LocationG(),"");
               try{
                   addNotice( notice );
               }
               catch (Exception e ){
                   e.printStackTrace();
               }
            }
           catch(Exception e){
                Toast.makeText(getActivity(),"Wrong values please try again", Toast.LENGTH_LONG).show();
           }
           uploadFile();
           DBHelper.updateNotice(notice);
        }

        HomePageFragment homePageFragment = new HomePageFragment();
        getActivity().setContentView(R.layout.blank_layout);
        homePageFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_layout,homePageFragment);
        fragmentTransaction.commit();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);
    }
    public void addNotice( Notice notice ){
        String id = databaseNotices.push().getKey();
        notice.setId(id);
        databaseNotices.child(id).setValue( notice );
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



