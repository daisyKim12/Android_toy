package org.texchtown.samplecallintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText phone;
    Button call;
    Button changeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.editText);

        call = findViewById(R.id.button);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = phone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(phoneNum));
                startActivity(intent);
                finish();
            }
        });

        changeActivity = findViewById(R.id.button2);
        changeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ComponentName name = new ComponentName("org.texchtown.samplecallintent", "org.texchtown.samplecallintent.MenuActivity");
                intent.setComponent(name);
                startActivity(intent);
                finish();
            }
        });
    }
}