package org.texchtown.doitmission04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    String text;
    int textLength;
    InputMethodManager imm;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        textView.setText("0 / 80 바이트");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                text = editText.getText().toString();
                int textLength = text.length();
                if(textLength == 79)
                {
                    hideKeyboard();

                    Toast toast = Toast.makeText(MainActivity.this,"글자 수를 초과했습니다", Toast.LENGTH_LONG);
                    toast.show();

                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(textLength == 79)
                {
                    hideKeyboard();

                    Toast toast = Toast.makeText(MainActivity.this,"글자 수를 초과했습니다", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                text = editText.getText().toString();
                int textLength = text.length();
                String cntText = textLength + " / 80 바이트";
                textView.setText(cntText);
            }
        });

    }

    private void hideKeyboard() {
        imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
    }

    public void onButton1Clicked(View v){
        Toast toastResult = Toast.makeText(MainActivity.this,text,Toast.LENGTH_LONG);
        toastResult.show();
    }

}