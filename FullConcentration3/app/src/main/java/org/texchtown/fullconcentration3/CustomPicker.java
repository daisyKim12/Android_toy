package org.texchtown.fullconcentration3;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;


public class CustomPicker extends AppCompatDialogFragment implements TimePickerDialog.OnTimeSetListener {

//    private SharedViewModel viewModel;
    String hour, minute;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //default value
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        ///create picker
        return new TimePickerDialog(getActivity(), this, hour, minute
                , DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
//        hour = String.valueOf(i);
//        minute = String.valueOf(i1);

    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        viewModel.setConcentrationTime(hour +":"+minute);
//    }
}
