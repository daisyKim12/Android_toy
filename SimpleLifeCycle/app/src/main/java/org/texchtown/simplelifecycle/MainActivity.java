package org.texchtown.simplelifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Toast toastCreate;
    Toast toastStart;
    Toast toastStop;
    Toast toastDestroy;
    Toast toastPause;
    Toast toastResume;
    EditText nameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);

        toastCreate = Toast.makeText(MainActivity.this, "onCreate 호출됨", Toast.LENGTH_LONG);
        toastCreate.show();
        Log.d("cycle", "onCreate");

        Button button1 = findViewById(R.id.btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        toastStart = Toast.makeText(MainActivity.this, "onStart 호출됨", Toast.LENGTH_LONG);
        toastStart.show();
        Log.d("cycle", "onStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        toastStop = Toast.makeText(MainActivity.this, "onStop 호출됨", Toast.LENGTH_LONG);
        toastStop.show();
        Log.d("cycle", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        toastDestroy = Toast.makeText(MainActivity.this, "onDestroy 호출됨", Toast.LENGTH_LONG);
        toastDestroy.show();
        Log.d("cycle", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        toastPause = Toast.makeText(MainActivity.this, "onPause 호출됨", Toast.LENGTH_LONG);
        toastPause.show();
        Log.d("cycle", "onPause");
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toastResume = Toast.makeText(MainActivity.this, "onResume 호출됨", Toast.LENGTH_LONG);
        toastResume.show();
        Log.d("cycle", "onResume");
        restoreState();
    }

    protected void saveState() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name", nameInput.getText().toString());
        editor.commit();
    }

    protected  void restoreState(){
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if((pref != null) && (pref.contains("name"))) {
            String name = pref.getString("name", "");
            nameInput.setText(name);
        }
    }

    protected void clearState() {
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}