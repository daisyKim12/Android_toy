package org.texchtown.fullconcentration3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentLock = new Intent(context, LockActivity.class);
        intentLock.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentLock);
    }
}
