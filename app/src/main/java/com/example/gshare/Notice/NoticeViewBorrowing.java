package com.example.gshare.Notice;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.DBHelper;
import com.example.gshare.ModelClasses.Location.LocationG;
import com.example.gshare.ModelClasses.NoticeModel.Notice;
import com.example.gshare.ModelClasses.Sort.Sort;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.R;
import com.squareup.picasso.Picasso;

public class NoticeViewBorrowing extends Fragment implements View.OnClickListener {

    Notice notice;
    String noticeId;

    EditText name;
    EditText days;
    EditText note;
    Button editNoticeButton;
    ImageButton backButton;
    ImageView categoryImageView;
    ImageButton image;

    String email;
    boolean showable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_notice_view_borrowing, container, false);

       Bundle bundle = getArguments();
       email = bundle.getString("email");
       noticeId = bundle.getString("noticeId");

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

        //Initialize components
        categoryImageView = view.findViewById(R.id.categoryPicImageView);
        name = view.findViewById(R.id.itemNameinput);
        days = view.findViewById(R.id.daysinput);
       note = view.findViewById(R.id.noteinput);
       editNoticeButton = view.findViewById(R.id.EditNoticeBorrowing);
       backButton = view.findViewById(R.id.backButton);
       image = view.findViewById(R.id.imageButton2);

       editNoticeButton.setOnClickListener(this);
       backButton.setOnClickListener(this);

       Handler handler = new Handler();

       handler.postDelayed(new Runnable() {
           @Override
           public void run() {

        //Set the category picture according to the category of the notice
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

       name.setText(notice.getName());
       days.setText(notice.getDay()+"");
       note.setText(notice.getNote());
       showable = notice.isShowable();
       Picasso.with( getActivity() ).load( notice.getImageUrl() ).into( image );
           }
       },100);

       return view;

    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch ( v.getId() ){
            case R.id.EditNoticeBorrowing:
                if( showable ) {
                    bundle.putString("email", email);
                    bundle.putString("noticeId", noticeId);
                    getActivity().setContentView(R.layout.fullyblanklayout);
                    NoticeEditBorrowingFragment noticeEditBorrowingFragment = new NoticeEditBorrowingFragment();
                    noticeEditBorrowingFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.main_biglayout, noticeEditBorrowingFragment);
                    fragmentTransaction1.commit();
                }
                break;
            case R.id.backButton:
                bundle.putString("email",email);
                bundle.putString("noticeId",noticeId);
                getActivity().setContentView(R.layout.fullyblanklayout);
                MyNoticesFragment myNoticesFragment = new MyNoticesFragment();
                myNoticesFragment.setArguments( bundle );
                FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.main_biglayout,myNoticesFragment);
                fragmentTransaction2.commit();
                break;
        }
    }

}
