package se.wowhack.jam.models;

/**
 * Created by jonathan on 11/08/15.
 */
public class Track {
    private String name;
    private String uri;
    private String id;

    public Track(String name, String uri, String id){
        this.name = name;
        this.uri = uri;
        this.id = id;

    }

    public String getId() {
        return id;
    }
    public String getName(){
        return this.name;
    }

    public String getUri(){
        return this.uri;
    }
}
