package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private EditText username_txt,password_txt;
    private Button login_btn,register_btn;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username_txt=(EditText) findViewById(R.id.txt1);
        password_txt=(EditText) findViewById(R.id.txt2);
        login_btn=(Button) findViewById(R.id.btn1);
        register_btn=(Button) findViewById(R.id.btn2);

        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email=username_txt.getText().toString();
                String password=password_txt.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(MainActivity.this,"Please enter Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(MainActivity.this,"Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(MainActivity.this,HomePage.class); //add activity
                            startActivity(intent);
                        }else
                            Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();

                    }
                });

            }

        });

        register_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                OpenRegistration();
            }
        });
    }


    public void OpenRegistration()
    {
        Intent intent=new Intent(this,RegisterUser.class);
        startActivity(intent);
    }

}

