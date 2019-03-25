package com.example.a533.cours5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        setListeners();
    }

    private void setListeners() {
        findViewById(R.id.button_logIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    private void logIn(){
        EditText email = findViewById(R.id.editText_user_email);
        EditText password = findViewById(R.id.editText_password_login);
        auth.signInWithEmailAndPassword(email.getText().toString(),
                password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendToMainActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid input, check your username and password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendToMainActivity(){
        Intent moveToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(moveToMainActivity);
    }
}
