package se.wowhack.jam;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class WakeUpService extends Service {
    public WakeUpService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        Toast.makeText(this, "I'm running", Toast.LENGTH_SHORT).show();




        Intent i = new Intent(this, WakeUpActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(intent.getStringExtra("Playlist") != null){
            i.putExtra("Playlist", intent.getStringExtra("Playlist"));
        }
        startActivity(i);
        // stopped, so return sticky.
        return START_STICKY;
    }
}
