package com.example.appcon.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appcon.Group_Chat;
import com.example.appcon.New_Message;
import com.example.appcon.R;
import com.example.appcon.adapter.UserAdapter;
import com.example.appcon.model.user;
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

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentMessage extends Fragment {

    private RecyclerView rv;
    private UserAdapter userAdapter;
    private List<user> ls;
    private CircleImageView messages_profile;
    private View Message_View;
    private FloatingActionButton group_chat_button,new_chat_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Message_View=inflater.inflate(R.layout.fragment_message, container, false);
        rv =Message_View.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        ls=new ArrayList<>();
        this.messages_profile=Message_View.findViewById(R.id.messages_profile);
        this.messages_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });
        readUsers();
        group_chat_button=Message_View.findViewById(R.id.action2);
        group_chat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             Intent intent=new Intent(getContext(), Group_Chat.class);
                     startActivity(intent);
            }
        });
        new_chat_button=Message_View.findViewById(R.id.action1);
        new_chat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), New_Message.class);

                startActivity(intent);
            }
        });

        return this.Message_View;
    }
    private void readUsers(){
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        final Context c=getContext();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ls.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    user u=snapshot.getValue(user.class);
                    assert u!=null;
                    assert firebaseUser!=null;
                    if(u.getId().matches(firebaseUser.getUid())==false && u.getName().matches("admin")==false){
                        ls.add(u);
                    }
                    if(u.getId().matches(firebaseUser.getUid())==true){

                        if(u.getUser_profile().matches("default")!=true){
                            Picasso.get().load(u.getUser_profile())
                                    .into(messages_profile);
                        }
                        else{
                            Picasso.get().load(R.drawable.user)
                                    .into(messages_profile);
                        }
                    }
                }
                userAdapter=new UserAdapter(c,ls);
                rv.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    void openDrawer(){
        DrawerLayout navDrawer = getActivity().findViewById(R.id.Home_drawer);
        if(!navDrawer.isDrawerOpen(GravityCompat.START))
            navDrawer.openDrawer(GravityCompat.START);
        else
            navDrawer.closeDrawer(GravityCompat.END);
    }
}

