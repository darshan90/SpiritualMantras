package com.dstechnologies.mediaplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.dstechnologies.gurumantras.R;
import com.dstechnologies.gurumantras.SpiritualMantrasUIMainActivity;

/**
 * Created by DARSHAN on 6/21/2016.
 */
public class NotificationCreation {

    private Context mContext;
    private int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    private String title;
    private String title1;
    private String url;
    public RemoteViews notificationView;
    public RemoteViews notificationView1;
    CharSequence tickerText;
    public int cnt = 0;
    int icon;
    private Notification.Builder mBuilder;
    private Notification noti;

    public NotificationCreation(Context context, String url)
    {
        mContext = context;
        this.title = "Title";
        this.title1 = "Title1";
        this.url = url;
    }

    public Notification getNotification()
    {
        return noti;
    }

    public void createSongInfoNotification(int newtask, boolean bPlayOrPause,
                                           String[] songData,String mantraTitle)
    {
        NOTIFICATION_ID = newtask;
        if(this.title == null )
            return;

        if(this.title != null && this.title.trim().equalsIgnoreCase(""))
            return;

        this.title = "Title";
        String artistName = "Title1";
        notificationView = new RemoteViews(mContext.getPackageName(), R.layout.songinfo_persistent_noti);

        if(mantraTitle != null)
        {
            notificationView.setTextViewText(R.id.title, mantraTitle);
        }
        /*if(artistName != null)
        {
            notificationView.setTextViewText(R.id.song_name, artistName);
        }
        else
        {
            notificationView.setTextViewText(R.id.song_name,"JIVE ARTIST");
        }*/

        notificationView.setViewVisibility(R.id.play, View.GONE);
        notificationView.setViewVisibility(R.id.pause, View.VISIBLE);

        if (!bPlayOrPause)
        {
            notificationView.setViewVisibility(R.id.play, View.GONE);
            notificationView.setViewVisibility(R.id.pause, View.VISIBLE);
        }
        else
        {
            notificationView.setViewVisibility(R.id.pause, View.GONE);
            notificationView.setViewVisibility(R.id.play, View.VISIBLE);
        }

        mBuilder =  new Notification.Builder(mContext)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(title)
                .setDefaults(0);

        Intent i = null;
        Intent intent = null;

        i = new Intent(this.mContext, SpiritualMantrasUIMainActivity.class);
        i.putExtra("song_url", url);
        i.putExtra("startSplash", "1");
        i.setAction(Intent.ACTION_MAIN);

        Intent intent1= new Intent();
        intent1.setAction("com.example.mediaplayer.CLOSENOTI");

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);//test
        notificationView.setOnClickPendingIntent(R.id.ll_textContainer1, pendingIntent);

        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(mContext, 2, intent1, PendingIntent.FLAG_UPDATE_CURRENT);//test
        notificationView.setOnClickPendingIntent(R.id.close, pendingIntent3);

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        this.NOTIFICATION_ID = newtask;
        noti = mBuilder.build();
        noti.contentView = notificationView;
    }

}
