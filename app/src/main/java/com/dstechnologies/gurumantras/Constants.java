package com.dstechnologies.gurumantras;

/**
 * Created by DARSHAN on 7/16/2016.
 */
public class Constants
{

    public static boolean isMantraPlaying=false;

    public static boolean getIsMantraPlaying()
    {
        return isMantraPlaying;
    }
    public static void setIsMantraPlaying(boolean flag)
    {
        Constants.isMantraPlaying=flag;
    }

    public static int position;

    public static int getPosition() {
        return position;
    }

    public static void setPosition(int position) {
        Constants.position = position;
    }
}
