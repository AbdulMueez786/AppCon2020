package com.example.appcon.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcon.MessageActivity;
import com.example.appcon.R;
import com.example.appcon.model.peerModel;
import com.example.appcon.model.profile;
import com.example.appcon.model.user;
import com.example.appcon.sendCheer;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeerRvAdapter extends RecyclerView.Adapter<PeerRvAdapter.MyViewHolder> implements Filterable {
    List<peerModel> ls;
    private List<peerModel> contact_list_copy;
    Context c;

    public PeerRvAdapter(List<peerModel> ls, Context c) {
        this.c=c;
        this.ls=ls;
        this.contact_list_copy =new ArrayList<>(ls);//copy of our main list
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.peer_row, parent, false);
        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.name.setText(ls.get(position).getPeer_username());
        holder.cheerscount.setText(ls.get(position).getCheer_points());

        if(Integer.parseInt(ls.get(position).getCheer_points())<100) {
            holder.medal.setImageURI(Uri.parse("android.resource://com.example.appcon/drawable/silvermedal"));
        }
        else if(Integer.parseInt(ls.get(position).getCheer_points())<200) {
            holder.medal.setImageURI(Uri.parse("android.resource://com.example.appcon/drawable/bronzemedal"));
        }
        else if(Integer.parseInt(ls.get(position).getCheer_points())<300) {
            holder.medal.setImageURI(Uri.parse("android.resource://com.example.appcon/drawable/goldmedal"));
        }


        DatabaseReference temp= FirebaseDatabase.getInstance().getReference("users").child(ls.get(position).getId());
        temp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){


                       if (dataSnapshot.child("user_profile").getValue(String.class).matches("default") == true) {
                           Picasso.get().load(R.drawable.user).into(holder.cvProfile);
                       } else {
                           Picasso.get().load(dataSnapshot.child("user_profile").getValue(String.class)).into(holder.cvProfile);
                       }


                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //holder.cvProfile.setImageURI();
        holder.l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               FirebaseDatabase.getInstance().getReference("profile").child(ls.get(position).getId())
                       .addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      profile p=dataSnapshot.getValue(profile.class);
                       View v = LayoutInflater.from(c).inflate(R.layout.layout_bottom_sheet, null);
                       com.makeramen.roundedimageview.RoundedImageView peer_pic=v.findViewById(R.id.peer_pic);
                       TextView peer_name=v.findViewById(R.id.peer_name);
                       TextView peer_phone_no=v.findViewById(R.id.peer_phone_no);
                       TextView peer_gender=v.findViewById(R.id.peer_gender);
                       TextView peer_bio=v.findViewById(R.id.peer_bio);
                       peer_name.setText(p.getFname()+" "+p.getLname());
                       peer_phone_no.setText(p.getPhone_no());
                       peer_gender.setText(p.getGender());
                       peer_bio.setText(p.getBio());
                       Button send_cheer_button=v.findViewById(R.id.button_send_cheer);
                       send_cheer_button.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent intent=new Intent(c, sendCheer.class);
                               intent.putExtra("id",ls.get(position).getId());
                               intent.putExtra("name",ls.get(position).getPeer_username());
                               c.startActivity(intent);
                           }
                       });
                       Picasso.get().load(p.getProfile()).into(peer_pic);

                       BottomSheetDialog dialog = new BottomSheetDialog(c);
                       dialog.setContentView(v);
                       dialog.show();

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

                /*
                final Dialog epicDialog;
                epicDialog=new Dialog(c);
                epicDialog.setContentView(R.layout.peer_profile);

                ImageView close=epicDialog.findViewById(R.id.close_peer_profile);

                TextView peer_name=epicDialog.findViewById(R.id.peer_name);
                peer_name.setText(ls.get(position).getPeer_username());

                TextView peer_points=epicDialog.findViewById(R.id.peer_points);
                peer_points.setText(ls.get(position).getCheer_points());

                Button send_cheer=epicDialog.findViewById(R.id.sendCheer);


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        epicDialog.dismiss();
                    }
                });
                send_cheer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(c, sendCheer.class);
                        intent.putExtra("id",ls.get(position).getId());
                        intent.putExtra("name",ls.get(position).getPeer_username());
                        c.startActivity(intent);
                    }
                });
                epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                epicDialog.show();
           */

            }
        });

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public void addlist(){
        contact_list_copy =new ArrayList<peerModel>(ls);
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter=new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<peerModel> filteredList = new ArrayList<>();

            System.out.println("Hjhjfhjsdhjsfdh"+constraint);

            if (constraint.equals(null) || constraint.length() == 0) {
                filteredList.addAll(contact_list_copy);
                System.out.println("Working_______________");
                System.out.println(contact_list_copy);
            } else {
                System.out.println("Else Block");
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (peerModel item : contact_list_copy) {
                    System.out.println(filterPattern);
                    System.out.println("");
                    if (item.getPeer_username().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            ls.clear();
            System.out.println("Result");
            ls.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,cheerscount;
        ImageView medal;
        LinearLayout l1;
        de.hdodenhof.circleimageview.CircleImageView cvProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            cheerscount=itemView.findViewById(R.id.cheerscount);
            medal=itemView.findViewById(R.id.user_medal);
            cvProfile=itemView.findViewById(R.id.profilePic);
            l1=itemView.findViewById(R.id.l1);
        }
    }

}

