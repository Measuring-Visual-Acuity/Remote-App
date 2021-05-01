package com.securevision.remote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SegregatedActivity extends AppCompatActivity {

    String name, email, phone, age, literacy, gender, ipaddress;
    Button periscope, nonperiscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segregated);

        Intent i = getIntent();
        name = i.getStringExtra("INPUTNAME");
        email = i.getStringExtra("INPUTEMAIL");
        age = i.getStringExtra("INPUTAGE");
        phone = i.getStringExtra("INPUTMOBILE");
        gender = i.getStringExtra("INPUTGENDER");
        ipaddress = i.getStringExtra("INPUTIPADDRESS");
        periscope = findViewById(R.id.periscope);
        nonperiscope = findViewById(R.id.nonperiscope);
        Log.i("name", name);

        periscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SegregatedActivity.this, MainActivity.class);
                i.putExtra("INPUTNAME",name);
                i.putExtra("INPUTEMAIL",email);
                i.putExtra("INPUTAGE", age);
                i.putExtra("INPUTMOBILE",phone);
                i.putExtra("INPUTGENDER",gender);
                i.putExtra("INPUTIPADDRESS", ipaddress);
                startActivity(i);
            }
        });

        nonperiscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SegregatedActivity.this, NonPeriscope.class);
                i.putExtra("INPUTNAME",name);
                i.putExtra("INPUTEMAIL",email);
                i.putExtra("INPUTAGE", age);
                i.putExtra("INPUTMOBILE",phone);
                i.putExtra("INPUTGENDER",gender);
                i.putExtra("INPUTIPADDRESS", ipaddress);
                startActivity(i);
            }
        });

    }
}