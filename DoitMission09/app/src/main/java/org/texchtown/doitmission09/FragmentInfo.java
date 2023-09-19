package org.texchtown.doitmission09;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FragmentInfo extends Fragment {

    public static final String TAG = "FragmentInfo";
    public static final String PATTERN = "MM/dd/yyyy";


    //interface for activity communication
    public interface GetDate {
        void DateOutput();
    }

    //vars
    private Context context;
    public GetDate callBack;

    //widget
    Button button_toast;
    Button button_date_fragment;
    EditText editText_name;
    EditText editText_age;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: FragmentInfo attached to Activity");
        if(context instanceof GetDate) {
            callBack = (GetDate) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        context = container.getContext();

        button_toast = rootView.findViewById(R.id.button);
        button_date_fragment = rootView.findViewById(R.id.button_birthdate);
        editText_name = rootView.findViewById(R.id.editText_name);
        editText_age = rootView.findViewById(R.id.editText_age);

        //today date formatting
        DateFormat dateFormat = new SimpleDateFormat(PATTERN);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = dateFormat.format(today);

        button_date_fragment.setText(todayAsString);

        button_date_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: button_date clicked");
                if(callBack != null) {
                    callBack.DateOutput();
                }
            }
        });

        button_toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText_name.getText().toString();
                String age = editText_age.getText().toString();
                String birthDate = button_date_fragment.getText().toString();
                Toast.makeText(context,"이름: " + name + "\n" + "나이: " + age + "\n" + "생년월일: "+ birthDate + "\n", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }
}
