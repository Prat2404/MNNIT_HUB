package com.example.mnnit_hub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import CommonFiles.Users;

public class RegisterActivity extends AppCompatActivity {
    private EditText name,email,passwd,passwd2,phone;
    private Button register;
    private FirebaseAuth auth;
    private CheckBox terms;
    String username,useremail,userpasswd,userpasswd2,userphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.registerNameET);
        email=findViewById(R.id.registerEmailET);
        passwd=findViewById(R.id.registerPasswordET);
        passwd2=findViewById(R.id.registerPassword2ET);
        register=findViewById(R.id.registeruser);
        phone  = findViewById(R.id.registerPhoneET);
        terms=findViewById(R.id.loggedincheck);
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null)
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(terms.isChecked()) {
                        username = name.getText().toString().trim();
                        useremail = email.getText().toString().trim();
                        userpasswd = passwd.getText().toString().trim();
                        userpasswd2 = passwd2.getText().toString().trim();
                        userphone = phone.getText().toString().trim();

                   if(userpasswd.equals(userpasswd2))
                   {
                       if(username.equals(null) || username.equals("") || useremail.equals(null) || useremail.equals("") || userpasswd.equals(null) || userpasswd.equals(""))
                       {
                           Toast.makeText(getApplicationContext(),"Any field cannot be empty",Toast.LENGTH_SHORT).show();
                           passwd.setText("");
                           passwd2.setText("");
                       }
                       else
                       {
                           if(userpasswd.length()<8)
                           {
                               Toast.makeText(getApplicationContext(),"Password must be atleast 8 characters long",Toast.LENGTH_SHORT).show();
                               passwd.setText("");
                               passwd2.setText("");
                           }
                           else
                           {
                               auth.createUserWithEmailAndPassword(useremail,userpasswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       if(task.isSuccessful())
                                       {
                                           Users user = new Users(username,useremail,userphone);
                                           FirebaseDatabase.getInstance().getReference("Users")
                                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                   .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   if(task.isSuccessful())
                                                   {
                                                       Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_SHORT).show();
                                                       startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                       finish();
                                                   }
                                                   else
                                                   {
                                                       Toast.makeText(getApplicationContext(),"Userdatabase entry error",Toast.LENGTH_SHORT).show();


                                                   }

                                               }
                                           });

                                       }
                                       else
                                       {
                                           Toast.makeText(getApplicationContext(),"Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               });
                           }

                       }

                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(),"Password mismatches",Toast.LENGTH_SHORT).show();
                       passwd.setText("");
                       passwd2.setText("");
                   }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Kindly confirm terms and conditions",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void gotologinPage(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}