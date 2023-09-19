package org.texchtown.fullconcentration3;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import io.realm.Realm;


public class CustomDialog extends AppCompatDialogFragment {

    public static interface DialogListener {
        public void textChange(String str);
    }

    //widget
    private EditText positiveText;

    //val
    DialogListener listener;
    String str_positive;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement DialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);

        builder.setView(view);
        //actions
        builder.setPositiveButton("저장", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                str_positive = positiveText.getText().toString();
                listener.textChange(str_positive);

//                setPositiveText(str_positive);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        positiveText = view.findViewById(R.id.positive_text);

        return builder.create();
    }

//    public void setPositiveText(String str) {
//        Realm.init(getActivity());
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                TitleVO vo = realm.createObject(TitleVO.class);
//                vo.title = "textChange";
//                vo.context = str;
//            }
//        });
//
//    }
}
