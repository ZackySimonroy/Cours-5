package com.example.a533.cours5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a533.cours5.Notification.NotificationService;
import com.example.a533.cours5.Notification.model.ImportantMessageModel;
import com.example.a533.cours5.Notification.model.MessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_log_out, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                //Write your code
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    private void startService(){
        Intent serviceIntent = new Intent(this, NotificationService.class);
        ContextCompat. startForegroundService(this, serviceIntent);
    }

    private void setListeners(){
        findViewById(R.id.button_sendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        findViewById(R.id.button_important).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendImportantMessage();
            }
        });
    }

    private void sendMessage(){
        EditText editTextMessage = findViewById(R.id.editText_message);
        MessageModel messageModel = new MessageModel(editTextMessage.getText().toString(),auth.getCurrentUser().getEmail());
        database.collection("Notification").add(messageModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendImportantMessage(){
        EditText editTextMessage = findViewById(R.id.editText_message);
        ImportantMessageModel messageModel = new ImportantMessageModel(editTextMessage.getText().toString(),auth.getCurrentUser().getEmail());
        database.collection("Important Message").add(messageModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Important Message Sent", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void logOut() {
        auth.signOut();
        updateUI(auth.getCurrentUser());
    }
    private void updateUI(FirebaseUser currentUser){
        if(currentUser == null){
            sendUserToSignUpOrLoginActivity();
        }else {
            startService();
        }
    }

    private void sendUserToSignUpOrLoginActivity(){
        Intent sendToSignUpOrLogin = new Intent(this, SignUpOrLoginActivity.class);

        startActivity(sendToSignUpOrLogin);
    }
}
