package com.dstechnologies.mediaplayer;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.dstechnologies.gurumantras.MantrasDetailsActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by DARSHAN on 6/21/2016.
 */
public class SongPlayService extends Service {

    private static final String TAG = "MyService";
    public static MediaPlayer player;
    SharedPreferences preferences ;
    public static String mantraTitle = null;
    private boolean bShowBuffering = false;
    private String strUrl = "";
    private String strPanelUrl = "";
    private String[] strArrayCurrentSong;
    public static NotificationCreation mNotificationHelper = null;
    public static String MAIN_ACTION = "com.truiton.foregroundservice.action.main";
    public static String PREV_ACTION = "com.truiton.foregroundservice.action.prev";
    public static String PLAY_ACTION = "com.truiton.foregroundservice.action.play";
    public static String NEXT_ACTION = "com.truiton.foregroundservice.action.next";
    public static String STARTFOREGROUND_ACTION = "com.truiton.foregroundservice.action.startforeground";
    public static String STOPFOREGROUND_ACTION = "com.truiton.foregroundservice.action.stopforeground";
    public static String STOPBUFFERINGDISPLAY ="com.truiton.foregroundservice.action.stopbuffering";
    public static String SHOWBUFFERING ="com.truiton.foregroundservice.action.showbuffering";
    public static int FOREGROUND_SERVICE = 101;
    private static final String LOG_TAG = "ForegroundService";
    public static String url="http://jiveplus.sonymusicjive.com/content/128K";
    @Override
    public void onDestroy() {	Log.d(TAG, "onDestroy");}

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (intent != null)
        {
            String actionName= intent.getAction();
            if(player == null)
                player = new MediaPlayer();

            if(actionName.equalsIgnoreCase("Pause"))
            {
                if(player.isPlaying())
                {
                    try
                    {
                        //ArrayList<String[]> alTempPlayerQueue = (ArrayList<String[]>) Constants.player_queue.clone();
                        //CommonMethods.GetPlayerQueueFromDatabase(this);
                        //int iPosition = GenericOnTouchListner.GetCurrentSongPostion(strUrl,0);
                        //strArrayCurrentSong = Constants.player_queue.get(iPosition);
						/*if (strArrayCurrentSong == null)
						{
							for (String[] str : alTempPlayerQueue)
							{
								if (str[Constants.SONG_URI].equalsIgnoreCase(strUrl))
								{
									strArrayCurrentSong = str;
									break;
								}
							}
						}*/
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                    stopForeground(false);
                    CreateSongInfoNotitication(strArrayCurrentSong, true);
                    startForeground(FOREGROUND_SERVICE,mNotificationHelper.getNotification());
                    player.pause();

                }
            }
            else if(actionName.equalsIgnoreCase("Release"))
            {
                player.release();
                player = null;
            }
            else if(actionName.equalsIgnoreCase("Stop"))
            {
                if (player != null && player.isPlaying())
                    player.stop();
            }
            else if(actionName.equalsIgnoreCase("Start"))
            {
                if (player != null && !player.isPlaying())
                    player.start();

            }
			/*else if(actionName.equalsIgnoreCase("GetCurrentPosition"))
			{
				try
				{
					int iOption = intent.getIntExtra("iOption", 0);
					Intent i=new Intent();
					i.setAction("com.white.sony.sonyjive.PLAYER_CURRENT_POSITION");
					i.putExtra("iOption", iOption);
					if(player != null && player.isPlaying())
						i.putExtra("iCurrentPosition", player.getCurrentPosition());
					else
						i.putExtra("iCurrentPosition", 0);

					sendBroadcast(i);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			 */
            else if(actionName.equalsIgnoreCase("Stopandstartplayer"))
            {
                try
                {
                    int playing = 0;
                    stopForeground(false);
                    if (player != null && player.isPlaying())
                    {
                        playing = 1;
                        player.stop();
                        player.release();
                    }
                    player = null;
                    if(player == null)
                        player = new MediaPlayer();
                    if(playing == 1)
                    {
                        try
                        {
                            Intent i=new Intent();
                            i.setAction("com.example.mediaplayer.PLAYER_IS_START");
                            sendBroadcast(i);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        playing = 0;
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            else if(actionName.equalsIgnoreCase("IsPrepared"))
            {
                try
                {
                    while(iIsPrepared==1)
                    {
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                    Intent i=new Intent();
                    i.setAction("com.example.mediaplayer.PLAYER_IS_PREAPARE");
                    i.putExtra("iIsPrepared", iIsPrepared);
                    sendBroadcast(i);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else if(actionName.equalsIgnoreCase("IsComplete"))
            {
                try
                {
                    Intent i=new Intent();
                    i.setAction("com.example.mediaplayer.PLAYER_IS_COMPLETE");
                    i.putExtra("isComplete", iIsCompleted);
                    sendBroadcast(i);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else if(actionName.equalsIgnoreCase("seekTo"))
            {
                try
                {
                    int iNewPosition = intent.getIntExtra("newPosition", 0);
                    player.seekTo(iNewPosition);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            else if(actionName.equalsIgnoreCase("setDataSource"))
            {
                String songUrl = null;
                strUrl  = intent.getStringExtra("URL");
                mantraTitle = intent.getStringExtra("title");
                try
                {
                    setDataSource(strUrl);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                //try
                //{
                // final File path = new File(Constants.ApplicationCachePath);
				/*  if (!path.exists())
				        path.mkdir();
				 */
                //}
                //catch (Exception e)
                //{
                // e.printStackTrace();
                //}

                //try
                //{
                // try
                //{
                //						strArrayCurrentSong = CommonMethods.getSongDetails(strUrl, this);//shreyas
                //					ArrayList<String[]> alTempPlayerQueue = (ArrayList<String[]>) Constants.player_queue.clone();
                // if (strArrayCurrentSong == null)
                //{
                //					for (String[] str : alTempPlayerQueue)
                // {
                //					if (str[Constants.SONG_URI].equalsIgnoreCase(strUrl))
                // {
                //					strArrayCurrentSong = str;
                //break;
                //}
                //}
                //}
                //}
                //catch (Exception e)
                // {
                // e.printStackTrace();
                //}
                //strUrl = getURL(strUrl, strArrayCurrentSong[Constants.SONG_TITILE]);
                //Log.e("shreyas","shreyas");
                //songUrl = strUrl;
                //	if(strUrl.contains("http"))
                //songUrl = getURL(strUrl, strArrayCurrentSong[Constants.SONG_TITILE]);
                //}
                //catch (Exception e)
                //{
                // e.printStackTrace();
                //}
                //try
                //{
                // setDataSource(songUrl);
                //}
                //catch (Exception e)
				/* {
					 e.printStackTrace();
				 }*/
            }

            else if (actionName.equals(NEXT_ACTION))
            {
                try
                {
                    Intent i=new Intent();
                    i.setAction("com.example.mediaplayer.PLAYER_CURRENT_POSITION");
                    i.putExtra("iOption", 0);
                    if(player.isPlaying())
                        i.putExtra("iCurrentPosition", 0);
                    else
                        i.putExtra("iCurrentPosition", 0);
                    sendBroadcast(i);
                    playSong(0);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            else if (actionName.equals(PREV_ACTION))
            {
                try
                {
                    Intent i=new Intent();
                    i.setAction("com.example.mediaplayer.PLAYER_CURRENT_POSITION");
                    i.putExtra("iOption", 0);
                    if(player.isPlaying())
                        i.putExtra("iCurrentPosition", 0);
                    else
                        i.putExtra("iCurrentPosition", 0);
                    sendBroadcast(i);
                    playSong(1);
                    Log.i(LOG_TAG, "Clicked Prev");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            else if (actionName.equals(PLAY_ACTION))
            {
                try
                {
                    playSong(2);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            else if (intent.getAction().equals(STOPFOREGROUND_ACTION))
            {
                try
                {
                    stopForeground(true);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            else if (intent.getAction().equals(STOPBUFFERINGDISPLAY))
            {
                try
                {
                    bShowBuffering = false;
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            else if (intent.getAction().equals(SHOWBUFFERING))
            {
                try
                {
                    bShowBuffering = true;
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            else if(intent.getAction().equals("Close_Noti"))
            {
                Log.e("shreyasinbroadcast12","shreyasinbroadcast12::service");
                try
                {
                    stopForeground(true);
                    if(player != null && player.isPlaying())
                    {
                        player.stop();
                        player.release();
                    }
                    player = null;
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startid)
    {
        player.start();
    }

    public void CreateSongInfoNotitication(String[] songData, boolean bPlayOrPause)
    {
        mNotificationHelper = new NotificationCreation(SongPlayService.this,"");
        mNotificationHelper.createSongInfoNotification(1000000, bPlayOrPause,songData,mantraTitle);
    }

    private int iIsPrepared,iIsCompleted;
    private long megAvailable;

    private void HandleMediaPlayer(final String strUrl)
    {
        iIsPrepared = 1;
        iIsCompleted = 0;
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                try
                {
                    if(!player.isPlaying())
                        player.start();
                    iIsPrepared =0;
                   /* stopForeground(false);
                    CreateSongInfoNotitication(strArrayCurrentSong, false);
                    startForeground(FOREGROUND_SERVICE,mNotificationHelper.getNotification());*///shreyas
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    iIsPrepared=-1;
                }
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer arg0)
                    {
                        /*Intent i=new Intent();
                        i.setAction("com.white.sony.sonyjive.PLAYER_IS_COMPLETE");
                        i.putExtra("iIsCompleted", iIsCompleted);
                        sendBroadcast(i);*/
                        player.stop();
                        player.release();
                        player = null;
                        startMediaPlayer(strUrl);
                    }
                });

                player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener()
                {
                    @Override
                    public void onBufferingUpdate(MediaPlayer arg0, int percent)
                    {
                        //if (bShowBuffering)
                        {
                            Intent i=new Intent();
                            i.setAction("com.white.sony.sonyjive.PLAYER_IS_BUFFER");
                            i.putExtra("iDuration", arg0.getDuration());
                            i.putExtra("iPercent", percent);
                            sendBroadcast(i);
                        }
                    }
                });
            }
        });
    }

    private void startMediaPlayer(String strUrl)
    {
        player = new MediaPlayer();

        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Uri nextTrack = Uri.parse(strDtUrl);
        try
        {
            player.setDataSource(SongPlayService.this,nextTrack);
            player.prepare();
            player.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        HandleMediaPlayer(strUrl);
    }

    public static void decrypt(String strurl,int cnt)
    {
        FileInputStream fis = null;
        GZIPInputStream gzis = null;
        FileOutputStream out = null;
        File file1;
        File file;
        try
        {
            strurl=strurl.substring(strurl.lastIndexOf("/"));
			/*if(!strurl.contains(".mp3c"))
				strurl = strurl+"c";
*/
            file=new File(Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/"+strurl+"s");
            fis = new FileInputStream(file);
            if(cnt == 1)
                file1=new File(Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/"+".temp");
            else
                file1=new File(Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/"+".temp1");

            gzis = new GZIPInputStream(fis);
            out = new FileOutputStream(file1);
            int len;
            byte[] buffer = new byte[(int) file.length()];
            while ((len = gzis.read(buffer)) > 0)
            {
                out.write(buffer, 0, len);
            }
            gzis.close();
            out.flush();
            out.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    String strSongUri = null;
    String strDtUrl = null;
    public void setDataSource(String strDataSourceUri)
    {
        bSongPlaying = false;
        strSongUri = strDataSourceUri;

        strDtUrl = "";
        try
        {
            try
            {
                if(Constants.tempCount == 0)
                {
                    decrypt(strDataSourceUri,Constants.tempCount);
                    Constants.tempCount = 1;
                    File f = new File(Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/"+".temp");
                    strDataSourceUri = (Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/"+".temp1");
                    strDtUrl = strDataSourceUri;
                    f.delete();
                }
                else
                {
                    decrypt(strDataSourceUri, Constants.tempCount);
                    Constants.tempCount = 0;
                    File f = new File(Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/"+".temp1");
                    strDataSourceUri = (Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/"+".temp");
                    strDtUrl = strDataSourceUri;
                    f.delete();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return;
            }
            if (player != null && player.isPlaying())
            {
                player.stop();
            }
            if (player != null)
            {
                player.release();
                player = null;
            }
            player = new MediaPlayer();

            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            Uri nextTrack = Uri.parse(strDtUrl);
            try
            {
                player.setDataSource(SongPlayService.this,nextTrack);
                player.prepare();
                player.start();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            HandleMediaPlayer(strDtUrl);
            stopForeground(false);
            CreateSongInfoNotitication(strArrayCurrentSong, false);
            startForeground(FOREGROUND_SERVICE,mNotificationHelper.getNotification());//shreyas
        }
        catch (Exception e)
        {
            strDtUrl = strSongUri;
            e.printStackTrace();
        }
        return;
    }

    private String strAkamaiUrl = "";
    public boolean bSongPlaying = false;
    public Handler hTempHandler = null;



    final Handler handler=new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message arg0)
        {
            if(arg0.arg1==ERROR_MESSEGE)
            {
                //Toast.makeText(SongPlayService.this, "Song playing failed due to server error. Please try again",Toast.LENGTH_LONG).show();//TODO shreyas
            }
            //setDataSource((String) arg0.obj);
            return false;
        }
    });

    protected void playSong(int iNextOrPrevious)
    {
        //if (Constants.player_queue == null )
        int iPosition = 0;
        int iMaxPosition = 0;
        try
        {

            if(strUrl.contains("http"))
            {
				/*			CommonMethods.GetPlayerQueueFromDatabase(this);
				iPosition = GenericOnTouchListner.GetCurrentSongPostion(strUrl,0);
				iMaxPosition = Constants.player_queue.size();
				 */
            }
            else
            {
				/*			String songUrl=strUrl.substring(strUrl.lastIndexOf("/"));
				if(songUrl.contains("mp3c"))
					songUrl = songUrl.replace("mp3c", "mp3");
				songUrl = url+songUrl;
				CommonMethods.GetPlayerQueueFromDatabase(this);
				iPosition = GenericOnTouchListner.GetCurrentSongPostion(songUrl,0);
				if(iPosition == -1)
					iPosition = 0;
				iMaxPosition = Constants.player_queue.size();
				 */
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        if (iNextOrPrevious == 0)
        {
            if(strUrl.contains("http"))
            {
                iPosition += 1;
                if (iPosition == iMaxPosition)
                    iPosition = 0;
            }
            else
                iPosition = 0;
        }
        else if (iNextOrPrevious == 1)
        {
            if(strUrl.contains("http"))
            {
                iPosition -= 1;
                if (iPosition == -1)
                    iPosition = (iMaxPosition - 1);
            }
            else
                iPosition = 0;
        }
        else
        {
            try
            {
                if (player != null && !player.isPlaying())
                    player.start();
                //				strArrayCurrentSong = Constants.player_queue.get(iPosition);
                stopForeground(false);
                //			CreateSongInfoNotitication(strArrayCurrentSong, false);
                //		startForeground(FOREGROUND_SERVICE, mNotificationHelper.getNotification());
                return;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        String dataSourceuri = null;
        try
        {
			/*strArrayCurrentSong = Constants.player_queue.get(iPosition);
			ArrayList<String[]> alTempPlayerQueue = (ArrayList<String[]>) Constants.player_queue.clone();
			if (strArrayCurrentSong == null)
			{
				for (String[] str : alTempPlayerQueue)
				{
					if (str[Constants.SONG_URI].equalsIgnoreCase(strUrl))
					{
						strArrayCurrentSong = str;
						break;
					}
				}
			}
			strUrl = strArrayCurrentSong[Constants.SONG_URI];
			strPanelUrl = strArrayCurrentSong[Constants.IMAGE_URI2];
			dataSourceuri = strUrl;
			if(strUrl.contains("http"))
				dataSourceuri = getURL(strUrl, strArrayCurrentSong[Constants.SONG_TITILE]);
			if(strUrl.contains("//"))
				strUrl.replace("//", "/");

			setDataSource(dataSourceuri);
			 */
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

		/*
  		stopForeground(false);
		CreateSongInfoNotitication(strArrayCurrentSong, false);
		startForeground(FOREGROUND_SERVICE, mNotificationHelper.getNotification());
		 */
        //player.prepareAsync();
        //HandleMediaPlayer();
    }
    protected static final int ERROR_MESSEGE = 0;

	/*@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}*/

    public String getURL(String url, String strSongTitle)
    {
        return strSongTitle;
		/*		if(!url.startsWith("http:"))
			return url;

		String strUrl=url.substring(url.lastIndexOf("/"));
		strUrl=Constants.ApplicationCachePath+"songs"+strUrl+"c";
		File file=new File(strUrl);
		if(file.exists())
			return strUrl;

		else
		{
			if(!CommonMethods.isSongDownloaded2(url, SongPlayService.this))
			{
				strUrl=Environment.getExternalStorageDirectory()+"/Download/SonyMusic/"+strSongTitle+".mp3";
				File file1=new File(strUrl);

				String strSongTitle1 = strSongTitle.replace("\"", "");
				String strUrl1=Environment.getExternalStorageDirectory()+"/Download/SonyMusic/"+strSongTitle1+".mp3";

				String strUrl2= url;
				File file3=new File(strUrl1);

				File file4=new File(url);
				if(file1.exists())
					return strUrl;

				if(file3.exists())
					return strUrl1;

				if(file4.exists())
					return strUrl2;

				return url;
			}
			strUrl=Environment.getExternalStorageDirectory()+"/Download/SonyMusic/"+strSongTitle+".mp3";
			File file1=new File(strUrl);

			String strSongTitle1 = strSongTitle.replace("\"", "");
			String strUrl1=Environment.getExternalStorageDirectory()+"/Download/SonyMusic/"+strSongTitle1+".mp3";
			File file2=new File(strUrl1);

			if(file1.exists())
				return strUrl;
			if(file2.exists())
				return strUrl1;
			else
				return url;
		}
		 */		//return url;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


}
