package org.texchtown.doitmission08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomerCare extends AppCompatActivity {
    private static final String TAG = "MyError";
    private static final int fromCustomer = 2001;
    private static final int fromRevenue = 2002;
    private static final int fromProduct = 2003;
    private static final int toLoginFromCustomer = 3001;
    private static final int toLoginFromRevenue = 3002;
    private static final int toLoginFromProduct = 3003;

    Button btn_toMenu;
    Button btn_toLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_care);

        btn_toMenu = findViewById(R.id.buttonToMenu1);
        btn_toLogin = findViewById(R.id.buttonToLogin1);

        btn_toMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("third", "고객관리 > 메인메뉴");
                setResult(fromCustomer, intent);
                finish();
            }
        });

        btn_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                setResult(toLoginFromCustomer, intent);
                finish();
            }
        });

    }
}