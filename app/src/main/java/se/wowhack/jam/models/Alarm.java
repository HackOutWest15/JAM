package se.wowhack.jam.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 11/08/15.
 */
public class Alarm {
    private Playlist playlist;
    private String description;
    private int time;
    private boolean active;
    private boolean[] daysActive = {false,false,false,false,false,false,false};

    public Alarm(){

    }

    public Alarm(Playlist playlist, String description, int time, boolean active, boolean[] daysActive){
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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
