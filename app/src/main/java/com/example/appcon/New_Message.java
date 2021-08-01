package com.example.appcon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.appcon.adapter.RvAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class New_Message extends AppCompatActivity {
    private RecyclerView rv;
    private RvAdapter adapter;
    private List<user> ls;
    private CircleImageView back_arrow;
    private SearchView new_chat_search;


    //Firebase
    private DatabaseReference databaseReference;
    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__message);

        ls=new ArrayList<>();
        rv=findViewById(R.id.rv);
        current_user= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        back_arrow=findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        read_data();
        new_chat_search=findViewById(R.id.simpleSearchView);
        new_chat_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }
    void read_data(){
        final Context c=this;

        this.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                user u=snapshot.getValue(user.class);
                if(u.getId().matches(current_user.getUid())==false){
                    ls.add(u);
                }
             }
            adapter=new RvAdapter(c,ls,1);
             rv.setAdapter(adapter);
             adapter.notifyDataSetChanged();
             adapter.addlist();
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