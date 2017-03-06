package com.dstechnologies.mediaplayer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by DARSHAN on 6/21/2016.
 */
public class DownloadServices extends Service {

    private ArrayList<String[]> downloadList = new ArrayList<String[]>();
    public static boolean bDownloadStarted = false;

    public  void addToDownloadList(String[] strPassedDownloadItem)
    {
        //Log.e("shreyasonaddownload","shreyasonaddownload"+strPassedDownloadItem[0]);
        downloadList.add(strPassedDownloadItem);
    }

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        try
        {
            IntentFilter filter = new IntentFilter();
            filter.addAction("DOWNLOAD_SONG"); //further more
            registerReceiver(downloadImageBroadcast, filter);
            Log.e("shreyasbroadcastregistering", "shreyasbroadcastregistering");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(downloadImageBroadcast);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //boolean flag = true
        //String[] data = intent.getStringArrayExtra("fileName");
        try
        {
            Log.e("shreyasonStart","shreyasonStart");
            if (intent.getStringArrayExtra("fileName") != null)
            {
                addToDownloadList(intent.getStringArrayExtra("fileName"));
                processDownload();
            }
        }
        catch(Exception e)
        {
            Log.e("shreyasd"+e.toString(),"shreyasd"+e.toString());
            e.printStackTrace();
        }
        return Service.START_NOT_STICKY;
    }

	/*	public int onStartCommand1(Intent intent, int flags, int startId)
	{
		if(intent != null && intent.getStringExtra("stopForeground")!=null)
		{
			stopForeground(true);
			return START_NOT_STICKY;
		}
		stopForeground(true);//Shreyas
		String[] strDownloadFileName = new String[2];


		if(intent !=null && intent.getStringArrayExtra("fileName") != null)
		{
			strDownloadFileName = intent.getStringArrayExtra("fileName");
			try
			{
						new DownloadingTheSongs(getBaseContext(), strDownloadFileName[0]);
				Log.e("dropboxurl0"+strDownloadFileName[0],"dropboxurl0"+strDownloadFileName[0]);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return Service.START_STICKY;
	}
	 */

    private void processDownload()
    {
        for (Iterator<String[]> dfIter = downloadList.iterator(); dfIter.hasNext();)
        {
            String[] strDownloadFileName = dfIter.next();

            if (strDownloadFileName[1].contains("song"))
            {
                new DownloadingTheSongs(getBaseContext(), strDownloadFileName[0]);
            }
            //check if file is downloaded and then remove CheckFile()
            if (CheckFile())
            {
                dfIter.remove();
                break;
            }
            if(!dfIter.hasNext())
            {
                stopSelf();
                return;
            }
        }
        if (!downloadList.isEmpty())
            processDownload(); // recurse for unsuccessfull download and mid way adds to download by user.
    }

    private boolean CheckFile()
    {
        return true;
    }
    private final BroadcastReceiver downloadImageBroadcast = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {

            Log.e("shreyasbroadcastinonrecieve","shreyasbroadcastinonrecieve");

            if(intent.getAction().equalsIgnoreCase("DOWNLOAD_SONG"))
            {
                Log.e("shreyasbroadcast","shreyasbroadcast");
                Intent intent1 = new Intent(context,DownloadServices.class);
                intent1.putExtra("fileName", intent.getStringArrayExtra("strPassedFileName"));
                context.startService(intent1);
            }
        }
    };

}
