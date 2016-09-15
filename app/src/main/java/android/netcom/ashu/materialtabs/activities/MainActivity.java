package android.netcom.ashu.materialtabs.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.netcom.ashu.materialtabs.R;
import android.netcom.ashu.materialtabs.activities.SimpleTabsActivity;
import android.netcom.ashu.materialtabs.anim.AnimationUtils;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btnSimpleTabs, btnScrollableTabs, btnIconTextTabs, btnIconTabs, btnCustomIconTextTabs;
    //private CoordinatorLayout rootView;
    private CoordinatorLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= 21){
            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.transiction_activity);
            getWindow().setExitTransition(transition);

            Slide slide = new Slide();
            slide.setDuration(5000);
            getWindow().setReenterTransition(slide);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rootView = (CoordinatorLayout) findViewById(R.id.root_layout);
        btnSimpleTabs = (Button) findViewById(R.id.btnSimpleTabs);
        btnScrollableTabs = (Button) findViewById(R.id.btnScrollableTabs);
        btnIconTextTabs = (Button) findViewById(R.id.btnIconTextTabs);
        btnIconTabs = (Button) findViewById(R.id.btnIconTabs);
        btnCustomIconTextTabs = (Button) findViewById(R.id.btnCustomIconTabs);

        btnSimpleTabs.setOnClickListener(this);
        btnScrollableTabs.setOnClickListener(this);
        btnIconTextTabs.setOnClickListener(this);
        btnIconTabs.setOnClickListener(this);
        btnCustomIconTextTabs.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        ActivityOptionsCompat activityOptionsCompat =  ActivityOptionsCompat.makeSceneTransitionAnimation(this,null);
        switch (view.getId()) {

            case R.id.btnSimpleTabs:
                startActivity(new Intent(this, SimpleTabsActivity.class), activityOptionsCompat.toBundle());
                //startActivity(new Intent(MainActivity.this, SimpleTabsActivity.class));
                break;
            case R.id.btnScrollableTabs:
                startActivity(new Intent(this, ScrollableTabsActivity.class), activityOptionsCompat.toBundle());
                //startActivity(new Intent(MainActivity.this, ScrollableTabsActivity.class));
                break;
            case R.id.btnIconTextTabs:
                startActivity(new Intent(this, IconTextTabsActivity.class), activityOptionsCompat.toBundle());
                //startActivity(new Intent(MainActivity.this, IconTextTabsActivity.class));
                break;
            case R.id.btnIconTabs:
                startActivity(new Intent(this, IconTabsActivity.class), activityOptionsCompat.toBundle());
                //startActivity(new Intent(MainActivity.this, IconTabsActivity.class));
                break;
            case R.id.btnCustomIconTabs:
                startActivity(new Intent(this, CustomViewIconTextTabsActivity.class), activityOptionsCompat.toBundle());
                //startActivity(new Intent(MainActivity.this, CustomViewIconTextTabsActivity.class));
                break;
        }
    }

    public void startActivityAndAnimation(Activity activity){

       ActivityOptionsCompat activityOptionsCompat =  ActivityOptionsCompat.makeSceneTransitionAnimation(this,null);
        startActivity(new Intent(this, activity.getClass()), activityOptionsCompat.toBundle());
    }

}
