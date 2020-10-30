package com.example.appcon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment {
    private View home_view;
    private RecyclerView home_rv;
    private List<post> home_posts_list=new ArrayList<>();;
    private CheerRvAdapter home_adapter;
    private DatabaseReference HomeFragment_FirebaseRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        home_view =inflater.inflate(R.layout.fragment_home,container,false);
        home_rv = home_view.findViewById(R.id.rv);
        home_adapter =new CheerRvAdapter(home_posts_list, getContext());
        RecyclerView.LayoutManager lm=new LinearLayoutManager(getContext());
        home_rv.setLayoutManager(lm);
        home_rv.setAdapter(home_adapter);



        this.HomeFragment_FirebaseRef = FirebaseDatabase.getInstance().getReference("Posts");


        this.HomeFragment_FirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                home_posts_list.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    post p=snapshot.getValue(post.class);
                    home_posts_list.add(p);
                }
                home_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Do something about the error
            }
        });
        return home_view;
    }
}
