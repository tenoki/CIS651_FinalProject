package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.google.firebase.Firebase.*;

/*This is the Login/Signup screen class.
* When the user opens the application for the first time or if the user logs out of the application,
* They will see this screen. The user must successfully log in to progress past this screen.*/
public class LoginSignup extends AppCompatActivity {
    //Class Variables.
    private EditText email, password, displayname, phonenumber;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    Button signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        email=findViewById(R.id.emailText);
        password=findViewById(R.id.passwordText);
        phonenumber=findViewById(R.id.phoneNumberText);
        displayname=findViewById(R.id.displayNameText);
        signupBtn=findViewById(R.id.singupBtn);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        updateUI();
    } //End onCreate callback function.


    /*This is a private class functions that updates the UI based on if the user is a successfully
    * register user or not. If successfully registered, then the user displayName and the user
    * phoneNumber EditText fields will disappear as they are not required login of verified users.*/
    private void updateUI(){
        //If the currentUser exists in the DB,
        if(currentUser!=null){
            //Remove these View objects from the login screen.
            findViewById(R.id.displayNameLayout).setVisibility(View.GONE);
            findViewById(R.id.phoneNumberLayout).setVisibility(View.GONE);
            signupBtn.setVisibility(View.GONE);
        } //End test if current user exists in the DB.
    } //End function updateUI.


    /*This is a private class function that saves the user's information to the realtime database
    * hosting on Google's Firebase.
    * This only is executed one the user's initial sign up.*/
    private void saveUserDataToDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("Users");
        usersRef.child(currentUser.getUid()).setValue(
                new User(displayname.getText().toString(),
                         email.getText().toString(),
                         phonenumber.getText().toString()));
    } //End function saveUserDataToDB


    /*This function allows a verified user to reset their password.
    * This function talks to the Firebase Authentication Module and and uses the
    * sendPasswordResetEmail build in API  call to initiate that transaction. */
    public void ResetPassword(View view) {
        //Test if the user has entered in their email address. We cannot do anything without it.
        if(email.getText().toString().equals("")){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return; //Return back to the original caller.
        } //End test if user provided an email address.

        //Assume that the user has an email address entered into the fields.
        mAuth.sendPasswordResetEmail(email.getText().toString())
                .addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginSignup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
        })
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(LoginSignup.this, "Email sent!", Toast.LENGTH_SHORT).show();
                }
        });
    } //End function ResetPassword.


    /*This function will send a verification email to a user trying to create a new account. It
    * will also resend a verification email to a user that has registered but has not been verified
    * by email yet.*/
    public void sendEmailVerification(View view) {
        //Ensure that the current user is logged in before trying to resend a verification email.
        if(mAuth.getCurrentUser()==null){
            Toast.makeText(this, "Please login first to resend verification email.", Toast.LENGTH_SHORT).show();
            return;
        } //End test if the user is currently logged in or not.

        //Use the current user's authorized instance to send the verification email.
        currentUser.sendEmailVerification()
                .addOnSuccessListener(LoginSignup.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(LoginSignup.this, "Verification email Setn!", Toast.LENGTH_SHORT).show();
                        updateUI();
                    }
                }).addOnFailureListener(LoginSignup.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginSignup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    } //End function sendEmailVerification


    /*This is the login function. If the user has not been verified and/or has not provided all of
    * the necessary fields for login then the user will be challenged.*/
    public void Login(View view) {

        //If the user's email address and/or password have not been provide
        if(email.getText().toString().equals("")|| password.getText().toString().equals("")){
            //Tell the user to fill in the required fields.
            Toast.makeText(this, "Please provide all information", Toast.LENGTH_SHORT).show();
            return;
        } //End required field entry check.

        //Assume that the user has provided the email address and password for login.
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        currentUser=authResult.getUser();

                        //If the current user has not been authorized for login.
                        if (currentUser == null){
                            // This should not happen
                            Toast.makeText(LoginSignup.this, "Error while handling your request. Please try again.", Toast.LENGTH_SHORT).show();
                            return;
                        } //End not authorized check.
                        //If the user has been verified by email for login...
                        if(currentUser.isEmailVerified()){ //for testing
                            Toast.makeText(LoginSignup.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginSignup.this, UserHome.class));
                            finish();
                        } //End user has been email verified check.
                        //Else the user is has not been email authorized yet.
                        else
                        {
                            Toast.makeText(LoginSignup.this, "Please verify your email and login again.", Toast.LENGTH_SHORT).show();
                        } //End user not email verified check.
                    } //End on Success path.

                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginSignup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }); //End on Failure path.
    } //End function Login.


    /*This is the sign up function for users that do not current have an account and have not
    * attempted to sign up yet. This function will not allow a user that has already created
    * account to try and sign up again with that account.*/
    public void Signup(View view) {
        //Check that the user has provided all of the required fields for initial sign up.
        if(email.getText().toString().equals("")|| password.getText().toString().equals("")
                || phonenumber.getText().toString().equals("")|| displayname.getText().toString().equals("")){
            Toast.makeText(this, "Please provide all information", Toast.LENGTH_SHORT).show();
            return; //Return to the original caller.
        } //End required signup fields check.

        //Assume that the user has provided information in all of the required fields.
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        currentUser=authResult.getUser();
                        if (currentUser == null){
                            // This should not happen
                            Toast.makeText(LoginSignup.this, "Error while handling your request. Please try again.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        currentUser.sendEmailVerification().addOnSuccessListener(LoginSignup.this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginSignup.this, "Signup successful. Verification email Sent!", Toast.LENGTH_SHORT).show();
                                saveUserDataToDB();
                                updateUI();
                            }
                        }).addOnFailureListener(LoginSignup.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginSignup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginSignup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    } //End function Signup.
} //End LoginSignup class definition and implementation