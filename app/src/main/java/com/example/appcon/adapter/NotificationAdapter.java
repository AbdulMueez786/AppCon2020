package com.example.appcon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcon.R;
import com.example.appcon.model.cheer_notification;
import com.example.appcon.model.peerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Holder> implements Filterable {
    List<cheer_notification> ls;
    List<cheer_notification> lsFull;
    Context c;

    public NotificationAdapter(List<cheer_notification> ls, Context c) {
        this.ls = ls;
        this.lsFull = new ArrayList<>(ls);
        this.c = c;
    }

    @NonNull
    @Override
    public NotificationAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(c).inflate(R.layout.notification_row,parent, false);
        return new Holder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.Holder holder, int position) {
        holder.name.setText(ls.get(position).getNotification_title());

        Picasso.get().load(ls.get(position).getSender_image()).into(holder.mImageView);

        if(ls.get(position).getCheer_type().matches("0")==true){
            Picasso.get().load(R.drawable.cheer1).into(holder.cheer);
        }
        else if(ls.get(position).getCheer_type().matches("1")==true){
            Picasso.get().load(R.drawable.cheer2).into(holder.cheer);
        }
        else if(ls.get(position).getCheer_type().matches("2")==true){
            Picasso.get().load(R.drawable.cheer3).into(holder.cheer);
        }
        else if(ls.get(position).getCheer_type().matches("3")==true){
            Picasso.get().load(R.drawable.cheer4).into(holder.cheer);
        }
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name;
        CircleImageView mImageView; ImageView cheer;
        LinearLayout ll;
        public Holder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.notification_title);
            mImageView = itemView.findViewById(R.id.notification_image);
            cheer = itemView.findViewById(R.id.notification_cheer);
            ll = itemView.findViewById(R.id.peer_search_row);

        }
    }

    public void refreshList(){
        lsFull = new ArrayList<cheer_notification>(ls);
    }

    @Override
    public Filter getFilter() {
        return peerFilter;
    }

    private Filter peerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<peerModel> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                //filteredList.addAll(lsFull);
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                /*for(peerModel p: lsFull){
                    if(p.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(p);
                    }
                }*/
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            ls.clear();
            ls.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
