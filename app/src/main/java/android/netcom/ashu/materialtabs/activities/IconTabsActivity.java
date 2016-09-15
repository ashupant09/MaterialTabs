package android.netcom.ashu.materialtabs.activities;

import android.content.ComponentName;
import android.netcom.ashu.materialtabs.R;
import android.netcom.ashu.materialtabs.anim.AnimationUtils;
import android.netcom.ashu.materialtabs.extras.SortListener;
import android.netcom.ashu.materialtabs.fragments.FragmentBoxOffice;
import android.netcom.ashu.materialtabs.fragments.FragmentSearch;
import android.netcom.ashu.materialtabs.fragments.FragmentUpcoming;
import android.netcom.ashu.materialtabs.services.MyService;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RunnableFuture;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

public class IconTabsActivity extends BaseActivity implements View.OnClickListener{

    private static final int JOB_ID = 100;
    private static final long POLL_FREQUENCY = 60000*10;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AppBarLayout appBarLayout;
    private CoordinatorLayout fullLayout;
    private PagerAdapter adapter;
    private JobScheduler mJobScheduler;
    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_DATE = "sortDate";
    private static final String TAG_SORT_RATING = "sortRating";

    private int[] tabIcons = {
            R.drawable.ic_tab_favourite,
            R.drawable.ic_tab_call,
            R.drawable.ic_tab_contacts
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >=21){
            Slide slide = new Slide();
            slide.setDuration(5000);
            getWindow().setEnterTransition(slide);

            getWindow().setReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.transiction_activity));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_tabs);
        setupWindowAnimation();
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        //AnimationUtils.animateToolbar(appBarLayout);
        mJobScheduler = JobScheduler.getInstance(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                constructJob();
            }
        }, 30000);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = fixTabLayout;

        fixTabLayout.setupWithViewPager(viewPager);
        setUpIcons();

        buildFab();
    }

    @Override
    protected boolean useToolbar() {
        return true;
    }

    @Override
    protected boolean useTabLayout() {
        return true;
    }

    private void constructJob(){
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyService.class));
        builder.setPeriodic(POLL_FREQUENCY)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);

        mJobScheduler.schedule(builder.build());
    }
    private void buildFab(){
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.ic_action_new);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setBackgroundDrawable(R.drawable.selecter_button_red)
                .build();

        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.drawable.ic_action_alphabets);

        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.drawable.ic_action_calendar);

        ImageView iconSortRating = new ImageView(this);
        iconSortRating.setImageResource(R.drawable.ic_action_important);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selecter_sub_button_grey));

        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRating = itemBuilder.setContentView(iconSortRating ).build();

        buttonSortName.setTag(TAG_SORT_NAME);
        buttonSortDate.setTag(TAG_SORT_DATE);
        buttonSortRating.setTag(TAG_SORT_RATING);

        buttonSortName.setOnClickListener(this);
        buttonSortDate.setOnClickListener(this);
        buttonSortRating.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRating)
                .attachTo(actionButton)
                .build();
    }
    private void setUpIcons(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
    private void setupViewPager(ViewPager viewPager) {
        adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragments(FragmentSearch.newInstance("", ""), "ONE");
        adapter.addFragments(FragmentBoxOffice.newInstance("", ""), "TWO");
        adapter.addFragments(FragmentUpcoming.newInstance("", ""), "THREE");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        Fragment fragment = (Fragment) adapter.instantiateItem(viewPager,viewPager.getCurrentItem());
        if(fragment instanceof SortListener) {
            if (v.getTag().equals(TAG_SORT_NAME)) {
                ((SortListener) fragment).sortByName();
            }
            if (v.getTag().equals(TAG_SORT_DATE)) {

                ((SortListener) fragment).sortByDate();
            }
            if (v.getTag().equals(TAG_SORT_RATING)) {

                ((SortListener) fragment).sortByRating();
            }
        }
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentsTitle = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragments(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentsTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }

    public void setupWindowAnimation(){

        Fade fadeTransition = new Fade();
        fadeTransition.setDuration(3000);
        getWindow().setEnterTransition(fadeTransition);
        getWindow().setReturnTransition(fadeTransition);
    }
}
