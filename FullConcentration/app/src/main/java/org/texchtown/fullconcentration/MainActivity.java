package org.texchtown.fullconcentration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navView;
    private NavController navController;
    private NavHostFragment navHostFragment;
    AppBarConfiguration appBarConfiguration;
    CustomDrawerFragment customDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        //Drawable wallpaperDrawable = wallpaperManager.getDrawable();

        // to get navController in fragmentContainerView need to access by FragmentManager
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        customDrawerFragment = new CustomDrawerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.CustomDrawerHolder, customDrawerFragment).commit();


        //getting AppBarConfiguration for navView and applying to navView
        drawerLayout = findViewById(R.id.nav_drawer_layout);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                        .setOpenableLayout(drawerLayout).build();
        //setting setupWithNavController
        navView = findViewById(R.id.nav_view);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}