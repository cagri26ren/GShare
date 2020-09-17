package com.example.gshare.Profile;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gshare.DBHelper;
import com.example.gshare.ModelClasses.User.User;
import com.example.gshare.R;

public class ProfileEditFragment extends DialogFragment implements View.OnClickListener {

    EditText editName;
    EditText oldPass;
    EditText newPass;
    Button applyButton;
    Button passwordButton;
    User user;

    String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        applyButton = view.findViewById(R.id.changeNameButton);
        editName = view.findViewById(R.id.changeNameEditText);
        passwordButton = view.findViewById(R.id.changePasswordButton);
        oldPass = view.findViewById(R.id.oldPasswordEditText);
        newPass = view.findViewById(R.id.newPasswordEditText);

        email = getArguments().getString("email");

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
        applyButton.setOnClickListener(this);
        passwordButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
            if( v.getId() == R.id.changeNameButton ) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        user.setUserName(editName.getText().toString());
                        DBHelper.updateUser(user);
                        getDialog().dismiss();
                    }
                }, 100);

        }
            if( v.getId() == R.id.changePasswordButton ){
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if( user.getPassword().equals( oldPass.getText().toString() )){
                            if( newPass.getText().toString() != "" ) {
                                user.setPassword(newPass.getText().toString());
                                DBHelper.updateUser(user);
                                getDialog().dismiss();
                            }
                        }
                    }
                },100);
            }

    }

}
