package org.texchtown.locktaskmode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //widget
    Button button_lock;
    Button button_unlock;

    //var
    Boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_lock = findViewById(R.id.button);
        button_unlock = findViewById(R.id.button2);


        flag = Boolean.FALSE;

        button_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = Boolean.TRUE;
            }
        });
        button_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = Boolean.FALSE;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (flag == Boolean.FALSE) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "locked", Toast.LENGTH_LONG).show();
        }
    }

}