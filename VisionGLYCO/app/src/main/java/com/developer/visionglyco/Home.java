package com.developer.visionglyco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.developer.visionglyco.Fragments.Camera;
import com.developer.visionglyco.Fragments.Help;
import com.developer.visionglyco.Fragments.History;
import com.developer.visionglyco.Fragments.Link;
import com.developer.visionglyco.Fragments.Notification;
import com.developer.visionglyco.Fragments.Report;
import com.developer.visionglyco.Fragments.ScanLink;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        ViewCompat.setBackgroundTintList(
                bottomNavigationView,
                ColorStateList.valueOf(getResources().getColor(R.color.lightBlue)));

// Set icon and text color
        bottomNavigationView.setItemIconTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.white)));
        bottomNavigationView.setItemTextColor(
                ColorStateList.valueOf(getResources().getColor(R.color.white)));
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id==R.id.home){
                    openFragment(new com.developer.visionglyco.Fragments.Home());
                    return true;
                } else if (id == R.id.camera) {
                    openFragment(new Camera());
                    return true;
                } else if (id == R.id.link) {
                    openFragment(new Link());
                    return true;
                } else if (id == R.id.history) {
                    openFragment(new History());
                    return true;
                }
                else{
//                    Report
                    openFragment(new Report());
                    return true;
                }
//                return true;
//                return false;
            }
        });
        fragmentManager = getSupportFragmentManager();
        openFragment(new com.developer.visionglyco.Fragments.Home());
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Home.this, "Processing...", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            openFragment(new com.developer.visionglyco.Fragments.Home());
        } else if (itemId == R.id.camera) {
            openFragment(new Camera());
        }else if (itemId == R.id.help) {
            openFragment(new Help());
        }else if (itemId == R.id.history) {
            openFragment(new History());
        }else if (itemId == R.id.link) {
            openFragment(new Link());
        }
        else if (itemId == R.id.notifications) {
            openFragment(new Notification());
        }else if (itemId == R.id.report) {
            openFragment(new Report());
        }else if (itemId == R.id.logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
            Toast.makeText(this, "Signout", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Soemthing went wrong", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }
    public void openFragment(Fragment fragment){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }
}