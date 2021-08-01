package com.example.appcon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class peerProfile extends AppCompatActivity {
private TextView peer_name,peer_cheerPoints;
private ImageView peer_medal;
private Button Send_Cheer;
private CircleImageView peer_profile;
private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peer_profile);
        peer_name=findViewById(R.id.peer_name);
        peer_cheerPoints=findViewById(R.id.peer_cheerPoints);
        peer_medal=findViewById(R.id.peer_medal);
        peer_profile=findViewById(R.id.peer_profile);
        Send_Cheer=findViewById(R.id.Send_Cheer);
        final String id=getIntent().getStringExtra("id");
        String ImageUrl=getIntent().getStringExtra("image_url");
        final String name=getIntent().getStringExtra("name");
        String cheerpoints=getIntent().getStringExtra("cheerpoints");
        String medal=getIntent().getStringExtra("medal");

        if(medal=="0") {
            peer_medal.setImageResource(R.drawable.medal);
        }
        peer_name.setText(name);
        peer_cheerPoints.setText(cheerpoints);

        Send_Cheer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(peerProfile.this,sendCheer.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }
}