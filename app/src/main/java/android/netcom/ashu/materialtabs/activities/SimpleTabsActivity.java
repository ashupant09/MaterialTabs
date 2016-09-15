package android.netcom.ashu.materialtabs.activities;

import android.netcom.ashu.materialtabs.R;
import android.netcom.ashu.materialtabs.fragments.OneFragment;
import android.netcom.ashu.materialtabs.fragments.ThreeFragment;
import android.netcom.ashu.materialtabs.fragments.TwoFragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KamaL on 28-08-2016.
 */
public class SimpleTabsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new OneFragment(), "ONE");
        adapter.addFragments(new TwoFragment(), "TWO");
        adapter.addFragments(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class PagerAdapter extends FragmentPagerAdapter {

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
            return mFragmentsTitle.get(position);
        }
    }

}

