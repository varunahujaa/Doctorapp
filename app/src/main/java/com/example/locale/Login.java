package com.example.locale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class Login extends AppCompatActivity  {

    TextView signUp;
    int p=0,q=0;
    EditText pNum;
    TextView useEmail;
    ProgressBar pbar;
    Button logIn;
    ScrollView mainLayout;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pNum = findViewById(R.id.phonenum);
        logIn =(Button) findViewById(R.id.login);
        pbar = findViewById(R.id.loading);
        mainLayout = (ScrollView)findViewById(R.id.container1);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phone = pNum.getText().toString();
                if (pNum.getText().toString().length()<10)
                    pNum.setError("Enter valid Phone Number");
                else {
                    pbar.setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference().child("Patients")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        pbar.setVisibility(View.GONE);
                                        Customers c = snapshot.getValue(Customers.class);
                                        if(c.getPhonenum().equals(phone)) {
                                            p=1;
                                            intent = new Intent(Login.this, OTP.class);
                                            intent.putExtra("type","Patient");
                                            intent.putExtra("phonenumber", "+91" + phone);
                                            startActivity(intent);
                                            break;
                                        }
                                        else
                                            p=0;
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });




                    FirebaseDatabase.getInstance().getReference().child("Doctors_type")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        ServiceProviders c = snapshot.getValue(ServiceProviders.class);

                                        if(c.getPhonenum().toString().equals(phone)) {
                                            q=1;
                                            pbar.setVisibility(View.GONE);
                                            Intent intent = new Intent(Login.this, OTP.class);
                                            intent.putExtra("type","Doctors_type");
                                            intent.putExtra("phonenumber","+91"+phone);
                                            startActivity(intent);
                                            break;
                                        }
                                        else
                                            q=0;

                                    }
                                    if(p==0 && q==0){
                                        pbar.setVisibility(View.GONE);
                                        Toast.makeText(Login.this, "Mobile Number not registered. Sign up", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                }
            }

        });

        useEmail = (TextView) findViewById(R.id.email);
        useEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent in1 = new Intent(Login.this, EmailLogin.class);
                startActivity(in1);
                finish();
            }
        });

        signUp = (TextView) findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
            }
        });
    }
//    @Override
//    public void onBackPressed(){
//
//        finish();
//    }
}
