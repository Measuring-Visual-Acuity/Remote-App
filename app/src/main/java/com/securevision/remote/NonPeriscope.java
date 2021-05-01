package com.securevision.remote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.securevision.remote.apiService.ApiService;
import com.securevision.remote.parsers.ImageDisplayParser;
import com.securevision.remote.parsers.SendDetail;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NonPeriscope extends AppCompatActivity {

    private int position = 0;
    private int check;
    private String leftEyeResult, rightEyeResult, result;
    private double sc;
    private int line = 1;
    private HashMap<Integer,Integer> scoreMap;
    private HashMap<Integer,Integer> mistake;
    private HashMap<Integer,Double> scoreMapLandoltC;


    private String score = null;
    String name, email, phone, age, literacy, gender;
    private int line1 = 0;
    private int line2 = 0;
    private int line3 = 0;
    private int line4 = 0;
    private int line5 = 0;
    private int line6 = 0;
    private int line7 = 0;
    private int line8 = 0;
    private int line9 = 0;
    private int line10 = 0;
    private int line11 = 0;
    private int line12 = 0;
    private  int correctLine;
    private int variable;
    int flag = 1;

    private SegregatedActivity segregatedActivity;

    TextInputEditText inputDistance;
    TextInputLayout textInputLayout;
    ImageView displayImage;
    RelativeLayout relativeLayout;
    Button submitDistance, english, hindi, octlets, yesButton, noButton;
    float distance = 0;
    String type = "";
    String ipaddress = "";
    TextView info, displayText, sample, posi, eyeDetails;
    ImageView shownImages;
    Retrofit retrofit;
    ApiService apiService;
    int positions = -1;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_periscope);

        //Accept distance and the perform calculations

        getIntentData();
        Toast.makeText(NonPeriscope.this, ipaddress, Toast.LENGTH_SHORT).show();
        retrofit = new Retrofit.Builder().baseUrl(ipaddress).
                addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        scoreMap = new HashMap<>();
        mistake = new HashMap<>();
        scoreMapLandoltC = new HashMap<>();
        scoreMap.put(1,200);
        scoreMap.put(2,100);
        scoreMap.put(3,70);
        scoreMap.put(4,50);
        scoreMap.put(5,40);
        scoreMap.put(6,30);
        scoreMap.put(7,25);
        scoreMap.put(8,20);

//      scoreMapLandoltC.put(0,0);
        scoreMapLandoltC.put(1,0.8);
        scoreMapLandoltC.put(2,0.7);
        scoreMapLandoltC.put(3,0.6);
        scoreMapLandoltC.put(4,0.5);
        scoreMapLandoltC.put(5,0.4);
        scoreMapLandoltC.put(6,0.3);
        scoreMapLandoltC.put(7,0.2);
        scoreMapLandoltC.put(8,0.1);
        scoreMapLandoltC.put(9,0.0);
//      scoreMapLandoltC.put(10,10);

        resetTest();

        setContentView(R.layout.activity_non_periscope);
        inputDistance = findViewById(R.id.inputDistance);
        submitDistance = findViewById(R.id.distanceButton);
        english = findViewById(R.id.buttonEnglish);
        octlets = findViewById(R.id.buttonOctlets);
        info = findViewById(R.id.sampleDisplayText);
        displayImage = findViewById(R.id.displayImage);
        shownImages = findViewById(R.id.shownImages);
        linearLayout = findViewById(R.id.linearLayout);
        displayText = findViewById(R.id.sampleDisplayImageShown);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        sample = findViewById(R.id.sample);
        posi = findViewById(R.id.posi);
        relativeLayout = findViewById(R.id.chartButtons);
        textInputLayout = findViewById(R.id.inputDistance1);
        eyeDetails = findViewById(R.id.sampleEyeText);
        eyeDetails.setText("Please close your Right eye.");
        displayImage.setClickable(true);
        inputDistance.addTextChangedListener(textWatcher);
        submitDistance.setClickable(false);
        Glide.with(NonPeriscope.this).load(R.drawable.eyegiffs).override(400,400).into(displayImage);

        submitDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(inputDistance.getText())) {
                    inputDistance.setError("Please enter distance.");
                }
                else  {
                    distance = Float.parseFloat(inputDistance.getText().toString().trim());
                    Toast.makeText(NonPeriscope.this,
                            String.valueOf(distance), Toast.LENGTH_SHORT).show();
                    submitDistance.setVisibility(View.INVISIBLE);
                    inputDistance.setVisibility(View.INVISIBLE);
                    english.setVisibility(View.VISIBLE);
                    octlets.setVisibility(View.VISIBLE);
                    textInputLayout.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                }

            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "english";
                english.setVisibility(View.INVISIBLE);
