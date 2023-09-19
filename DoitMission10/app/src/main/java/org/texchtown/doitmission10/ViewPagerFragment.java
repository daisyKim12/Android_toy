package org.texchtown.doitmission10;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewPagerFragment extends Fragment {

    View rootView;
    TextView text_pager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);
        text_pager = (TextView)rootView.findViewById(R.id.text_pager);

        text_pager.setOnClickListener(Navigation.findNavController(rootView).navigate(R.id.navigate_to_home););

        return rootView;
    }
}