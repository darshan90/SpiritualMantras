package com.dstechnologies.mediaplayer;

import android.content.Intent;
import android.view.View;

import com.dstechnologies.gurumantras.MantrasDetailsActivity;
import com.dstechnologies.gurumantras.SpiritualMantrasUIMainActivity;

/**
 * Created by DARSHAN on 6/21/2016.
 */
public class WhiteMediaPlayer {

    //@Override
    public void seekTo(int lPosition)
    {
        CallService("seekTo","newPosition", lPosition);
    }
    private void CallService(String strAction, String strParameter, int iIntExtra)
    {
		/*Intent i = new Intent(MainActivity.cContext, SongPlayService.class);
		i.setAction(strAction);
		i.putExtra(strParameter, iIntExtra);
		MainActivity.cContext.startService(i);*/
    }
    public int iSongDuration = 0;
    public void setCurrentSongDuration(int iDuration)
    {
        iSongDuration = iDuration;
    }

    //@Override
    public void setDataSource(String strDataSourceUri,String title)
    {
        Intent i = new Intent(SpiritualMantrasUIMainActivity.ccContext, SongPlayService.class);
        i.setAction("setDataSource");
        i.putExtra("URL", strDataSourceUri);
        i.putExtra("title",title);
        SpiritualMantrasUIMainActivity.ccContext.startService(i);

    }

    public boolean isPlaying()
    {

        return true;
    }

    public void pause()
    {
/*		Intent i = new Intent(MainActivity.cContext, SongPlayService.class);
		i.setAction("Pause");
		MainActivity.cContext.startService(i);
		GenericOnTouchListner.bUpdateSeekBar = false;
*/	}

    public void start()
    {
		/*Intent i = new Intent(MainActivity.cContext, SongPlayService.class);
		i.setAction("Start");
		MainActivity.cContext.startService(i);*/

    }

    public void release()
    {
		/*Intent i = new Intent(MainActivity.cContext, SongPlayService.class);
		i.setAction("Release");
		MainActivity.cContext.startService(i);*/

    }

    public void stop()
    {
		/*Intent i = new Intent(MainActivity.cContext, SongPlayService.class);
		i.setAction("Stop");
		MainActivity.cContext.startService(i);*/
    }

    public void prepareAsync()
    {
    }

    public void setAudioStreamType(int streamMusic)
    {
    }

    public int getCurrentPosition(int iOption)
    {
		/*Intent i = new Intent(MainActivity.cContext, SongPlayService.class);
		i.setAction("GetCurrentPosition");
		i.putExtra("iOption", iOption);
		MainActivity.cContext.startService(i);
		return 0;*/
        return 0;
    }

    public void stopandstartMediaPlayer(/*int pos*/)
    {
/*		Intent i = new Intent(MainActivity.cContext, SongPlayService.class);
		i.setAction("Stopandstartplayer");
		MainActivity.cContext.startService(i);
*/	}

    public void isPrepared()
    {
/*		Intent i = new Intent(MainActivity.cContext, SongPlayService.class);
		i.setAction("IsPrepared");
		MainActivity.cContext.startService(i);
*/	}

}
