package org.texchtown.samplefragment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ListFragment.ImageSelectionCallBack {

    ListFragment listFragment;
    ViewerFragment viewerFragment;

    int[] images = {R.drawable.mario, R.drawable.luigi, R.drawable.peach};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        listFragment = (ListFragment) manager.findFragmentById(R.id.listFragment);
        viewerFragment = (ViewerFragment) manager.findFragmentById(R.id.viewerFragment);

    }


    @Override
    public void onImageSelection(int position) {
        viewerFragment.setImage(images[position]);
    }
}