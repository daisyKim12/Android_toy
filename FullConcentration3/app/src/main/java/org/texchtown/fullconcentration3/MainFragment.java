package org.texchtown.fullconcentration3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;




public class MainFragment extends Fragment {

    final String pattern = "HH:mm";

    //widget
    TextView current_time;
    TextView positive_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root_view = inflater.inflate(R.layout.fragment_main, container, false);

        //initialize widget
        current_time = root_view.findViewById(R.id.textView_time);
        positive_text = root_view.findViewById(R.id.textView_positive);

        current_time.setText(CurrentTime());
        setTextViewValueFromDatabase();

        return root_view;
    }

    public String CurrentTime() {
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    public void setTextViewValueFromDatabase(){
        TitleDatabaseHelper titleDatabaseHelper = new TitleDatabaseHelper(getActivity());
        String output = titleDatabaseHelper.readLastData();
        positive_text.setText(output);
    }
}