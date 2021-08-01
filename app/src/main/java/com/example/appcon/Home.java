package com.example.appcon;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.appcon.Fragments.FragmentHome;
import com.example.appcon.Fragments.FragmentList;
import com.example.appcon.Fragments.FragmentMessage;
import com.example.appcon.Fragments.NotificationFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {
    private MeowBottomNavigation meo;
    private final static int ID_HOME=1;
    private final static int ID_SEARCH=2;
    private final static int ID_NOTIFICATION=3;
    private final static int ID_MESSAGE=4;
    private NavigationView navView;
    private de.hdodenhof.circleimageview.CircleImageView HomeProfile;

    private CircleImageView Drawer_Profile_Pic;
    private TextView Drawer_username,Drawer_email;

    //Firebase
    private FirebaseUser current_user=null;
    private DatabaseReference UserRef;
    private DatabaseReference NotifRef;
    public  int notification_unread=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final NavigationView navView = (NavigationView) findViewById(R.id.Home_navigation); // initiate a Navigation View

        this.current_user =FirebaseAuth.getInstance().getCurrentUser();
        this.UserRef= FirebaseDatabase.getInstance()
                .getReference("users").child(this.current_user.getUid());
        this.NotifRef=FirebaseDatabase.getInstance().getReference("Cheer_Notification");
        this.unread_notification();
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
                    Intent intent = new Intent(Home.this, UserProfile.class);
                    startActivity(intent);
                }
                if(itemId==R.id.dor_high_achiver){
                    Intent intent =new Intent(Home.this,Leaderboard.class);
                    startActivity(intent);
                }
                if(itemId==R.id.dor_logout){
                    //status("offline");
                    FirebaseAuth.getInstance().signOut();
                    Intent intent =new Intent(Home.this,Login.class);
                    startActivity(intent);
                    finish();
                }
                if(itemId==R.id.dor_policy){
                    Intent intent =new Intent(Home.this,TermsAndConditions.class);
                    startActivity(intent);
                }
                if(itemId==R.id.dor_help){
                    Intent intent =new Intent(Home.this,HelpCenter.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        System.out.println("________________________________Home_____________________________________");


        meo=(MeowBottomNavigation)findViewById(R.id.bottom_nav);
        meo.add(new MeowBottomNavigation.Model(1,R.drawable.home));
        meo.add(new MeowBottomNavigation.Model(2,R.drawable.search));
        meo.add(new MeowBottomNavigation.Model(3,R.drawable.notification));
        meo.add(new MeowBottomNavigation.Model(4,R.drawable.message));




        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();

        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(getApplicationContext(),"Clicked item"+item.getId(),Toast.LENGTH_SHORT).show();
            }
        });
        meo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

        FrameLayout f=findViewById(R.id.fragment_container);

        meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Fragment select_fragment=null;
                switch (item.getId()){
                    case ID_HOME:

                        select_fragment=new FragmentHome();
                        break;
                    case ID_SEARCH:
                        select_fragment=new FragmentList();
                        break;
                    case ID_NOTIFICATION:
                        select_fragment=new NotificationFragment();
                        meo.clearCount(3);
                        notification_readed();
                        break;

                    case ID_MESSAGE:
                        select_fragment=new FragmentMessage();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,select_fragment).commit();
            }
        });
        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment select_fragment;
                switch (item.getId()){
                    case ID_HOME:
                        System.out.println("Home");
                        select_fragment=new FragmentHome();
                        break;
                    case ID_SEARCH:
                        System.out.println("login");
                        select_fragment=new FragmentList();
                        break;
                    case ID_NOTIFICATION:
                        System.out.println("about");
                        select_fragment=new NotificationFragment();
                        meo.clearCount(3);
                        notification_readed();
                        break;

                        case ID_MESSAGE:
                        select_fragment=new FragmentMessage();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getId());
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,select_fragment).commit();
            }
        });

    }
void unread_notification(){

        this.NotifRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int counter=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                       if(snapshot.child("notification_status").getValue(String.class).matches("unread")==true &&
                               snapshot.child("reciever_id").getValue(String.class).matches(current_user.getUid())==true
                       ){
                          counter++;
                       }
                   }

                notification_unread=counter;
                System.out.println(notification_unread);
                if(notification_unread!=0){
                    meo.setCount(3,String.valueOf(notification_unread));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}
void notification_readed(){
    this.NotifRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                if(snapshot.child("notification_status").getValue(String.class).matches("unread")==true &&
                        snapshot.child("reciever_id").getValue(String.class).matches(current_user.getUid())==true
                ){
                    HashMap h1=new HashMap();
                    h1.put("notification_status","read");
                    NotifRef.child(snapshot.getKey()).updateChildren(h1);
                    System.out.println("working");
                }
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
    }
    */
}