package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splash_screen extends AppCompatActivity {
    //Class Variables.
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    } //End onCreate callback function.


    @Override
    protected void onResume() {
        super.onResume();
        new CountDownTimer(3000, 1000) {


            public void onTick(long millisUntilFinished) {
                /*TODO: Current does nothing.*/
            } //End function onTick.


            public void onFinish() {
                //Check if the current user is authorized.
                if(currentUser==null){
                    Toast.makeText(splash_screen.this, "No user found", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(splash_screen.this ,LoginSignup.class));
                    finish();
                } //End current user is authorized check
                else{
                    //IF the user is an authorized user and email verified.
                    if(currentUser.isEmailVerified()) {
                        //Toast.makeText(MainActivity.this, "User already signed in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(splash_screen.this, UserHome.class));
                        finish();
                    } //End user is email verified check.
                    //Else the user must not be email verified yet.
                    else{
                        Toast.makeText(splash_screen.this, "Please verify your email and login.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(splash_screen.this, LoginSignup.class));
                        finish();
                    } //End user not email verified trap.
                } //End user is a valid/authorized user check.
            } //End function onFinish.
        }.start();
    } //End onResume callback function.
} //End MainActivity class definition and implementation.
