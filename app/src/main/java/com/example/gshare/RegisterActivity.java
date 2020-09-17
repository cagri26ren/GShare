package com.example.gshare;

import com.example.gshare.ModelClasses.User.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonRegister;
    Button buttonLogin;
    EditText userName;
    EditText password;
    EditText email;
    TextView registerMessage;
    DatabaseReference databaseUsers;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonRegister = findViewById(R.id.regregisterButton);
        buttonLogin = findViewById(R.id.regloginButton);
        userName = findViewById(R.id.registerUsername);
        registerMessage = findViewById(R.id.registerMessage);
        password = findViewById(R.id.registerPassword);
        email = findViewById(R.id.registerEmail);


        buttonRegister.setOnClickListener( this );
        buttonLogin.setOnClickListener(this);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

    }

    public void register(){
        String s_email = email.getText().toString();
        String s_userName = userName.getText().toString();
        String s_password = password.getText().toString();


        String id = databaseUsers.push().getKey();
        User user = new User( s_userName , s_password , s_email , 0 , id );
        databaseUsers.child(id).setValue( user );
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.regregisterButton:
                if( password.getText().toString() != null && email.getText().toString().length() != 0 ){
                    register();
                    if(true){

                        openLogin();
                    }
                    else{
                        Toast.makeText( this, "Please try again, inappropriate information", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText( this, "Please try again, inappropriate information", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.regloginButton:
                openLogin();
                break;
        }
    }

    public void openLogin(){
        Intent intent = new Intent( this , MainActivity.class );
        startActivity(intent);
    }
}
