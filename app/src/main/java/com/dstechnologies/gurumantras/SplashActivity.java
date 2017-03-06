package com.dstechnologies.gurumantras;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.io.File;

public class SplashActivity extends Activity {
    String[] dropBoxUrl = {"https://dl.dropboxusercontent.com/s/iynkr3b6mk5tirk/Mantra1_GayatrMantra.mp3?dl=0", "https://dl.dropboxusercontent.com/s/e9nbt02j6tncyhj/Mantra2_SaiMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/jr54e0vmgax6tal/Mantra3_HareKrishnaHareRama.mp3?dl=0", "https://dl.dropboxusercontent.com/s/w6536n0lankbe09/Mantra4_OmNamahaShivay.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/turn1r3rildzu1y/Mantra12_MahaMrutyuanjayMantra.mp3?dl=0", "https://dl.dropboxusercontent.com/s/zpfx41nv1yxavlo/Mantra5_OmNamoBhagwateWasudeway.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/78w7vm1k43vnylu/Mantra6_GaneshMantra.mp3?dl=0", "https://dl.dropboxusercontent.com/s/9sy56pgaoo5rt04/Mantra7_ShaniMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/ee0zyfxqxq85wi0/Mantra8_OmNamoNaraynay.mp3?dl=0", "https://dl.dropboxusercontent.com/s/jihcjrg4873oh8d/Mantra9_BalajiMantra.mp3?dl=0", "https://dl.dropboxusercontent.com/s/zs85xz42w2g0r9o/Mantra10_HanumanMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/hpfnrrmr4lxypvt/Mantra11_SwamiSamarthaMantra.mp3?dl=0", "https://dl.dropboxusercontent.com/s/oga8vphc1uag6t0/Mantra13_BudhhaMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/xzspjjgeulxvbo6/Mantra14_KaliMantra.mp3?dl=0", "https://dl.dropboxusercontent.com/s/xcrlyvfpcx344ei/Mantra_HanumanChalisa.mp3?dl=0"
    };


    public static final int SPLASH_TIMEOUT = 5000;

   Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(SplashActivity.this));
        setContentView(R.layout.splash_activity);


        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        session=new Session(getApplicationContext());

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // This function start to download mantras from web when app install.
        startThreadtodownloadMantra();
    }

    private void startThreadtodownloadMantra()
    {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < dropBoxUrl.length; i++) {
                        downloadTask(dropBoxUrl[i]);
                    }
                }
            });
            thread.setPriority(Thread.MAX_PRIORITY);
            thread.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // After 5 seconds it goes to mainactivity screen.
        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, SpiritualMantrasUIMainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 5000);

    }

    protected void downloadTask(String downloadUrl) {
        String[] sngUrl = new String[2];
        sngUrl[0] = downloadUrl;
        sngUrl[1] = "song";

        Intent intent = new Intent();
        intent.putExtra("strPassedFileName", sngUrl);
        intent.setAction("com.example.mediaplayer.DOWNLOAD_SONG");
        this.sendBroadcast(intent);
    }

}
