package com.example.appcon.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcon.Login;
import com.example.appcon.MessageActivity;
import com.example.appcon.New_Message;
import com.example.appcon.R;
import com.example.appcon.SignUp;
import com.example.appcon.model.Chat;
import com.example.appcon.model.peerModel;
import com.example.appcon.model.user;
import com.example.appcon.sendCheer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder>implements Filterable {

    private Context c;
    private List<user> ls;
    private int option=0;
    private List<user> contact_list_copy;

    public RvAdapter(Context c , List<user> ls,int option){
        this.c=c;
        this.ls=ls;
        this.option=option;
        this.contact_list_copy =new ArrayList<>(ls);//copy of our main list
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.rox,parent,false);
        return new RvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RvAdapter.ViewHolder holder, final int position) {

        holder.row_name.setText(ls.get(position).getName());
        holder.row_email.setText(ls.get(position).getEmail());
        Picasso.get().load(ls.get(position).getUser_profile()).into(holder.row_image_url);
        System.out.println(ls.get(position).getName());
        if(option==1){
            holder.row_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(c, MessageActivity.class);
                    intent.putExtra("userid",ls.get(position).getId());
                    c.startActivity(intent);
                    //finish();
                }
            });
         }
         else{
            holder.row_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(c, sendCheer.class);
                    intent.putExtra("id",ls.get(position).getId());
                    intent.putExtra("name",ls.get(position).getName());
                    c.startActivity(intent);
                }
            });
         }

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public void addlist(){
        contact_list_copy =new ArrayList<user>(ls);
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter=new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<user> filteredList = new ArrayList<>();

            System.out.println("Hjhjfhjsdhjsfdh"+constraint);

            if (constraint.equals(null) || constraint.length() == 0) {
                filteredList.addAll(contact_list_copy);
                System.out.println("Working_______________");
                System.out.println(contact_list_copy);
            } else {
                System.out.println("Else Block");
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (user item : contact_list_copy) {
                    System.out.println(filterPattern);
                    System.out.println("");
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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


    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView row_name,row_email;
        public CircleImageView row_image_url;
        public LinearLayout row_main;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            row_name=itemView.findViewById(R.id.row_name);
            row_email=itemView.findViewById(R.id.row_email);
            row_image_url=itemView.findViewById(R.id.row_imageUrl);
            row_main=itemView.findViewById(R.id.row_main);
        }
    }

}
