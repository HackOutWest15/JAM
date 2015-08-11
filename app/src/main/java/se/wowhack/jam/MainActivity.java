package se.wowhack.jam;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Pager;
import kaaes.spotify.webapi.android.models.PlaylistSimple;
import kaaes.spotify.webapi.android.models.UserPrivate;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.wowhack.jam.models.Playlist;
import se.wowhack.jam.models.ProgressBarAnimation;

public class MainActivity extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {



    private static final String CLIENT_ID = "d7282f99268d46d7bc87e8006d9de939";
    private static final String REDIRECT_URI = "my-first-android-app-login://callback";

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;

    private Player mPlayer;
    private SpotifyApi api;
    private SpotifyService spotify;
    private String spotifyId;
    private ArrayList<Playlist> playLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = new SpotifyApi();
        playLists = new ArrayList<>();

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"user-read-private", "streaming", "playlist-read-private"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

<<<<<<< HEAD
        findViewById(R.id.gotoAlarm).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                gotoAlarm();
            }
        });

        findViewById(R.id.gotoCards).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                gotoCards();
            }
        });
=======
>>>>>>> webintegration
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

                final ProgressBar progressBar = ((ProgressBar) findViewById(R.id.progressBar));
        ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 1000, 1);
        progressBar.startAnimation(anim);
        //Check if result comes from the correct activity
        if(requestCode == REQUEST_CODE){
            final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            if(response.getType() == AuthenticationResponse.Type.TOKEN){
                //Init connection to rest api
                api.setAccessToken(response.getAccessToken());
                spotify = api.getService();

                spotify.getMe(new Callback<UserPrivate>() {
                    @Override
                    public void success(final UserPrivate userPrivate, Response r) {
                        spotify.getPlaylists(userPrivate.id, new Callback<Pager<PlaylistSimple>>() {
                            @Override
                            public void success(Pager<PlaylistSimple> playlistSimplePager, Response r) {
                                List<PlaylistSimple> tempPlaylists = playlistSimplePager.items;
                                for(PlaylistSimple list : tempPlaylists){
                                    Playlist temp = new Playlist(list.id, null, list.name);
                                    playLists.add(temp);
                                }
                                progressBar.clearAnimation();
                                gotoAlarm(playLists, userPrivate.id, response.getAccessToken());
                            }

                            @Override
                            public void failure(RetrofitError error) {

                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver(){
                    @Override
                    public void onInitialized(Player player){
                        Log.w("Initialized", "Init");
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addPlayerNotificationCallback(MainActivity.this);


                    }
                    @Override
                    public void onError(Throwable throwable){
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("Error login fail", error.getMessage());
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("MainActivity", "Playback event received: " + eventType.name());
        switch(eventType){
            //Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
        switch(errorType){
            //Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //VERY IMPORTANT! DO THIS ALWAYS MEMORY LEAK
        Spotify.destroyPlayer(this);
        super.onDestroy();

    }

    private void gotoAlarm(ArrayList<Playlist> playlists, String userId, String token) {
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.putExtra("Playlists", playlists);
        intent.putExtra("Userid", userId);
        intent.putExtra("Token", token);
        startActivity(intent);
    }

    private void gotoCards() {
        Intent intent = new Intent(this, CardsActivity.class);
        startActivity(intent);
    }
}