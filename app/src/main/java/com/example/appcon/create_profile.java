package com.example.appcon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.appcon.model.profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class create_profile extends AppCompatActivity {
    private com.google.android.material.textfield.TextInputLayout create_profile_fname,create_profile_lname,
            create_profile_dob,create_profile_phone,create_profile_bio;
    private com.google.android.material.textview.MaterialTextView profile_gender_male,profile_gender_female,
            profile_gender_neither;
    private com.google.android.material.button.MaterialButton save_button;
    private com.google.android.material.floatingactionbutton.FloatingActionButton camera;
    private CircleImageView image_profile;
    private String  selected_gender="none";
    private int request_code=200;
    private Uri selectedImage_uri=null;

    //Firebase
    private FirebaseUser USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        USER=FirebaseAuth.getInstance().getCurrentUser();

        image_profile=findViewById(R.id.image_profile);
        camera=findViewById(R.id.camera);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


        create_profile_fname=findViewById(R.id.create_profile_fname);
        create_profile_lname=findViewById(R.id.create_profile_lname);
        create_profile_dob=findViewById(R.id.create_profile_dob);
        create_profile_phone=findViewById(R.id.create_profile_phone);
        create_profile_bio=findViewById(R.id.create_profile_bio);

        profile_gender_male=findViewById(R.id.profile_gender_male);
        profile_gender_female=findViewById(R.id.profile_gender_female);
        profile_gender_neither=findViewById(R.id.profile_gender_neither);


        //gender selectioon:
        profile_gender_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  selected_gender="male";
            }
        });

        profile_gender_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  selected_gender="female";
            }
        });
        profile_gender_neither.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   selected_gender="none";
            }
        });

        save_button=findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if all fields are filed
                if(checkDataEntered()==true){
                    StoreProfile();

                }
            }
        });

    }
    void openGallery(){
         Intent intent=new Intent();
         intent.setAction(Intent.ACTION_GET_CONTENT);
         intent.setType("image/*");
         startActivityForResult(intent,request_code);
    }


    void StoreProfile(){
        if(selectedImage_uri!=null){
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        storageReference=storageReference.child("profile/"+USER.getUid());
        storageReference.putFile(this.selectedImage_uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(create_profile.this, "complited", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String dp=uri.toString();


                        HashMap hasmap=new HashMap();
                        hasmap.put("user_profile",dp);
                        hasmap.put("name",create_profile_fname.getEditText().getText().toString());
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(USER.getUid()).updateChildren(hasmap)
                                .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                FirebaseDatabase.getInstance().getReference("profile")
                                        .child(USER.getUid()).setValue(new profile(
                                        create_profile_fname.getEditText().getText().toString(),
                                        create_profile_lname.getEditText().getText().toString(),
                                        create_profile_dob.getEditText().getText().toString(),
                                        selected_gender,
                                        create_profile_phone.getEditText().getText().toString(),
                                        create_profile_bio.getEditText().getText().toString(),
                                        dp
                                )).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        FirebaseDatabase.getInstance().getReference("users").child(USER.getUid())
                                                .addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        String s=dataSnapshot.child("name").getValue(String.class).toLowerCase();
                                                        if (s.matches("admin")){
                                                            Intent intent = new Intent(create_profile.this, AdminHome.class);
                                                            FirebaseAuth.getInstance().signOut();
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                        else{
                                                            Intent intent = new Intent(create_profile.this, Home.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                    }
                                });
                            }
                        });




                        Toast.makeText(create_profile.this, "Successfull", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(create_profile.this, "Failuer", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(create_profile.this, "No internet,Try again", Toast.LENGTH_LONG).show();
            }
        });

    } else{
            Toast.makeText(create_profile.this, "Please select your profile pic", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            this.selectedImage_uri=data.getData();
            this.image_profile.setImageURI(this.selectedImage_uri);
        }
    }

    // verification
    boolean isEmpty(com.google.android.material.textfield.TextInputLayout obj) {
        CharSequence str = obj.getEditText().getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean checkDataEntered() {
        if (isEmpty(this.create_profile_fname)) {
            this.create_profile_fname.setError("First Name is required!");
            return false;
        }
        if (isEmpty(this.create_profile_lname)) {
            this.create_profile_lname.setError("Last Name is required!");
            return false;
        }
        if (isEmpty(this.create_profile_dob)) {
            this.create_profile_dob.setError("DOB is required!");
            return false;
        }
        if (isEmpty(this.create_profile_phone)) {
            this.create_profile_phone.setError("PhoneNumber is required!");
            return false;
        }
        if (isEmpty(this.create_profile_bio)) {
            this.create_profile_bio.setError("Bio is required!");
            return false;
        }
        if(this.selected_gender.matches("")==true){
            Toast.makeText(create_profile.this, "Please select gender", Toast.LENGTH_LONG).show();
            return false;
        }
        if(selectedImage_uri==null){
            Toast.makeText(create_profile.this, "Please select your profile pic", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}