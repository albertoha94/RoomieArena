package com.apps.manxdev.roomiearena;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class CreatePartyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        Toolbar toolBar = (Toolbar) findViewById(R.id.pc_to);
        setSupportActionBar(toolBar);
        setTitle("Party Maker");


    }
}
