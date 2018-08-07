package com.hztech.peopledeputies.circlemenu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private final String TAG = "CircleMenu";
    private ImageView iv1, iv2, iv3, iv4, iv5, iv6;
    private List<ImageView> imageViews = new ArrayList<>();
    private int radius2;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setClickListener();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                    }
                });
            }
        }, 200);

    }

    private void findViews() {
        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        iv5 = findViewById(R.id.iv5);
        iv6 = findViewById(R.id.iv6);
        imageViews.add(iv5);
        imageViews.add(iv4);
        imageViews.add(iv3);
        imageViews.add(iv2);
        imageViews.add(iv1);
    }

    private void setClickListener() {
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);

        iv1.setOnTouchListener(this);
        iv2.setOnTouchListener(this);
        iv3.setOnTouchListener(this);
        iv4.setOnTouchListener(this);
        iv5.setOnTouchListener(this);
    }

    private void initData() {
        //中间国徽的宽度  基本等于  展开的半径
        radius2 = iv6.getHeight();

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv6, "rotation", 0, 360);
        objectAnimator.setDuration(500);
        objectAnimator.start();
        iv6.setTag(true);
        showCircleMenu();
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == iv1.getId()) {
            Toast.makeText(this, "iv1", Toast.LENGTH_SHORT).show();
        } else if (i == iv2.getId()) {
            Toast.makeText(this, "iv2", Toast.LENGTH_SHORT).show();
        } else if (i == iv3.getId()) {
            Toast.makeText(this, "iv3", Toast.LENGTH_SHORT).show();
        } else if (i == iv4.getId()) {
            Toast.makeText(this, "iv4", Toast.LENGTH_SHORT).show();
        } else if (i == iv5.getId()) {
            Toast.makeText(this, "iv5", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //设置Animation
        Animation animDwon = AnimationUtils.loadAnimation(this, R.anim.show_down);
        Animation animUp = AnimationUtils.loadAnimation(this, R.anim.show_up);

        ImageView imageView = (ImageView) v;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                imageView.startAnimation(animDwon);
                animDwon.setFillAfter(true);
                break;
            case MotionEvent.ACTION_UP:
                imageView.startAnimation(animUp);
                animUp.setFillAfter(true);
                break;
        }
        return false;
    }

    /**
     * 显示圆形菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void showCircleMenu() {
        PointF point = new PointF();

        for (int i = 0; i < imageViews.size(); i++) {
            int avgAngle = 360 / (imageViews.size());
            int angle = avgAngle * i;
            Log.e(TAG, "angle=" + angle);
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius2;
            point.y = (float) Math.sin(angle * (Math.PI / 180)) * radius2;

            Log.e(TAG, point.toString());

            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", 0, point.x);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", 0, point.y);
            ObjectAnimator objectAnimatorR = ObjectAnimator.ofFloat(imageViews.get(i), "rotation", 0f, 270f + angle);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.play(objectAnimatorX).with(objectAnimatorY).after(objectAnimatorR);
            animatorSet.start();
        }
    }

    /**
     * 关闭圆形菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void closeCircleMenu() {
        for (int i = 0; i < imageViews.size(); i++) {
            PointF point = new PointF();
            int avgAngle = 360 / (imageViews.size() - 1);
            int angle = avgAngle * i;
            Log.d(TAG, "angle=" + angle);
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius2;
            point.y = (float) Math.sin(angle * (Math.PI / 180)) * radius2;

            Log.d(TAG, point.toString());
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", point.x, 0);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", point.y, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            animatorSet.start();
        }
    }
}
