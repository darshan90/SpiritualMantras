package com.dstechnologies.gurumantras;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

public class DownloadMantrasService extends Service {
    String[] dropBoxUrl =  {"https://www.dropbox.com/s/3xfuup448u1a4gd/82134-balaji-mantra.mp3?dl=0","https://www.dropbox.com/s/mz793yu5trawr77/Buddha%2520Zee%2520Tv%2520Buddham%2520Sharanam%2520Gachchami%28ringtonemaza.in%29.mp3?dl=0","https://www.dropbox.com/s/3jp5k65j2pkpqe6/gayatri-mantra-5.mp3?dl=0","https://www.dropbox.com/s/b921paeopt6vqf0/Om%20Namo%20Narayanaya%20Mantra%20Chanting%20For%20World%20Peace1.mp3?dl=0","https://www.dropbox.com/s/or6dfp5c7xy1qjg/Sai%20Namo%20Namah.mp3?dl=0","https://www.dropbox.com/s/rur66cs0quzd86m/shani%2520mantra%28ringtonemaza.in%29.mp3?dl=0","https://www.dropbox.com/s/n47qt5xjfgxsmca/swamisamarth.mp3?dl=0"};

    public static boolean downloadMantrasFlag=false;

    public DownloadMantrasService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Service created", "Service created...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service started", "Service started");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        for(int i=0;i<dropBoxUrl.length;i++) {

                            String strDestFileName = Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/";
                            String url = dropBoxUrl[i].substring(dropBoxUrl[i].lastIndexOf("/"), dropBoxUrl[i].indexOf(".mp3") + 4);
                            //Check file exist or not if yes then stop service.
                            //int sizeOfList=totalMantrasOnStorage.size();
                            loadData(dropBoxUrl[i]);
                            Thread.sleep(500);
                        }
                        stopSelf();//stop service after executing.

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


        });
        t.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String convertStreamToString(InputStream is) {
        String line = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        try {
            if ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void loadData(String strUrl) {
        int iNoOfByteRead = 0;
        int iOffset = 0;
        int downloadedSize = 0;
        InputStream localInputStream;

        //Check file already exist or not.
        String strDestFileName = Environment.getExternalStorageDirectory() + "/Android/data/" + "com.example/";
        String url = strUrl.substring(strUrl.lastIndexOf("/"), strUrl.indexOf(".mp3") + 4);
        Log.d("url:", "file name in asynctask:" + url);

        try {
            URL localUrl = new URL(strUrl);
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

            File imageFile1 = new File(strDestFileName + url+"temp");

            GZIPOutputStream gzipOutputStream=null;
            gzipOutputStream=new GZIPOutputStream(new FileOutputStream(imageFile1));
            while ((iNoOfByteRead = bis.read(buffer, iOffset, buffer.length)) != -1)
            //while (	downloadedSize <= apkSize)
            {
                downloadedSize += iNoOfByteRead;

                gzipOutputStream.write(buffer, iOffset, iNoOfByteRead);
                gzipOutputStream.flush();
            }
            gzipOutputStream.close();
            bis.close();
            renameFile(imageFile1,url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isFileExist(String strURL)
    {
        File file = new File(strURL);
        if(file.exists())
            return true;
        else
            return false;
    }

    public void renameFile(File imageFile1,String url)
    {
        String strDestFileName = Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/";
        imageFile1.renameTo(new File(strDestFileName+url+"s"));
    }



}
