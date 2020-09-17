package com.example.gshare.Popup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gshare.Chat.ChatAgreedFragment;
import com.example.gshare.HomePageFragment;
import com.example.gshare.Notice.CreateYellowNoticeFragment;
import com.example.gshare.R;

public class PopupSortByFragment extends DialogFragment implements View.OnClickListener {
    CheckBox gCheckBox;
    CheckBox newestFirstCheckBox;
    CheckBox alphabeticalCheckBox;
    EditText minGText;
    EditText maxGText;
    Button applyButton;

    String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popup_sort_by, container, false);

        email = getArguments().getString("email" );

        gCheckBox = view.findViewById(R.id.gIntervalCheckBox);
        newestFirstCheckBox = view.findViewById(R.id.newestFirstCheckBox);
        alphabeticalCheckBox = view.findViewById(R.id.alphabeticallyCheckBox);
        minGText = view.findViewById(R.id.minGEditText);
        maxGText = view.findViewById(R.id.maxGEditText);
        applyButton = view.findViewById(R.id.applySortButton);

        applyButton.setOnClickListener(this);

        HomePageFragment.accepted = false;

        return view;
    }

    @Override
    public void onClick(View v) {

        switch ( v.getId() ){
            case R.id.applySortButton:
                int min = 0;
                int max = 0;
                boolean g = false;
                boolean newest = false;
                boolean alphabetical = false;

                if( gCheckBox.isChecked() ){
                    try {
                         min = Integer.parseInt(minGText.getText().toString());
                         max = Integer.parseInt(maxGText.getText().toString());
                         g = true;
                    }
                    catch( Exception e ){
                        Toast.makeText(getActivity(),"Wrong values please try again", Toast.LENGTH_SHORT).show();
                        minGText.setText("0");
                        maxGText.setText("0");
                    }

                }
                if( newestFirstCheckBox.isChecked() ){
                    newest = true;
                }
                else{
                    newest = false;
                }
                if( alphabeticalCheckBox.isChecked() ){
                    alphabetical = true;
                }
                else{
                    alphabetical = false;
                }

                Bundle bundle = new Bundle();
                HomePageFragment.g = g;
                HomePageFragment.newest = newest;
                HomePageFragment.accepted = true;
                HomePageFragment.alphabetical = alphabetical;
                HomePageFragment.min = min;
                HomePageFragment.max = max;
                bundle.putString( "email", email );

                HomePageFragment homePageFragment = new HomePageFragment();
                getActivity().setContentView(R.layout.fullyblanklayout);
                homePageFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_biglayout,homePageFragment);
                fragmentTransaction.commit();
                getDialog().dismiss();
        }
    }
}
