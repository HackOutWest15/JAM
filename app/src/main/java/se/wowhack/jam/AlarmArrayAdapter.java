package se.wowhack.jam;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import se.wowhack.jam.models.Alarm;

public class AlarmArrayAdapter extends ArrayAdapter<Alarm> {

    // declaring our ArrayList of items
    private ArrayList<Alarm> objects;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public AlarmArrayAdapter(Context context, int textViewResourceId, ArrayList<Alarm> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.layout_card, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        Alarm i = objects.get(position);

        if (i != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            TextView alarmTextView = (TextView) v.findViewById(R.id.alarmText);
            TextView alarmTimeView = (TextView) v.findViewById(R.id.alarmTime);
            SwitchCompat alarmSwitchView = (SwitchCompat) v.findViewById(R.id.alarmSwitch);
            TextView alarmDaysView = (TextView) v.findViewById(R.id.alarmDays);

            if (alarmTimeView != null){
                alarmTimeView.setText("" + i.getTime());
            }
            /*
            if (alarmSwitchView != null){
                alarmSwitchView.setChecked(i.isActive());
            } */
            if (alarmDaysView != null){
                alarmDaysView.setText("FIXA DETTA YO");
            }
            if (i.getDescription() == "") {
                // TODO: Hide alarmdivider and textview
            } else {
                if (alarmTextView != null){
                    alarmTextView.setText(i.getDescription());
                }
            }
        }

        // the view must be returned to our activity
        return v;

    }

}