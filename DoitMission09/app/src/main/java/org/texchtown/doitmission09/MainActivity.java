package org.texchtown.doitmission09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements FragmentInfo.GetDate, FragmentDateDialog.OnDateInputListener {

    private static final String TAG = "MainActivity";

    //fragment vars
    FragmentManager fragmentManager;
    FragmentInfo fragmentInfo;

    //widget
    Button button_date_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentInfo = (FragmentInfo) fragmentManager.findFragmentById(R.id.fragment_container_view);
    }

    // interface to communicate with FragmentInfo
    @Override
    public void DateOutput() {
        Log.d(TAG, ": showing date dialog picker fragment");
        FragmentDateDialog newFragment = new FragmentDateDialog();
        newFragment.show(getSupportFragmentManager(), FragmentDateDialog.TAG);
    }

    // interface to communicate with date dialog picker fragment
    @Override
    public void DateInput(int ip1, int ip2, int ip3) {
        Log.d(TAG, ": getting date from date picker dialog and sending to FragmentInfo");
        View view_frag = fragmentInfo.getView();
        button_date_activity = (Button)view_frag.findViewById(R.id.button_birthdate);
        button_date_activity.setText(Integer.toString(ip1)+"/"+Integer.toString(ip2)+"/"+Integer.toString(ip3));
    }

}