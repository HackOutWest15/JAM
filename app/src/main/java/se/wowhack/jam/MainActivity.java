package se.wowhack.jam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}