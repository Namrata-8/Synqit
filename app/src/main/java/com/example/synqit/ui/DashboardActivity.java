package com.example.synqit.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.synqit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(DashboardActivity.this, R.id.container);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                navController.navigate(R.id.navigation_home);
                return true;

            case R.id.navigation_scan:
                navController.navigate(R.id.navigation_scan);
                return true;

            case R.id.navigation_graph:
                navController.navigate(R.id.navigation_graph);
                return true;

            case R.id.navigation_setting:
                navController.navigate(R.id.navigation_setting);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//          a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(a);
            finish();
    }
}