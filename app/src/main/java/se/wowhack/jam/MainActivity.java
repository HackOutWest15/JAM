package se.wowhack.jam;

import android.app.Activity;
import android.content.Intent;
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
public class MainActivity extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {



    private static final String CLIENT_ID = "d7282f99268d46d7bc87e8006d9de939";
    private static final String REDIRECT_URI = "my-first-android-app-login://callback";

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;

    private Player mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //Check if result comes from the correct activity
        if(requestCode == REQUEST_CODE){
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            if(response.getType() == AuthenticationResponse.Type.TOKEN){
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver(){
                    @Override
                    public void onInitialized(Player player){
                        //Default song shoreline
                        Log.w("Initialized", "Init");
                        mPlayer.addConnectionStateCallback(MainActivity.this);
                        mPlayer.addPlayerNotificationCallback(MainActivity.this);
                        mPlayer.play("spotify:track:2nK30KDjrDHRedyIcHTOQS");
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


        findViewById(R.id.gotoAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAlarm();
            }
        });
    }

    private void gotoAlarm() {
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }

    private void gotoCards() {
        Intent intent = new Intent(this, CardsActivity.class);
        startActivity(intent);
    }
}