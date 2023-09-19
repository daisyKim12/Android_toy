package org.texchtown.fullconcentration3;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeLockFragment extends Fragment {

    final String pattern = "HH:mm";
    final String TAG = "TimeLockFragment";

    //widget
    TextView startTime;
    TextView endTime;
    Button buttonFinish;

    //val
    CustomPicker customPicker;
    FragmentManager fragmentManager;
    String start_time;
    String end_time;
    int flag;
//    private SharedViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_time_lock, container, false);
        // initialize widget
        startTime = rootView.findViewById(R.id.start_time);
        endTime = rootView.findViewById(R.id.end_time);
        buttonFinish = rootView.findViewById(R.id.button_finish_time_setting);

        //initialize time to current time
        startTime.setText(CurrentTime());
        endTime.setText(CurrentTime());

        //set Click Listener to change time
        flag = 99;
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                popTimePicker();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                popTimePicker();
            }
        });

        //end fragment
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LockTimeDatabaseHelper lockTimeDatabaseHelper = new LockTimeDatabaseHelper(getActivity());
                lockTimeDatabaseHelper.deleteAndAddTime(CurrentTime(), start_time, end_time);
//                lockTimeDatabaseHelper.readStartTimeFromDatabase();
//                lockTimeDatabaseHelper.readEndTimeFromDatabase();
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(0);
            }
        });

        return rootView;
    }

    public void popTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if(flag == 0) {
                    start_time = String.format(Locale.getDefault(), "%02d:%02d", i, i1);
                    startTime.setText(start_time);
                } else if (flag == 1) {
                    end_time = String.format(Locale.getDefault(), "%02d:%02d", i, i1);
                    endTime.setText(end_time);
                } else {
                    Log.d(TAG, "TimeLockFragment: flag == 99");
                }
            }
        };

        if(flag == 0) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK
                    , onTimeSetListener, 0,0,true);
            timePickerDialog.setTitle("시작 시간");
            timePickerDialog.show();
        } else if (flag == 1) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK
                    , onTimeSetListener, 0,0,true);
            timePickerDialog.setTitle("종료 시간");
            timePickerDialog.show();
        } else {Log.d(TAG, "TimeLockFragment"+"flag == 99");}

    }

    public String CurrentTime() {
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }

    //    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        viewModel.getConcentrationTime().observe(getViewLifecycleOwner(), input -> {
//            startTime.setText(input);
//            Log.d("TimeLockFragment", "TimeLockFragment"+input);
//        });
//    }

//    public void openPicker() {
//        customPicker = new CustomPicker();
//        fragmentManager = requireActivity().getSupportFragmentManager();
//        customPicker.show(fragmentManager, "TimePickerFragment");
//    }

}
