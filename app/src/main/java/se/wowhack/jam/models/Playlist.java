package se.wowhack.jam.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 11/08/15.
 */
public class Playlist {
    private List<Track> tracks;
    private String id;
    private String name;

    public Playlist(){

    }
    public Playlist(String id, List<Track> tracks, String name){
        if(tracks == null){
            tracks = new ArrayList<>();
        }else {
            this.tracks = tracks;
        }
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTracks() {

        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
