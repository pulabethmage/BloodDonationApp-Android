package com.industrialmaster.rudhiraya;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("LOGINSTATUS", MODE_PRIVATE);
        final String loginstatus = prefs.getString("logstatus", "0");

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                if(loginstatus.equals("1")){

                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(MainActivity.this,HomeActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();

                }
                else{

                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(MainActivity.this,DonateBloodActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();

                }




            }
        }, SPLASH_DISPLAY_LENGTH);





    }
}
