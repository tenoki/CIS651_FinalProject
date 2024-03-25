package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class easy extends AppCompatActivity {
    private final Handler handler = new Handler();
    private Runnable flipBackFirstCard;

    private TextView time_view;
    private int secondsElapsed = 0;
    private final Handler timerHandler = new Handler();
    private Runnable timerRunnable;

    ImageView block_1, block_2, block_3, block_4;
    Integer[] blocks = {101, 102, 201, 202};
    int b1, b2, b11, b12;
    int fClick, sClick, first, second, click=1, point=0;
    Button button_exit;

    //Firebase specific variables.
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String playerDisplayName = "anonymous";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        View rootView = findViewById(android.R.id.content);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        rootView.startAnimation(fadeInAnimation);

        define();
        Collections.shuffle(Arrays.asList(blocks));
        showCardsTemporarily(); // Temporarily show the cards to the player
        new Handler().postDelayed(this::flipAllCardsBack, 1000); // Set a delay for flipping cards back to the cover
        startListeners();

        // Timer setup
        time_view = findViewById(R.id.time_view);
        timerRunnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                secondsElapsed++;
                time_view.setText("" + secondsElapsed);
                timerHandler.postDelayed(this, 1000);
            }
        };
        //timerHandler.postDelayed(timerRunnable, 1000);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
       
    }

    protected void onResume() {
        super.onResume();
        timerHandler.postDelayed(timerRunnable, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void flipAllCardsBack() {
        flipCardBack(block_1);
        flipCardBack(block_2);
        flipCardBack(block_3);
        flipCardBack(block_4);
        /*block_1.setImageResource(R.drawable.block);
        block_2.setImageResource(R.drawable.block);
        block_3.setImageResource(R.drawable.block);
        block_4.setImageResource(R.drawable.block);*/

    }

    private void flipCardBack(final ImageView block) {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(block, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(block, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.setDuration(100);
        oa2.setDuration(100);

        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                block.setImageResource(R.drawable.block); // Reset to default image
                oa2.start();
            }
        });
        oa1.start();
    }

    private void showCardsTemporarily() {
        ImageView[] blockViews = new ImageView[]{block_1, block_2, block_3, block_4};
        for (int i = 0; i < blocks.length; i++) {
            int blockId = blocks[i];
            ImageView blockView = blockViews[i];
            if (blockId == 101 || blockId == 201) {
                blockView.setImageResource(b1);
            } else if (blockId == 102 || blockId == 202) {
                blockView.setImageResource(b2);
            }
        }
    }

    //the startListeners method will set teh images to set on click, so that when clicked it would show the image behind it
    private void startListeners() {

        button_exit.setOnClickListener(v -> finish());

        block_1.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_1, image);
        });

        block_2.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_2, image);
        });

        block_3.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_3, image);
        });

        block_4.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_4, image);
        });

    }

    private void flipCard(final ImageView block, final int image) {
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(block, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(block, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.setDuration(100);
        oa2.setDuration(100);

        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setImg(block, image);
                oa2.start();
            }
        });
        oa1.start();
    }

    private void setImg(ImageView block, int image) {

        point++;
        //this switch will set the image as per it's code, 101 - 201 and 102-202 are the image pairs
        switch (blocks[image]) {
            case 101: block.setImageResource(b1);break;
            case 102: block.setImageResource(b2);break;
            case 201: block.setImageResource(b11);break;
            case 202: block.setImageResource(b12);break;
        }

        //this will check which block is been clicked using a switch 'click'
        //if the click value is 1 and we get the image with value greater than 200 we will change it's value so that it matches with it's pair
        if (click == 1){
            fClick = blocks[image];
            if (fClick > 200)
                fClick -= 100;
            click = 2;
            first = image;
            block.setEnabled(false);

            // Prepare to flip back the first card if no second guess within 3 seconds
            flipBackFirstCard = () -> {
                block.setImageResource(R.drawable.block);
                block.setEnabled(true);
                click = 1;
            };
            handler.postDelayed(flipBackFirstCard, 1500);
        //if the click value is 2, it means we have already clicked 1 time now this is our 2nd click and we will try and match the image using a handler
        } else if (click == 2){
            handler.removeCallbacks(flipBackFirstCard); // Cancel the flip back if the second card is clicked
            sClick = blocks[image];
            if (sClick > 200)
                sClick -= 100;
            click = 1;
            second = image;
            setState(false);

            Handler handler = new Handler();
            handler.postDelayed(this::calc, 500);
        }
    }

    private void calc() {
        //point++;
        //here we are checking if we have found a match and then as per the tag value we will make it invisible if we have found a match using the first and second click tag values
        if (fClick == sClick){
            setBlockInvisible(first);
            setBlockInvisible(second);
            //point++;
        }
        else{
            //point++;
            //flipAllCardsBack();
            ImageView firstBlock = getBlockByIndex(first);
            ImageView secondBlock = getBlockByIndex(second);

            if (firstBlock != null && secondBlock != null) {
                flipCardBack(firstBlock);
                flipCardBack(secondBlock);
            }
        }

        setState(true);
        ifEnd();
    }

    private ImageView getBlockByIndex(int index) {
        switch (index) {
            case 0: return block_1;
            case 1: return block_2;
            case 2: return block_3;
            case 3: return block_4;
        }
        return null;
    }

    private void setBlockInvisible(int block) {
        switch (block){
            case 0: block_1.setVisibility(View.INVISIBLE); break;
            case 1: block_2.setVisibility(View.INVISIBLE); break;
            case 2: block_3.setVisibility(View.INVISIBLE); break;
            case 3: block_4.setVisibility(View.INVISIBLE); break;
        }
    }

    //what if the click is last click?
    private void ifEnd() {
        if (block_1.getVisibility() == View.INVISIBLE && block_2.getVisibility() == View.INVISIBLE && block_3.getVisibility() == View.INVISIBLE && block_4.getVisibility() == View.INVISIBLE){
            timerHandler.removeCallbacks(timerRunnable);
            AlertDialog.Builder alert = getAlert();
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }
    }

    @NonNull
    private AlertDialog.Builder getAlert() {
        AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(easy.this);
        alert.setMessage("Game Over\nScore: " + (point+secondsElapsed-1));
        alert.setCancelable(false);
        alert.setPositiveButton("NEW GAME", (dialog, which) -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
        alert.setNegativeButton("EXIT", (dialog, which) -> {
            Intent intent = new Intent(getApplicationContext(), UserHome.class);
            startActivity(intent);
            finish();
        });

        //Get the current user's information so that we can use it to insert the user's score.
        Log.d("Building Score Entry", "Start...");

        //We need to insert the score into the database for this specific user.
        int score_value = (point+secondsElapsed-1);
        Score playerScore = new Score( score_value, currentUser.getUid(), currentUser.getDisplayName(), "Easy");
        this.saveScoreToDB( playerScore );
        return alert;
    }


    public String getPlayerDisplayName(){
        String currentPlayerDisplayName = "anonymous";

        //Get the current user's display name.
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Log.d("CURRENT_USER_KEY", snapshot.getKey().toString() );
                    //If the UUID of the current user matches a UUID in the list,
                    if( currentUser.getUid().equalsIgnoreCase( snapshot.getKey())   ){

                    }
                    String userDisplayName = snapshot.child("displayname").getValue().toString();

                    Log.d("DISPLAY_NAME", userDisplayName);
                } //End callback function onDataChange.
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return playerDisplayName;
    }
    public void saveScoreToDB( Score playerScore ) {


        //String userDisplayName = userReference.child( currentUser.getUid() ).child("displayname").get().getResult().toString();
        //Log.d("DISPLAY_NAME", userDisplayName  );

        //Insert the current user's score into the DB.
        FirebaseDatabase scoreDatabase = FirebaseDatabase.getInstance();
        DatabaseReference scoresReference = scoreDatabase.getReference("Scores");
        DatabaseReference newScoreReference = scoresReference.push();
        Log.d("CURRENT_USER", currentUser.getDisplayName().toString() );
        playerScore.setPlayerName( currentUser.getDisplayName().toString());
        playerScore.setMode( "Easy" );
        scoresReference.child( newScoreReference.getKey() ).setValue( playerScore );
//        scoresReference.child( newScoreReference.getKey() ).setValue( playerScore.UUID );
//        scoresReference.child( newScoreReference.getKey() ).setValue( playerScore.score_value );
//        scoresReference.child( newScoreReference.getKey() ).setValue( playerScore.mode );
//        scoresReference.child( newScoreReference.getKey() ).setValue( playerScore.timeStamp );
    } //End function saveScoreToDB


    private void setState(boolean b) {
        block_1.setEnabled(b);
        block_2.setEnabled(b);
        block_3.setEnabled(b);
        block_4.setEnabled(b);
    }


    //this method defines all the values and images and matches it as per the tags and ids
    private void define (){

        block_1 = findViewById(R.id.block_1);
        block_2 = findViewById(R.id.block_2);
        block_3 = findViewById(R.id.block_3);
        block_4 = findViewById(R.id.block_4);

        button_exit = findViewById(R.id.button_exit);

        b1 = R.drawable.b1;
        b2 = R.drawable.b2;
        b11 = R.drawable.b1;
        b12 = R.drawable.b2;

    }

}