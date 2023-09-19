package org.texchtown.doitmission08;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyError";

    private static final int fromCustomer = 2001;
    private static final int fromRevenue = 2002;
    private static final int fromProduct = 2003;

    TextView text_id;
    TextView text_pw;
    Button btn_login;

    ActivityResultLauncher<Intent> activityResultLauncher;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_id = findViewById(R.id.editText1);
        text_pw = findViewById(R.id.editText2);
        btn_login = findViewById(R.id.button);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == fromCustomer) {
                    Intent intent1 = result.getData();
                    CharSequence data1 = intent1.getStringExtra("third");
                    checkAndToast(intent1, data1);
                }
                else if(result.getResultCode() == fromRevenue) {
                    Intent intent2 = result.getData();
                    CharSequence data2 = intent2.getStringExtra("third");
                    checkAndToast(intent2, data2);
                }
                else if(result.getResultCode() == fromProduct) {
                    Intent intent3 = result.getData();
                    CharSequence data3 = intent3.getStringExtra("third");
                    checkAndToast(intent3, data3);
                }
                else {
                    Log.d(TAG, "<MAIN>.getresultcode is not correct: resultcode = "+result.getResultCode());
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text_id.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해야 합니다.", Toast.LENGTH_LONG).show();
                    Log.d(TAG, text_id.getText().toString());

                }
                else if(text_id.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해야 합니다.", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    String id = text_id.getText().toString();
                    String pw = text_pw.getText().toString();
                    intent.putExtra("id", id);
                    intent.putExtra("pw", pw);
                    activityResultLauncher.launch(intent);

                }
            }
        });

    }

    private void checkAndToast (Intent intent, CharSequence data) {
        if(intent != null && data != null) {
            Toast.makeText(getApplicationContext(), data, Toast.LENGTH_LONG).show();
        }
        else {
            Log.d(TAG, "intent or data is null");
        }
    }

}