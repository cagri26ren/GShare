package com.example.gshare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.gshare.ModelClasses.User.User;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin;
    Button buttonRegister;
    EditText e_email;
    EditText password;
    TextView err;
    FirebaseDatabase database;
    DatabaseReference users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = (Button) findViewById(R.id.regloginButton);
        buttonRegister = (Button) findViewById(R.id.regregisterButton);
        e_email = (EditText) findViewById(R.id.loginUserName);
        password = (EditText) findViewById(R.id.loginPassword);
        err = (TextView) findViewById(R.id.err);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_email = e_email.getText().toString();
                String s_password = password.getText().toString();
                loginOnClick( s_email , s_password);
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });

    }

    public void loginOnClick( final String email , final String password){

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean check = false;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User login = postSnapshot.getValue(User.class);
                    if( login.getPassword().equals(password) && login.getEmail().equals(email)){
                        Toast.makeText(MainActivity.this,"Login is successful",Toast.LENGTH_LONG).show();
                        check = true;
                        DBHelper.userToReturn = login;
                        openHomePage();
                    }
                }
                if(!check){
                    Toast.makeText(MainActivity.this, "Wrong email or password. Please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void openRegister(){
        Intent intent = new Intent( this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openHomePage(){

        String email = e_email.getText().toString();
        Intent intent = new Intent( this, HomePageActivity.class);
        intent.putExtra( "email" , email );
        startActivity(intent);


    }
    /**NO NEED FOR NOW
    private static String fixEmailFormat( String email ){
        for( int i = 0; i < email.length(); i++ ){
            if( email.charAt(i) == '.'){
                email = email.substring(0,i) + "1" + email.substring( i + 1 );
            }
        }
        return email;
    }*/


}
