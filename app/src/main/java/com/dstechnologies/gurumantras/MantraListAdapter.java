package com.dstechnologies.gurumantras;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by DARSHAN on 6/9/2016.
 */
public class MantraListAdapter extends RecyclerView.Adapter<MantraListAdapter.MyViewHolder> {


    List<HashMap<String,Object>> mantraList;

    MantraListAdapter(List<HashMap<String,Object>> mantraList)
    {
        this.mantraList=mantraList;
        Log.d("List in","Constructor:"+mantraList.size());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ViewGroup viewGroup;
        List<HashMap<String,Object>> mantraList;
        public ImageView thumbImage;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            thumbImage= (ImageView) itemView.findViewById(R.id.thumbImage);
            title= (TextView) itemView.findViewById(R.id.mantraTitle);
            title.setTypeface(SpiritualMantrasUIMainActivity.titleTypeface,Typeface.NORMAL);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView= LayoutInflater.from(parent.getContext()).inflate(R.layout.mantra_card_layout,parent,false);
        return new MyViewHolder(rootView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //holder.setIsRecyclable(false);
        try{
            holder.thumbImage.setImageResource((Integer) mantraList.get(position).get("ThumbImages"));
            //holder.title.setTypeface(titleTypeface,Typeface.BOLD);
            holder.title.setText((String) mantraList.get(position).get("Title"));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return mantraList.size();
    }


}
