package com.dstechnologies.gurumantras;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dstechnologies.mediaplayer.SongPlayService;
import com.dstechnologies.mediaplayer.WhiteMediaPlayer;
import com.dstechnologies.utils.Utilities;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import dmax.dialog.SpotsDialog;

public class MantrasDetailsActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    public static ImageView mainImageView,playMantra,shareMantra,setasRingMantra,setwallpaper,pause,infoMantra;
    Context context;
    ProgressDialog progressDialog;
    Integer thumbImages[] = {R.drawable.devi, R.drawable.saibaba, R.drawable.krishna, R.drawable.shiva, R.drawable.mahamritunjay,R.drawable.narayana,R.drawable.ganesha,R.drawable.shani,R.drawable.omnamonarayana,R.drawable.balaji,R.drawable.hanuman,R.drawable.swamisamrath,R.drawable.buddha,R.drawable.kalimata,R.drawable.hanuman,R.drawable.gurudevdatta};
    TextView mantraTitle,nameOfMantra;
    Animation animationToLeft;
    AdView adView;
    AdRequest adrequest;
    Typeface typeface;
    Handler mHandler;
    ImageView backbutton;
    String [] mantraMeaning;
    AlertDialog alert = null;
    AlertDialog alertDialog;
    View view;
    Toolbar toolbar;
   // public static AdView adView = null;

    String []dropBoxUrl={"https://dl.dropboxusercontent.com/s/iynkr3b6mk5tirk/Mantra1_GayatrMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/e9nbt02j6tncyhj/Mantra2_SaiMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/jr54e0vmgax6tal/Mantra3_HareKrishnaHareRama.mp3?dl=0","https://dl.dropboxusercontent.com/s/w6536n0lankbe09/Mantra4_OmNamahaShivay.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/turn1r3rildzu1y/Mantra12_MahaMrutyuanjayMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/zpfx41nv1yxavlo/Mantra5_OmNamoBhagwateWasudeway.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/78w7vm1k43vnylu/Mantra6_GaneshMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/9sy56pgaoo5rt04/Mantra7_ShaniMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/ee0zyfxqxq85wi0/Mantra8_OmNamoNaraynay.mp3?dl=0","https://dl.dropboxusercontent.com/s/jihcjrg4873oh8d/Mantra9_BalajiMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/zs85xz42w2g0r9o/Mantra10_HanumanMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/hpfnrrmr4lxypvt/Mantra11_SwamiSamarthaMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/oga8vphc1uag6t0/Mantra13_BudhhaMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/xzspjjgeulxvbo6/Mantra14_KaliMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/xcrlyvfpcx344ei/Mantra_HanumanChalisa.mp3?dl=0","https://dl.dropboxusercontent.com/s/e0qgnmi02dnkkco/Mantra15_GurudevDatta.mp3?dl=0"
    };

    String []mantraName=null;
    int position;
    WhiteMediaPlayer wmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(MantrasDetailsActivity.this));
        setContentView(R.layout.activity_mantras_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left);
        mantraTitle= (TextView) toolbar.findViewById(R.id.titleOfMantras);
        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        relativeLayout= (RelativeLayout) findViewById(R.id.Layout);
        typeface=Typeface.createFromAsset(getAssets(),"fonts/CaviarDreams.ttf");
        mainImageView= (ImageView) findViewById(R.id.mainImageView);
        playMantra= (ImageView) findViewById(R.id.playButton);
        shareMantra= (ImageView) findViewById(R.id.shareButton);
        setasRingMantra= (ImageView) findViewById(R.id.ringtonebutton);
        setwallpaper= (ImageView) findViewById(R.id.wallpaperbutton);
        pause= (ImageView) findViewById(R.id.pauseButton);
        infoMantra= (ImageView) findViewById(R.id.infoButton);
        nameOfMantra= (TextView) findViewById(R.id.mantranameView);
        adView = (AdView) findViewById(R.id.adView);
        adrequest = new AdRequest.Builder().build();

        adView.loadAd(adrequest);
        nameOfMantra.setSelected(true);
