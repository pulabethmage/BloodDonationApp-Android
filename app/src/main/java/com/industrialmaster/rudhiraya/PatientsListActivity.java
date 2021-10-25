package com.industrialmaster.rudhiraya;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientsListActivity extends Activity implements Response.Listener<String>,Response.ErrorListener{

    ListView doneelistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);

        doneelistview = findViewById(R.id.listview_donees);


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDonees();


    }



    public void loadDonees() {


        RequestQueue queue = Volley.newRequestQueue(this);
       // String url ="http://rudhiraya.tk/register/donees_list.php";
        String url ="http://192.248.85.22/rudhiraya.tk/register/donees_list.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,

                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        setDonees(response);
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                    }
                });

        queue.add(request);


    }

    public void setDonees(JSONArray response) {


        // List<String> list = new ArrayList<String>();

        List<HashMap<String,String>> list = new ArrayList<>();


        for (int i = 0; i<response.length();i++)
        {
            try {
                JSONObject obj = response.getJSONObject(i);

                HashMap<String,String> map = new HashMap<>();
                map.put("pro_picture_url", obj.getString("pro_picture_url"));
                map.put("full_name",obj.getString("full_name"));
                map.put("gender",obj.getString("gender"));
                map.put("age",obj.getString("age"));
                map.put("blood_type",obj.getString("blood_type"));
                map.put("email",obj.getString("email"));
                map.put("phone",obj.getString("phone"));
                map.put("online_status",obj.getString("online_status"));

                list.add(map);

                // list.add(obj.getString("date")+" "+obj.getString("doctor_name"));


            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        // This is setting all date into android specified simple_list view
        //  int layout = android.R.layout.simple_list_item_1;
        //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(DoctorSessionActivity.this,layout, list);


        // Now we are creating custom layout (single_session.xml)
        //1. Custom layout
        int layout = R.layout.patients_layout_list_item;


        /*TextView online_tv = findViewById(R.id.tv_patient_onlinestat);
        online_tv.setBackgroundColor(Color.GREEN);*/

        //2. View array
        int[] views = {R.id.patient_profile_name,R.id.patient_gender,R.id.patient_age,R.id.patient_blood_type,R.id.patient_email,R.id.patient_phone};

        //3. Columns Strings
        String[] cols = {"full_name","gender","age","blood_type","email","phone"};

        ListAdapter adapter = new MyAdapter(this,list,layout,cols,views);

       // SimpleAdapter adapter = new SimpleAdapter(this,list,layout,cols,views);

        doneelistview.setAdapter(adapter);



    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {

    }

    public void callnow(View v)
    {
        LinearLayout parent = (LinearLayout)v.getParent();//In the same linear layout where the button placed
        LinearLayout parent2 = (LinearLayout) parent.getParent();//next linear layout


        TextView phonenumber = parent2.findViewById(R.id.patient_phone);
        String phone = phonenumber.getText().toString();

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phone));
        startActivity(callIntent);
    }
}
