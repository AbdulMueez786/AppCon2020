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
import com.example.appcon.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static  final int msg_type_left=0;

    public static  final int msg_type_right=1;


    private Context c;
    private List<Chat> ls;
    private String image_url;
    private FirebaseUser fuser;

    public MessageAdapter(Context c , List<Chat> ls, String image_url){
        this.c=c;
        this.ls=ls;
        this.image_url=image_url;
    }

    @NonNull
    @Override
    public  MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==msg_type_right) {
            View view = LayoutInflater.from(c).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(c).inflate(R.layout.chat_item_left ,parent, false);
            return new MessageAdapter.ViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapter.ViewHolder holder, int position) {


        final Chat chat=ls.get(position);
          if (ls.get(position).getMessage_Type().matches("text") == true) {
              holder.show_message.setVisibility(View.VISIBLE);
              holder.show_image.setVisibility(View.GONE);
              holder.time.setVisibility(View.GONE);
              System.out.println(chat.getMessage());

              holder.show_message.setText(chat.getMessage()+" "+chat.getTime()+" ");
          } else {
              holder.time.setVisibility(View.VISIBLE);
              holder.time.setText(chat.getTime());
              holder.show_message.setVisibility(View.GONE);
              holder.show_image.setVisibility(View.VISIBLE);
              Picasso.get().load(chat.getMessage())
                      .into(holder.show_image);
          }

          holder.show_message.setOnLongClickListener(new View.OnLongClickListener() {
              @Override
              public boolean onLongClick(View view) {
                  holder.show_message.setText("You deleted this message");
                  HashMap<String, Object> hm = new HashMap<>();
                  hm.put("Message_Type", "text");
                  hm.put("message", "You deleted this message");
                  FirebaseDatabase.getInstance().getReference("Chats").child(chat.getMessage_id()).updateChildren(hm);
                  return false;
              }
          });
          holder.show_image.setOnLongClickListener(new View.OnLongClickListener() {
              @Override
              public boolean onLongClick(View view) {
                  holder.show_message.setText("You deleted this message");
                  holder.show_message.setVisibility(View.VISIBLE);
                  holder.show_image.setVisibility(View.GONE);
                  HashMap<String, Object> hm = new HashMap<>();
                  hm.put("Message_Type", "text");
                  hm.put("message", "You deleted this message");
                  FirebaseDatabase.getInstance().getReference("Chats").child(chat.getMessage_id()).updateChildren(hm);

                  return false;
              }
          });



        if(ls.get(position).getSender().equals(fuser.getUid())!=true){
           if (image_url.equals("default") != true) {
               System.out.println("+++++++++++++++++++++++++++++++++++++++++" + ls.get(position).getReceiver());
               FirebaseDatabase.getInstance().getReference("users")
                       .child(chat.getSender()).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       Picasso.get().load(dataSnapshot.child("user_profile").getValue(String.class))
                               .into(holder.profile_image);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
           } else {
               Picasso.get().load(R.drawable.user)
                       .into(holder.profile_image);
           }
       }
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView show_image;
        public TextView show_message,time;
        public CircleImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message=itemView.findViewById(R.id.show_message);
            profile_image=itemView.findViewById(R.id.profile_image);
            show_image=itemView.findViewById(R.id.show_image);
            time=itemView.findViewById(R.id.time);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(ls.get(position).getSender().equals(fuser.getUid())){
            return msg_type_right;
        }
        else {
            return msg_type_left;
        }
    }
}

