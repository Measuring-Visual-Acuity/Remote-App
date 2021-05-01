package com.securevision.remote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegistrationActivity extends AppCompatActivity {

    EditText inputName, inputEmail, inputAge, inputMobile, inputIpaddress;
    String name, email, mobile, age, gender, literacy, ipaddress;
    RadioGroup genderRadioGroup;
    RadioButton genderRadioButton;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputAge = findViewById(R.id.inputAge);
        inputMobile = findViewById(R.id.inputMobile);
        button = findViewById(R.id.submitDetails);
        genderRadioGroup = findViewById(R.id.gender);
        inputIpaddress = findViewById(R.id.inputIpaddress);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = inputName.getText().toString().trim();
                email = inputEmail.getText().toString().trim();
                age = inputAge.getText().toString().trim();
                mobile = inputMobile.getText().toString().trim();
                int genderId = genderRadioGroup.getCheckedRadioButtonId();
                genderRadioButton = findViewById(genderId);
                gender = genderRadioButton.getText().toString().trim();
                ipaddress = inputIpaddress.getText().toString().trim();
                Log.i("inputIpAddress", ipaddress);

                Intent i = new Intent(RegistrationActivity.this, SegregatedActivity.class);
                i.putExtra("INPUTNAME",name);
                i.putExtra("INPUTEMAIL",email);
                i.putExtra("INPUTAGE", age);
                i.putExtra("INPUTMOBILE",mobile);
                i.putExtra("INPUTGENDER",gender);
                i.putExtra("INPUTIPADDRESS", ipaddress);
                startActivity(i);
            }
        });
    }
}