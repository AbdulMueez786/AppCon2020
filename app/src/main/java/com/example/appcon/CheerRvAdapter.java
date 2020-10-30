package com.example.appcon;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CheerRvAdapter extends RecyclerView.Adapter<CheerRvAdapter.MyViewHolder> {
    List<post> ls;
    Context c;
    public CheerRvAdapter(List<post> ls, Context c) {
        this.c=c;
        this.ls=ls;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(c).inflate(R.layout.cheer_row, parent, false);
        return new MyViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.cheer_title.setText(ls.get(position).getTitle());
        holder.cheer_desc.setText(ls.get(position).getDescription());

        holder.cheers_point.setText(ls.get(position).getCheer_type());






        if(ls.get(position).getCheer_type().matches("0")){
            holder.cheer.setImageURI(Uri.parse("android.resource://com.example.appcon/drawable/cheer1"));
    }
        else if(ls.get(position).getCheer_type().matches("1")){
            holder.cheer.setImageURI(Uri.parse("android.resource://com.example.appcon/drawable/cheer2"));
        }
        else if(ls.get(position).getCheer_type().matches("2")){
            holder.cheer.setImageURI(Uri.parse("android.resource://com.example.appcon/drawable/cheer3"));
        }
        else if(ls.get(position).getCheer_type().matches("3")){
            holder.cheer.setImageURI(Uri.parse("android.resource://com.example.appcon/drawable/cheer4"));
        }

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cheers_point,cheer_title,cheer_desc;
        ImageView cheer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cheer_title=itemView.findViewById(R.id.cheer_title);
            cheer_desc=itemView.findViewById(R.id.cheer_desc);
            cheers_point=itemView.findViewById(R.id.cheers_point);

            cheer=itemView.findViewById(R.id.cheer);

        }
    }
}
