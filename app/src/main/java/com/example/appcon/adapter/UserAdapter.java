package com.example.appcon.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcon.MessageActivity;
import com.example.appcon.R;
import com.example.appcon.model.Chat;
import com.example.appcon.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context c;
    private List<user> ls;

    public UserAdapter(Context c , List<user> ls){
        this.c=c;
        this.ls=ls;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(c).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.username.setText(ls.get(position).getName());

        final user u=ls.get(position);
        System.out.println(position);
        System.out.println(ls.get(position));
        System.out.println(ls.get(position).getName());
        System.out.println(ls.get(position).getStatus());
        if(u.getStatus().matches("online")){

            holder.offline_status.setVisibility(View.GONE);
            holder.online_status.setVisibility(View.VISIBLE);

        }else{
            holder.offline_status.setVisibility(View.VISIBLE);
            holder.online_status.setVisibility(View.GONE);
        }

        if (u.getUser_profile().equals("default")!=true){
            Picasso.get().load(u.getUser_profile())
                    .into(holder.profile_image);
        }
        else{
            Picasso.get().load(R.drawable.user)
                    .into(holder.profile_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(c, MessageActivity.class);
                intent.putExtra("userid",u.getId());
                c.startActivity(intent);
            }
        });
        final String recv_id=ls.get(position).getId();
        FirebaseDatabase.getInstance().getReference("Chats").addListenerForSingleValueEvent(new ValueEventListener() {
            List<Chat> k=new ArrayList<Chat>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean flag=false;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if((recv_id.matches(snapshot.child("receiver").getValue(String.class)) &&
                            FirebaseAuth.getInstance().getCurrentUser().getUid().matches(snapshot.child("sender").getValue(String.class))) ||
                            (FirebaseAuth.getInstance().getCurrentUser().getUid().matches(snapshot.child("receiver").getValue(String.class)) &&
                                    recv_id.matches(snapshot.child("sender").getValue(String.class)))
                    ){
                      k.add(snapshot.getValue(Chat.class));
                      flag=true;
                    }
                }
                if(flag==true){
                    if(k.get(k.size()-1).getMessage_Type()=="text"){
                    holder.lastmessage.setText(k.get(k.size()-1).getMessage());
                    holder.lastmessage.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    holder.chat_date.setText(k.get(k.size()-1).getTime());}
                    else{
                        holder.lastmessage.setText("photo");
                        holder.chat_date.setText(k.get(k.size()-1).getTime());
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

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username,lastmessage,chat_date;
        public CircleImageView profile_image,online_status,offline_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            profile_image=itemView.findViewById(R.id.profile_image);
            online_status=itemView.findViewById(R.id.status_online);
            offline_status=itemView.findViewById(R.id.status_offline);
            lastmessage=itemView.findViewById(R.id.lastmessage);
            chat_date=itemView.findViewById(R.id.chat_date);
        }
    }

}
