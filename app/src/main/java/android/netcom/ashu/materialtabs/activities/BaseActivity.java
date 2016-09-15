package android.netcom.ashu.materialtabs.activities;

import android.app.Activity;
import android.netcom.ashu.materialtabs.R;
import android.netcom.ashu.materialtabs.anim.AnimationUtils;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

public class BaseActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private DrawerLayout fullLayout;
    private Toolbar toolbar;
    protected TabLayout fixTabLayout;
    protected TabLayout scrollTabLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedNavItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(fullLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        if (useToolbar())
        {
            setSupportActionBar(toolbar);
        }
        else
        {
            toolbar.setVisibility(View.GONE);
        }
        fixTabLayout = (TabLayout)findViewById(R.id.tabs);
        scrollTabLayout = (TabLayout) findViewById(R.id.scrolltabs);

        if(useTabLayout()){
            fixTabLayout.setVisibility(View.VISIBLE);
        }else{
            fixTabLayout.setVisibility(View.GONE);
        }

        if(useScrollTabs()){
            scrollTabLayout.setVisibility(View.VISIBLE);
        }else{
            scrollTabLayout.setVisibility(View.GONE);
        }

        setUpNavView();
    }

    protected boolean useToolbar()
    {
        return false;
    }

    protected boolean useTabLayout(){
        return false;
    }

    protected boolean useScrollTabs(){
        return false;
    }

    protected void setUpNavView()
    {
        navigationView.setNavigationItemSelectedListener(this);

        if( useDrawerToggle()) { // use the hamburger menu
            drawerToggle = new ActionBarDrawerToggle(this, fullLayout, toolbar,
                    R.string.nav_drawer_opened,
                    R.string.nav_drawer_closed);

            fullLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();
        } else if(useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources()
                    .getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        }
    }

    protected boolean useDrawerToggle()
    {
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        fullLayout.closeDrawer(GravityCompat.START);
        selectedNavItemId = menuItem.getItemId();

        return onOptionsItemSelected(menuItem);
    }

}
