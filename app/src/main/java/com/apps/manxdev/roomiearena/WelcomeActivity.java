package com.apps.manxdev.roomiearena;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void CreateParty(View v){

        startActivity(new Intent(getApplicationContext(), CreatePartyActivity.class));

    }

    public void JoinParty(View v){

        //startActivity(new Intent(getApplicationContext(), JoinPartyActivity.class));

    }

    public void MyProfile(View v){

        //startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));

    }
}
