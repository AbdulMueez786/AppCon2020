package com.example.appcon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class FragmentProfile extends Fragment {
    View view;
    TextView cheers,allcheers,name;
    ImageView img;
    FirebaseUser firebaseUser;
    DatabaseReference user_info_Ref;
    public FragmentProfile() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.profile,container,false);
        cheers=view.findViewById(R.id.cheers);
        name=view.findViewById(R.id.name);
        allcheers=view.findViewById(R.id.allcheers);
        img=view.findViewById(R.id.img);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        user_info_Ref=FirebaseDatabase.getInstance().getReference("users");
        if(firebaseUser !=null){

            user_info_Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user u=(user) dataSnapshot.child(firebaseUser.getUid()).getValue(user.class);
                    name.setText(u.getName());
                    cheers.setText(u.getCheerpoints());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        return view;
    }

}
