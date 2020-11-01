package com.example.smartdispenser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {
    EditText mUserName, mMobileNumber, mEmail, mPassword;
    Button mRegButton;
    TextView mLogButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUserName = findViewById(R.id.editText);
        mMobileNumber   = findViewById(R.id.editText2);
        mEmail    = findViewById(R.id.editText4);
        mPassword = findViewById(R.id.editText5);
        mRegButton  = findViewById(R.id.regbutton);
        mLogButton  = findViewById(R.id.textView10);

        fAuth   = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext() ,MainActivity.class));
            finish();
        }

        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }
                if(password.length() < 5){
                    mPassword.setError("Password must be >= 5 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //register the user with firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Signup.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext() ,Main2Activity.class));
                            progressBar.setVisibility(View.GONE);

                        }else{
                            Toast.makeText(Signup.this, "Error!"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        mLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext() ,Login.class));
            }
        });
    }
}
