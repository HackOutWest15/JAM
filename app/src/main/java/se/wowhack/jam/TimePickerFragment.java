package se.wowhack.jam;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import java.util.Calendar;

import se.wowhack.jam.models.Alarm;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private Alarm alarm;
    private DFragment frag;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void setAlarm(Alarm alarm){
        this.alarm = alarm;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
        ((AlarmActivity) getActivity()).setAlarm(hourOfDay, minuteOfHour);
        alarm.setMinute(minuteOfHour);
        alarm.setHour(hourOfDay);
        frag.notifyUpdate();
        Log.d("####", "Time set");


    }

    public void setListener(DFragment frag){
        Log.d("####", "Listener set");
        this.frag = frag;
    }
}