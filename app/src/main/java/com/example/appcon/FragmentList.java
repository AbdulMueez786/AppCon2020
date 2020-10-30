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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentList extends Fragment {
private View list_view;
private RecyclerView list_rv;
private List<peerModel> ls=new ArrayList<peerModel>();
private FirebaseUser USER;
private  PeerRvAdapter list_adapter;
private DatabaseReference root;

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
        this.root = FirebaseDatabase.getInstance().getReference("users");
        this.root.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
        ls.clear();
        for (DataSnapshot snapshot1:dataSnapshot1.getChildren()){
            user obj=snapshot1.getValue(user.class);
            if(USER.getUid().matches(obj.getId())!=true) {
                ls.add(new peerModel(obj.getId(), obj.getName(), obj.getCheerpoints(), obj.getMedal(), ""));
                list_adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
        return this.list_view;
    }
}