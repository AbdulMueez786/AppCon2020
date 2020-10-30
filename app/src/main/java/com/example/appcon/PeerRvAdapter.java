package com.example.appcon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PeerRvAdapter extends RecyclerView.Adapter<PeerRvAdapter.MyViewHolder> {
    List<peerModel> ls;
    Context c;
    public PeerRvAdapter(List<peerModel> ls, Context c) {
        this.c=c;
        this.ls=ls;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.peer_row, parent, false);
        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
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


        holder.l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("clicked");
                Intent intent=new Intent(c,peerProfile.class);
                intent.putExtra("id",ls.get(position).getId());
                intent.putExtra("image_url",ls.get(position).getPeer_profile());
                intent.putExtra("name",ls.get(position).getPeer_username());
                intent.putExtra("cheerpoints",ls.get(position).getCheer_points());
                intent.putExtra("medal",ls.get(position).getPeer_medal());
                c.startActivity(intent);

            }
        });
        //holder.cvProfile.setImageURI(Uri.parse(ls.get(position).getPeer_profile()));
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,cheerscount;
        ImageView medal;
        LinearLayout l1;
        de.hdodenhof.circleimageview.CircleImageView cvProfile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            cheerscount=itemView.findViewById(R.id.cheerscount);
            medal=itemView.findViewById(R.id.medal);
            cvProfile=itemView.findViewById(R.id.profilePic);
            l1=itemView.findViewById(R.id.l1);
        }
    }
}

