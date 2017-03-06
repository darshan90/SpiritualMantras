package com.dstechnologies.mediaplayer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPOutputStream;

/**
 * Created by DARSHAN on 6/21/2016.
 */
public class DownloadingTheSongs {

    Context context;
    public String stringUrl = "";
    public DownloadingTheSongs(Context baseContext, String string)
    {
        context = baseContext;
        stringUrl = string;
        DownloadAsync(stringUrl);
    }
    private void DownloadAsync(final String strUrl)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                int iNoOfByteRead = 0;
                int iOffset = 0;
                int downloadedSize = 0;
                InputStream localInputStream;
                String strDestFileName = Environment.getExternalStorageDirectory()+"/Android/data/"+"com.example/";
                try
                {
                    String url = strUrl.substring(strUrl.lastIndexOf("/"),strUrl.indexOf(".mp3")+4);

                    if(isFileExit(strDestFileName+url+"s"))
                        return;

                    URL localUrl = new URL (strUrl);
                    Log.e("kulkarni" + strUrl, "kulkarni" + strUrl);
                    HttpURLConnection localConn = (HttpURLConnection) localUrl.openConnection();
                    localConn = (HttpURLConnection) localUrl.openConnection();

                    localConn.setDoInput(true);
                    localConn.connect();
                    localConn.getResponseCode();
                    localInputStream = localConn.getInputStream();

                    int totalSize = localConn.getContentLength();

                    BufferedInputStream bis = new BufferedInputStream(localInputStream);
                    byte[] buffer = new byte[50000];

                    File imageFile = new File(strDestFileName);

                    if (!imageFile.exists())
                        imageFile.mkdirs();

                    File imageFile1 = new File(strDestFileName+url+"temp");

                    //BufferedOutputStream bos = null;
                    //bos = new BufferedOutputStream(new FileOutputStream(imageFile1));
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
                    renameFile(imageFile1,url);
                }
                catch(Exception e)
                {
                    Log.e("shreyaskk"+e.toString(),"shreyaskk"+e.toString());
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    protected boolean isFileExit(String strURL)
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
