package com.industrialmaster.rudhiraya;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class RegisterActivity extends Activity implements Response.Listener<String>,Response.ErrorListener {


    EditText EtFullname,EtNic,EtAge,EtEmail,EtPassword,EtConfPassword,EtMobile,EtAddress;
    Spinner SpActionType,SpBloodType;
    RadioButton RdGenderMale,RdGenderFemal,SelectedBtn;
    RadioGroup RgGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EtFullname = findViewById(R.id.regFullname);
        EtNic = findViewById(R.id.regNic);
        EtAge = findViewById(R.id.regAge);
        EtEmail = findViewById(R.id.regEmail);
        EtPassword = findViewById(R.id.regPassword);
        EtConfPassword = findViewById(R.id.regConfPassword);
        EtMobile = findViewById(R.id.regPhoneNumber);
        EtAddress = findViewById(R.id.regAddress);


        SpActionType = findViewById(R.id.actionTypeSpinner);
        SpBloodType = findViewById(R.id.bloodSpinner);


        RgGender =  findViewById(R.id.regRadioGroup);
        RdGenderMale = findViewById(R.id.regGenMale);
        RdGenderFemal = findViewById(R.id.regGenFemale);

        //Radio Button click https://www.youtube.com/watch?v=HMsNpVOM804


    }

    public  void btnregister(View v)
    {

        String Fullname = EtFullname.getText().toString();
        String Nic = EtNic.getText().toString();
        String Age = EtAge.getText().toString();
        String Email = EtEmail.getText().toString();
        String Password = EtPassword.getText().toString();
        String ConfPassword = EtConfPassword.getText().toString();
        String Mobile = EtMobile.getText().toString();
        String Address = EtAddress.getText().toString();

        String ActionType = SpActionType.getSelectedItem().toString();
        String BloodType = SpBloodType.getSelectedItem().toString();

        String Pro_Pic_Url = "http://192.248.85.22/rudhiraya.tk/register/proimages/logo.jpg";

        String Gender="Donor";
        int selectedRadioId = RgGender.getCheckedRadioButtonId();
        if(selectedRadioId == -1)
        {
            Toast.makeText(RegisterActivity.this,"Please Select Gender.",Toast.LENGTH_LONG).show();
        }
        else {
            SelectedBtn = findViewById(selectedRadioId);
            Gender = SelectedBtn.getText().toString();
        }

        ///////////////////////////////

        //Toast.makeText(RegisterActivity.this,ActionType,Toast.LENGTH_LONG).show();

        confirmAdd(Fullname,Nic,Gender,Age,Email,BloodType,Mobile,Address,Password,"0",Pro_Pic_Url,ActionType);



    }


    public void confirmAdd(String fullname,String nic,String gender,String age,String email,String bloodtype,String mobile,String address, String password,String onlinestatus,String propicurl,String actiontype)
    {
        final String Efullname=fullname;
        final String Enic=nic;
        final String Egender=gender;
        final String Eage=age;
        final String Eemail=email;
        final String Ebloodtype=bloodtype;
        final String Emobile=mobile;
        final String Eaddress=address;
        final String Epassword=password;
        final String Eonlinestatus=onlinestatus;
        final String Epropicurl=propicurl;
        final String Eactiontype=actiontype;


        RequestQueue queue = Volley.newRequestQueue(this);
        int type = Request.Method.POST;
        String url ="http://192.248.85.22/rudhiraya.tk/register/add.php";

        StringRequest request = new StringRequest(
                type,
                url,
                this,
                this
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String,String> params = new HashMap<>();
                params.put("full_name",Efullname);
                params.put("nic_number",Enic);
                params.put("gender",Egender);
                params.put("age",Eage);
                params.put("email",Eemail);
                params.put("blood_type",Ebloodtype);
                params.put("phone",Emobile);
                params.put("address",Eaddress);
                params.put("password",Epassword);
                params.put("online_status",Eonlinestatus);
                params.put("pro_picture_url",Epropicurl);
                params.put("action_type",Eactiontype);
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

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Welcome to Rudhiraya Mobile App");
        builder.setMessage("You are successfully added to the group please Login now.");
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fini();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    public void fini()
    {
       // Toast.makeText(this,"You have added to the!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,DonateBloodActivity.class);
        startActivity(intent);
        this.finish();

    }

}
