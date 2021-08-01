package com.example.appcon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class UserProfile extends AppCompatActivity {

    private CircleImageView user_profile;
    private ImageView user_medal;
    private TextView user_name,edit_profile,user_bio,user_cheer_points,user_cheer_sent_no,
            user_cheer_received_no,user_email,user_phone;

    //Firebase
    private DatabaseReference UserRef;
    private DatabaseReference UserProfile;
    private FirebaseUser current_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        System.out.println();
        this.current_user = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println(this.current_user);


        this.UserRef= FirebaseDatabase.getInstance().getReference("users").child(this.current_user.getUid());
        this.UserProfile=FirebaseDatabase.getInstance().getReference("profile").child(this.current_user.getUid());
        user_profile=findViewById(R.id.user_profile);
        user_medal=findViewById(R.id.user_medal);

        user_name=findViewById(R.id.user_name);
        edit_profile=findViewById(R.id.edit_profile);
        user_bio=findViewById(R.id.user_bio);
        user_cheer_points=findViewById(R.id.user_cheer_points);
        user_cheer_sent_no=findViewById(R.id.user_cheer_sent_no);
        user_cheer_received_no=findViewById(R.id.user_cheer_received_no);
        user_email=findViewById(R.id.user_email);
        user_phone=findViewById(R.id.user_phone);

        this.retrieve_profile();



        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navigate to another screen
            }
        });

    }
void retrieve_profile(){

        this.UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String medal=dataSnapshot.child("medal").getValue(String.class);
                String user_profile_url=dataSnapshot.child("user_profile").getValue(String.class);
                String name=dataSnapshot.child("name").getValue(String.class);
                String cheerpoints=dataSnapshot.child("cheerpoints").getValue(String.class);
                String email=dataSnapshot.child("email").getValue(String.class);

                if(user_profile_url.matches("default")!=true){
                    Picasso.get().load(user_profile_url).into(user_profile);
                }
                else{
                    Picasso.get().load(R.drawable.user).into(user_profile);
                }

                if(medal.matches("0")){
                  Picasso.get().load(R.drawable.goldmedal).into(user_medal);
                }
                else if(medal.matches("1")){

                }
                else if(medal.matches("2")){

                }
                user_cheer_points.setText(cheerpoints);

                user_name.setText(name);
                user_email.setText(email);

                FirebaseDatabase.getInstance().getReference("points").child(current_user.getUid())
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       String cheer_point_sent=dataSnapshot.child("cheer_point_sent").getValue(String.class);
                       String cheer_point_received=dataSnapshot.child("cheer_point_received").getValue(String.class);
                        user_cheer_sent_no.setText(cheer_point_sent);
                        user_cheer_received_no.setText(cheer_point_received);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        this.UserProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String phone_no=dataSnapshot.child("phone_no").getValue(String.class);
                String bio=dataSnapshot.child("bio").getValue(String.class);

                user_phone.setText(phone_no);
                user_bio.setText(bio);

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
    }
*/

}


