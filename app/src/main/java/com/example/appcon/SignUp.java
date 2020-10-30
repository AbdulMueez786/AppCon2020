package com.example.appcon;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {

    private LinearLayout Signup_LinearLayout;
    private CircleImageView Signup_image;
    private EditText Signup_Password,Signup_email;
    private Button Signup_button;
    private FirebaseAuth auth;

    private FirebaseDatabase rootNode;
    private DatabaseReference user_ref;
    private DatabaseReference current_user_ref;
    private FirebaseUser USER;
    private TextView Signup_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        Signup_email=findViewById(R.id.Signup_email);
        Signup_Password=findViewById(R.id.Signup_Password);
        Signup_button=findViewById(R.id.Signup_button);
        Signup_Name=findViewById(R.id.Signup_Name);
        user_ref = FirebaseDatabase.getInstance().getReference("users");
        Signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String EMAIL=Signup_email.getText().toString();
                final String PASSWORD=Signup_Password.getText().toString();
                final String NAME=Signup_Name.getText().toString();

                auth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast toast = Toast.makeText(SignUp.this, "Successfully created", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                                    toast.show(); // display the Toast
                                    USER=FirebaseAuth.getInstance().getCurrentUser();
                                    String id=USER.getUid();
                                    user USE=new user(id,NAME,"0","0",EMAIL);
                                    user_ref.child(id).setValue(USE);
                                    Intent intent=new Intent(SignUp.this,Home.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast toast = Toast.makeText(SignUp.this, "Sorry some error occur please try again", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                                    toast.show(); // display the Toast
                                }

                            }
                        });
            }
        });

    }

    //validation function
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean checkDataEntered() {
        if (isEmpty(this.Signup_email)) {
            this.Signup_email.setError("Email is required!");
            return false;
        }
        if (isEmail(this.Signup_email)==false) {
            this.Signup_email.setError("Enter email in correct format!");
            return false;
        }
        if (isEmpty(this.Signup_Password)) {
            this.Signup_Password.setError("Password is required!");
            return false;
        }
        if(this.Signup_Password.length()<6){
            this.Signup_Password.setError("Password should be greater then 6 digit!");
            return false;
        }
      return true;
    }


}