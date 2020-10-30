package com.example.appcon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Login extends AppCompatActivity {

   private LinearLayout login_LinearLayout;
   private CircleImageView login_image;
   private TextView Login_title,login_ForgetPasswordLabel;
   private EditText login_email,login_Password;
   private Button login_button;
   private FirebaseAuth auth;
   private TextView login_signinlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        login_button=findViewById(R.id.login_button);
        login_signinlink=findViewById(R.id.login_signinlink);
        login_signinlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });

        login_email=findViewById(R.id.login_email);

        login_Password=findViewById(R.id.login_Password);



        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=login_email.getText().toString();
                final String password=login_Password.getText().toString();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent=new Intent(Login.this,Home.class);
                                    startActivity(intent);
                                } else {
                                    Toast toast = Toast.makeText(Login.this, "Sorry some error occur please try again", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                                    toast.show(); // display the Toast
                                }

                            }
                        });
                }
            });





    }

}