package com.example.appcon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcon.adapter.MessageAdapter;
import com.example.appcon.model.Chat;
import com.example.appcon.model.cheer_notification;
import com.example.appcon.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private CircleImageView profile_image;
    private TextView username;
    private FirebaseUser fuser;
    private DatabaseReference reference;
    private MessageAdapter messageAdapter;
    private Intent intent;
    private RecyclerView rv;
    private List<Chat> ls;
    private String userid="";
    private EditText text_send;
    private ImageView btn_send;

    private int request_code=200;
    private Uri selectedImage_uri=null;
    private String message_Type="text";
    private ImageView attach_pic;
    private FirebaseUser current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        current_user =FirebaseAuth.getInstance().getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.attach_pic=findViewById(R.id.attach_pic);
        this.attach_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message_Type="Image";
                openGallery();
            }
        });

        rv=findViewById(R.id.rv);

        rv.setHasFixedSize(true);

        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);
        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);
        intent=getIntent();
        this.userid=intent.getStringExtra("userid");
       System.out.println("USER ID ->"+userid);

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("users").child(userid);
        text_send=findViewById(R.id.text_send);
        btn_send=findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=text_send.getText().toString();
                if(!msg.equals("")){
                    message_Type="text";
                    sendmessage(fuser.getUid(),userid,msg);
                }
                else if( message_Type.matches("Image")){
                    sendmessage(fuser.getUid(),userid,selectedImage_uri.toString());
                }
                else{


                }
                text_send.setText("");
            }
        });



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user u=dataSnapshot.getValue(user.class);
                username.setText(u.getName());
                if (u.getUser_profile().equals("default")!=true){
                    Picasso.get().load(u.getUser_profile())
                            .into(profile_image);
                }
                else{
                    Picasso.get().load(R.drawable.user)
                            .into(profile_image);
                }
                readmessage(fuser.getUid(),userid,u.getUser_profile());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void openGallery(){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,request_code);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            this.selectedImage_uri=data.getData();
            Toast.makeText(MessageActivity.this, "Image selected", Toast.LENGTH_LONG).show();
        }
    }

    private  void  sendmessage(String sender, String reciever, final String message){
    if(this.message_Type.matches("text")==true) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hm = new HashMap<>();

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        CharSequence s= df.format("hh:mm a", new Date());

        hm.put("sender", FirebaseAuth.getInstance().getUid());
        hm.put("receiver", userid);
        hm.put("message", message);
        hm.put("Message_Type", message_Type);
        hm.put("Time",s);
        hm.put("Status","not_seen");
        String key=ref.child("Chats").push().getKey();
        hm.put("Message_id",key);
        ref.child("Chats").child(key).setValue(hm);

        ref.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String appId=dataSnapshot.child(userid).child("app_id").getValue(String.class);
                final String sender_name=dataSnapshot.child(current_user.getUid()).child("name").getValue(String.class);
                final String sender_profile=dataSnapshot.child(current_user.getUid()).child("user_profile").getValue(String.class);

                try {
                    OneSignal.postNotification(new JSONObject("{'contents':{'en':'" +

                                    sender_name+" have messaged you " +
                                    "'},'headings':{'en':'"+
                                    "Cheer For Peer"+
                                    "'},'include_player_ids': ['" +
                                    appId+
                                    "']}"),
                            new OneSignal.PostNotificationResponseHandler() {
                                @Override
                                public void onSuccess(JSONObject response) {


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
    else {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String key=ref.push().getKey().toString();
        storageReference = storageReference.child("chat/").child(key);
        storageReference.putFile(this.selectedImage_uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(MessageActivity.this, "complited", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        android.text.format.DateFormat df = new android.text.format.DateFormat();
                        CharSequence s= df.format("hh:mm a", new Date());
                        final String dp=uri.toString();
                        HashMap<String, Object> hm = new HashMap<>();
                        hm.put("sender", FirebaseAuth.getInstance().getUid());
                        hm.put("receiver", userid);
                        hm.put("message", dp);
                        hm.put("Message_Type", message_Type);
                        hm.put("Time",s);
                        hm.put("Status","not_seen");
                        String key=ref.child("Chats").push().getKey();
                        hm.put("Message_id",key);
                        ref.child("Chats").child(key).setValue(hm);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MessageActivity.this, "No internet,Try again", Toast.LENGTH_LONG).show();
            }
        });

        }
    }

    private void readmessage(final String myid, final String user_id, final String image_Url){
        ls=new ArrayList<>();
        messageAdapter =new MessageAdapter(MessageActivity.this,ls,image_Url);
        rv.setAdapter(messageAdapter);
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ls.clear();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chat c=snapshot.getValue(Chat.class);

                    if((c.getReceiver().equals(myid)==true && c.getSender().equals(user_id)==true) ||
                            (c.getReceiver().equals(user_id) && c.getSender().equals(myid))
                    ){
                        ls.add(c);
                    }

                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
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
    }*/
}