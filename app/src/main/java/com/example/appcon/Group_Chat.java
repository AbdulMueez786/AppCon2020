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

import com.example.appcon.adapter.GroupChatAdapter;
import com.example.appcon.model.GroupChat;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Group_Chat extends AppCompatActivity {
    private CircleImageView profile_image;
    private TextView username;
    private RecyclerView rv;
    private List<GroupChat> ls;
    private EditText text_send;
    private ImageView btn_send;
    private GroupChatAdapter messageAdapter;

    private int request_code=200;
    private Uri selectedImage_uri=null;
    private String message_Type="text";
    private ImageView attach_pic;

    //Firebase
    private FirebaseUser current_user;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group__chat);
        this.current_user = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        messageAdapter=new GroupChatAdapter(this,ls,"");
        rv=findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager lm=new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);

        read_message("");
        this.attach_pic=findViewById(R.id.attach_pic);
        this.attach_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message_Type="Image";
                openGallery();
            }
        });

        profile_image=findViewById(R.id.profile_image);
        Picasso.get().load(R.drawable.group).into(profile_image);
        username=findViewById(R.id.username);
        username.setText("Group Chat");



        text_send=findViewById(R.id.text_send);
        btn_send=findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=text_send.getText().toString();
                if(!msg.equals("")){
                    message_Type="text";
                    send_Message(current_user.getUid(),msg);
                }
                else if( message_Type.matches("Image")){
                    send_Message(current_user.getUid(),selectedImage_uri.toString());
                }
                else{


                }
                text_send.setText("");
            }
        });



    }

    private  void send_Message(String sender, final String message){
        if(this.message_Type.matches("text")==true) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            HashMap<String, Object> hm = new HashMap<>();

            android.text.format.DateFormat df = new android.text.format.DateFormat();
            CharSequence s= df.format("hh:mm a", new Date());

            hm.put("sender", FirebaseAuth.getInstance().getUid());
            hm.put("receiver", "null");
            hm.put("message", message);
            hm.put("Message_Type", message_Type);
            hm.put("Time",s);
            hm.put("Status","not_seen");
            String key=ref.child("Chats").push().getKey();
            hm.put("Message_id",key);
            ref.child("Group_Chat").child(key).setValue(hm);
        }
        else {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            String key=ref.push().getKey().toString();
            storageReference = storageReference.child("chatGroup/").child(key);
            storageReference.putFile(this.selectedImage_uri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(Group_Chat.this, "complited", Toast.LENGTH_LONG).show();
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
                            hm.put("receiver", "null");
                            hm.put("message", dp);
                            hm.put("Message_Type", message_Type);
                            hm.put("Time",s);
                            hm.put("Status","not_seen");
                            String key=ref.child("Chats").push().getKey();
                            hm.put("Message_id",key);
                            ref.child("Group_Chat").child(key).setValue(hm);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Group_Chat.this, "No internet,Try again", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void read_message(final String image_Url){
        ls=new ArrayList<>();
        messageAdapter =new GroupChatAdapter(this,ls,image_Url);
        rv.setAdapter(messageAdapter);
        reference=FirebaseDatabase.getInstance().getReference("Group_Chat");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ls.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    GroupChat c=snapshot.getValue(GroupChat.class);
                        ls.add(c);
                    messageAdapter.notifyDataSetChanged();
                }
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
            Toast.makeText(Group_Chat.this, "Image selected", Toast.LENGTH_LONG).show();
        }
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
    }
*/
}