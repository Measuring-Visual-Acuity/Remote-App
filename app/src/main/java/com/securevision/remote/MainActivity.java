package com.securevision.remote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.securevision.remote.apiService.ApiService;
import com.securevision.remote.parsers.ImageDisplayParser;
import com.securevision.remote.parsers.SendDetail;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private int position = 0;
    private int check;
    private String leftEyeResult, rightEyeResult, result;
    private double sc;
    private int line = 1;
    private int variable;
    private HashMap<Integer,Integer> scoreMap;
    private HashMap<Integer,Integer> mistake;
    private HashMap<Integer,Integer> scoreMapLandoltC;

    private String score = null;
    String name, email, phone, age, literacy, gender;
    private int correctLine;
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

    int flag = 1;

    private SegregatedActivity segregatedActivity;

    EditText inputDistance;
    String url;
    ImageView displayImage;
    Button submitDistance, english, hindi, octlets, yesButton, noButton;
    float distance = 0.67f;
    String type = "";
    TextView info, displayText, sample, posi, eyeDetails;
    ImageView shownImages;
    int positions = -1;
    LinearLayout linearLayout;
    RelativeLayout chartButtons;
    Retrofit retrofit;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Perisocpe mode. Just perform calculations

        getIntentData();
        retrofit = new Retrofit.Builder().baseUrl(url).
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

        scoreMapLandoltC.put(1,125);
        scoreMapLandoltC.put(2,100);
        scoreMapLandoltC.put(3,80);
        scoreMapLandoltC.put(4,63);
        scoreMapLandoltC.put(5,50);
        scoreMapLandoltC.put(6,40);
        scoreMapLandoltC.put(7,32);
        scoreMapLandoltC.put(8,25);
        scoreMapLandoltC.put(9,20);
        scoreMapLandoltC.put(10,10);

        resetTest();

        setContentView(R.layout.activity_main);
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
        chartButtons = findViewById(R.id.chartButtons);
        eyeDetails = findViewById(R.id.sampleEyeText);
        eyeDetails.setText("Please close your Right eye.");
        Glide.with(MainActivity.this).load(R.drawable.eyegiffs).override(400,400).into(displayImage);

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "english";
                english.setVisibility(View.INVISIBLE);
                //hindi.setVisibility(View.INVISIBLE);
                chartButtons.setVisibility(View.INVISIBLE);
                octlets.setVisibility(View.INVISIBLE);
                info.setText("Click on the button to display English Letters.");
                manageVisibility();

            }
        });


        octlets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "landlot_c";
                english.setVisibility(View.INVISIBLE);
                //hindi.setVisibility(View.INVISIBLE);
                octlets.setVisibility(View.INVISIBLE);
                chartButtons.setVisibility(View.INVISIBLE);
                info.setText("Click on the button to display Aucklands.");
                manageVisibility();
            }
        });


        displayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    callApiToRenderImage(type, position);
                }
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
//                    checkStatus();
//                    position++;
//                    checkMistakeNo();
//                }
                checkStatus();
                position++;
                checkMistakeNo();





            }
        });

    }

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
        line9 = 0;
        line10 = 0;
        line11 = 0;
        line12 = 0;


        mistake.put(1,0);
        mistake.put(2,0);
        mistake.put(3,0);
        mistake.put(4,0);
        mistake.put(5,0);
        mistake.put(6,0);
        mistake.put(7,0);
        mistake.put(8,0);
        mistake.put(9,0);
        mistake.put(10,0);
    }

    public void displayAlert(String message)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
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
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Displaying image. Please wait...");
        progressDialog.show();
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
                    Glide.with(MainActivity.this).load(response.body().getImgurl().toString().trim()).override(300,200).into(shownImages);
                    posi.setText(String.valueOf(response.body().getAccuity()));
                    linearLayout.setVisibility(View.VISIBLE);
                    sample.setVisibility(View.VISIBLE);
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Something went wrong.",
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
                Toast.makeText(MainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                Log.i("displayImageException", t.toString());
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
                    Toast.makeText(MainActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
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
                        displayAlert("Left Eye Score: "+leftEyeResult);
                    }
                    else if(flag == 0)
                    {
                        rightEyeResult = result;
                        displayAlert("Right Eye Score: "+rightEyeResult);
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
                            displayAlert("Left Eye Score: "+leftEyeResult);
                        }
                        else if(flag == 0)
                        {
                            rightEyeResult = result;
                            displayAlert("Right Eye Score: "+rightEyeResult);
                        }//                                Toast.makeText(NonPeriscope.this, "Score: 20/" + score + " - " + "2", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        score = String.valueOf(scoreMap.get((line-1)));
                        result = "20-" + score;
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
                displayAlert("Left Eye Score: "+leftEyeResult);
            }
            else if(flag == 0)
            {
                rightEyeResult = result;
                displayAlert("Right Eye Score: "+rightEyeResult);
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
                        displayAlert("Left Eye Score: "+leftEyeResult);
                    }
                    else if(flag == 0)
                    {
                        rightEyeResult = result;
                        displayAlert("Right Eye Score: "+rightEyeResult);
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
                            displayAlert("Left Eye Score: "+leftEyeResult);
                        }
                        else if(flag == 0)
                        {
                            rightEyeResult = result;
                            displayAlert("Right Eye Score: "+rightEyeResult);
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
                            displayAlert("Left Eye Score: "+leftEyeResult);
                        }
                        else if(flag == 0)
                        {
                            rightEyeResult = result;
                            displayAlert("Right Eye Score: "+rightEyeResult);
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

        url = "http://"+
                getIntent().getExtras().getString("INPUTIPADDRESS")+ ":1234/";
        name = getIntent().getStringExtra("INPUTNAME");
        email = getIntent().getExtras().getString("INPUTEMAIL");
        phone = getIntent().getExtras().getString("INPUTMOBILE");
        age = getIntent().getExtras().getString("INPUTAGE");
        gender = getIntent().getExtras().getString("INPUTGENDER");
        Log.d("data",name);
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
        else if(position >= 40 && position <= 44) {
            line = 9;
            line9++;
            mistake.put(line,line9);
        }
        else if(position >= 45 && position <= 49) {
            line = 10;
            line10++;
            mistake.put(line,line10);
        }
        else if(position >= 50 && position <= 54) {
            line = 11;
            line11++;
            mistake.put(line,line11);
        }
        else if(position >= 55 && position <= 59) {
            line = 12;
            line12++;
            mistake.put(line,line12);
        }
    }

    public void checkMistakeEtdrs() {
        if(line != 0) {
            if(position <= 59) {
                if(mistake.get(line) != 0 && (position == 4 || position == 9 || position == 14 || position == 19 || position == 24 || position == 29 || position == 34 || position == 39 || position == 44 || position == 49 || position == 54 || position == 59)) {
                    if(flag == 1) {
                        correctLine = line - 1;
                        result = String.valueOf(scoreMapLandoltC.get(correctLine) - ((5 - mistake.get(line)) * 0.02));
                        leftEyeResult = result;
                    }
                    else if(flag == 0) {
                        correctLine = line - 1;
                        result = String.valueOf(scoreMapLandoltC.get(correctLine) - ((5 - mistake.get(line)) * 0.02));
                        rightEyeResult = result;
                    }
                }
            }
        }
    }

}