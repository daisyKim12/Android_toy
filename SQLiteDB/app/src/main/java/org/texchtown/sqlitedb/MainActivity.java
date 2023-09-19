package org.texchtown.sqlitedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //var
    MainFragment mainFragment;
    FragmentManager manager;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, mainFragment).commit();

    }

    @Override
    protected void onDestroy() {
        dbHelper = new DBHelper(this);
        dbHelper.close();
        super.onDestroy();
    }
}