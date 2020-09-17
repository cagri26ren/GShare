package com.example.gshare.Popup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gshare.DBHelper;
import com.example.gshare.ModelClasses.Report;
import com.example.gshare.R;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PopupReport extends DialogFragment {

    String email;
    String chatId;

    EditText reportEdit;
    EditText reportedName;

    Button sendButton;

    DatabaseReference reportReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        email = getArguments().getString("email");
        if( getArguments().getString("chatId") != null ) {
            chatId = getArguments().getString("chatId");
        }
        else {
            chatId = "";
        }

        sendButton = view.findViewById(R.id.button2);
        reportEdit = view.findViewById(R.id.editText2);
        reportedName = view.findViewById(R.id.editText3);


        reportReference = FirebaseDatabase.getInstance().getReference("reports");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = reportReference.push().getKey();
                Report report = new Report( reportEdit.getText().toString() , chatId , reportedName.getText().toString() , email , id );
                reportReference.child(id).setValue(report);
                Toast.makeText(getActivity(), "Report sended!",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        return view;
    }


}
