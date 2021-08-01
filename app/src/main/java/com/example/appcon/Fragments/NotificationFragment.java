package com.example.appcon.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appcon.CheerActivity;
import com.example.appcon.R;
import com.example.appcon.adapter.NotificationAdapter;
import com.example.appcon.model.cheer_notification;
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
import java.util.List;

public class NotificationFragment extends Fragment {
    private FloatingActionButton select_peer;
    private View view;
    private de.hdodenhof.circleimageview.CircleImageView notification_profile;
    private List ls;
    private RecyclerView rv;
    private NotificationAdapter adapter;

    //Firebase
    private FirebaseUser USER;
    private DatabaseReference UserRef;
    private DatabaseReference NotificationRef;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notification, container, false);

        notification_profile=view.findViewById(R.id.notification_profile);
        this.select_peer=view.findViewById(R.id.action1);
        this.select_peer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), CheerActivity.class);
                getContext().startActivity(intent);
            }
        });
        this.notification_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });
        this.USER= FirebaseAuth.getInstance().getCurrentUser();
        this.UserRef= FirebaseDatabase.getInstance().getReference("users");
        this.NotificationRef=FirebaseDatabase.getInstance().getReference("Cheer_Notification");
        this.ls=new ArrayList<cheer_notification>();
        this.rv= view.findViewById(R.id.notification_rv);
        this.adapter = new NotificationAdapter(ls,getContext());
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        this.rv.setLayoutManager(lm);
        this.rv.setAdapter(adapter);
        this.read();
        return view;

    }
    void openDrawer(){
        DrawerLayout navDrawer = getActivity().findViewById(R.id.Home_drawer);
        if(!navDrawer.isDrawerOpen(GravityCompat.START))
            navDrawer.openDrawer(GravityCompat.START);
        else
            navDrawer.closeDrawer(GravityCompat.END);
    }
    void read(){
        this.UserRef.child(this.USER.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get().load(dataSnapshot.child("user_profile").getValue(String.class))
                        .into(notification_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

     this.NotificationRef.orderByChild("time").addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                 cheer_notification notification=snapshot.getValue(cheer_notification.class);

                 if(notification.getReciever_id().matches(USER.getUid())==true){
                       ls.add(notification);
                 }

             }
             java.util.Collections.reverse(ls);
             adapter.notifyDataSetChanged();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });




    }
    }