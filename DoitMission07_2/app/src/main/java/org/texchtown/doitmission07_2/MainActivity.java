package org.texchtown.doitmission07_2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == 9001) {
                    Intent intent = result.getData();
                    CharSequence result_data = intent.getStringExtra(getString(R.string.login_key));
                    if(intent != null && result_data != null){
                    Toast.makeText(getApplicationContext(), result_data, Toast.LENGTH_LONG).show();
                    }
                    else if(intent == null){
                        Log.d("myError", "no intent value");
                    }
                    else{
                        Log.d("myError", "no result_data value");
                    }
                }
            }

        });

        Button button = findViewById(R.id.buttonLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                activityResultLauncher.launch(intent);
            }
        });
    }
}