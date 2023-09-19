package org.texchtown.doitmission10;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {


    View rootview;
    TextView textHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_home, container, false);
        textHome = (TextView) rootview.findViewById(R.id.text_home);

        textHome.setOnClickListener(Navigation.findNavController(rootview).navigate(R.id.navigate_to_pager););


        return rootview;
    }
}