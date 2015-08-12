package se.wowhack.jam.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jonathan on 11/08/15.
 */
public class Alarm {
    private Playlist playlist;
    private String description;
    private Calendar time;
    private boolean active;
    private boolean[] daysActive = {false,false,false,false,false,false,false};

    public Alarm(){
        this.playlist = null;
        this.description = "Placeholder text here yolo";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        this.time = calendar;
        this.active = true;
        for (int i = 0; i < daysActive.length; i++) {
            daysActive[i] = true;
        }
    }

    public Alarm(Playlist playlist, String description, Calendar time, boolean active, boolean[] daysActive){
        this.playlist = playlist;
        this.description = description;
        this.time = time;
        this.active = active;
        this.daysActive = daysActive;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getTime() {
        return time;
    }

    public void setHour(int hour) {
        time.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setMinute(int minute) {
        time.set(Calendar.MINUTE, minute);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean[] getDaysActive() {
        return daysActive;
    }

    public void setDaysActive(boolean[] daysActive) {
        this.daysActive = daysActive;
    }
}
