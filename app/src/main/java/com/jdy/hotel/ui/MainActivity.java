package com.jdy.hotel.ui;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.jdy.hotel.R;
import com.jdy.hotel.utils.L;
import com.jdy.hotel.utils.StatusBarUtil;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_tools, R.id.nav_share, R.id.nav_send,
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).setDrawerLayout(drawer)
                .build();

        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setCommonUI(this, true);
    }


    @Override
    protected void onStart() {
        super.onStart();
        L.i("-----------------------onStart----------------------------");
        Toolbar toolbar = findViewById(R.id.home_app_collapsing_toolbar);
        setSupportActionBar(toolbar);
//        toolbar.collapseActionView();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationView navigationView = findViewById(R.id.main_nav_view);
        BottomNavigationView navView = findViewById(R.id.main_tab_nav_view);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);

    }




    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(null, color);
        L.i(" -----------------------------------onTitleChanged----------------------------    Title : %s,     Color : %d", title, color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        L.i(" -----------------------------------onCreateOptionsMenu---------------------------- ");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        L.i(" -----------------------------------onSupportNavigateUp---------------------------- ");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
