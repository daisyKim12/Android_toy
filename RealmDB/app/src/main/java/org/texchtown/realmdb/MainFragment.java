package org.texchtown.realmdb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.CaseMap;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.texchtown.realmdb.model.TitleVO;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    //widget
    TextView textView;
    Button button;

    //var
    String input;
    String output;
    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        textView = rootView.findViewById(R.id.text_view);
        button = rootView.findViewById(R.id.bnt_dialog);

        realm = Realm.getDefaultInstance();


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
                saveData(input);
                readData();
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

    public void saveData(String str) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TitleVO vo = realm.createObject(TitleVO.class);
                vo.setTitle("title");
                vo.setMessage(str.trim());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //Successfully saved
                Log.d(TAG, "onSuccess: DATA written successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //error occured
                Log.d(TAG, "onError: ERROR occured");
            }
        });
    }

    public void readData() {
        RealmResults<TitleVO> titleVOS = realm.where(TitleVO.class).findAll();
        output = realm.where(TitleVO.class).findFirstAsync().getMessage();
        textView.setText(output);

    }

}