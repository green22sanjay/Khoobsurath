package com.example.kubsurath.kubsurath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.kubsurath.kubsurath.adapters.MyPagerAdapter;
import com.example.kubsurath.kubsurath.sessionmanager.SessionManager;


public class MainActivity extends ActionBarActivity {

    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpa();

        /*
         ViewPager pager = (ViewPager) findViewById(R.id.myViewPager);
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            String toOpen = extras.getString("toOpen");
            // viewpager setup
            if (toOpen.equals("Login")) {
                viewpa();
                pager.setCurrentItem(4, false);
            }

        }
        else {
            viewpa();
        }
        */

    }


    public void viewpa(){
        ViewPager pager = (ViewPager) findViewById(R.id.myViewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fragment_menu, menu);


        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.Search:
                break;
            case R.id.Add:
                session = new SessionManager(getApplicationContext());
                if (session.isLoggedIn()) {
                    Intent intenpt = new Intent(getApplicationContext(),
                            AddPostActivitys.class);
                    startActivity(intenpt);
                }else {
                    Intent intent = new Intent(getApplicationContext(),
                            CheckLoginAddButton.class);
                    startActivity(intent);

                }
                break;
            case R.id.Refresh:
                break;
        }
        return true;

    }
}
