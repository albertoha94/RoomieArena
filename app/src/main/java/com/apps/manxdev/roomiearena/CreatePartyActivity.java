package com.apps.manxdev.roomiearena;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

public class CreatePartyActivity extends AppCompatActivity {

    EditText et_partyname, et_partymoto;

    RequestQueue requestQueue;
    String selectURL = "http://192.168.1.11/RoomieArena/partyCreate.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        Toolbar toolBar = (Toolbar) findViewById(R.id.pc_to);
        setSupportActionBar(toolBar);
        setTitle("Party Maker");

        et_partyname=(EditText)findViewById(R.id.pc_et_partyname);
        et_partymoto=(EditText)findViewById(R.id.pc_et_partymoto);




    }

    public void MakeParty(View v){

        final String partyname = et_partyname.getText().toString();
        final String partymoto = et_partymoto.getText().toString();

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        final String id_roomie = sp.getString("id_roomie", "-1");

        Toast.makeText(CreatePartyActivity.this, id_roomie, Toast.LENGTH_SHORT).show();


        if ( partymoto.length() == 0 || partyname.length() == 0)
            Toast.makeText(getApplicationContext(), "Campos Incompletos", Toast.LENGTH_SHORT).show();
        else {
            // Hacer Busqueda
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            // Request
            // new StringRequest(Metodo, URL, ListenerRespuesta, ListenerError){ Parametros };
            StringRequest request = new StringRequest(Request.Method.POST, selectURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Party Has been Created", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String, String> parameters = new HashMap<>();
                    parameters.put("partyname",partyname);
                    parameters.put("partymoto", partymoto);
                    parameters.put("idhost", id_roomie);

                    return parameters;
                }
            };

            requestQueue.add(request);

        }

    }
}
