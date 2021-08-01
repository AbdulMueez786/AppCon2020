package com.example.appcon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Report extends AppCompatActivity {

    private CircleImageView Report_admin_profile;
    private TextView Report_total_cheer_count,Report_cheer1_count,Report_cheer2_count
            ,Report_cheer3_count,Report_cheer4_count;

    //Firebase
    private FirebaseUser current_user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        current_user= FirebaseAuth.getInstance().getCurrentUser();
        this.Report_admin_profile=findViewById(R.id.Report_admin_profile);
        this.Report_total_cheer_count=findViewById(R.id.Report_total_cheer_count);
        this.Report_cheer1_count=findViewById(R.id.Report_cheer1_count);
        this.Report_cheer2_count=findViewById(R.id.Report_cheer2_count);
        this.Report_cheer3_count=findViewById(R.id.Report_cheer3_count);
        this.Report_cheer4_count=findViewById(R.id.Report_cheer4_count);
        this.databaseReference= FirebaseDatabase.getInstance().getReference("cheer_report");

        Picasso.get().load( getIntent().getStringExtra("Admin_Profile")).into(this.Report_admin_profile);
        this.read();

    }

    void read(){
       this.databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               Report_cheer1_count.setText(dataSnapshot.child("cheer1").getValue(String.class)+" cheers");
               Report_cheer2_count.setText(dataSnapshot.child("cheer2").getValue(String.class)+" cheers");
               Report_cheer3_count.setText(dataSnapshot.child("cheer3").getValue(String.class)+" cheers");
               Report_cheer4_count.setText(dataSnapshot.child("cheer4").getValue(String.class)+" cheers");
               Report_total_cheer_count.setText("Totals Cheers: "+dataSnapshot.child("total_cheers").getValue(String.class));

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

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
    }*/

}