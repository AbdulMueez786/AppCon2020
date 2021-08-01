package com.example.appcon.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcon.CheerActivity;
import com.example.appcon.R;
import com.example.appcon.adapter.PeerRvAdapter;
import com.example.appcon.model.peerModel;
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

public class FragmentList extends Fragment {
private View list_view;
private RecyclerView list_rv;
private List<peerModel> ls=new ArrayList<peerModel>();
private FirebaseUser USER;
private PeerRvAdapter list_adapter;
private DatabaseReference root;
private DatabaseReference temp;
private de.hdodenhof.circleimageview.CircleImageView searchbar_profile;
private com.google.android.material.textfield.TextInputEditText list_search;
    private FloatingActionButton select_peer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { ;
        list_view =inflater.inflate(R.layout.list,container,false);
        USER= FirebaseAuth.getInstance().getCurrentUser();
        this.list_rv = list_view.findViewById(R.id.rv);
        list_adapter =new PeerRvAdapter(ls, getContext());
        RecyclerView.LayoutManager lm=new LinearLayoutManager(getActivity());
        list_rv.setLayoutManager(lm);
        list_rv.setAdapter(list_adapter);
        this.select_peer=list_view.findViewById(R.id.action1);
        this.select_peer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), CheerActivity.class);
                getContext().startActivity(intent);
            }
        });
        this.USER=FirebaseAuth.getInstance().getCurrentUser();
        this.searchbar_profile=list_view.findViewById(R.id.searchbar_profile);
        this.list_search=list_view.findViewById(R.id.list_search);

        this.list_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               list_adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        this.searchbar_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });
        this.root = FirebaseDatabase.getInstance().getReference("users");

        this.root.addListenerForSingleValueEvent(new ValueEventListener() {
     @Override
     public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
        ls.clear();
        for (DataSnapshot snapshot1:dataSnapshot1.getChildren()){
            user U=snapshot1.getValue(user.class);
            if(USER.getUid().matches(U.getId())!=true && U.getName().matches("admin")==false) {
                ls.add(new peerModel(U.getId(), U.getName(), U.getCheerpoints(), U.getMedal(),U.getUser_profile()));
                list_adapter.notifyDataSetChanged();
                list_adapter.addlist();
            }
            if(USER.getUid().matches(U.getId())==true){
                if(U.getUser_profile().matches("default")==true){
                    Picasso.get().load(R.drawable.user).into(searchbar_profile);
                }
                else{
                    Picasso.get().load(U.getUser_profile()).into(searchbar_profile);
                }
            }

        }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
        return this.list_view;
    }
    void openDrawer(){
        DrawerLayout navDrawer = getActivity().findViewById(R.id.Home_drawer);
        if(!navDrawer.isDrawerOpen(GravityCompat.START))
            navDrawer.openDrawer(GravityCompat.START);
        else
            navDrawer.closeDrawer(GravityCompat.END);
    }


}