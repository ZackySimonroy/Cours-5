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

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        setListener();
    }

    private void setListener(){
        findViewById(R.id.button_signUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser(){
        EditText userEmail = findViewById(R.id.editText_signupEmail);
        EditText password = findViewById(R.id.editText_signupPassword);
        EditText passwordConfirm = findViewById(R.id.editText_signupPasswordConfirmation);
/*
        if(password.getText().toString() != passwordConfirm.getText().toString()) {
            Toast.makeText(this, "Password don't match", Toast.LENGTH_LONG).show();
            return;
        }
*/
        auth.createUserWithEmailAndPassword(userEmail.getText().toString(),
                password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendUserToMainActivity();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to sign up!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendUserToMainActivity(){
        Intent sendToMainActivity = new Intent(this, MainActivity.class);
        startActivity(sendToMainActivity);
    }
}
