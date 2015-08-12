package se.wowhack.jam.Utils;

import android.util.Log;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.FeaturedPlaylists;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.PlaylistTracksInformation;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.wowhack.jam.models.Playlist;

/**
 * Created by jonathan on 11/08/15.
 */
public class Backend {
    private static Backend instance = null;
    private String accessToken;
    private Playlist stored;
    private Backend(){

    }

    public static Backend getInstance(){
        if(instance == null){
            instance = new Backend();
        }
        return instance;
    }
    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }
    public String getAccessToken(){
        return this.accessToken;

    }

    public void setStored(Playlist stored){
        this.stored = stored;
    }

    public Playlist getStored(){
        return this.stored;
    }




}
