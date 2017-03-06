package com.dstechnologies.mediaplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dstechnologies.gurumantras.SpiritualMantrasUIMainActivity;

/**
 * Created by DARSHAN on 6/21/2016.
 */
public class AudioPlayerBroadcastReceiver extends BroadcastReceiver {

    private Context cContext;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        cContext = context;
        if(action == null)
            return;
        //Log.e("shreyasinbroadcast11","shreyasinbroadcast11::"+action);
        if(action.equalsIgnoreCase("com.example.mediaplayer.CLOSENOTI"))
        {
            Log.e("shreyasinbroadcast12", "shreyasinbroadcast12::" + action);
        }
        if (SpiritualMantrasUIMainActivity.ccContext == null)
        {
            if(action.equalsIgnoreCase("com.example.mediaplayer.CLOSENOTI"))
            {
                Intent i = new Intent(context, SongPlayService.class);
                i.setAction("Close_Noti");
                context.startService(i);
                return;
            }
        }

        if(action.equalsIgnoreCase("com.example.mediaplayer.CLOSENOTI"))
        {
            Intent i = new Intent(context, SongPlayService.class);
            i.setAction("Close_Noti");
            context.startService(i);
        }

        if(action.equalsIgnoreCase("com.example.mediaplayer.DOWNLOAD_SONG"))
        {
            String[] data = intent.getStringArrayExtra("strPassedFileName");
            Intent intent1 = new Intent(context,DownloadServices.class);
            intent1.putExtra("fileName", intent.getStringArrayExtra("strPassedFileName"));
            context.startService(intent1);
        }
    }
}
