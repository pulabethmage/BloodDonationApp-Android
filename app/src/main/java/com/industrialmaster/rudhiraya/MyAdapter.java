package com.industrialmaster.rudhiraya;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends SimpleAdapter {


    public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        // here you let SimpleAdapter built the view normally.
        View v = super.getView(position, convertView, parent);


        // Then we get reference for Picasso
        CircleImageView img = (CircleImageView) v.getTag();
        if(img == null){
            img = (CircleImageView) v.findViewById(R.id.patient_pro_pic);
            v.setTag(img); // <<< THIS LINE !!!!
        }
            // get the url from the data you passed to the `Map`
            String url = ((Map)getItem(position)).get("pro_picture_url").toString();
            // do Picasso
            Picasso.get().load(url).memoryPolicy(MemoryPolicy.NO_CACHE).into(img);


            /// MAKE ONLINE TEXTVIEW background GREEN
        ///get the online status

        String onlinestat = ((Map)getItem(position)).get("online_status").toString();

        if(onlinestat.equals("1"))
        {
            TextView tvon = v.findViewById(R.id.tv_patient_onlinestat);
            tvon.setText("Online");
            tvon.setBackgroundResource(R.drawable.onlineofflinebackground);

        }
        else {
            TextView tvon = v.findViewById(R.id.tv_patient_onlinestat);
            tvon.setText("Offline");
            tvon.setBackgroundResource(R.drawable.offlinebackground);
        }


        // return the view
        return v;
    }




}
