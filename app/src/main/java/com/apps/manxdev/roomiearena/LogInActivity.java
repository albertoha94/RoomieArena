package com.apps.manxdev.roomiearena;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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

public class LogInActivity extends AppCompatActivity {

    EditText et_email, et_pass;
    RequestQueue requestQueue;
    String selectURL = "http://192.168.1.11/RoomieArena/loginCheck.php";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        et_email = (EditText)findViewById(R.id.li_et_email);
        et_pass = (EditText)findViewById(R.id.li_et_pass);

    }

    public void LogIn(View v){

        final String email = et_email.getText().toString();
        final String pass = et_pass.getText().toString();


        if ( email.length() == 0 || pass.length() == 0)
            Toast.makeText(LogInActivity.this, "Campos Incompletos", Toast.LENGTH_SHORT).show();
        else {
            // Hacer Busqueda
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            // Request
            // new StringRequest(Metodo, URL, ListenerRespuesta, ListenerError){ Parametros };
            StringRequest request = new StringRequest(Request.Method.POST, selectURL,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   // Toast.makeText(LogInActivity.this, response, Toast.LENGTH_SHORT).show();

                    if ( response.contains("id")){
                        // Abrir Welcom Activity

                        sp  = PreferenceManager
                                .getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("id_roomie", response.substring(21, response.length() - 4));
                        editor.commit();
                        Toast.makeText(LogInActivity.this, sp.getString("id_roomie", "-1"), Toast.LENGTH_SHORT).show();

                        Welcome();

                    }
                    else {
                        Toast.makeText(LogInActivity.this, "Incorrect Data", Toast.LENGTH_SHORT).show();
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
                    Map <String, String> parameters = new HashMap<>();
                    parameters.put("email",email);
                    parameters.put("pass", pass);
                    return parameters;
                }
            };

            requestQueue.add(request);

        }

    }

    public void NewAccount(View v){
        startActivity(new Intent(getApplicationContext(), NewAccountActivity.class));
    }

    public void Welcome(){
        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
    }


}
