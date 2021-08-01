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

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_Profile extends AppCompatActivity {

    private CircleImageView admin_pic;
    private TextView admin_name,admin_email,admin_phone;

    //Firebase
    private FirebaseUser USER;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__profile);
        admin_pic=findViewById(R.id.admin_pic);
        admin_name=findViewById(R.id.admin_name);
        admin_email=findViewById(R.id.admin_email);
        admin_phone=findViewById(R.id.admin_phone);

        this.USER= FirebaseAuth.getInstance().getCurrentUser();
        this.databaseReference= FirebaseDatabase.getInstance().getReference("users").child(USER.getUid());
        this.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 admin_name.setText(dataSnapshot.child("name").getValue(String.class));
                                 admin_email.setText(dataSnapshot.child("email").getValue(String.class));
                                 Picasso.get().load(dataSnapshot.child("user_profile").getValue(String.class))
                                         .into(admin_pic);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("profile").child(USER.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          admin_phone.setText(dataSnapshot.child("phone_no").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




    }
    void read(){

    }

}