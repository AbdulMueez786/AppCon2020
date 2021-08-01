package com.example.appcon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcon.R;
import com.example.appcon.model.post;
import com.example.appcon.model.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CheerRvAdapter extends RecyclerView.Adapter<CheerRvAdapter.MyViewHolder> {
    List<post> ls;
    Context c;
    private DatabaseReference post_ref;
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        post_ref = FirebaseDatabase.getInstance().getReference("users");
        //post_ref.keepSynced(true);
        final String[] SenderName =null;
        final String[] RecieverName = null;
        post_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    user u=snapshot.getValue(user.class);
                   if(ls.get(position).getReceiver_id().matches(u.getId())==true){
                       holder.reciever_name.setText(u.getName());
                   }
                    if(ls.get(position).getSender_id().matches(u.getId())==true){
                        holder.sender_name.setText(u.getName());
                        if(u.getUser_profile().equals("default")==true){

                        }
                        else{
                            System.out.println("______________________+D+F"+u.getUser_profile());
                            Picasso.get().load(u.getUser_profile())
                                    .into(holder.homescreen_profile);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        holder.cheer_desc.setText(ls.get(position).getDescription());
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
        makeTextViewResizable(holder.cheer_desc, 1, "...view more", true);
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }







    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cheer_desc,sender_name,reciever_name;
        ImageView cheer;
        de.hdodenhof.circleimageview.CircleImageView homescreen_profile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            sender_name=itemView.findViewById(R.id.homescreen_sender);
            reciever_name=itemView.findViewById(R.id.homescreen_receiver);
            cheer_desc=itemView.findViewById(R.id.cheer_desc);
            homescreen_profile=itemView.findViewById(R.id.homescreen_profile);
            //cheers_point=itemView.findViewById(R.id.cheers_point);
            cheer=itemView.findViewById(R.id.cheer);

        }
    }

    public static class MySpannable extends ClickableSpan {

        private boolean isUnderline = false;

        /**
         * Constructor
         */
        public MySpannable(boolean isUnderline) {
            this.isUnderline = isUnderline;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderline);
            ds.setColor(Color.parseColor("#343434"));

        }

        @Override
        public void onClick(View widget) {

        }
    }



    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                }

                else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    //text=tv.getText().subSequence(0,  0+ 1) + " " + expandText;
                    text=tv.getText().toString();
                    //text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(
                        addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);
            }
        });
    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "...view less", false);
                    } else {
                        makeTextViewResizable(tv, 1, "...view more", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;
    }


}
