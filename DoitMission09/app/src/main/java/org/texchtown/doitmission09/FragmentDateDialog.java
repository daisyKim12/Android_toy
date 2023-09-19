package org.texchtown.doitmission09;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class FragmentDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "DatePickerFragment";


    //interface for activity communication
    public interface OnDateInputListener {
        void DateInput(int ip1, int ip2, int ip3);
    }

    //vars
    public OnDateInputListener inputCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,  "onCreateDialog: making a calendar");
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year,month, day);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        inputCallback.DateInput(i, i1, i2);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: DatePicker attached to Activity");
        if(context instanceof OnDateInputListener) {
            inputCallback = (OnDateInputListener) context;
        }
    }

}
