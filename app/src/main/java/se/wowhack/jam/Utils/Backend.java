package se.wowhack.jam.Utils;

import android.util.Log;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.FeaturedPlaylists;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.PlaylistTracksInformation;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jonathan on 11/08/15.
 */
public class Backend {
    private static Backend instance = null;
    SpotifyApi api;
    SpotifyService spotify;
    private Backend(String accessToken){
        api = new SpotifyApi();
        api.setAccessToken(accessToken);
        spotify = api.getService();
    }

    public static Backend getInstance(String accessToken){
        if(instance == null){
            instance = new Backend(accessToken);
        }
        return instance;
    }

    public void printMyPlaylists(){
        spotify.getMe(new Callback<UserPrivate>() {
            @Override
            public void success(UserPrivate userPrivate, Response response) {

                spotify.getPlaylists(userPrivate.id, new Callback<Pager<PlaylistSimple>>() {
                    @Override
                    public void success(Pager<PlaylistSimple> playlistPager, Response response) {
                        List<PlaylistSimple> playlists = playlistPager.items;

                        for(PlaylistSimple p : playlists){
                            Log.d("PLAYLIST: ", p.name);
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Fail", "");
            }
        });
    }

    public void getMyPlaylists(){


    }




}
