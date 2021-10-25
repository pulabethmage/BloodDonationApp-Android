package com.industrialmaster.rudhiraya;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends Activity implements Response.Listener<String>,Response.ErrorListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("LOGINSTATUS", MODE_PRIVATE);
        final String lognic = prefs.getString("lognic", "0");

       // onlineupdateinfo(lognic,"1");

        OnlineStatus onstat = new OnlineStatus();
        onstat.onlineupdateinfo(this,lognic,"1");

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("LOGINSTATUS", MODE_PRIVATE);
        final String lognic = prefs.getString("lognic", "0");

        OnlineStatus onstat = new OnlineStatus();
        onstat.onlineupdateinfo(this,lognic,"0");


    }


    public void logoutbtn(View v)
    {

        SharedPreferences.Editor editor = getSharedPreferences("LOGINSTATUS", MODE_PRIVATE).edit();
        editor.putString("logstatus", "0");
        editor.putString("loguserpropicurl", "0");
        editor.apply();

        Toast.makeText(this,"Thank you for using Rudhiraya!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(HomeActivity.this,DonateBloodActivity.class);
        startActivity(intent);
        this.finish();

    }

    public void donateblood(View v)
    {

        SharedPreferences prefs = getSharedPreferences("LOGINSTATUS", MODE_PRIVATE);
        final String lognic = prefs.getString("lognic", "0");

        // onlineupdateinfo(lognic,"1");

        OnlineStatus onstat = new OnlineStatus();
        onstat.onlineupdateinfo(this,lognic,"1");


        Intent intent = new Intent(HomeActivity.this,PatientsListActivity.class);
        startActivity(intent);




    }

    public void finddonor(View v)
    {
        SharedPreferences prefs = getSharedPreferences("LOGINSTATUS", MODE_PRIVATE);
        final String lognic = prefs.getString("lognic", "0");

        // onlineupdateinfo(lognic,"1");

        OnlineStatus onstat = new OnlineStatus();
        onstat.onlineupdateinfo(this,lognic,"1");


        Intent intent = new Intent(HomeActivity.this,DonorListActivity.class);
        startActivity(intent);

        //   Toast.makeText(this,"Find a Donor...",Toast.LENGTH_LONG).show();

    }

    public void profilecard(View v)
    {

        SharedPreferences prefs = getSharedPreferences("LOGINSTATUS", MODE_PRIVATE);
        final String lognic = prefs.getString("lognic", "0");

        // onlineupdateinfo(lognic,"1");

        OnlineStatus onstat = new OnlineStatus();
        onstat.onlineupdateinfo(this,lognic,"1");

        Intent intent = new Intent(HomeActivity.this,PatientProfileActivity.class);
        startActivity(intent);
        //Toast.makeText(this,"Blood Safety...",Toast.LENGTH_LONG).show();

    }

    public void bloodmatch(View v)
    {
        SharedPreferences prefs = getSharedPreferences("LOGINSTATUS", MODE_PRIVATE);
        final String lognic = prefs.getString("lognic", "0");

        // onlineupdateinfo(lognic,"1");
        Toast.makeText(this,lognic,Toast.LENGTH_LONG).show();
        OnlineStatus onstat = new OnlineStatus();
        onstat.onlineupdateinfo(this,lognic,"1");

        Intent intent = new Intent(HomeActivity.this,MatchingBloodTypeActivity.class);
        startActivity(intent);
       // Toast.makeText(this,"Blood Match...",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {
       // Toast.makeText(this,"You are Online!",Toast.LENGTH_LONG).show();
    }
}
