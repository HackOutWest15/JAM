package se.wowhack.jam;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import se.wowhack.jam.models.Alarm;

public class AlarmArrayAdapter extends ArrayAdapter<Alarm> {

    // declaring our ArrayList of items
    private ArrayList<Alarm> objects;
    private Context mContext;
    final String[] dayNames = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public AlarmArrayAdapter(Context context, int textViewResourceId, ArrayList<Alarm> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        this.mContext = context;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(final int position, View convertView, ViewGroup parent){

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
            View alarmDivider = v.findViewById(R.id.divider);

            alarmSwitchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlarmArrayAdapter.this.objects.get(position).setActive(!AlarmArrayAdapter.this.objects.get(position).isActive());
                    if(AlarmArrayAdapter.this.objects.get(position).isActive()){
                        ((AlarmActivity)mContext).setAlarm(AlarmArrayAdapter.this.objects.get(position));

                        String timeString = "";
                        if (AlarmArrayAdapter.this.objects.get(position).getTime().get(Calendar.HOUR_OF_DAY) <= 9) {
                            timeString = "0" + AlarmArrayAdapter.this.objects.get(position).getTime().get(Calendar.HOUR_OF_DAY);
                        } else {
                            timeString = "" + AlarmArrayAdapter.this.objects.get(position).getTime().get(Calendar.HOUR_OF_DAY);
                        }
                        timeString = timeString + ":";
                        if (AlarmArrayAdapter.this.objects.get(position).getTime().get(Calendar.MINUTE) <= 9) {
                            timeString = timeString + "0" + AlarmArrayAdapter.this.objects.get(position).getTime().get(Calendar.MINUTE);
                        } else {
                            timeString = timeString + AlarmArrayAdapter.this.objects.get(position).getTime().get(Calendar.MINUTE);
                        }
                        Toast.makeText(AlarmArrayAdapter.this.mContext.getApplicationContext(),
                                "Alarm set at " + timeString, Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });

            if (alarmTimeView != null){
                String timeString = "";
                if (i.getTime().get(Calendar.HOUR_OF_DAY) <= 9) {
                    timeString = "0" + i.getTime().get(Calendar.HOUR_OF_DAY);
                } else {
                    timeString = "" + i.getTime().get(Calendar.HOUR_OF_DAY);
                }
                timeString = timeString + ":";
                if (i.getTime().get(Calendar.MINUTE) <= 9) {
                    timeString = timeString + "0" + i.getTime().get(Calendar.MINUTE);
                } else {
                    timeString = timeString + i.getTime().get(Calendar.MINUTE);
                }
                alarmTimeView.setText(timeString);
            }

            if (alarmSwitchView != null){
                alarmSwitchView.setChecked(i.isActive());
            }
            if (alarmDaysView != null){
                String days = "";
                for (int j = 0; j < i.getDaysActive().length; j++) {
                    if (i.getDaysActive()[j]) {
                        days = days + dayNames[j] + " ";
                    }
                }
                alarmDaysView.setText(days);
            }
            if (i.getDescription() == "") {
                alarmTextView.setVisibility(View.INVISIBLE);
                alarmDivider.setVisibility(View.INVISIBLE);
            } else {
                alarmTextView.setVisibility(View.VISIBLE);
                alarmDivider.setVisibility(View.VISIBLE);
                if (alarmTextView != null){
                    alarmTextView.setText(i.getDescription());
                }
            }
        }

        // the view must be returned to our activity
        return v;

    }

    public ArrayList<Alarm> getAlarms() {
        return objects;
    }


}