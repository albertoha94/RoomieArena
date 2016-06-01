package com.apps.manxdev.roomiearena;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewAccountActivity extends AppCompatActivity {

    EditText et_fname,et_lname, et_email, et_cemail, et_pass, et_cpass ;
    Spinner sp_month, sp_day;
    ArrayList<String> days;
    RequestQueue requestQueue;
    String selectURL = "http://192.168.1.11/RoomieArena/roomieCreate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        et_fname = (EditText) findViewById(R.id.na_et_name);
        et_lname = (EditText) findViewById(R.id.na_et_lastname);
        et_email = (EditText) findViewById(R.id.na_et_email);
        et_cemail = (EditText) findViewById(R.id.na_et_email2);
        et_pass=(EditText)findViewById(R.id.na_et_pass);
        et_cpass=(EditText)findViewById(R.id.na_et_pass2);
        sp_month = (Spinner) findViewById(R.id.na_sp_month);
        sp_day = (Spinner) findViewById(R.id.na_sp_day);


        // Fill Spinner Months
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this
                , R.array.monthsArray, R.layout.spinner_element);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_month.setAdapter(adapter);

        // Fill Spinner Days
        days = new ArrayList<>();
        for ( int i = 0; i < 31; i++ )
            days.add(  String.valueOf(i+1) );
        final ArrayAdapter<String> adapterDays = new ArrayAdapter<String>(this,
                R.layout.spinner_element, days);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_day.setAdapter(adapterDays);

        // Listener Months
        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        days.clear();
                        for ( int i = 0; i < 29; i++ )
                            days.add(  String.valueOf(i+1) );
                        sp_day.setAdapter(adapterDays);
                        break;
                    case 0:
                    case 2:
                    case 4:
                    case 6:
                    case 8:
                    case 10:
                        days.clear();
                        for ( int i = 0; i < 31; i++ )
                            days.add(  String.valueOf(i+1) );
                        sp_day.setAdapter(adapterDays);
                        break;
                    case 3:
                    case 5:
                    case 7:
                    case 9:
                    case 11:
                        days.clear();
                        for ( int i = 0; i < 30; i++ )
                            days.add(  String.valueOf(i+1) );
                        sp_day.setAdapter(adapterDays);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void SignUp(View v){

         final String fname = et_fname.getText().toString();
         final String lname = et_lname.getText().toString();
         final String bday = sp_month.getSelectedItem().toString() + "_" + sp_day.getSelectedItem().toString();
         final String email = et_email.getText().toString();
         final String cemail = et_cemail.getText().toString();
         final String pass = et_pass.getText().toString();
         final String cpass = et_cpass.getText().toString();



        if (fname.length()==0||lname.length()==0||email.length()==0||cemail.length()==0||pass.length()==0||cpass.length()==0)
            Toast.makeText(NewAccountActivity.this, "Incomplete Data", Toast.LENGTH_SHORT).show();
        else if( !cemail.equals(email) )
            Toast.makeText(NewAccountActivity.this, "E-Mail fields don't match", Toast.LENGTH_SHORT).show();

        else if( !cpass.equals(pass) )
            Toast.makeText(NewAccountActivity.this, "Password fields don't match", Toast.LENGTH_SHORT).show();

        else{

            // Hacer Busqueda
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            // Request
            // new StringRequest(Metodo, URL, ListenerRespuesta, ListenerError){ Parametros };
            StringRequest request = new StringRequest(Request.Method.POST, selectURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                            if ( response.contains("Duplicate")){
                                // Abrir Welcom Activity
                                Toast.makeText(NewAccountActivity.this, "An account with the given e-mail already exists", Toast.LENGTH_SHORT).show();

                            }
                            else if ( response.contains("Insert")) {

                                Toast.makeText(getApplicationContext(), "A mail has been sent to the given email", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String, String> parameters = new HashMap<String, String>();
                    parameters.put("email",email);
                    parameters.put("pass", pass);
                    parameters.put("fname", fname);
                    parameters.put("lname", lname);
                    parameters.put("photo", "nill");
                    parameters.put("bday", bday);
                    parameters.put("color", "nill");
                    parameters.put("rate","00000_00" );
                    parameters.put("status", "nill");
                    return parameters;
                }
            };

            requestQueue.add(request);
        }







    }




}
