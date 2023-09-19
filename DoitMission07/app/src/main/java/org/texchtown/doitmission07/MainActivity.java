package org.texchtown.doitmission07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyError";

    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onCreate 호출됨");
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause 호출됨");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume 호출됨");
    }

    public void processCommand(Intent intent) {
        if(intent != null){
            String command = intent.getStringExtra("finish");
            Toast.makeText(this, command, Toast.LENGTH_LONG).show();
        }
        else{
            Log.d(TAG, "intent == null");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop 호출됨");
    }
}