//                hindi.setVisibility(View.INVISIBLE);
                octlets.setVisibility(View.INVISIBLE);
                info.setText("Click on the button to display English Letters.");
                relativeLayout.setVisibility(View.INVISIBLE);
                manageVisibility();

            }
        });

        octlets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "landlot_c";
                english.setVisibility(View.INVISIBLE);
//                hindi.setVisibility(View.INVISIBLE);
                octlets.setVisibility(View.INVISIBLE);
                info.setText("Click on the button to display Aucklands.");
                relativeLayout.setVisibility(View.INVISIBLE);
                manageVisibility();
            }
        });

        displayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NonPeriscope.this, "button is clicked", Toast.LENGTH_SHORT).show();
//                callApiToRenderImage(type, position);
                if(type.equals("english") && position <= 35)
                {
                    callApiToRenderImage(type, position);
                }
                else if(type.equals("hindi") && position <= 29)
                {
                    callApiToRenderImage(type, position);
                }
                else if(type.equals("landlot_c") && position <= 42)
                {
                    Toast.makeText(NonPeriscope.this,"ckick hua", Toast.LENGTH_SHORT).show();
                    callApiToRenderImage(type, position);
                }

            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(NonPeriscope.this, "Yes is clicked.", Toast.LENGTH_SHORT).show();
                if(type.equals("landlot_c")) {
                    checkMistake();
                    position++;
//                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    if(position <=35) {
                        callApiToRenderImage(type,position);
                    }
                }
                else if(type.equals("english")){
                    checkMistake();
                    position++;
//                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    if(position <=35) {
                        callApiToRenderImage(type,position);
                    }
                }
                else if(type.equals("hindi")) {
                    checkMistake();
                    position++;
//                Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    if(position <=29) {
                        callApiToRenderImage(type,position);
                    }
                }


            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(NonPeriscope.this, "No is clicked.", Toast.LENGTH_SHORT).show();
//                if(type.equals("landlot_c")) {
//                    checkStatusEtdrs();
//                    position++;
//                    checkMistakeEtdrs();
//                }
//                else {
                checkStatus();
                position++;
                checkMistakeNo();
