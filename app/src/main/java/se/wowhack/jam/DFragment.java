package se.wowhack.jam;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import se.wowhack.jam.models.Alarm;
import se.wowhack.jam.models.Playlist;

public class DFragment extends DialogFragment {

    private ArrayList<Playlist> playlists = new ArrayList<>();
    private Alarm currentAlarm;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alarm_dialog, container,
                false);
        // Do something else

        currentAlarm = ((AlarmActivity) getActivity()).getCurrentlyClickedAlarm();
        playlists = ((AlarmActivity) getActivity()).getPlaylists();

        // Get ListView object from xml
        listView = (ListView) rootView.findViewById(R.id.list);

        if (playlists != null) {
            // Defined Array values to show in ListView
            String[] values = new String[playlists.size()];
            for (int i = 0; i < playlists.size(); i++) {
                values[i] = playlists.get(i).getName();
            }

            // Define a new Adapter
            // First parameter - Context
            // Second parameter - Layout for the row
            // Third parameter - ID of the TextView to which the data is written
            // Forth - the Array of data

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);


            // Assign adapter to ListView
            listView.setAdapter(adapter);

            // ListView Item Click Listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition     = position;

                    // ListView Clicked item value
                    String  itemValue    = (String) listView.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                            .show();

                }

            });
        }

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return rootView;
    }
}