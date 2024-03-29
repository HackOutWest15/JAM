package se.wowhack.jam;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import se.wowhack.jam.Utils.Backend;
import se.wowhack.jam.models.Alarm;
import se.wowhack.jam.models.Playlist;

public class AlarmActivity extends FragmentActivity {

    private ListView listView;
    private ArrayList<Alarm> alarms = new ArrayList<>();
    private PendingIntent pendingIntent;
    private List<Playlist> playlists;
    private String userId;
    private SpotifyApi api;
    private SpotifyService spotify;
    private Playlist savedPlaylist;
    private FragmentManager supportFragmentManager = getSupportFragmentManager();
    private Alarm currentlyClickedAlarm;
    private AlarmArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        savedPlaylist = null;


        if(getIntent().getStringExtra("Userid") != null) {
            Intent from = getIntent();
            playlists = (ArrayList<Playlist>) from.getSerializableExtra("Playlists");
            userId = from.getStringExtra("Userid");
            api = new SpotifyApi();
            api.setAccessToken(from.getStringExtra("Token"));
            spotify = api.getService();
            //pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, alarmIntent, 0);
        }





        findViewById(R.id.newAlarmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAlarm();
            }
        });


        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        alarms.add(new Alarm());

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        adapter = new AlarmArrayAdapter(this, R.layout.layout_card, alarms);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                currentlyClickedAlarm = alarms.get(position);
                DFragment dialogFragment  = new DFragment();
                // Show Alert DialogFragment
                dialogFragment.show(supportFragmentManager, "Albins fina dialog");
            }
        });
    }



    public void cancel(Alarm alarm) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.setAction("alarmAction"+alarm.toString());
        alarmIntent.putExtra("Playlist", alarm.getPlaylist().getId());
        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, alarmIntent, 0);
        manager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }



    public void setAlarm(Alarm alarm) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        alarmIntent.setAction("alarmAction"+alarm.toString());
        if(alarm.getPlaylist() != null) {
            alarmIntent.putExtra("Playlist", alarm.getPlaylist().getId());
        }
        pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, alarmIntent, 0);
        /* Repeat every 24 hours */
        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarm.getTime().getTimeInMillis(),
                1000 * 60 * 60 * 24, pendingIntent);
    }
    //Use this method to add the songs to the playlist
    public void selectPlaylist(Playlist playlist1) {
        savedPlaylist = playlist1;
        spotify.getPlaylist(userId, playlist1.getId(), new Callback<kaaes.spotify.webapi.android.models.Playlist>() {
            @Override
            public void success(kaaes.spotify.webapi.android.models.Playlist playlist, Response response) {
                List<String> tracks = new ArrayList<>();

                for (PlaylistTrack item : playlist.tracks.items) {
                    tracks.add(item.track.uri);
                }
                savedPlaylist.setTracks(tracks);
                Backend.getInstance().setStored(savedPlaylist);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public Alarm getCurrentlyClickedAlarm() {
        return currentlyClickedAlarm;
    }

    public void setCurrentlyClickedAlarm(Alarm currentlyClickedAlarm) {
        this.currentlyClickedAlarm = currentlyClickedAlarm;
    }

    public ArrayList<Playlist> getPlaylists() {
        return (ArrayList) playlists;
    }

    public void removeAlarm(Alarm theAlarm) {
        //alarms.remove(theAlarm);
        adapter.remove(theAlarm);
    }

    private void createNewAlarm() {
        Alarm newAlarm = new Alarm();
        adapter.add(newAlarm);
        currentlyClickedAlarm = newAlarm;
        DFragment dialogFragment  = new DFragment();
        // Show Alert DialogFragment
        dialogFragment.show(supportFragmentManager, "Albins fina dialog");
        //alarms.add(newAlarm);
    }

    public void notifyChanged(){
        adapter.notifyDataSetChanged();
    }
}