package com.example.appcon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TermsAndConditions extends AppCompatActivity {

    private Button bt_accept,bt_decline;
    private ImageButton bt_close;

    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        current_user= FirebaseAuth.getInstance().getCurrentUser();
        bt_accept=findViewById(R.id.bt_accept);
        bt_decline=findViewById(R.id.bt_decline);
        bt_close=findViewById(R.id.bt_close);

        bt_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bt_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }
/*
    private void status(String status) {

        if (current_user!=null){
            DatabaseReference databaseReference;
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(current_user.getUid());
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("status", status);
            databaseReference.updateChildren(hashMap);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
*/
}