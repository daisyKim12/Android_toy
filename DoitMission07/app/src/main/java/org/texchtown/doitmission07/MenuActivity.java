package org.texchtown.doitmission07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button buttonRevenue;
    Button buttonCustomer;
    Button buttonProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonCustomer = findViewById(R.id.buttonCustomer);
        buttonProduct = findViewById(R.id.buttonProduct);
        buttonRevenue = findViewById(R.id.buttonRevenue);

        buttonCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain("고객관리 확인함");
            }
        });
        buttonProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain("상품관리 확인함");
            }
        });
        buttonRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain("상품관리 확인함");
            }
        });
    }

    public void backToMain(String str){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("finish", str);
        //startActivity(intent);
        finish();
    }
}