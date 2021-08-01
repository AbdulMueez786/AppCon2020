package com.example.appcon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.appcon.model.cheer_notification;
import com.example.appcon.model.post;
import com.example.appcon.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class sendCheer extends AppCompatActivity {


    private ImageView sendCheer_button;
    private Button sendCheer_sendButton;
    private String sendCheer_cheerType="3";
    private EditText cheer_desc;


    public static float screen_width;
    private String receiver_id;
    private int points;

    //Firebase
    private DatabaseReference sendCheer_postsRef;
    private DatabaseReference sendCheer_userRef;
    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cheer);
        sendCheer_button = findViewById(R.id.reactButton);
        sendCheer_button.setImageResource(R.drawable.cheer4);
        sendCheer_sendButton =findViewById(R.id.sendreact);
        current_user = FirebaseAuth.getInstance().getCurrentUser();
        cheer_desc=findViewById(R.id.cheer_desc);
        cheer_desc.setHint("Say some thing nice to "+getIntent().getStringExtra("name")+"...");
        receiver_id =getIntent().getStringExtra("id");//id

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screen_width = metrics.widthPixels;

        final float x = sendCheer_button.getX();
        final float y = sendCheer_button.getY();

        sendCheer_postsRef = FirebaseDatabase.getInstance().getReference("Posts");

        sendCheer_userRef = FirebaseDatabase.getInstance().getReference("users");
        sendCheer_sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_post();
                send_notification();
                Toast.makeText(sendCheer.this, "cheer sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendCheer_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ImageView react1 = (ImageView)popupView.findViewById(R.id.react1);
                ImageView react2 = (ImageView)popupView.findViewById(R.id.react2);
                ImageView react3 = (ImageView)popupView.findViewById(R.id.react3);
                ImageView react4 = (ImageView)popupView.findViewById(R.id.react4);



                react1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            float scale = sendCheer.screen_width / view.getWidth();
                            scale = scale/5;
                            view.setScaleY(scale);
                            view.setScaleX(scale);
                            return true;
                        }else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            view.setScaleY(1);
                            view.setScaleX(1);

                            Toast.makeText(sendCheer.this, "helpful", Toast.LENGTH_SHORT).show();
                            sendCheer_button.setImageResource(R.drawable.reactback);
                            sendCheer_button.setImageResource(R.drawable.cheer1);
                            sendCheer_cheerType ="0";
                            popupWindow.dismiss();
                        }
                        return false;
                    }
                });

                react2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            float scale = sendCheer.screen_width / view.getWidth();
                            scale = scale/5;
                            view.setScaleY(scale);
                            view.setScaleX(scale);
                            return true;
                        }else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            view.setScaleY(1);
                            view.setScaleX(1);

                            Toast.makeText(sendCheer.this, "brilliant", Toast.LENGTH_SHORT).show();
                            sendCheer_button.setImageResource(R.drawable.reactback);
                            sendCheer_button.setImageResource(R.drawable.cheer2);
                            sendCheer_cheerType ="1";
                            popupWindow.dismiss();
                        }


                        return false;
                    }
                });

                react3.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            float scale = sendCheer.screen_width / view.getWidth();
                            scale = scale/5;
                            view.setScaleY(scale);
                            view.setScaleX(scale);
                            return true;
                        }else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            view.setScaleY(1);
                            view.setScaleX(1);

                            Toast.makeText(sendCheer.this, "punctual", Toast.LENGTH_SHORT).show();
                            sendCheer_button.setImageResource(R.drawable.reactback);
                            sendCheer_button.setImageResource(R.drawable.cheer3);
                            sendCheer_cheerType ="2";
                            popupWindow.dismiss();
                        }


                        return false;
                    }
                });

                react4.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            float scale = sendCheer.screen_width / view.getWidth();
                            scale = scale/5;
                            view.setScaleY(scale);
                            view.setScaleX(scale);
                            return true;
                        }else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            view.setScaleY(1);
                            view.setScaleX(1);

                            Toast.makeText(sendCheer.this, "kind", Toast.LENGTH_SHORT).show();
                            sendCheer_button.setImageResource(R.drawable.reactback);
                            sendCheer_button.setImageResource(R.drawable.cheer4);
                            sendCheer_cheerType ="3";
                            popupWindow.dismiss();
                        }


                        return false;
                    }
                });

                popupWindow.showAsDropDown(sendCheer_button, (int)x-100, (int)y-300);

                return false;
            }
        });
    }

    void send_notification(){
       sendCheer_userRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String appId=dataSnapshot.child(receiver_id).child("app_id").getValue(String.class);
               final String sender_name=dataSnapshot.child(current_user.getUid()).child("name").getValue(String.class);
               final String sender_profile=dataSnapshot.child(current_user.getUid()).child("user_profile").getValue(String.class);

               try {
                   OneSignal.postNotification(new JSONObject("{'contents':{'en':'" +

                                   sender_name+" have cheered you " +
                                   "'},'headings':{'en':'"+
                                   "Cheer For Peer"+
                                   "'},'include_player_ids': ['" +
                                   appId+
                                   "']}"),
                           new OneSignal.PostNotificationResponseHandler() {
                               @Override
                               public void onSuccess(JSONObject response) {
                                   android.text.format.DateFormat df = new android.text.format.DateFormat();
                                   CharSequence s= df.format("yyyy-MM-dd hh:mm:ss a", new Date());
                                   FirebaseDatabase.getInstance().getReference("Cheer_Notification")
                                           .push().setValue(new cheer_notification(sender_name+" have cheered you ",receiver_id,
                                           FirebaseAuth.getInstance().getCurrentUser().getUid(),s.toString(),sendCheer_cheerType,sender_profile,"unread" ));

                               }
                               @Override
                               public void onFailure(JSONObject response) {

                               }
                           });
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }

    void create_post(){


        this.sendCheer_userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                user sender=dataSnapshot.child(current_user.getUid()).getValue(user.class);
                user reciever=dataSnapshot.child(receiver_id).getValue(user.class);

                points=Integer.parseInt(reciever.getCheerpoints());


                if(sendCheer_cheerType=="0"){
                    points=points+30;
                }
                else if(sendCheer_cheerType=="1"){
                    points=points+60;
                }
                else if(sendCheer_cheerType=="2"){
                    points=points+90;
                }
                else if(sendCheer_cheerType=="3"){
                    points=points+110;
                }


                android.text.format.DateFormat df = new android.text.format.DateFormat();
                CharSequence s= df.format("yyyy-MM-dd hh:mm:ss a", new Date());
                post p=new post(sender.getName()+" Cheers "+reciever.getName(), current_user.getUid(),
                        receiver_id,sendCheer_cheerType,cheer_desc.getText().toString(),s.toString());

                String post_id= sendCheer_postsRef.push().getKey();
                sendCheer_postsRef.child(post_id).setValue(p);

                final HashMap hasmap=new HashMap();
                hasmap.put("cheerpoints",String.valueOf(points));

                sendCheer_userRef.child(receiver_id).updateChildren(hasmap);

                String sent;

                FirebaseDatabase.getInstance().getReference("points")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String sent=dataSnapshot.child(current_user.getUid())
                                        .child("cheer_point_sent").getValue(String.class);
                                String recieve=dataSnapshot.child(receiver_id)
                                        .child("cheer_point_received").getValue(String.class);
                                int i1=Integer.parseInt(sent);
                                int i2=Integer.parseInt(recieve);
                                i1=i1+1;
                                i2=i2+1;
                                HashMap h1=new HashMap();
                                h1.put("cheer_point_sent",String.valueOf(i1));
                                HashMap h2=new HashMap();
                                h2.put("cheer_point_received",String.valueOf(i2));
                                FirebaseDatabase.getInstance().getReference("points")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(h1);
                                FirebaseDatabase.getInstance().getReference("points")
                                        .child(receiver_id).updateChildren(h2);
                                FirebaseDatabase.getInstance().getReference("cheer_report")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                if(sendCheer_cheerType=="0"){
                                                    HashMap h=new HashMap();
                                                    h.put("cheer1",String.valueOf(Integer.parseInt(dataSnapshot.child("cheer1").getValue(String.class))+1));
                                                    FirebaseDatabase.getInstance().getReference("cheer_report")
                                                        .updateChildren(h);
                                                }
                                                else if(sendCheer_cheerType=="1"){
                                                    HashMap h=new HashMap();
                                                    h.put("cheer2", String.valueOf(Integer.parseInt(dataSnapshot.child("cheer2").getValue(String.class))+1));
                                                    FirebaseDatabase.getInstance().getReference("cheer_report")
                                                            .updateChildren(h);
                                                }
                                                else if(sendCheer_cheerType=="2"){
                                                    HashMap h=new HashMap();
                                                    h.put("cheer3",  String.valueOf(Integer.parseInt(dataSnapshot.child("cheer3").getValue(String.class))+1));
                                                    FirebaseDatabase.getInstance().getReference("cheer_report")
                                                            .updateChildren(h);
                                                }
                                                else if(sendCheer_cheerType=="3"){
                                                    HashMap h=new HashMap();
                                                    h.put("cheer4",  String.valueOf(Integer.parseInt(dataSnapshot.child("cheer4").getValue(String.class))+1));
                                                    FirebaseDatabase.getInstance().getReference("cheer_report")
                                                            .updateChildren(h);
                                                }

                                                HashMap hmap=new HashMap();
                                                hmap.put("total_cheers",  String.valueOf(Integer.parseInt(dataSnapshot.child("total_cheers").getValue(String.class))+1));
                                                FirebaseDatabase.getInstance().getReference("cheer_report")
                                                        .updateChildren(hmap);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(sendCheer.this, "Try again something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
/*
    private void status(String status) {

        if (current_user!=null){
            DatabaseReference databaseReference;
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(current_user.getUid());
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("status", status);
            databaseReference.updateChildren(hashMap);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
*/
}