package se.wowhack.jam.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jonathan on 11/08/15.
 */
public class Playlist implements Serializable{
    private List<String> tracks;
    private String id;
    private String name;

    public Playlist(){

    }
    public Playlist(String id, List<String> tracks, String name){
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

    public List<String> getTracks() {

        return tracks;
    }

    public void setTracks(List<String> tracks) {
        this.tracks = tracks;
    }

    public void shuffleTracks(){
        Collections.shuffle(tracks);

    }
}
