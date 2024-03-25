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
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class medium extends AppCompatActivity {

    private final Handler handler = new Handler();
    private Runnable flipBackFirstCard;

    private TextView time_view;
    private int secondsElapsed = 0;
    private final Handler timerHandler = new Handler();
    private Runnable timerRunnable;

    ImageView block_1, block_2, block_3, block_4, block_5, block_6, block_7, block_8, block_9, block_10, block_11, block_12;
    Integer[] blocks = {101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206};
    int b1, b2, b3, b4, b11, b12, b13, b14, b21, b22, b23, b24;
    int fClick, sClick, first, second, click=1, point=0;
    Button button_exit;
    //boolean isMultiplayer = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium);

        View rootView = findViewById(android.R.id.content);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        rootView.startAnimation(fadeInAnimation);

        define();
        Collections.shuffle(Arrays.asList(blocks));
        showCardsTemporarily();
        new Handler().postDelayed(this::flipAllCardsBack, 3000);
        startListeners();

        // Timer setup
        time_view = findViewById(R.id.time_view1);
        timerRunnable = new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                secondsElapsed++;
                time_view.setText("" + secondsElapsed);
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.postDelayed(timerRunnable, 1000);

    }

    private void flipAllCardsBack() {
        flipCardBack(block_1);
        flipCardBack(block_2);
        flipCardBack(block_3);
        flipCardBack(block_4);
        flipCardBack(block_5);
        flipCardBack(block_6);
        flipCardBack(block_7);
        flipCardBack(block_8);
        flipCardBack(block_9);
        flipCardBack(block_10);
        flipCardBack(block_11);
        flipCardBack(block_12);
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
        ImageView[] blockViews = new ImageView[]{block_1, block_2, block_3, block_4, block_5, block_6, block_7, block_8, block_9, block_10, block_11, block_12};
        for (int i = 0; i < blocks.length; i++) {
            int blockId = blocks[i];
            ImageView blockView = blockViews[i];
            if (blockId == 101 || blockId == 201) {
                blockView.setImageResource(b1);
            } else if (blockId == 102 || blockId == 202) {
                blockView.setImageResource(b2);
            }else if (blockId == 103 || blockId == 203) {
                blockView.setImageResource(b3);
            }else if (blockId == 104 || blockId == 204) {
                blockView.setImageResource(b4);
            }else if (blockId == 105 || blockId == 205) {
                blockView.setImageResource(b11);
            }else if (blockId == 106 || blockId == 206) {
                blockView.setImageResource(b12);
            }
        }
    }

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

        block_5.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_5, image);
        });

        block_6.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_6, image);
        });

        block_7.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_7, image);
        });

        block_8.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_8, image);
        });

        block_9.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_9, image);
        });

        block_10.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_10, image);
        });

        block_11.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_11, image);
        });

        block_12.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_12, image);
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
                // This is where you change the image when the ImageView is flipped halfway
                setImg(block, image);
                oa2.start();
            }
        });
        oa1.start();
    }


    private void setImg(ImageView block, int image) {
        point++;
        switch (blocks[image]) {
            case 101: block.setImageResource(b1);break;
            case 102: block.setImageResource(b2);break;
            case 103: block.setImageResource(b3);break;
            case 104: block.setImageResource(b4);break;
            case 105: block.setImageResource(b11);break;
            case 106: block.setImageResource(b12);break;
            case 201: block.setImageResource(b13);break;
            case 202: block.setImageResource(b14);break;
            case 203: block.setImageResource(b21);break;
            case 204: block.setImageResource(b22);break;
            case 205: block.setImageResource(b23);break;
            case 206: block.setImageResource(b24);break;
        }

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
        if (fClick == sClick){
            setBlockInvisible(first);
            setBlockInvisible(second);
            //point++;
            //score.setText(point);
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
            case 4: return block_5;
            case 5: return block_6;
            case 6: return block_7;
            case 7: return block_8;
            case 8: return block_9;
            case 9: return block_10;
            case 10: return block_11;
            case 11: return block_12;
        }
        return null;
    }

    private void setBlockInvisible(int block) {
        switch (block){
            case 0: block_1.setVisibility(View.INVISIBLE); break;
            case 1: block_2.setVisibility(View.INVISIBLE); break;
            case 2: block_3.setVisibility(View.INVISIBLE); break;
            case 3: block_4.setVisibility(View.INVISIBLE); break;
            case 4: block_5.setVisibility(View.INVISIBLE); break;
            case 5: block_6.setVisibility(View.INVISIBLE); break;
            case 6: block_7.setVisibility(View.INVISIBLE); break;
            case 7: block_8.setVisibility(View.INVISIBLE); break;
            case 8: block_9.setVisibility(View.INVISIBLE); break;
            case 9: block_10.setVisibility(View.INVISIBLE);break;
            case 10: block_11.setVisibility(View.INVISIBLE);break;
            case 11: block_12.setVisibility(View.INVISIBLE);break;
        }
    }

    private void ifEnd() {
        if (block_1.getVisibility() == View.INVISIBLE && block_2.getVisibility() == View.INVISIBLE && block_3.getVisibility() == View.INVISIBLE && block_4.getVisibility() == View.INVISIBLE &&
                block_5.getVisibility() == View.INVISIBLE && block_6.getVisibility() == View.INVISIBLE && block_7.getVisibility() == View.INVISIBLE && block_8.getVisibility() == View.INVISIBLE &&
                block_9.getVisibility() == View.INVISIBLE && block_10.getVisibility() == View.INVISIBLE && block_11.getVisibility() == View.INVISIBLE && block_12.getVisibility() == View.INVISIBLE){
            timerHandler.removeCallbacks(timerRunnable);
            AlertDialog alertDialog = getAlertDialog();
            alertDialog.show();
        }
    }

    @NonNull
    private AlertDialog getAlertDialog() {
        AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(medium.this);
        alert.setMessage("Game Over\nScore: " + (point+secondsElapsed-3));
        alert.setCancelable(false);
        alert.setPositiveButton("NEW GAME", (dialog, which) -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
        alert.setNegativeButton("EXIT", (dialog, which) -> finish());
        return alert.create();
    }

    private void setState(boolean b) {
        block_1.setEnabled(b);
        block_2.setEnabled(b);
        block_3.setEnabled(b);
        block_4.setEnabled(b);
        block_5.setEnabled(b);
        block_6.setEnabled(b);
        block_7.setEnabled(b);
        block_8.setEnabled(b);
        block_9.setEnabled(b);
        block_10.setEnabled(b);
        block_11.setEnabled(b);
        block_12.setEnabled(b);
    }

    private void define (){

        block_1 = findViewById(R.id.block_1);
        block_2 = findViewById(R.id.block_2);
        block_3 = findViewById(R.id.block_3);
        block_4 = findViewById(R.id.block_4);
        block_5 = findViewById(R.id.block_5);
        block_6 = findViewById(R.id.block_6);
        block_7 = findViewById(R.id.block_7);
        block_8 = findViewById(R.id.block_8);
        block_9 = findViewById(R.id.block_9);
        block_10 = findViewById(R.id.block_10);
        block_11 = findViewById(R.id.block_11);
        block_12 = findViewById(R.id.block_12);

        button_exit = findViewById(R.id.button_exit);

        b1 = R.drawable.b1;
        b2 = R.drawable.b2;
        b3 = R.drawable.b3;
        b4 = R.drawable.b4;
        b11 = R.drawable.b5;
        b12 = R.drawable.b6;
        b13 = R.drawable.b1;
        b14 = R.drawable.b2;
        b21 = R.drawable.b3;
        b22 = R.drawable.b4;
        b23 = R.drawable.b5;
        b24 = R.drawable.b6;


    }

}