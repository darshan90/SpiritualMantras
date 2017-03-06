package com.dstechnologies.gurumantras;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import com.dstechnologies.mediaplayer.SongPlayService;
import com.dstechnologies.mediaplayer.WhiteMediaPlayer;
import com.dstechnologies.utils.Utilities;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;



public class SpiritualMantrasUIMainActivity extends AppCompatActivity
{
    Context context;
    public static Context ccContext;
    Toolbar toolbar;
    View rootView;
    TextView appTitleView;
    ProgressDialog progressDialog;
    String applicationInfo;
    View view;
    AdView adView;

    String title[]; // Title of mantras array list.
    String []dropBoxUrl={"https://dl.dropboxusercontent.com/s/iynkr3b6mk5tirk/Mantra1_GayatrMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/e9nbt02j6tncyhj/Mantra2_SaiMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/jr54e0vmgax6tal/Mantra3_HareKrishnaHareRama.mp3?dl=0","https://dl.dropboxusercontent.com/s/w6536n0lankbe09/Mantra4_OmNamahaShivay.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/turn1r3rildzu1y/Mantra12_MahaMrutyuanjayMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/zpfx41nv1yxavlo/Mantra5_OmNamoBhagwateWasudeway.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/78w7vm1k43vnylu/Mantra6_GaneshMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/9sy56pgaoo5rt04/Mantra7_ShaniMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/ee0zyfxqxq85wi0/Mantra8_OmNamoNaraynay.mp3?dl=0","https://dl.dropboxusercontent.com/s/jihcjrg4873oh8d/Mantra9_BalajiMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/zs85xz42w2g0r9o/Mantra10_HanumanMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/hpfnrrmr4lxypvt/Mantra11_SwamiSamarthaMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/oga8vphc1uag6t0/Mantra13_BudhhaMantra.mp3?dl=0",
            "https://dl.dropboxusercontent.com/s/xzspjjgeulxvbo6/Mantra14_KaliMantra.mp3?dl=0","https://dl.dropboxusercontent.com/s/xcrlyvfpcx344ei/Mantra_HanumanChalisa.mp3?dl=0","https://dl.dropboxusercontent.com/s/e0qgnmi02dnkkco/Mantra15_GurudevDatta.mp3?dl=0"
    };

    Integer thumbImages[] = {R.drawable.devi, R.drawable.saibaba, R.drawable.krishna, R.drawable.shiva, R.drawable.mahamritunjay,R.drawable.narayana,R.drawable.ganesha,R.drawable.shani,R.drawable.omnamonarayana,
            R.drawable.balaji,R.drawable.hanuman,R.drawable.swamisamrath,R.drawable.buddha,R.drawable.kalimata,R.drawable.hanuman,R.drawable.gurudevdatta};


    Integer blurImages[]={R.drawable.gayatridevi_blur,R.drawable.saibaba_blur,R.drawable.krishna_blur,R.drawable.shiva_blur,R.drawable.mahamrutunjay,R.drawable.narayana_blur,R.drawable.ganesha_blur,R.drawable.shani_blur,R.drawable.narayana_blur,
            R.drawable.balaji_blur,R.drawable.hanuman_blur,R.drawable.swamisamrath_blur,R.drawable.gautambuddha2_blur,R.drawable.kalikamata_blur,R.drawable.hanuman_blur,R.drawable.dattatray_blur};

    List<HashMap<String, Object>> mantraDetailsList;
    HashMap<String, Object> hashMap;
    RecyclerView recyclerView;
    MantraListAdapter mantraListAdapter;

