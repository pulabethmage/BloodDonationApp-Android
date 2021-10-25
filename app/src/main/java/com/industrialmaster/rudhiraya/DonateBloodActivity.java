package com.industrialmaster.rudhiraya;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


public class DonateBloodActivity extends Activity{

    EditText Etusername,Etpassword;


    String Susername,Spassword;

    String Dbfullname, Dbgender, Dbage, Dbemail, Dbbloodtype, Dbmobile, Dbaddress, Dbpassword, Dbonlinestatus, Dbpropicurl,
     Dbactiontype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);

        Etusername = findViewById(R.id.log_username);
        Etpassword = findViewById(R.id.log_password);

    }

    public void loginbtn(View v)
    {
        loaduserdata();
    }


    public void loaduserdata()
    {
        Susername = Etusername.getText().toString();
        Spassword = Etpassword.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.248.85.22/rudhiraya.tk/register/login.php?nic_number="+Susername;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        setUserData(response);
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(DonateBloodActivity.this,"Please Check your Username or Password!",Toast.LENGTH_LONG).show();

                    }
                });

        queue.add(request);

    }

    public void setUserData(JSONArray response) {

        DataStore dst = new DataStore();

        for (int i = 0; i<response.length();i++)
        {
            try {
                JSONObject obj = response.getJSONObject(i);


                dst.Danic=obj.getString("nic_number");
                dst.Dabfullname= obj.getString("full_name");
                dst.Dabgender = obj.getString("gender");
                dst.Dabactiontype = obj.getString("action_type");
                dst.Dabage = obj.getString("age");
                dst.Dabaddress = obj.getString("address");
                dst.Dabbloodtype = obj.getString("blood_type");
                dst.Dabemail = obj.getString("email");
                dst.Dabmobile = obj.getString("phone");
                dst.Dabpropicurl = obj.getString("pro_picture_url");
                dst.Dabpassword = obj.getString("password");


            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(Spassword.equals(dst.Dabpassword))
        {
            SharedPreferences.Editor editor = getSharedPreferences("LOGINSTATUS", MODE_PRIVATE).edit();
            editor.putString("logstatus", "1");
            editor.putString("lognic", dst.Danic);

            editor.apply();

            Toast.makeText(DonateBloodActivity.this,"Welcome "+dst.Dabfullname,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,HomeActivity.class);
            intent.putExtra("usernic",dst.Danic);
            startActivity(intent);

            this.finish();
        }
        else {
            Toast.makeText(DonateBloodActivity.this,"Please Check your Username or Password!!",Toast.LENGTH_LONG).show();

        }

    }

    public void forgotpassw(View v)
    {
        Toast.makeText(this,"Forgot Password...",Toast.LENGTH_LONG).show();
    }



    public void notamember(View v)
    {

        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }




}
