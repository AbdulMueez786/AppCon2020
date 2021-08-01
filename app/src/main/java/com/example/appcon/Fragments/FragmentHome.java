package com.example.appcon.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcon.CheerActivity;
import com.example.appcon.Group_Chat;
import com.example.appcon.R;
import com.example.appcon.adapter.CheerRvAdapter;
import com.example.appcon.model.post;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentHome extends Fragment {

    private View home_view;
    private RecyclerView home_rv;
    private List<post> home_posts_list=new ArrayList<>();;
    private CheerRvAdapter home_adapter;
    private FloatingActionButton chat;
    private CircleImageView home_UserProfile;
    private ImageView home_message;
    private FloatingActionButton select_peer;
    //Firebase
    private DatabaseReference HomeFragment_FirebaseRef;
    private FirebaseUser USER;
    private DatabaseReference UserRef;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        home_view =inflater.inflate(R.layout.fragment_home,container,false);
        home_rv = home_view.findViewById(R.id.rv);
        home_adapter =new CheerRvAdapter(home_posts_list, getContext());
        RecyclerView.LayoutManager lm=new LinearLayoutManager(getContext());
        home_rv.setLayoutManager(lm);
        home_rv.setAdapter(home_adapter);
        this.home_message=home_view.findViewById(R.id.home_message);
        this.home_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), Group_Chat.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        this.select_peer=home_view.findViewById(R.id.action1);
        this.select_peer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), CheerActivity.class);
                //getContext().startActivity(intent);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        this.home_UserProfile=home_view.findViewById(R.id.home_UserProfile);
        this.home_UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });


        this.USER=FirebaseAuth.getInstance().getCurrentUser();

        this.UserRef=FirebaseDatabase.getInstance().getReference("users").child(this.USER.getUid());

        this.UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.child("user_profile").getValue(String.class))
                        .into(home_UserProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        this.HomeFragment_FirebaseRef = FirebaseDatabase.getInstance().getReference("Posts");
        this.HomeFragment_FirebaseRef.orderByChild("time").limitToFirst(20).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                home_posts_list.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    post p=snapshot.getValue(post.class);
                    home_posts_list.add(p);
                }
                java.util.Collections.reverse(home_posts_list);
                home_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Do something about the error
            }
        });
        return home_view;
    }

    void openDrawer(){
        DrawerLayout navDrawer = getActivity().findViewById(R.id.Home_drawer);
        if(!navDrawer.isDrawerOpen(GravityCompat.START))
            navDrawer.openDrawer(GravityCompat.START);
        else
            navDrawer.closeDrawer(GravityCompat.END);
    }

}
