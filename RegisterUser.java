package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterUser extends AppCompatActivity {
    EditText editemailtxt,editpasswordtxt,editrepasswordtxt;
    Button registerbutton;
    private FirebaseAuth mAuth;
    private ProgressDialog processDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        processDialog=new ProgressDialog(this);
        setContentView(R.layout.activity_register_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editemailtxt = (EditText) findViewById(R.id.addemail); //email
        editpasswordtxt = (EditText) findViewById(R.id.addpw);
        editrepasswordtxt = (EditText) findViewById(R.id.addrepw);
        registerbutton = (Button) findViewById(R.id.userbutton);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });

    }
    private void registeruser(){
        String email=editemailtxt.getText().toString();
        String password=editpasswordtxt.getText().toString();
        String repassword=editrepasswordtxt.getText().toString();
        if(email.isEmpty()){
           editemailtxt.setError("Email is Empty");
           editemailtxt.requestFocus();
           return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<8)
        {
            Toast.makeText(this,"Use atleast 8 characters", Toast.LENGTH_SHORT).show();
            return;
        }
        processDialog.setMessage("Registering.....");
        processDialog.show();
        if(password.equals(repassword)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                               Toast.makeText(RegisterUser.this,"Registration Successful",Toast.LENGTH_SHORT).show();

                            }else {
                                if(task.getException() instanceof FirebaseAuthUserCollisionException)
                                {
                                    Toast.makeText(RegisterUser.this, "You are Already Registered", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(RegisterUser.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
        }
    }

}
