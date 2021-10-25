package com.industrialmaster.rudhiraya;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PatientProfileActivity extends Activity implements Response.Listener<String>,Response.ErrorListener {

    ImageView imageProPic,editimageview,choosephoto;
    TextView profileName,typeId;
    EditText profileAddress,profileNic,profileMobile,profileBlood;
    Spinner SpActionType;
    Button edit;
    LinearLayout typelayout;

    private final int IMG_REQUEST = 1;
    private Bitmap bitMap,bm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        imageProPic = findViewById(R.id.imageViewPatintProPic);

        edit = findViewById(R.id.editbtn);
        choosephoto = findViewById(R.id.choosephoto);
        editimageview=findViewById(R.id.editimageview);

        profileName = findViewById(R.id.profile_name);
        typeId = findViewById(R.id.typeid);
        profileAddress = findViewById(R.id.profile_address);
        profileMobile = findViewById(R.id.profile_mobile);
        profileBlood = findViewById(R.id.profile_bloodtype);
        profileNic = findViewById(R.id.profile_nic);
        SpActionType = findViewById(R.id.actionTypeSpinner);

        typelayout = findViewById(R.id.typelayout);

       // Picasso.get().load("http://rudhiraya.tk/register/proimages/logo.jpg").into(imageProPic);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences("LOGINSTATUS", MODE_PRIVATE);
        final String lognic = prefs.getString("lognic", "0");

        loaduserdata(lognic);


    }


    public void onlineupdateinfo(String nic, String onstat)
    {


        final String Nic = nic;
        final String Onstat = onstat;

        RequestQueue queue = Volley.newRequestQueue(this);
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

    public void loaduserdata(String nic)
    {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.248.85.22/rudhiraya.tk/register/login.php?nic_number="+nic;

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

                        Toast.makeText(PatientProfileActivity.this,"No Data Found!",Toast.LENGTH_LONG).show();

                    }
                });

        queue.add(request);

    }

    public void setUserData(JSONArray response) {


        for (int i = 0; i<response.length();i++)
        {
            try {
                JSONObject obj = response.getJSONObject(i);

                profileName.setText(obj.getString("full_name"));
                profileAddress.setText(obj.getString("address"));
                profileMobile.setText(obj.getString("phone"));
                profileBlood.setText(obj.getString("blood_type"));
                profileNic.setText(obj.getString("nic_number"));
                typeId.setText(obj.getString("action_type"));
                Picasso.get().load(obj.getString("pro_picture_url")).memoryPolicy(MemoryPolicy.NO_CACHE).into(imageProPic);

            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }


    public void editbtn(View v)
    {

        String editstring = edit.getText().toString();

        if(editstring.equals("edit")) {
            choosephoto.setVisibility(View.VISIBLE);
            edit.setText("Update");

            profileAddress.setEnabled(true);
            profileMobile.setEnabled(true);
            profileBlood.setEnabled(true);
            typelayout.setVisibility(View.VISIBLE);



        }
        else if(editstring.equals("Update"))
        {
            ///save button action
            updateinfo();

            choosephoto.setVisibility(View.GONE);
            edit.setText("edit");

            profileAddress.setEnabled(false);
            profileMobile.setEnabled(false);
            profileBlood.setEnabled(false);
            typelayout.setVisibility(View.GONE);
        }


    }

    public void choosephoto(View v)
    {

        selectImage();

    }
    ////// SELECT IMAGE ////
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();
            try {
                bitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);


                bm = decodeBitmap(path,this);
                // String picturePath = getPath( this.getApplicationContext(), path );
                // bm = ShrinkBitmap(picturePath, 300, 300);

                imageProPic.setVisibility(View.GONE);
                editimageview.setVisibility(View.VISIBLE);
                editimageview.setImageBitmap(bm);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static Bitmap decodeBitmap(Uri selectedImage, Context context)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(selectedImage), null, o2);
    }


    ///// BIT MAP TO STRING CONVERTION

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String imageEncoded = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return imageEncoded;

    }


    public void updateinfo()
    {

        if (bitMap != null) {

            String address= profileAddress.getText().toString();
            String phone = profileMobile.getText().toString();
            String bloodtype = profileBlood.getText().toString();
            String nic = profileNic.getText().toString();
            String imageselec = imageToString(bm);
            String ActionType = SpActionType.getSelectedItem().toString();
            confirmUpdateWithImage(nic,address,phone,bloodtype,imageselec,ActionType);


        }
        else
        {

            String address= profileAddress.getText().toString();
            String phone = profileMobile.getText().toString();
            String bloodtype = profileBlood.getText().toString();
            String nic = profileNic.getText().toString();
            String ActionType = SpActionType.getSelectedItem().toString();
            String imagestring = "Empty";
            confirmUpdateWithOutImage(nic,address,phone,bloodtype,imagestring,ActionType);

        }

    }


    public  void confirmUpdateWithImage(String nic,String address,String phone,String bloodtype,String selectedimage,String ActionTypes )
    {
        final String Nic= nic;
        final String Address = address;
        final String Phone = phone;
        final String BloodType = bloodtype;
        final String SelectedImage = selectedimage;
        final  String ActionType = ActionTypes;

        RequestQueue queue = Volley.newRequestQueue(this);
        int type = Request.Method.POST;
        String url ="http://192.248.85.22/rudhiraya.tk/register/update.php";

        Toast.makeText(PatientProfileActivity.this,SelectedImage,Toast.LENGTH_LONG).toString();

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
                params.put("blood_type",BloodType);
                params.put("phone",Phone);
                params.put("address",Address);
                params.put("pro_picture_url",SelectedImage);
                params.put("action_type",ActionType);

                return params;
            }
        };

        queue.add(request);



    }


    public  void confirmUpdateWithOutImage(String nic,String address,String phone,String bloodtype,String emptyimage,String ActionTypes)
    {
        final String Nic= nic;
        final String Address = address;
        final String Phone = phone;
        final String BloodType = bloodtype;
        final String EmptyImage = emptyimage;
        final String ActionType = ActionTypes;

        RequestQueue queue = Volley.newRequestQueue(this);
        int type = Request.Method.POST;
        String url ="http://192.248.85.22/rudhiraya.tk/register/update.php";

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
                params.put("blood_type",BloodType);
                params.put("phone",Phone);
                params.put("address",Address);
                params.put("pro_picture_url",EmptyImage);
                params.put("action_type",ActionType);



                return params;
            }
        };

        queue.add(request);

    }



    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Error!"+error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(String response) {

        String Response = response.replace("\"", "");;

        if(Response.equals("Updated"))
        {
            Toast.makeText(this,"Updated!", Toast.LENGTH_LONG).show();
        }
        else if(Response.equals("Online"))
        {
            //Toast.makeText(this,"Online!", Toast.LENGTH_LONG).show();
        }
       // String res = response;
       //Toast.makeText(this,"Updated! "+Response, Toast.LENGTH_LONG).show();
    }
}
