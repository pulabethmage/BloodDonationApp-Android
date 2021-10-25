package com.industrialmaster.rudhiraya;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class OnlineStatus implements Response.Listener<String>,Response.ErrorListener {


   public String lognic,stat;


    public void onlineupdateinfo(Context context,String nic, String onstat)
    {


        final String Nic = nic;
        final String Onstat = onstat;

        RequestQueue queue = Volley.newRequestQueue(context);
        int type = Request.Method.POST;
        String url ="http://192.248.85.22/rudhiraya.tk/register/updateonlinestat.php";

        StringRequest request = new StringRequest(
                type,
                url,
                this,
                this
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> params = new HashMap<>();
                params.put("nic_number",Nic);
                params.put("online_stat",Onstat);


                return params;
            }
        };

        queue.add(request);

    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }
}
