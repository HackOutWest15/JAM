package se.wowhack.jam;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.PlaylistTrack;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.wowhack.jam.models.Playlist;

public class AlarmActivity extends FragmentActivity {

    private PendingIntent pendingIntent;
    private List<Playlist> playlists;
    private String userId;
    private SpotifyApi api;
    private SpotifyService spotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = getIntent();
        playlists = (ArrayList<Playlist>) alarmIntent.getSerializableExtra("Playlists");
        userId = alarmIntent.getStringExtra("Userid");
        api = new SpotifyApi();
        api.setAccessToken(alarmIntent.getStringExtra("Token"));
        spotify = api.getService();
        LinearLayout layout = (LinearLayout) findViewById(R.id.lao);

        for(Playlist item : playlists){
            Button button = new Button(this);
            button.setText(item.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = (String)((Button) v).getText();
                    for(Playlist item : playlists){
                        if(item.getName().equals(name)){
                            selectPlaylist(item.getId());
                            break;
                        }
                    }
                }
            });
            layout.addView(button);
        }
        //pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, alarmIntent, 0);

        findViewById(R.id.startAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        findViewById(R.id.stopAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        findViewById(R.id.alarmSetter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime();
            }
        });
    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        /* Repeating on interval */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                interval, pendingIntent);
    }

    public void cancel() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    public void pickTime() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void setAlarm(int pickedHour, int pickedMinute) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Set the alarm to start at time set */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, pickedHour);
        calendar.set(Calendar.MINUTE, pickedMinute);

        /* Repeat every 24 hours */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60, pendingIntent);
    }
    private void selectPlaylist(String playlistid){
        spotify.getPlaylist(userId, playlistid, new Callback<kaaes.spotify.webapi.android.models.Playlist>() {
            @Override
            public void success(kaaes.spotify.webapi.android.models.Playlist playlist, Response response) {
                for(PlaylistTrack item :playlist.tracks.items){
                    Log.d("Song:", item.track.name);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}