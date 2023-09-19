package org.texchtown.twoimageview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    ScrollView  scrollViewTop, scrollViewBottom;
    ImageView imageViewTop, imageViewBottom;
    BitmapDrawable bitmap1, bitmap2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewTop = findViewById(R.id.imageViewTop);
        imageViewBottom = findViewById(R.id.imageViewBottom);
    }

    public void onButton1Clicked(View v)
    {
        changeUp();
    }


    public void onButton2Clicked(View v)
    {
        changeDown();
    }

    private void changeUp()
    {
        Resources res = getResources();
        bitmap1 = (BitmapDrawable) res.getDrawable(R.drawable.dog);
        bitmap2 = (BitmapDrawable) res.getDrawable(R.drawable.cat);
        int bitmap1Width = bitmap1.getIntrinsicWidth();
        int bitmap1Height = bitmap1.getIntrinsicHeight();
        int bitmap2Width = bitmap2.getIntrinsicWidth();
        int bitmap2Hight = bitmap2.getIntrinsicWidth();

        imageViewTop.setImageDrawable(bitmap1);
        imageViewTop.getLayoutParams().width = bitmap1Width;
        imageViewTop.getLayoutParams().height = bitmap1Height;

        imageViewBottom.setImageDrawable(bitmap2);
        imageViewBottom.getLayoutParams().width = bitmap2Width;
        imageViewBottom.getLayoutParams().height = bitmap2Hight;
    }

    private void changeDown()
    {
        Resources res = getResources();
        bitmap1 = (BitmapDrawable) res.getDrawable(R.drawable.cat);
        bitmap2 = (BitmapDrawable) res.getDrawable(R.drawable.dog);
        int bitmap1Width = bitmap1.getIntrinsicWidth();
        int bitmap1Height = bitmap1.getIntrinsicHeight();
        int bitmap2Width = bitmap2.getIntrinsicWidth();
        int bitmap2Hight = bitmap2.getIntrinsicWidth();

        imageViewTop.setImageDrawable(bitmap1);
        imageViewTop.getLayoutParams().width = bitmap1Width;
        imageViewTop.getLayoutParams().height = bitmap1Height;

        imageViewBottom.setImageDrawable(bitmap2);
        imageViewBottom.getLayoutParams().width = bitmap2Width;
        imageViewBottom.getLayoutParams().height = bitmap2Hight;
    }

}