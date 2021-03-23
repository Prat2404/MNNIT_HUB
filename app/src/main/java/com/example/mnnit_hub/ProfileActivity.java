package com.example.mnnit_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;

import CommonFiles.*;

public class ProfileActivity extends AppCompatActivity {
  private TextView userName,userEmail,userNumber;
  private String rName,Email,Number;
    private FirebaseAuth auth;
    private  FirebaseDatabase fire;
    private  String Name;
    public static  Users user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userName=findViewById(R.id.profileName);
        userNumber=findViewById(R.id.profileNumber);
        userEmail=findViewById(R.id.profileEmail);
        fire=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        DatabaseReference userref= fire.getReference("Users");
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren())
                {
                    if(ds.getKey().equals(auth.getCurrentUser().getUid()))
                    {
                        userName.setText(ds.child("name").getValue().toString());
                        userEmail.setText(ds.child("email").getValue().toString());
                        userNumber.setText(ds.child("phone").getValue().toString());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       // userName.setText(Name);
    }
    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

}