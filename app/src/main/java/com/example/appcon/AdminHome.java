package com.example.appcon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminHome extends AppCompatActivity {
    private CardView profile,report,lboard,peers;
    private CircleImageView home_UserProfile;
    private String Image;
    private CircleImageView Drawer_Profile_Pic;
    private TextView Drawer_username,Drawer_email;

    //Firebase
    private FirebaseUser USER;
    private DatabaseReference UserRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        this.USER= FirebaseAuth.getInstance().getCurrentUser();
        this.UserRef= FirebaseDatabase.getInstance().getReference("users").child(this.USER.getUid());



        home_UserProfile=findViewById(R.id.home_UserProfile);
        profile=(CardView)findViewById(R.id.profile);
        lboard=(CardView)findViewById(R.id.leaderboard);
        report=(CardView)findViewById(R.id.report);
        peers=(CardView)findViewById(R.id.peers);

        this.read();

        home_UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(AdminHome.this, Admin_Profile.class);
                startActivity(it);
            }
        });
        lboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(AdminHome.this, Leaderboard.class);
                startActivity(it);
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHome.this, Report.class);
                intent.putExtra("Admin_Profile",Image);
                startActivity(intent);
            }
        });
        peers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        final NavigationView navView = (NavigationView) findViewById(R.id.Home_navigation); // initiate a Navigation View

        this.UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name=dataSnapshot.child("name").getValue(String.class);
                String email=dataSnapshot.child("email").getValue(String.class);
                String user_profile=dataSnapshot.child("user_profile").getValue(String.class);
                View hview=navView.getHeaderView(0);
                Drawer_Profile_Pic=(CircleImageView)hview
                        .findViewById(R.id.Home_profilePic);
                Drawer_username=(TextView) hview.findViewById(R.id.drawer_username);
                Drawer_email=(TextView)hview.findViewById(R.id.drawer_email);

                if(user_profile.matches("default")!=true){
                    Picasso.get().load(user_profile).into(Drawer_Profile_Pic);
                }
                else{
                    Picasso.get().load(R.drawable.user).into(Drawer_Profile_Pic);
                }
                Drawer_username.setText(name);
                Drawer_email.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId==R.id.dor_profile){
                    Intent intent = new Intent(AdminHome.this, UserProfile.class);
                    startActivity(intent);
                }
                if(itemId==R.id.dor_high_achiver){
                    Intent intent =new Intent(AdminHome.this,Leaderboard.class);
                    startActivity(intent);
                }
                if(itemId==R.id.dor_logout){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent =new Intent(AdminHome.this,Login.class);
                    startActivity(intent);
                    finish();
                }
                if(itemId==R.id.dor_policy){
                    Intent intent =new Intent(AdminHome.this,TermsAndConditions.class);
                    startActivity(intent);
                }
                if(itemId==R.id.dor_help){
                    Intent intent =new Intent(AdminHome.this,HelpCenter.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
    void read(){
        this.UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Image=dataSnapshot.child("user_profile").getValue(String.class);
                if(Image.matches("default")==false){
                    Picasso.get().load(Image)
                            .into(home_UserProfile);
                }
                else {
                    Picasso.get().load(R.drawable.user)
                            .into(home_UserProfile);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    void openDrawer(){
        DrawerLayout navDrawer = findViewById(R.id.Home_drawer);
        if(!navDrawer.isDrawerOpen(GravityCompat.START))
            navDrawer.openDrawer(GravityCompat.START);
        else
            navDrawer.closeDrawer(GravityCompat.END);
    }


}