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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class sendCheer extends AppCompatActivity {


    private ImageView sendCheer_button;
    private Button sendCheer_sendButton;
    private String sendCheer_cheerType;
    private EditText cheer_desc;


    public static float screen_width;
    private String receiver_id;
    private int points;

    //Firebase
    private DatabaseReference sendCheer_postsRef;
    private DatabaseReference sendCheer_userRef;
    private FirebaseUser sendCheer_authUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_cheer);

        sendCheer_button = findViewById(R.id.reactButton);
        sendCheer_button.setImageResource(R.drawable.cheer4);
        sendCheer_sendButton =findViewById(R.id.sendreact);
        sendCheer_sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_post();
                Toast.makeText(sendCheer.this, "cheer sent", Toast.LENGTH_SHORT).show();
            }
        });
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
    void create_post(){
        sendCheer_authUser = FirebaseAuth.getInstance().getCurrentUser();

        this.sendCheer_userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user sender=dataSnapshot.child(sendCheer_authUser.getUid()).getValue(user.class);
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

                post p=new post(sender.getName()+" Cheers "+reciever.getName(), sendCheer_authUser.getUid(),receiver_id,sendCheer_cheerType,cheer_desc.getText().toString());

                String post_id= sendCheer_postsRef.push().getKey();
                sendCheer_postsRef.child(post_id).setValue(p);
                HashMap hasmap=new HashMap();
                hasmap.put("cheerpoints",String.valueOf(points));
                sendCheer_userRef.child(receiver_id).updateChildren(hasmap);
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(sendCheer.this, "Try again something went wrong", Toast.LENGTH_SHORT).show();
            }
        });




    }

}