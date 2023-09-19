package org.texchtown.fullconcentration3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //widget
    DrawerLayout drawerLayout;
    ActionBar actionBar;
    Toolbar toolbar;
    NavigationView navigationView;

    //fragment
    FragmentManager fragmentManager;
    MainFragment mainFragment;
    PreviewFragment previewFragment;
    TimeLockFragment timeLockFragment;
    AllowAppSettingFragment allowAppSettingFragment;

    //val
    String positiveText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set toolbar as actionbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();


        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_View);
        navigationView.setNavigationItemSelectedListener(this);

        //getting fragmentManger
        fragmentManager = getSupportFragmentManager();
        mainFragment = new MainFragment();
        previewFragment = new PreviewFragment();
        timeLockFragment = new TimeLockFragment();
        allowAppSettingFragment = new AllowAppSettingFragment();


        if(savedInstanceState == null) {
//          //initialize mainFragment to container
//          fragmentManager.beginTransaction()
//                  .replace(R.id.container_frame, mainFragment).commit();
            //initialize home item in menu
            navigationView.setCheckedItem(R.id.menu_main);
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_main:
                //main
                onFragmentChanged(0);
                break;
            case R.id.menu_textedit:
                //main
                onFragmentChanged(0);
                //show dialog to edit and pass text to main fragment
                openDialog();
                break;
            case R.id.menu_alarm:
                //time picker
                onFragmentChanged(2);
                break;
            case R.id.menu_app:
                // ?? new fragment for setting apps
                onFragmentChanged(3);
                break;
            case R.id.menu_preview:
                //preview fragment
                onFragmentChanged(1);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    public void openDialog() {
//        CustomDialog customDialog = new CustomDialog();
//        customDialog.show(getSupportFragmentManager(), "positive saying dialog");
//    }

    public void openDialog() {
        TitleDatabaseHelper titleDatabaseHelper = new TitleDatabaseHelper(this);

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(view);
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText = view.findViewById(R.id.positive_text);
                String input = editText.getText().toString();
                //saving input data in SQLite
                titleDatabaseHelper.deleteAndAddData(input.trim());

                //change fragment textview
                mainFragment.setTextViewValueFromDatabase();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onFragmentChanged(int Index) {
        switch (Index) {
            case 0:
                //main
                fragmentManager.beginTransaction()
                        .replace(R.id.container_frame,mainFragment).commit();
                break;
            case 1:
                //preview
                fragmentManager.beginTransaction()
                        .replace(R.id.container_frame, previewFragment).commit();
                break;
            case 2:
                //time set
                fragmentManager.beginTransaction()
                        .replace(R.id.container_frame, timeLockFragment).commit();
                break;
            case 3:
                // app setting
                fragmentManager.beginTransaction()
                        .replace(R.id.container_frame, allowAppSettingFragment).commit();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.menu_start_fire)
        LockTimeDatabaseHelper lockTimeDatabaseHelper = new LockTimeDatabaseHelper(this);
        startAlarm(lockTimeDatabaseHelper.readStartTimeFromDatabase());
        return true;
    }

    private void startAlarm(Calendar c) {
        //create an alarm receiver, which waits and listens for an alarm event
        Intent intentAlarmReceiver = new Intent(this, AlarmReceiver.class);

        //send alarm receiver to broadcast
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intentAlarmReceiver, PendingIntent.FLAG_IMMUTABLE);

        //alarm manager instance from the system service
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        //set time for alarm and attach to broadcast
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() ,pendingIntent);
    }
}