//                }



            }
        });

    }


    public TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String dis = inputDistance.getText().toString().trim();
            if(!dis.isEmpty())
            {
                distance = Float.parseFloat(dis);
                if(distance > 6)
                {
                    inputDistance.setText("");
                    Toast.makeText(NonPeriscope.this, "Please keep the distance between 1 to 6 " +
                                    "meters. Not more than that.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(distance > 0 && distance <=6)
                {
                    submitDistance.setClickable(true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void resetTest() {
        position = 0;
        line = 1;
        line1 = 0;
        line2 = 0;
        line3 = 0;
        line4 = 0;
        line5 = 0;
        line6 = 0;
        line7 = 0;
        line8 = 0;

        mistake.put(0,0);
        mistake.put(1,0);
        mistake.put(2,0);
        mistake.put(3,0);
        mistake.put(4,0);
        mistake.put(5,0);
        mistake.put(6,0);
        mistake.put(7,0);
        mistake.put(8,0);
    }

    public void displayAlert(String message)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(NonPeriscope.this);
        builder1.setTitle("Message");
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setNeutralButton(R.string.new_test,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(flag == 0) {
                            callApiToSendDetail();
                            Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                            startActivity(intent);
                        }
                        else {
                            eyeDetails.setText("Please close your Left Eye.");
                            flag = 0;
                            resetTest();
                            callApiToRenderImage(type, position);
                        }

                    }
                });
        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }

    public void callApiToRenderImage(String type, int pos)
    {
        Log.i("type", type);
        final ProgressDialog progressDialog = new ProgressDialog(NonPeriscope.this);
        progressDialog.setMessage("Displaying image. Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
//        pos = pos + 1;
        positions = pos;
        Log.i("pos", String.valueOf(positions));
        Call<ImageDisplayParser> call = apiService.displayImage(pos, 352,
                distance, type);
        call.enqueue(new Callback<ImageDisplayParser>() {
            @Override
            public void onResponse(Call<ImageDisplayParser> call, Response<ImageDisplayParser> response) {
                if(!response.body().getImgurl().isEmpty())
                {
                    Log.i("imageUrl", response.body().getImgurl().toString());
                    displayImage.setClickable(false);
                    yesButton.setClickable(true);
                    noButton.setClickable(true);
                    progressDialog.dismiss();
                    Glide.with(NonPeriscope.this).load(response.body().getImgurl().toString().trim()).override(200,200).into(shownImages);
                    posi.setText(String.valueOf(response.body().getAccuity()));
                    linearLayout.setVisibility(View.VISIBLE);
                    sample.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(NonPeriscope.this, "Something went wrong in url.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImageDisplayParser> call, Throwable t) {
                if(position == -1)
                {
                    positions = -1;
                }
                else
                {
                    position = position - 1;
                }
                progressDialog.dismiss();
                Toast.makeText(NonPeriscope.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                Log.i("displayImageException", t.toString());
                Toast.makeText(NonPeriscope.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callApiToSendDetail()
    {
        Call<SendDetail> call = apiService.sendDetail(name,
                email,age,phone,
                gender,type,leftEyeResult,rightEyeResult, distance);
        call.enqueue(new Callback<SendDetail>() {
            @Override
            public void onResponse(Call<SendDetail> call, Response<SendDetail> response) {
                if(response.body().getStatus().equals("success")) {
                    Toast.makeText(NonPeriscope.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendDetail> call, Throwable t) {
            }
        });
    }

    public void manageVisibility()
    {
        info.setVisibility(View.VISIBLE);
        displayImage.setVisibility(View.VISIBLE);
        shownImages.setVisibility(View.VISIBLE);
        displayText.setVisibility(View.VISIBLE);
        sample.setVisibility(View.INVISIBLE);
        eyeDetails.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        yesButton.setClickable(false);
        noButton.setClickable(false);
    }

    public void checkMistake() {

        if(type.equals("english") || type.equals("landlot_c")) {
            variable = 34;
        }
        else {
            variable = 28;
        }

        if(position <= variable) {
            if(line != 0) {
                check = position;
//                Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                if(line == 1 && mistake.get(line) == 1) {
                    result = "NA";
                    if(flag == 1)
                    {
                        leftEyeResult = result;
                        displayAlert("Left Eye Score: "+leftEyeResult);
                    }
                    else if(flag == 0)
                    {
                        rightEyeResult = result;
                        displayAlert("Right Eye Score: "+rightEyeResult);
                    }
                }
                else if(mistake.get(line) == 1 && (check == 2 || check == 5 || check == 9 || check == 14 || check == 20 || check == 27 || check == 35)) {
                    score = String.valueOf(scoreMap.get(line));
                    result = "20-" + score + "-" + "1";
                    if(flag == 1)
                    {
                        leftEyeResult = result;
                        result = "20/" + score + "-" + "1";

                        displayAlert("Left Eye Score: "+result);
                    }
                    else if(flag == 0)
                    {
                        rightEyeResult = result;
                        result = "20/" + score + "-" + "1";

                        displayAlert("Right Eye Score: "+result);
                    }
//                            Toast.makeText(NonPeriscope.this, result, Toast.LENGTH_SHORT).show();

                }
                else if(mistake.get(line) >= 2 && (check == 2 || check == 5 || check == 9 || check == 14 || check == 20 || check == 27 || check == 35)) {
                    if(mistake.get(line) == 2) {

                        score = String.valueOf(scoreMap.get(line));
                        result = "20-" + score + "-" + "2";
                        if(flag == 1)
                        {
                            leftEyeResult = result;
                            result = "20/" + score + "-" + "2";

                            displayAlert("Left Eye Score: "+result);
                        }
                        else if(flag == 0)
                        {
                            rightEyeResult = result;
                            result = "20/" + score + "-" + "2";

                            displayAlert("Right Eye Score: "+result);
                        }//                                Toast.makeText(NonPeriscope.this, "Score: 20/" + score + " - " + "2", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        score = String.valueOf(scoreMap.get((line-1)));
                        result = "20-" + score;
                        if(flag == 1)
                        {
                            leftEyeResult = result;
                            result = "20/" + score;

                            displayAlert("Left Eye Score: "+result);
                        }
                        else if(flag == 0)
                        {
                            rightEyeResult = result;
                            result = "20/" + score;

                            displayAlert("Right Eye Score: "+result);
                        }
//                                Toast.makeText(NonPeriscope.this, "Score: 20/" + score,
//                                        Toast.LENGTH_SHORT).show();

                    }

                }
                else {

//                    callApiToRenderImage(type,position);

                }
            }
            else {

//                callApiToRenderImage(type,position);

            }

        }
        else  {
            result = "20-20";
            if(flag == 1)
            {
                leftEyeResult = result;
                result = "20/20";
                displayAlert("Left Eye Score: "+result);
            }
            else if(flag == 0)
            {
                rightEyeResult = result;
                result = "20/20";
                displayAlert("Right Eye Score: "+result);
            }
        }
    }

    public void checkMistakeNo() {



        if(position <= 36) {
            if(line != 0) {
                check = position - 1;
//                Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                if(line == 1 && mistake.get(line) == 1) {
                    result = "NA";
                    if(flag == 1)
                    {
                        leftEyeResult = result;
                        displayAlert("Left Eye Score: "+leftEyeResult);
                    }
                    else if(flag == 0)
                    {
                        rightEyeResult = result;
                        displayAlert("Right Eye Score: "+rightEyeResult);
                    }
                }
                else if(mistake.get(line) == 1 && (check == 2 || check == 5 || check == 9 || check == 14 || check == 20 || check == 27 || check == 35)) {
                    score = String.valueOf(scoreMap.get(line));
                    result = "20-" + score + "-" + "1";
                    if(flag == 1)
                    {
                        leftEyeResult = result;
                        result = "20/" + score + "-" + "1";
                        displayAlert("Left Eye Score: "+result);
                    }
                    else if(flag == 0)
                    {
                        rightEyeResult = result;
                        result = "20/" + score + "-" + "1";
                        displayAlert("Right Eye Score: "+result);
                    }
//                            Toast.makeText(NonPeriscope.this, result, Toast.LENGTH_SHORT).show();

                }
                else if(mistake.get(line) >= 2 && (check == 2 || check == 5 || check == 9 || check == 14 || check == 20 || check == 27 || check == 35)) {
                    if(mistake.get(line) == 2) {

                        score = String.valueOf(scoreMap.get(line));
                        result = "20-" + score + "-" + "2";
                        if(flag == 1)
                        {
                            leftEyeResult = result;
                            result = "20/" + score + "-" + "2";
                            displayAlert("Left Eye Score: "+result);

                        }
                        else if(flag == 0)
                        {
                            rightEyeResult = result;
                            result = "20/" + score + "-" + "2";
                            displayAlert("Right Eye Score: "+result);
                        }
                        //displayAlert(result);
//                                Toast.makeText(NonPeriscope.this, "Score: 20/" + score + " - " + "2", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        score = String.valueOf(scoreMap.get((line-1)));
                        result = "20-" + score;
                        if(flag == 1)
                        {
                            leftEyeResult = result;
                            result = "20/" + score;
                            displayAlert("Left Eye Score: "+result);
                        }
                        else if(flag == 0)
                        {
                            rightEyeResult = result;
                            result = "20/" + score;
                            displayAlert("Right Eye Score: "+result);
                        }
                        //displayAlert(result);
//                                Toast.makeText(NonPeriscope.this, "Score: 20/" + score,
//                                        Toast.LENGTH_SHORT).show();
                    }

                }
                else {

                    callApiToRenderImage(type,position);

                }
            }
            else {

                callApiToRenderImage(type,position);

            }

        }
        else  {
            result = "20-20";
            if(flag == 1)
            {
                leftEyeResult = result;
                displayAlert("Left Eye Score: 20/20");
            }
            else if(flag == 0)
            {
                rightEyeResult = result;
                displayAlert("Right Eye Score: 20/20");
            }

        }
    }

    public void checkStatus() {
        if(position == 0) {
            line = 1;
            line1++;
            mistake.put(line,line1);
        }
        else if(position == 1 || position == 2) {
            line = 2;
            line2++;
            mistake.put(line,line2);

        }
        else if(position >= 3 && position <=5) {
            line = 3;
            line3++;
            mistake.put(line,line3);

        }
        else if(position >= 6 && position <= 9) {
            line = 4;
            line4++;
            mistake.put(line,line4);

        }
        else if(position >= 10 && position <= 14) {
            line = 5;
            line5++;
            mistake.put(line,line5);
        }
        else if(position >= 15 && position <= 20) {
            line = 6;
            line6++;
            mistake.put(line,line6);
        }
        else if(position >= 21 && position <= 27) {
            line = 7;
            line7++;
            mistake.put(line,line7);
        }
        else if(position >= 28 && position <= 35) {
            line = 8;
            line8++;
            mistake.put(line,line8);
        }
    }

    public void getIntentData() {
        segregatedActivity = new SegregatedActivity();

        ipaddress = "http://"+
                getIntent().getExtras().getString("INPUTIPADDRESS").trim()+ ":1234/";
        name = getIntent().getStringExtra("INPUTNAME");
        email = getIntent().getExtras().getString("INPUTEMAIL");
        phone = getIntent().getExtras().getString("INPUTMOBILE");
        age = getIntent().getExtras().getString("INPUTAGE");
        gender = getIntent().getExtras().getString("INPUTGENDER");
        Log.d("data",name);
        Log.i("ipaddress", ipaddress);
    }

    public void checkStatusEtdrs() {
        if(position >= 0 || position <= 4) {
            line = 1;
            line1++;
            mistake.put(line,line1);
        }
        else if(position >= 5 || position <= 9) {
            line = 2;
            line2++;
            mistake.put(line,line2);

        }
        else if(position >= 10 && position <=14) {
            line = 3;
            line3++;
            mistake.put(line,line3);

        }
        else if(position >= 15 && position <= 19) {
            line = 4;
            line4++;
            mistake.put(line,line4);

        }
        else if(position >= 20 && position <= 24) {
            line = 5;
            line5++;
            mistake.put(line,line5);
        }
        else if(position >= 25 && position <= 29) {
            line = 6;
            line6++;
            mistake.put(line,line6);
        }
        else if(position >= 30 && position <= 34) {
            line = 7;
            line7++;
            mistake.put(line,line7);
        }
        else if(position >= 35 && position <= 39) {
            line = 8;
            line8++;
            mistake.put(line,line8);
        }
//        else if(position >= 40 && position <= 44) {
//            line = 9;
//            line9++;
//            mistake.put(line,line9);
//        }
//        else if(position >= 45 && position <= 49) {
//            line = 10;
//            line10++;
//            mistake.put(line,line10);
//        }
//        else if(position >= 50 && position <= 54) {
//            line = 11;
//            line11++;
//            mistake.put(line,line11);
//        }
//        else if(position >= 55 && position <= 59) {
//            line = 12;
//            line12++;
//            mistake.put(line,line12);
//        }
    }

    public void checkMistakeEtdrs() {
        if(position <= 39) {
            if(line != 0) {
                check = position - 1;
                if(mistake.get(line) != 0 && (check == 4 || check == 9 || check == 14 || check == 19 || check == 24 || check == 29 || check == 34 || check == 39 || check == 44 || check == 49 || check == 54 || check == 59)) {
                    if(flag == 1) {
                        correctLine = line - 1;
                        result = String.valueOf(scoreMapLandoltC.get(correctLine) - ((5 - mistake.get(line)) * 0.02));
                        leftEyeResult = result;
                        displayAlert("Left Eye Score: " + leftEyeResult);
                    }
                    else if(flag == 0) {
                        correctLine = line - 1;
                        result = String.valueOf(scoreMapLandoltC.get(correctLine) - ((5 - mistake.get(line)) * 0.02));
                        rightEyeResult = result;
                        displayAlert("Left Eye Score: " + rightEyeResult);
                    }
                }
                else {
                    callApiToRenderImage(type,position);
                }
            }
        }
        else {
            if(flag == 1) {

                result = "test is finished";
                leftEyeResult = result;
                displayAlert(leftEyeResult);
            }
            else if(flag == 0) {

                result = "test is finished";
                rightEyeResult = result;
                displayAlert(rightEyeResult);
            }


        }
    }

}