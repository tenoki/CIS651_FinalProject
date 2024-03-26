package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    //Class Variables.
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    Button button_easy, button_medium, button_hard, button_exit;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    //Switch multiplayerSwitch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        button_easy = findViewById(R.id.button_easy);
        button_medium = findViewById(R.id.button_medium);
        button_hard = findViewById(R.id.button_hard);
        button_exit = findViewById(R.id.button_exit);

        //multiplayerSwitch = findViewById(R.id.multiplayer_switch);

        button_exit.setOnClickListener(v -> finish());

        /*multiplayerSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            button_easy.setEnabled(!isChecked); // Disable the easy button if multiplayer is checked
        });*/

        //onclick listener to goto easy layout using intent
        button_easy.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, easy.class);
            startActivity(intent);
        });

        //onclick listener to goto medium layout using intent
        button_medium.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, medium.class);
            //intent.putExtra("isMultiplayer", multiplayerSwitch.isChecked());
            startActivity(intent);
        });

        //onclick listener to goto hard layout using intent
        button_hard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, hard.class);
            //intent.putExtra("isMultiplayer", multiplayerSwitch.isChecked());
            startActivity(intent);
        });

    }

}

//the code for easy medium and hard are same, just the number of block size is increased as the difficulty level increased