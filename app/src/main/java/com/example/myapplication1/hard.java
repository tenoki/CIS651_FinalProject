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

public class hard extends AppCompatActivity {

    private final Handler handler = new Handler();
    private Runnable flipBackFirstCard;

    private TextView time_view;
    private int secondsElapsed = 0;
    private final Handler timerHandler = new Handler();
    private Runnable timerRunnable;

    ImageView block_1, block_2, block_3, block_4, block_5, block_6, block_7, block_8, block_9, block_10, block_11, block_12, block_13, block_14, block_15, block_16, block_17, block_18, block_19, block_20, block_21, block_22, block_23, block_24, block_25, block_26, block_27, block_28, block_29, block_30;
    Integer[] blocks = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215};
    int b1, b2, b3, b4, b5, b11, b12, b13, b14, b15, b21, b22, b23, b24, b25, b31, b32, b33, b34, b35, b41, b42, b43, b44, b45, b51, b52, b53, b54, b55;
    int fClick, sClick, first, second, click=1, point=0;
    Button button_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);

        View rootView = findViewById(android.R.id.content);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        rootView.startAnimation(fadeInAnimation);

        define();
        Collections.shuffle(Arrays.asList(blocks));
        showCardsTemporarily();
        new Handler().postDelayed(this::flipAllCardsBack, 5000);
        startListeners();

        time_view = findViewById(R.id.time_view2);
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
        flipCardBack(block_13);
        flipCardBack(block_14);
        flipCardBack(block_15);
        flipCardBack(block_16);
        flipCardBack(block_17);
        flipCardBack(block_18);
        flipCardBack(block_19);
        flipCardBack(block_20);
        flipCardBack(block_21);
        flipCardBack(block_22);
        flipCardBack(block_23);
        flipCardBack(block_24);
        flipCardBack(block_25);
        flipCardBack(block_26);
        flipCardBack(block_27);
        flipCardBack(block_28);
        flipCardBack(block_29);
        flipCardBack(block_30);
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
        ImageView[] blockViews = new ImageView[]{block_1, block_2, block_3, block_4, block_5, block_6, block_7, block_8, block_9, block_10, block_11, block_12, block_13, block_14, block_15, block_16, block_17, block_18, block_19, block_20, block_21, block_22, block_23, block_24, block_25, block_26, block_27, block_28, block_29, block_30};
        for (int i = 0; i < blocks.length; i++) {
            int blockId = blocks[i];
            ImageView blockView = blockViews[i];
            if (blockId == 101 || blockId == 201) {
                blockView.setImageResource(b1);
            }else if (blockId == 102 || blockId == 202) {
                blockView.setImageResource(b2);
            }else if (blockId == 103 || blockId == 203) {
                blockView.setImageResource(b3);
            }else if (blockId == 104 || blockId == 204) {
                blockView.setImageResource(b4);
            }else if (blockId == 105 || blockId == 205) {
                blockView.setImageResource(b5);
            }else if (blockId == 106 || blockId == 206) {
                blockView.setImageResource(b11);
            } else if (blockId == 107 || blockId == 207) {
                blockView.setImageResource(b12);
            }else if (blockId == 108 || blockId == 208) {
                blockView.setImageResource(b13);
            }else if (blockId == 109 || blockId == 209) {
                blockView.setImageResource(b14);
            }else if (blockId == 110 || blockId == 210) {
                blockView.setImageResource(b15);
            }else if (blockId == 111 || blockId == 211) {
                blockView.setImageResource(b21);
            } else if (blockId == 112 || blockId == 212) {
                blockView.setImageResource(b22);
            }else if (blockId == 113 || blockId == 213) {
                blockView.setImageResource(b23);
            }else if (blockId == 114 || blockId == 214) {
                blockView.setImageResource(b24);
            }else if (blockId == 115 || blockId == 215) {
                blockView.setImageResource(b25);
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

        block_13.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_13, image);
        });

        block_14.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_14, image);
        });

        block_15.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_15, image);
        });

        block_16.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_16, image);
        });

        block_17.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_17, image);
        });

        block_18.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_18, image);
        });

        block_19.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_19, image);
        });

        block_20.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_20, image);
        });

        block_21.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_21, image);
        });

        block_22.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_22, image);
        });

        block_23.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_23, image);
        });

        block_24.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_24, image);
        });

        block_25.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_25, image);
        });

        block_26.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_26, image);
        });

        block_27.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_27, image);
        });

        block_28.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_28, image);
        });

        block_29.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_29, image);
        });

        block_30.setOnClickListener(v -> {
            int image = Integer.parseInt((String) v.getTag());
            flipCard(block_30, image);
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
        switch (blocks[image]) {
            case 101: block.setImageResource(b1);break;
            case 102: block.setImageResource(b2);break;
            case 103: block.setImageResource(b3);break;
            case 104: block.setImageResource(b4);break;
            case 105: block.setImageResource(b5);break;
            case 106: block.setImageResource(b11);break;
            case 107: block.setImageResource(b12);break;
            case 108: block.setImageResource(b13);break;
            case 109: block.setImageResource(b14);break;
            case 110: block.setImageResource(b15);break;
            case 111: block.setImageResource(b21);break;
            case 112: block.setImageResource(b22);break;
            case 113: block.setImageResource(b23);break;
            case 114: block.setImageResource(b24);break;
            case 115: block.setImageResource(b25);break;
            case 201: block.setImageResource(b31);break;
            case 202: block.setImageResource(b32);break;
            case 203: block.setImageResource(b33);break;
            case 204: block.setImageResource(b34);break;
            case 205: block.setImageResource(b35);break;
            case 206: block.setImageResource(b41);break;
            case 207: block.setImageResource(b42);break;
            case 208: block.setImageResource(b43);break;
            case 209: block.setImageResource(b44);break;
            case 210: block.setImageResource(b45);break;
            case 211: block.setImageResource(b51);break;
            case 212: block.setImageResource(b52);break;
            case 213: block.setImageResource(b53);break;
            case 214: block.setImageResource(b54);break;
            case 215: block.setImageResource(b55);break;
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
            case 12: return block_13;
            case 13: return block_14;
            case 14: return block_15;
            case 15: return block_16;
            case 16: return block_17;
            case 17: return block_18;
            case 18: return block_19;
            case 19: return block_20;
            case 20: return block_21;
            case 21: return block_22;
            case 22: return block_23;
            case 23: return block_24;
            case 24: return block_25;
            case 25: return block_26;
            case 26: return block_27;
            case 27: return block_28;
            case 28: return block_29;
            case 29: return block_30;
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
            case 12: block_13.setVisibility(View.INVISIBLE);break;
            case 13: block_14.setVisibility(View.INVISIBLE);break;
            case 14: block_15.setVisibility(View.INVISIBLE);break;
            case 15: block_16.setVisibility(View.INVISIBLE);break;
            case 16: block_17.setVisibility(View.INVISIBLE);break;
            case 17: block_18.setVisibility(View.INVISIBLE);break;
            case 18: block_19.setVisibility(View.INVISIBLE);break;
            case 19: block_20.setVisibility(View.INVISIBLE);break;
            case 20: block_21.setVisibility(View.INVISIBLE);break;
            case 21: block_22.setVisibility(View.INVISIBLE);break;
            case 22: block_23.setVisibility(View.INVISIBLE);break;
            case 23: block_24.setVisibility(View.INVISIBLE);break;
            case 24: block_25.setVisibility(View.INVISIBLE);break;
            case 25: block_26.setVisibility(View.INVISIBLE);break;
            case 26: block_27.setVisibility(View.INVISIBLE);break;
            case 27: block_28.setVisibility(View.INVISIBLE);break;
            case 28: block_29.setVisibility(View.INVISIBLE);break;
            case 29: block_30.setVisibility(View.INVISIBLE);break;
        }
    }

    private void ifEnd() {
        if (block_1.getVisibility() == View.INVISIBLE && block_2.getVisibility() == View.INVISIBLE && block_3.getVisibility() == View.INVISIBLE && block_4.getVisibility() == View.INVISIBLE &&
                block_5.getVisibility() == View.INVISIBLE && block_6.getVisibility() == View.INVISIBLE && block_7.getVisibility() == View.INVISIBLE && block_8.getVisibility() == View.INVISIBLE &&
                block_9.getVisibility() == View.INVISIBLE && block_10.getVisibility() == View.INVISIBLE && block_11.getVisibility() == View.INVISIBLE && block_12.getVisibility() == View.INVISIBLE &&
                block_13.getVisibility() == View.INVISIBLE && block_14.getVisibility() == View.INVISIBLE && block_15.getVisibility() == View.INVISIBLE && block_16.getVisibility() == View.INVISIBLE &&
                block_17.getVisibility() == View.INVISIBLE && block_18.getVisibility() == View.INVISIBLE && block_19.getVisibility() == View.INVISIBLE && block_20.getVisibility() == View.INVISIBLE &&
                block_21.getVisibility() == View.INVISIBLE && block_22.getVisibility() == View.INVISIBLE && block_23.getVisibility() == View.INVISIBLE && block_24.getVisibility() == View.INVISIBLE &&
                block_25.getVisibility() == View.INVISIBLE && block_26.getVisibility() == View.INVISIBLE && block_27.getVisibility() == View.INVISIBLE && block_28.getVisibility() == View.INVISIBLE &&
                block_29.getVisibility() == View.INVISIBLE && block_30.getVisibility() == View.INVISIBLE){
            timerHandler.removeCallbacks(timerRunnable);
            AlertDialog alertDialog = getAlertDialog();
            alertDialog.show();
        }
    }

    @NonNull
    private AlertDialog getAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(hard.this);
        alert.setMessage("Game Over\nScore: " + (point+secondsElapsed-5));
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
        block_13.setEnabled(b);
        block_14.setEnabled(b);
        block_15.setEnabled(b);
        block_16.setEnabled(b);
        block_17.setEnabled(b);
        block_18.setEnabled(b);
        block_19.setEnabled(b);
        block_20.setEnabled(b);
        block_21.setEnabled(b);
        block_22.setEnabled(b);
        block_23.setEnabled(b);
        block_24.setEnabled(b);
        block_25.setEnabled(b);
        block_26.setEnabled(b);
        block_27.setEnabled(b);
        block_28.setEnabled(b);
        block_29.setEnabled(b);
        block_30.setEnabled(b);
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
        block_13 = findViewById(R.id.block_13);
        block_14 = findViewById(R.id.block_14);
        block_15 = findViewById(R.id.block_15);
        block_16 = findViewById(R.id.block_16);
        block_17 = findViewById(R.id.block_17);
        block_18 = findViewById(R.id.block_18);
        block_19 = findViewById(R.id.block_19);
        block_20 = findViewById(R.id.block_20);
        block_21 = findViewById(R.id.block_21);
        block_22 = findViewById(R.id.block_22);
        block_23 = findViewById(R.id.block_23);
        block_24 = findViewById(R.id.block_24);
        block_25 = findViewById(R.id.block_25);
        block_26 = findViewById(R.id.block_26);
        block_27 = findViewById(R.id.block_27);
        block_28 = findViewById(R.id.block_28);
        block_29 = findViewById(R.id.block_29);
        block_30 = findViewById(R.id.block_30);

        button_exit = findViewById(R.id.button_exit);


        block_1.setTag("0");
        block_2.setTag("1");
        block_3.setTag("2");
        block_4.setTag("3");
        block_5.setTag("4");
        block_6.setTag("5");
        block_7.setTag("6");
        block_8.setTag("7");
        block_9.setTag("8");
        block_10.setTag("9");
        block_11.setTag("10");
        block_12.setTag("11");
        block_13.setTag("12");
        block_14.setTag("13");
        block_15.setTag("14");
        block_16.setTag("15");
        block_17.setTag("16");
        block_18.setTag("17");
        block_19.setTag("18");
        block_20.setTag("19");
        block_21.setTag("20");
        block_22.setTag("21");
        block_23.setTag("22");
        block_24.setTag("23");
        block_25.setTag("24");
        block_26.setTag("25");
        block_27.setTag("26");
        block_28.setTag("27");
        block_29.setTag("28");
        block_30.setTag("29");


        b1 = R.drawable.b1;
        b2 = R.drawable.b2;
        b3 = R.drawable.b3;
        b4 = R.drawable.b4;
        b5 = R.drawable.b5;
        b11 = R.drawable.b6;
        b12 = R.drawable.b7;
        b13 = R.drawable.b8;
        b14 = R.drawable.b9;
        b15 = R.drawable.b10;
        b21 = R.drawable.b11;
        b22 = R.drawable.b12;
        b23 = R.drawable.b13;
        b24 = R.drawable.b14;
        b25 = R.drawable.b15;
        b31 = R.drawable.b1;
        b32 = R.drawable.b2;
        b33 = R.drawable.b3;
        b34 = R.drawable.b4;
        b35 = R.drawable.b5;
        b41 = R.drawable.b6;
        b42 = R.drawable.b7;
        b43 = R.drawable.b8;
        b44 = R.drawable.b9;
        b45 = R.drawable.b10;
        b51 = R.drawable.b11;
        b52 = R.drawable.b12;
        b53 = R.drawable.b13;
        b54 = R.drawable.b14;
        b55 = R.drawable.b15;

    }

}