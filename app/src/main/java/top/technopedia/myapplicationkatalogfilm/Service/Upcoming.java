package top.technopedia.myapplicationkatalogfilm.Service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;



public class Upcoming {

    private GcmNetworkManager mGcmNetworkManager;

    public Upcoming(Context context) {
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        @SuppressLint("MissingPermission") Task periodicTask = new PeriodicTask.Builder()
                .setService(UpcomingAlarmReceiver.class)
                .setPeriod(60)
                .setFlex(10)
                .setTag(UpcomingAlarmReceiver.TAG_TASK_UPCOMING)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask() {
        if (mGcmNetworkManager != null) {
            mGcmNetworkManager.cancelTask(UpcomingAlarmReceiver.TAG_TASK_UPCOMING, UpcomingAlarmReceiver.class);
        }
    }
}

