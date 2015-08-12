package se.wowhack.jam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import se.wowhack.jam.Utils.Backend;
import se.wowhack.jam.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class WakeUpActivity extends Activity implements PlayerNotificationCallback, ConnectionStateCallback {

    private Player mPlayer;
    PowerManager.WakeLock wl;
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_wake_up);
        Log.d("PlaylistId:" , ""+getIntent().getStringExtra("Playlist"));
        //Start a player
        Log.d("AccessToken:", Backend.getInstance().getAccessToken());
        Config playerConfig = new Config(this, Backend.getInstance().getAccessToken(), "d7282f99268d46d7bc87e8006d9de939");
        mPlayer = Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
            @Override
            public void onInitialized(Player player) {
                player.addConnectionStateCallback(WakeUpActivity.this);
                player.addPlayerNotificationCallback(WakeUpActivity.this);
                if(Backend.getInstance().getStored() == null || Backend.getInstance().getStored().getTracks().size() == 0) {
                    player.play("spotify:track:5XsMz0YfEaHZE0MTb1aujs");
                }
                else{
                    Backend.getInstance().getStored().shuffleTracks();
                    player.play(Backend.getInstance().getStored().getTracks());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });

        findViewById(R.id.awake_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.shutdown();
                //navigateBack();

            }
        });
        findViewById(R.id.snooze_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause();
                new CountDownTimer(30000, 1000) {//CountDownTimer(edittext1.getText()+edittext2.getText()) also parse it to long

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        mPlayer.resume();
                    }
                }
                        .start();
            }
        });









        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Tag");

        wl.acquire();
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {

    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {

    }

    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Throwable throwable) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    public void onAwake(View view){
        // turn off music
        Log.v("WakeUpActivity", "awake");
        Window window = this.getWindow();



        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        /** Turn off: */
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        //TODO Store original brightness value
        params.screenBrightness = 00;
        this.getWindow().setAttributes(params);




        wl.release();



    }

    public void navigateBack(){
        Intent intent = new Intent(this, AlarmActivity.class);
        startActivity(intent);
    }

    public void onSnooze(View view){
        // set alarm in 5 minutes
        Log.v("WakeUpActivity", "snooze");

    }

    public void nextSong(View view){
        Log.v("WakeUpActivity", "next");

    }

}