    WhiteMediaPlayer wmp;
    public static Typeface titleTypeface,appTitleTypeface;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Show dialog window on exception.
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(SpiritualMantrasUIMainActivity.this));
        setContentView(R.layout.activity_spiritual_mantras_uimain);
        ccContext = getBaseContext();
        title=getResources().getStringArray(R.array.titleOfMantras);
        applicationInfo=getResources().getString(R.string.aboutapp);
        titleTypeface= Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        appTitleTypeface=Typeface.createFromAsset(getAssets(), "fonts/Titania.ttf");
        Log.d("array", "Title array" + title.length);
        Log.d("size","ThumbImage:"+thumbImages.length);
        Log.d("size","BlurImages:"+blurImages.length);

        // adview load
        adView= (AdView) findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        session=new Session(this);
        showAppInfo();

        //Initialize toolbar as actionbar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.appicon);
        getSupportActionBar().setDisplayShowTitleEnabled(true); // Hide default app title.
        TextView titleOfApplication= (TextView) toolbar.findViewById(R.id.titleOfMantras);

        //RecyclerView as initialize.
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        try
        {
            addDatatoList();
        }
       catch (Exception e)
       {
           e.printStackTrace();
       }

        mantraListAdapter = new MantraListAdapter(mantraDetailsList);
        recyclerView.setAdapter(mantraListAdapter);

        // On item click of list 
        recyclerView.addOnItemTouchListener(new RecyclerViewOnTouch(context, recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {

                    //Check internet connection at first installation.
                if(!session.getCheckInternetFirsttime())
                {
                    if(!Utilities.isNetworkAvailable(SpiritualMantrasUIMainActivity.this))
                    {
                        showAlertMessage();
                    }
                    session.setCheckInternetFirsttime(true);
                }
                else
                    {
                    switch (position)
                    {
                        case 0:
                            Intent i=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i.putExtra("positionOfMantra",position);
                            i.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao=new ByteArrayOutputStream();
                            bitmapofBlur.compress(Bitmap.CompressFormat.PNG, 100, bao);
                            byte []byteOfblurImage=bao.toByteArray();
                            i.putExtra("blurImage",byteOfblurImage);
                            startActivity(i);
                            break;

                        case 1:
                            Intent i1=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i1.putExtra("positionOfMantra",position);
                            i1.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur1= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao1=new ByteArrayOutputStream();
                            bitmapofBlur1.compress(Bitmap.CompressFormat.PNG, 100, bao1);
                            byte []byteOfblurImage1=bao1.toByteArray();
                            i1.putExtra("blurImage",byteOfblurImage1);
                            startActivity(i1);
                            break;

                        case 2: Intent i2=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i2.putExtra("positionOfMantra",position);
                            i2.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur2= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao2=new ByteArrayOutputStream();
                            bitmapofBlur2.compress(Bitmap.CompressFormat.PNG, 100, bao2);
                            byte []byteOfblurImage2=bao2.toByteArray();
                            i2.putExtra("blurImage",byteOfblurImage2);
                            startActivity(i2);
                            break;

                        case 3:
                            Intent i3=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i3.putExtra("positionOfMantra",position);
                            i3.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur3= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao3=new ByteArrayOutputStream();
                            bitmapofBlur3.compress(Bitmap.CompressFormat.PNG, 100, bao3);
                            byte []byteOfblurImage3=bao3.toByteArray();
                            i3.putExtra("blurImage",byteOfblurImage3);
                            startActivity(i3);
                            break;

                        case 4:
                            Intent i4=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i4.putExtra("positionOfMantra",position);
                            i4.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur4= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao4=new ByteArrayOutputStream();
                            bitmapofBlur4.compress(Bitmap.CompressFormat.PNG, 100, bao4);
                            byte []byteOfblurImage4=bao4.toByteArray();
                            i4.putExtra("blurImage",byteOfblurImage4);
                            startActivity(i4);
                            break;

                        case 5:
                            Intent i5=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i5.putExtra("positionOfMantra",position);
                            i5.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur5= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao5=new ByteArrayOutputStream();
                            bitmapofBlur5.compress(Bitmap.CompressFormat.PNG, 100, bao5);
                            byte []byteOfblurImage5=bao5.toByteArray();
                            i5.putExtra("blurImage",byteOfblurImage5);
                            startActivity(i5);
                            break;

                        case 6:
                            Intent i6=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i6.putExtra("positionOfMantra",position);
                            i6.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur6= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao6=new ByteArrayOutputStream();
                            bitmapofBlur6.compress(Bitmap.CompressFormat.PNG, 100, bao6);
                            byte []byteOfblurImage6=bao6.toByteArray();
                            i6.putExtra("blurImage",byteOfblurImage6);
                            startActivity(i6);
                            break;

                        case 7:
                            Intent i7=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i7.putExtra("positionOfMantra",position);
                            i7.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur7= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao7=new ByteArrayOutputStream();
                            bitmapofBlur7.compress(Bitmap.CompressFormat.PNG, 100, bao7);
                            byte []byteOfblurImage7=bao7.toByteArray();
                            i7.putExtra("blurImage",byteOfblurImage7);
                            startActivity(i7);
                            break;

                        case 8:
                            Intent i8=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i8.putExtra("positionOfMantra",position);
                            i8.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur8= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao8=new ByteArrayOutputStream();
                            bitmapofBlur8.compress(Bitmap.CompressFormat.PNG, 100, bao8);
                            byte []byteOfblurImage8=bao8.toByteArray();
                            i8.putExtra("blurImage",byteOfblurImage8);
                            startActivity(i8);
                            break;

                        case 9:
                            Intent i9=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i9.putExtra("positionOfMantra",position);
                            i9.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur9= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao9=new ByteArrayOutputStream();
                            bitmapofBlur9.compress(Bitmap.CompressFormat.PNG, 100, bao9);
                            byte []byteOfblurImage9=bao9.toByteArray();
                            i9.putExtra("blurImage",byteOfblurImage9);
                            startActivity(i9);
                            break;

                        case 10:
                            Intent i10=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i10.putExtra("positionOfMantra",position);
                            i10.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur10= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao10=new ByteArrayOutputStream();
                            bitmapofBlur10.compress(Bitmap.CompressFormat.PNG, 100, bao10);
                            byte []byteOfblurImage10=bao10.toByteArray();
                            i10.putExtra("blurImage",byteOfblurImage10);
                            startActivity(i10);
                            break;

                        case 11:
                            Intent i11=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i11.putExtra("positionOfMantra",position);
                            i11.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur11= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao11=new ByteArrayOutputStream();
                            bitmapofBlur11.compress(Bitmap.CompressFormat.PNG, 100, bao11);
                            byte []byteOfblurImage11=bao11.toByteArray();
                            i11.putExtra("blurImage",byteOfblurImage11);
                            startActivity(i11);
                            break;

                        case 12:
                            Intent i12=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i12.putExtra("positionOfMantra",position);
                            i12.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur12= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao12=new ByteArrayOutputStream();
                            bitmapofBlur12.compress(Bitmap.CompressFormat.PNG, 100, bao12);
                            byte []byteOfblurImage12=bao12.toByteArray();
                            i12.putExtra("blurImage",byteOfblurImage12);
                            startActivity(i12);
                            break;

                        case 13:
                            Intent i13=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i13.putExtra("positionOfMantra",position);
                            i13.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur13= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao13=new ByteArrayOutputStream();
                            bitmapofBlur13.compress(Bitmap.CompressFormat.PNG, 100, bao13);
                            byte []byteOfblurImage13=bao13.toByteArray();
                            i13.putExtra("blurImage",byteOfblurImage13);
                            startActivity(i13);
                            break;

                        case 14:
                            Intent i14=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i14.putExtra("positionOfMantra",position);
                            i14.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur14= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao14=new ByteArrayOutputStream();
                            bitmapofBlur14.compress(Bitmap.CompressFormat.PNG, 100, bao14);
                            byte []byteOfblurImage14=bao14.toByteArray();
                            i14.putExtra("blurImage",byteOfblurImage14);
                            startActivity(i14);
                            break;
                        case 15:
                            Intent i15=new Intent(SpiritualMantrasUIMainActivity.this,MantrasDetailsActivity.class);
                            i15.putExtra("positionOfMantra",position);
                            i15.putExtra("name", title[position]); //Send title name.
                            //Send blur image as background to detailed screen.
                            Bitmap bitmapofBlur15= BitmapFactory.decodeResource(getResources(),blurImages[position]);
                            ByteArrayOutputStream bao15=new ByteArrayOutputStream();
                            bitmapofBlur15.compress(Bitmap.CompressFormat.PNG, 100, bao15);
                            byte []byteOfblurImage15=bao15.toByteArray();
                            i15.putExtra("blurImage",byteOfblurImage15);
                            startActivity(i15);
                            break;
                    }

                }
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));
    }

    private void showAlertMessage() {

                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
                        LayoutInflater inflater=getLayoutInflater();
                        View testview=inflater.inflate(R.layout.dialog_onebtn, null);
                        TextView title= (TextView) testview.findViewById(R.id.dialogTitle1);
                        TextView message= (TextView) testview.findViewById(R.id.dialog_message1);
                        Button okButton= (Button) testview.findViewById(R.id.positiveButton1);
                        alertDialog.setView(testview);
                        final AlertDialog dialog=alertDialog.create();
                        dialog.show();
                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.cancel();
                            }
                        });

    }

    private void showAppInfo() {

        Log.d("In showapp", "In showapp" + session.getFlagStatus());
        if(!session.getFlagStatus())
        {
            AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
            LayoutInflater inflater=getLayoutInflater();
            View dialog=inflater.inflate(R.layout.diloglayout, null);
            TextView aboutapp=(TextView) dialog.findViewById(R.id.appinfotextview);
            Button closebutton= (Button) dialog.findViewById(R.id.close);
            Button okbutton= (Button) dialog.findViewById(R.id.okbutton);
            aboutapp.setTypeface(titleTypeface);
            aboutapp.setText(applicationInfo);
            alertdialog.setView(dialog);
            final AlertDialog alert=alertdialog.create();
            alert.show();

            closebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.cancel();
                }
            });
            okbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.cancel();
                }
            });

            session.setFlagStatus(true);
        }
    }

    @Override
    public void onBackPressed()
    {

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View testview=inflater.inflate(R.layout.dialog_with_title_text_twobtns, null);
        TextView title= (TextView) testview.findViewById(R.id.dialogTitle);
        TextView message= (TextView) testview.findViewById(R.id.dialog_message);
        Button yesButton= (Button) testview.findViewById(R.id.positiveButton);
        Button noButton= (Button) testview.findViewById(R.id.negativeButton);
        alertDialog.setView(testview);
        final AlertDialog dialog=alertDialog.create();
        dialog.show();
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


    }

        private void addDatatoList() {
        mantraDetailsList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < title.length; i++) {
            hashMap = new HashMap<String, Object>();
            hashMap.put("Title", title[i]);
            hashMap.put("ThumbImages", thumbImages[i]);
            mantraDetailsList.add(hashMap);
        }

    }


    public interface ClickListener
    {
        void onClick(View v,int position);
        void onLongClick(View v,int position);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_launcher_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_settings:rateusByUser();
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void rateusByUser() {
        Uri uri=Uri.parse("market://details?id="+getPackageName());
        Intent i=new Intent(Intent.ACTION_VIEW,uri);
        i.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try
        {
            startActivity(i);
        }
        catch (Exception e)
        {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(getBaseContext(), SongPlayService.class);
        i.setAction(SongPlayService.STOPBUFFERINGDISPLAY);
        SpiritualMantrasUIMainActivity.ccContext.startService(i);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void copyToExternalSd(String strUrl)
    {
        InputStream in = null;
        OutputStream out = null;
        GZIPInputStream gzis = null;
        try
        {

            strUrl=strUrl.substring(strUrl.lastIndexOf("/")+1,strUrl.length());
            File f = new File(Environment.getExternalStorageDirectory()+"/Android/data/com.example/"+strUrl+"s");
            in = new FileInputStream(f);
            gzis = new GZIPInputStream(in);

            out = new FileOutputStream(Environment.getExternalStorageDirectory()+"/Android/data/com.example/ringtone/"+strUrl);

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
        try
        {

            final String strUrl = stringUrl;

            progressDialog = ProgressDialog.show(this, "Getting Mantra", "Please wait....", true, false);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);

            final Handler handler = new Handler()
            {
                public void handleMessage(Message msg)
                {
                    progressDialog.dismiss();
                }
            };

            final String strDestFileName = Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/";
            final String url = strUrl.substring(strUrl.lastIndexOf("/"),strUrl.indexOf(".mp3")+4);

            if(isFileExit(strDestFileName+url+"s"))
            {
                progressDialog.dismiss();
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

                        BufferedOutputStream bos = null;
                        bos = new BufferedOutputStream(new FileOutputStream(imageFile1));

                        while ((iNoOfByteRead = bis.read(buffer, iOffset, buffer.length)) != -1)
                        {
                            downloadedSize += iNoOfByteRead;
                            bos.write(buffer, iOffset, iNoOfByteRead);
                            bos.flush();
                        }
                        bos.close();
                        bis.close();
                        progressDialog.dismiss();
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
    protected boolean isFileExit(String strURL)
    {
        File file = new File(strURL);
        if(file.exists())
            return true;
        else
            return false;
    }
}
