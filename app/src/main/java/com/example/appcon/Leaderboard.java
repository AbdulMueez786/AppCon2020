package com.example.appcon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.appcon.adapter.LeaderboardAdapter;
import com.example.appcon.model.LeaderboardItem;
import com.example.appcon.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Leaderboard extends AppCompatActivity {
    private RecyclerView rv;
    private List<LeaderboardItem> ls;

    private LeaderboardAdapter adapter;

    //Firebase
    private FirebaseUser current_user;
    private DatabaseReference UserRef;

    private de.hdodenhof.circleimageview.CircleImageView leaderbard_pic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


        this.current_user = FirebaseAuth.getInstance().getCurrentUser();
        this.UserRef= FirebaseDatabase.getInstance().getReference("users");
        rv=findViewById(R.id.leaderboard_rv);
        ls=new ArrayList<>();
        read();
        this.leaderbard_pic=findViewById(R.id.leaderboard_profile);
        FirebaseDatabase.getInstance().getReference("users").child(this.current_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("user_profile").getValue(String.class).matches("default") == true) {
                    Picasso.get().load(R.drawable.user).into(leaderbard_pic);
                } else {
                    Picasso.get().load(dataSnapshot.child("user_profile").getValue(String.class)).into(leaderbard_pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void read(){

        final Context c=this;
        RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        this.UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List position=new ArrayList<>();
                ls.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        user u=snapshot.getValue(user.class);
                        System.out.println(u.getName());
                        if(u.getName().matches("admin")!=true) {
                            ls.add(new LeaderboardItem(u.getName(), Integer.parseInt(u.getCheerpoints()), 1,u.getId() ));
                            position.add(Integer.parseInt(u.getCheerpoints()));
                        }
                }
                Collections.sort(position,Collections.reverseOrder());
                List<LeaderboardItem> a=new ArrayList<>();
                int k=1;
                for(int i = 0; i<position.size(); i++){
                    for (LeaderboardItem j : ls){
                        if(j.getCheerscount()==(int)position.get(i) && a.contains(j)==false){
                            j.setPosition(k);
                            a.add(j);
                            System.out.println(j.getCheerscount());
                            k++;
                        }
                    }
                }
                ls.clear();
                ls=a;
                adapter=new LeaderboardAdapter(ls,c);

                rv.setAdapter(adapter);
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