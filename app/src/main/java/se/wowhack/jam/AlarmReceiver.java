package se.wowhack.jam;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by Garpetun on 15-08-11.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("alarmAction")) {
            Log.d("Playlist from alarmReceiver", intent.getStringExtra("Playlist"));

            Log.d("#################", "Larm fired event");
            // For our recurring task, we'll just display a message
            Intent wakeUpIntent = new Intent(context, WakeUpService.class);
            startWakefulService(context,wakeUpIntent);
        }
    }
}