;       mantraMeaning=getResources().getStringArray(R.array.mantraDetails);
        // Animation for title textview
        Animation animationToRight = new TranslateAnimation(-400,400, 0, 0);
        animationToLeft = new TranslateAnimation(400, -400, 0, 0);
        animationToLeft.setDuration(12000);
        animationToLeft.setRepeatMode(Animation.RESTART);
        animationToLeft.setRepeatCount(Animation.INFINITE);
        mantraTitle.setAnimation(animationToLeft);
        //mantraTitle.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface, Typeface.BOLD);
        nameOfMantra.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface, Typeface.BOLD);
        mantraName = getResources().getStringArray(R.array.titleOfMantras);
        getDetailsfromlistData();
        genrateKeyHash();

        if(Constants.getIsMantraPlaying())
        {
            pause.setVisibility(View.VISIBLE);
        }

        playMantra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Mantra", "Mantra position:" + position);

                    switch (position)
                    {
                        case 0: int a = DownloadAsync(dropBoxUrl[position]);
                            if (a == 1) {
                                Constants.setPosition(position);
                                Constants.setIsMantraPlaying(true);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra1_GayatrMantra.mp3", mantraName[position]);
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);

                            }
                            break;
                        case 1:int b=DownloadAsync(dropBoxUrl[position]);
                            if(b==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra2_SaiMantra.mp3", mantraName[position]);
                            }
                            break;
                        case 2:int c=DownloadAsync(dropBoxUrl[position]);
                            if(c==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra3_HareKrishnaHareRama.mp3",mantraName[position]);
                            }
                            break;
                        case 3:int d=DownloadAsync(dropBoxUrl[position]);
                            if(d==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra4_OmNamahaShivay.mp3",mantraName[position]);
                            }
                            break;
                        case 4:int e=DownloadAsync(dropBoxUrl[position]);
                            if(e==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra12_MahaMrutyuanjayMantra.mp3",mantraName[position]);
                            }
                            break;
                        case 5:int f=DownloadAsync(dropBoxUrl[position]);
                            if(f==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra5_OmNamoBhagwateWasudeway.mp3",mantraName[position]);

                            }
                        case 6:int g=DownloadAsync(dropBoxUrl[position]);
                            if(g==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra6_GaneshMantra.mp3",mantraName[position]);

                            }
                            break;
                        case 7:int h=DownloadAsync(dropBoxUrl[position]);
                            if(h==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra7_ShaniMantra.mp3",mantraName[position]);

                            }
                            break;
                        case 8:int i=DownloadAsync(dropBoxUrl[position]);
                            if(i==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra8_OmNamoNaraynay.mp3",mantraName[position]);

                            }
                            break;
                        case 9:int j=DownloadAsync(dropBoxUrl[position]);
                            if(j==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra9_BalajiMantra.mp3",mantraName[position]);

                            }
                            break;
                        case 10:int k=DownloadAsync(dropBoxUrl[position]);
                            if(k==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra10_HanumanMantra.mp3",mantraName[position]);

                            }
                            break;
                        case 11:int l=DownloadAsync(dropBoxUrl[position]);
                            if(l==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra11_SwamiSamarthaMantra.mp3",mantraName[position]);

                            }
                            break;
                        case 12:int m=DownloadAsync(dropBoxUrl[position]);
                            if(m==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra13_BudhhaMantra.mp3",mantraName[position]);

                            }
                            break;
                        case 13:int n=DownloadAsync(dropBoxUrl[position]);
                            if(n==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra14_KaliMantra.mp3",mantraName[position]);

                            }
                            break;

                        case 14:int o=DownloadAsync(dropBoxUrl[position]);
                            if(o==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra_HanumanChalisa.mp3",mantraName[position]);

                            }
                            break;
                        case 15:int p=DownloadAsync(dropBoxUrl[position]);
                            if(p==1)
                            {
                                //Toast.makeText(getApplicationContext(), "Position:" + position, Toast.LENGTH_SHORT).show();
                                playMantra.setVisibility(View.INVISIBLE);
                                pause.setVisibility(View.VISIBLE);
                                WhiteMediaPlayer wmp = new WhiteMediaPlayer();
                                wmp.setDataSource(Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/Mantra15_GurudevDatta.mp3",mantraName[position]);

                            }
                            break;

                    }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            pause.setVisibility(View.INVISIBLE);
            playMantra.setVisibility(View.VISIBLE);
                Intent i = new Intent(MantrasDetailsActivity.this, SongPlayService.class);
                i.setAction("Stop");
                startService(i);

            }
        });

        setwallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (position)
                {
                    case 0:setWallpaperDialog();
                        break;
                    case 1:setWallpaperDialog();
                        break;
                    case 2:setWallpaperDialog();
                        break;
                    case 3:setWallpaperDialog();
                        break;
                    case 4:setWallpaperDialog();
                        break;
                    case 5:setWallpaperDialog();
                        break;
                    case 6:setWallpaperDialog();
                        break;
                    case 7:setWallpaperDialog();
                        break;
                    case 8:setWallpaperDialog();
                        break;
                    case 9:setWallpaperDialog();
                        break;
                    case 10:setWallpaperDialog();
                        break;
                    case 11:setWallpaperDialog();
                        break;
                    case 12:setWallpaperDialog();
                        break;
                    case 13:setWallpaperDialog();
                        break;
                    case 14:setWallpaperDialog();
                        break;
                    case 15:setWallpaperDialog();
                        break;


                }


            }
        });
        shareMantra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out Spiritual Mantra app available on Playstore https://play.google.com/store/apps/details?id=com.dstechnologies.gurumantras&hl=en");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });

        setasRingMantra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            switch(position)
            {
                case 0:
                        openRingtoneAlert(position);
                    break;
                case 1: openRingtoneAlert(position);
                    break;
                case 2: openRingtoneAlert(position);
                    break;
                case 3: openRingtoneAlert(position);
                    break;
                case 4: openRingtoneAlert(position);
                    break;
                case 5: openRingtoneAlert(position);
                    break;
                case 6: openRingtoneAlert(position);
                    break;
                case 7: openRingtoneAlert(position);
                    break;
                case 8: openRingtoneAlert(position);
                    break;
                case 9: openRingtoneAlert(position);
                    break;
                case 10: openRingtoneAlert(position);
                    break;
                case 11: openRingtoneAlert(position);
                    break;
                case 12: openRingtoneAlert(position);
                    break;
                case 13: openRingtoneAlert(position);
                    break;
                case 14: openRingtoneAlert(position);
                    break;
                case 15: openRingtoneAlert(position);
                    break;

            }

            }
        });


        infoMantra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String meaningOfMantra=null;
                AlertDialog.Builder alertdialog=new AlertDialog.Builder(MantrasDetailsActivity.this);
                LayoutInflater inflater=getLayoutInflater();
                final View dialog=inflater.inflate(R.layout.diloglayout, null);
                TextView mantraView=(TextView) dialog.findViewById(R.id.appinfotextview);
                Button closeDialogButton= (Button) dialog.findViewById(R.id.close);
                Button okDialogButton= (Button) dialog.findViewById(R.id.okbutton);

                switch (position)
                {
                    case 0:meaningOfMantra=mantraMeaning[position];
                           mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                           mantraView.setText(meaningOfMantra);
                           alertdialog.setView(dialog);
                           alert=alertdialog.create();
                           alert.show();
                        break;
                    case 1:meaningOfMantra=mantraMeaning[position];
                           mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                           mantraView.setText(meaningOfMantra);
                           alertdialog.setView(dialog);
                           alert=alertdialog.create();
                           alert.show();
                        break;
                    case 2:meaningOfMantra=mantraMeaning[position];
                           mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                           mantraView.setText(meaningOfMantra);
                           alertdialog.setView(dialog);
                           alert=alertdialog.create();
                           alert.show();
                        break;
                    case 3:meaningOfMantra=mantraMeaning[position];
                          mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                          mantraView.setText(meaningOfMantra);
                          alertdialog.setView(dialog);
                          alert=alertdialog.create();
                          alert.show();
                        break;
                    case 4:meaningOfMantra=mantraMeaning[position];
                         mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                         mantraView.setText(meaningOfMantra);
                         alertdialog.setView(dialog);
                         alert=alertdialog.create();
                         alert.show();
                        break;
                    case 5:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;
                    case 6:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;
                    case 7:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;
                    case 8:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;
                    case 9:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;
                    case 10:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;
                    case 11:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;
                    case 12:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;
                    case 13:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;
                    case 14:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;
                    case 15:meaningOfMantra=mantraMeaning[position];
                        mantraView.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface);
                        mantraView.setText(meaningOfMantra);
                        alertdialog.setView(dialog);
                        alert=alertdialog.create();
                        alert.show();
                        break;


                }

                closeDialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.cancel();

                    }
                });
                okDialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.cancel();
                    }
                });

            }
        });
    }

    private void setWallpaperDialog() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View testview=inflater.inflate(R.layout.dialog_with_title_text_twobtns, null);
        TextView title= (TextView) testview.findViewById(R.id.dialogTitle);
        TextView message= (TextView) testview.findViewById(R.id.dialog_message);
        title.setText("Wallpaper");
        message.setText("Do you want to set it as a wallpaper?");
        Button yesButton= (Button) testview.findViewById(R.id.positiveButton);
        Button noButton= (Button) testview.findViewById(R.id.negativeButton);
        alertDialog.setView(testview);
        final AlertDialog dialog=alertDialog.create();
        dialog.show();
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWallpaper(thumbImages[position]);
                dialog.dismiss();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void openRingtoneAlert(final int position) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View testview=inflater.inflate(R.layout.dialog_with_title_text_twobtns, null);
        TextView title= (TextView) testview.findViewById(R.id.dialogTitle);
        TextView message= (TextView) testview.findViewById(R.id.dialog_message);
        title.setText("Ringtone");
        message.setText("Do you want to set it as a ringtone?");
        Button yesButton= (Button) testview.findViewById(R.id.positiveButton);
        Button noButton= (Button) testview.findViewById(R.id.negativeButton);
        alertDialog.setView(testview);
        final AlertDialog dialog=alertDialog.create();
        dialog.show();
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRingtone(position);
                dialog.dismiss();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void setRingtone(int position) {

        try
        {
          //  File dir = new File (Environment.getExternalStorageDirectory()+"/Android/data/com.example/ringtone");
            File dir = new File (Environment.getExternalStorageDirectory()+"/ringtone");
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            String[] strUrl1 = dropBoxUrl[position].split("\\?");
            String strUrl11=strUrl1[0].substring(strUrl1[0].lastIndexOf("/") + 1, strUrl1[0].length());
            String[] mantraName1 = strUrl11.split("\\.");
            String mantra = mantraName1[0];
            copyToExternalSd(strUrl11);
            File ringtoneFile = new File(dir, strUrl11); // path is a file to /sdcard/media/ringtone
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DATA, ringtoneFile.getAbsolutePath());
            values.put(MediaStore.MediaColumns.TITLE, mantra+"");
            values.put(MediaStore.MediaColumns.SIZE, 215454);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
            values.put(MediaStore.Audio.Media.ARTIST, "Madonna");
            values.put(MediaStore.Audio.Media.DURATION, 230);
            values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
            values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
            values.put(MediaStore.Audio.Media.IS_ALARM, false);
            values.put(MediaStore.Audio.Media.IS_MUSIC, false);

            //Insert it into the database
            Uri uri = MediaStore.Audio.Media.getContentUriForPath(ringtoneFile.getAbsolutePath());
            getContentResolver().delete(
                    uri,
                    MediaStore.MediaColumns.DATA + "=\""
                            + ringtoneFile.getAbsolutePath() + "\"", null);
            Uri newUri = getContentResolver().insert(uri, values);

            try
            {
                RingtoneManager.setActualDefaultRingtoneUri(MantrasDetailsActivity.this, RingtoneManager.TYPE_RINGTONE, newUri);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }


    private void genrateKeyHash() {
        // Add code to print out the key hash
        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(
                    "com.dstechnologies.gurumantras",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void setWallpaper(final int setImageasWallpaper) {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                WallpaperManager wallpaperManager=WallpaperManager.getInstance(getApplicationContext());
                try {
                    Drawable drawable=getResources().getDrawable(setImageasWallpaper);
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    // wallpaperManager.
                    wallpaperManager.setBitmap(bitmap);
                    Message msgObj = mHandler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("message", "Wallpaper set.");
                    msgObj.setData(b);
                    mHandler.sendMessage(msgObj);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // This is where you do your work in the UI thread.
                // Your worker tells you in the message what to do.
                String text=message.getData().getString("message");
                Toast.makeText(MantrasDetailsActivity.this,""+text,Toast.LENGTH_LONG).show();
            }
        };
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void getDetailsfromlistData() {
        position=getIntent().getIntExtra("positionOfMantra",0);
        String title=getIntent().getStringExtra("name");
        byte []blurimageByte=getIntent().getByteArrayExtra("blurImage");
        Bitmap blurImageBitmap= BitmapFactory.decodeByteArray(blurimageByte, 0, blurimageByte.length);
        Drawable blurImageDrawable=new BitmapDrawable(blurImageBitmap);
        relativeLayout.setBackground(blurImageDrawable);

        for(int i=0;i<thumbImages.length;i++)
        {
            mainImageView.setImageResource(thumbImages[position]);
            mantraTitle.setText(title);
            nameOfMantra.setText(title);

        }


    }

    private void copyToExternalSd(String strUrl)
    {
        InputStream in = null;
        OutputStream out = null;
        GZIPInputStream gzis = null;
        try
        {


            File f = new File(Environment.getExternalStorageDirectory()+"/Android/data/com.example/"+strUrl+"s");
            in = new FileInputStream(f);
            gzis = new GZIPInputStream(in);

            out = new FileOutputStream(Environment.getExternalStorageDirectory()+"/ringtone/"+strUrl);

            byte[] buffer = new byte[(int) f.length()];
            int read;

            while ((read = gzis.read(buffer)) != -1)
            {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            out.flush();
            out.close();
            out = null;
        }

        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private int DownloadAsync(String stringUrl)
    {
        //final String url = "https://www.dropbox.com/s/mj0fwchgsjd02hd/Watersound.mp3?dl=0";
        try
        {
			/*final String strUrl = "https://dl.dropboxusercontent.com/s/xbiksg0q5ezv20h/02.%20Dil%20Laga%20Liya%20Maine.mp3?dl=0";*/
            final String strUrl = stringUrl;

//            progressDialog = ProgressDialog.show(this, "Getting Mantra", "Please wait....", true, false);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setCancelable(false);

            alertDialog=new SpotsDialog(this,R.style.Custom);
            alertDialog.setCancelable(false);
            alertDialog.show();


            final Handler handler = new Handler()
            {
                public void handleMessage(android.os.Message msg)
                {
                    //progressDialog.dismiss();
                    alertDialog.cancel();
                }
            };

            final String strDestFileName = Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/";
            final String url = strUrl.substring(strUrl.lastIndexOf("/"),strUrl.indexOf(".mp3")+4);

            if(isFileExit(strDestFileName+url+"s"))
            {
                //progressDialog.dismiss();
                alertDialog.cancel();
                return 1;
            }

            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    int iNoOfByteRead = 0;
                    int iOffset = 0;
                    int downloadedSize = 0;
                    InputStream localInputStream;

                    File imageFile1 = new File(strDestFileName+url+"temp");
                    if(isFileExit(strDestFileName+url+"temp"))
                    {
                        imageFile1.delete();
                    }
                    try
                    {
                        URL localUrl = new URL (strUrl);
                        HttpURLConnection localConn = (HttpURLConnection) localUrl.openConnection();
                        localConn = (HttpURLConnection) localUrl.openConnection();

                        localConn.setDoInput(true);
                        localConn.connect();
                        localConn.getResponseCode();
                        localInputStream = localConn.getInputStream();
                        long apkSize = 5000000;

                        BufferedInputStream bis = new BufferedInputStream(localInputStream);
                        byte[] buffer = new byte[50000];

                        File imageFile = new File(strDestFileName);

                        if (!imageFile.exists())
                            imageFile.mkdirs();

                        imageFile1 = new File(strDestFileName+url+"temp");

                        /*BufferedOutputStream bos = null;
                        bos = new BufferedOutputStream(new FileOutputStream(imageFile1));*/

						GZIPOutputStream bos = null;
						bos = new GZIPOutputStream(new FileOutputStream(imageFile1));

                        while ((iNoOfByteRead = bis.read(buffer, iOffset, buffer.length)) != -1)
                        {
                            downloadedSize += iNoOfByteRead;
                            bos.write(buffer, iOffset, iNoOfByteRead);
                            bos.flush();
                        }
                        bos.close();
                        bis.close();

                        renameFile(imageFile1, url);
                        //handler.sendEmptyMessage(0);
                       // progressDialog.dismiss();
                        alertDialog.cancel();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public void renameFile(File imageFile1,String url)
    {
        String strDestFileName = Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/";
        //imageFile1.renameTo(new File(strDestFileName+url+"s"));
        imageFile1.renameTo(new File(strDestFileName+url+"s"));
    }

    protected boolean isFileExit(String strURL)
    {
        File file = new File(strURL);
        if(file.exists())
            return true;
        else
            return false;
    }

}
