package se.wowhack.jam;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Garpetun on 15-08-11.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("#################","Larm fired event");
        // For our recurring task, we'll just display a message
        Intent wakeUpIntent = new Intent(context, WakeUpService.class);
        context.startService(wakeUpIntent);
    }
}
