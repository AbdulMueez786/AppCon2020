package com.example.appcon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcon.R;
import com.example.appcon.model.LeaderboardItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {
    List<LeaderboardItem> ls;
    Context c;
    public LeaderboardAdapter(List<LeaderboardItem> ls, Context c) {
        this.c=c;
        this.ls=ls;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.leaderboard_row, parent, false);
        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.name.setText(ls.get(position).getName());
        holder.cheerscount.setText(String.valueOf(ls.get(position).getCheerscount())+" Cheers");
        holder.position.setText(String.valueOf(ls.get(position).getPosition()));
        FirebaseDatabase.getInstance().getReference("users").child(ls.get(position).getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String s=dataSnapshot.child("user_profile").getValue(String.class);
                      if (s.matches("default") == true) {
                            Picasso.get().load(R.drawable.user).into(holder.profile);
                        } else {
                        if(s!=null) {
                            Picasso.get().load(s).into(holder.profile);
                        }
                      }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,cheerscount,cheers,position;
        ImageView medal;
        CircleImageView profile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            cheerscount=itemView.findViewById(R.id.cheerscount);
            position=itemView.findViewById(R.id.position);
            medal=itemView.findViewById(R.id.medal);
            profile=itemView.findViewById(R.id.leaderboard_pic);
        }
    }
}
