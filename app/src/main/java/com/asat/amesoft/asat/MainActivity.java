package com.asat.amesoft.asat;

import android.content.res.Configuration;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.asat.amesoft.asat.fragments.AdvicesFragment;
import com.asat.amesoft.asat.fragments.HospitalFragment;
import com.asat.amesoft.asat.fragments.HospitalRulesFragment;
import com.asat.amesoft.asat.fragments.MenuFragment;
import com.asat.amesoft.asat.fragments.RecordFragment;
import com.asat.amesoft.asat.fragments.SettingsFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String token="";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //token=getIntent().getStringExtra("token");
        token = MyApplication.getToken();

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
       // TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);


        setSupportActionBar(toolbar);

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState==null) {
            change_content(new MenuFragment(), false);
        }
        //token=token.replace("K","q");

    }

    public void goHospital(View view){
        Log.v("STATIC TOKEN",token);
        Bundle args = new Bundle();
        args.putString("token",token);
        Fragment f = new HospitalFragment();
        f.setArguments(args);
        change_content(f,true);
    }

    public void goRules(View view){
        Bundle args = new Bundle();
        args.putString("token",token);
        Fragment f = new HospitalRulesFragment();
        f.setArguments(args);
        change_content(f,true);

    }

    public void goRecord(View view){
        Fragment f = new RecordFragment();
        change_content(f,true);
    }

    public void goAdvices(View view){
        Fragment f = new AdvicesFragment();
        change_content(f,true);
    }

    public void goSettings(View view){
        Fragment f = new SettingsFragment();
        change_content(f,true);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()){
            case android.R.id.home:
                change_content(new MenuFragment(),true);
                break;
        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void change_content(Fragment f, boolean back){
        FragmentTransaction ft;

        if(f.getClass().equals(MenuFragment.class)){
            toolbar.setNavigationIcon(null);
        }
        else{
            toolbar.setNavigationIcon(+R.drawable.ic_menu_black_24dp);
        }

        ft=getSupportFragmentManager().beginTransaction().replace(R.id.content_main,f);
        if(back){
            ft.addToBackStack(null).commit();
        }
        else{
         ft.commit(); //
        }
    }


}
