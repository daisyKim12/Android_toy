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
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MyError";
    private static final int fromCustomer = 2001;
    private static final int fromRevenue = 2002;
    private static final int fromProduct = 2003;

    ActivityResultLauncher<Intent> activityResultLauncher;

    Button btn_c;
    Button btn_r;
    Button btn_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == 2001) {
                    Intent intent1 = result.getData();
                    CharSequence data1 = intent1.getStringExtra("third");
                    checkAndToast(intent1, data1);
                }
                else if(result.getResultCode() == 2002) {
                    Intent intent2 = result.getData();
                    CharSequence data2 = intent2.getStringExtra("third");
                    checkAndToast(intent2, data2);
                }
                else if(result.getResultCode() == 2003) {
                    Intent intent3 = result.getData();
                    CharSequence data3 = intent3.getStringExtra("third");
                    checkAndToast(intent3, data3);
                }
                else if(result.getResultCode() == 3001) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("third", "고객관리 > 로그인하기");
                    setResult(fromCustomer, intent);
                    finish();
                }
                else if(result.getResultCode() == 3002) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("third", "매출관리 > 로그인하기");
                    setResult(fromRevenue, intent);
                    finish();
                }
                else if(result.getResultCode() == 3003) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("third", "상품관리 > 로그인하기");
                    setResult(fromProduct, intent);
                    finish();
                }
                else {
                    Log.d(TAG, "<MENU>.getresultcode is not correct");
                }
            }
        });

        btn_c = findViewById(R.id.button1);
        btn_r = findViewById(R.id.button2);
        btn_p = findViewById(R.id.button3);

        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), CustomerCare.class);
                activityResultLauncher.launch(intent1);
            }
        });

        btn_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), RevenueCare.class);
                activityResultLauncher.launch(intent2);
            }
        });

        btn_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), ProductCare.class);
                activityResultLauncher.launch(intent3);
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