package org.texchtown.sqlitedb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private static final String DATABASE_NAME = "positive.db";
    private static final String COLUMN_SAYING = "positive_saying";

    //widget
    TextView textView;
    Button button;

    //var
    String input;
    String output;
    DBHelper dbHelper;
    ArrayList<String> saying;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        dbHelper = new DBHelper(getActivity());
        saying = new ArrayList<>();


        textView = rootView.findViewById(R.id.text_view);
        button = rootView.findViewById(R.id.bnt_dialog);

        displayData();
        textView.setText(output);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(view);
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText = view.findViewById(R.id.edit_text_input);
                input = editText.getText().toString();
                //saving input data in SQLite
                DBHelper dbHelper = new DBHelper(getActivity());
                dbHelper.addData(input.trim());
                //reading output data
                displayData();
                textView.setText(output);
                //delete extra data
                dbHelper.deleteNeedlessData(output);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void displayData() {
        Cursor cursor = dbHelper.readAllData();

        while (cursor.moveToNext()){
            output = cursor.getString(0);
        }

    }


 }
