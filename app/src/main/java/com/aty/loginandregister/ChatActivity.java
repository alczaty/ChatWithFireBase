package com.aty.loginandregister;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private EditText mImputText;
    private ImageButton mSendButton;
    private ListView mChatListView;
    private String email;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> myKeys = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final String TAG = "Chat";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        mImputText = findViewById(R.id.messageInput);
        mSendButton = findViewById(R.id.sendButton);

        mChatListView = findViewById(R.id.chat_list_view);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String email = ds.child("email").getValue(String.class);
                    String message = ds.child("message").getValue(String.class);
                    String keys = ds.getKey();
                    myKeys.add(keys);
                    arrayList.add(email + "\n" + message);

                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                for(DataSnapshot ds:dataSnapshot.getChildren()){
//                    String email = ds.child("email").getValue(String.class);
//                    String message = ds.child("message").getValue(String.class);
//                    String keys = dataSnapshot.getKey();
//                    int index = myKeys.indexOf(keys);
//                    arrayList.set(index, message);
//                    arrayAdapter.notifyDataSetChanged();
//
//                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String input = mImputText.getText().toString();

        Log.d(TAG, "message pushed");
        String emaill = user.getEmail();
        User chat = new User(input, emaill);
        myRef.child("messages").push().setValue(chat);
        mImputText.setText("");
    }

    @Override
    protected void onStart() {

        super.onStart();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        mChatListView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        arrayAdapter.clear();
    }

}
