package com.mycompany.hw9;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.*;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ResultDisplay extends AppCompatActivity {

    CallbackManager callbackManager;
    ShareDialog shareDialog;
    String SummaryTextView;
    String cityVal;
    String stateVal;
    String FacebookSummary;
    String summary;
    String temp;
    int temperature;
    String icon;
    String unitValue;
    Uri facebookIcon;
    String display;
    String lat;
    String lon;
    String Sunrise;
    String Sunset;
    String timezone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        display = getIntent().getExtras().get("result").toString();
        JSONObject obj1 = null;
        try {
            obj1 = new JSONObject(display);
            lat = obj1.getString("latitude");
            lon = obj1.getString("longitude");
            timezone = obj1.getString("timezone");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(getApplicationContext(), "Facebook Post Successful", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        setContentView(R.layout.activity_result_display);


        Button btn = (Button) findViewById(R.id.MoreDetailsbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultDisplay.this, MoreDetails.class);
                intent.putExtra("result", display);
                intent.putExtra("city", cityVal);
                intent.putExtra("state", stateVal);
                intent.putExtra("unitTempVal", unitValue);
                startActivity(intent);
            }
        });


        Button bn = (Button) findViewById(R.id.buttonMap);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultDisplay.this, MapDisplay.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                startActivity(intent);


            }
        });

        Button button = (Button) findViewById(R.id.fbbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(FacebookSummary)
                            .setContentDescription(
                                    summary + "," + temperature + "\u2070" + unitValue)
                            .setContentUrl(Uri.parse("https://www.forecast.io")).
                                    setImageUrl(facebookIcon)
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });

        //String Sunrise;
        try {


            cityVal = getIntent().getExtras().get("city").toString();
            stateVal = getIntent().getExtras().get("state").toString();

            FacebookSummary = "Current Weather in " + cityVal + "," + stateVal;


            unitValue = getIntent().getExtras().get("unitTempVal").toString();
            String unitDew = getIntent().getExtras().get("unitDew").toString();
            String unitVisibility = getIntent().getExtras().get("unitVisibility").toString();
            String unitWind = getIntent().getExtras().get("unitWind").toString();
            JSONObject obj = new JSONObject(display);

            summary = obj.getJSONObject("currently").getString("summary");
            icon = obj.getJSONObject("currently").getString("icon");

            temp = obj.getJSONObject("currently").getString("temperature");
            float floatTemp = Float.parseFloat(temp);
            temperature = Math.round(floatTemp);

            JSONArray textViewHighTemp = obj.getJSONObject("daily").getJSONArray("data");
            String tempMin = textViewHighTemp.getJSONObject(0).getString("temperatureMin");
            float floatTempMin = Float.parseFloat(tempMin);
            int temperatureMin = Math.round(floatTempMin);

            JSONArray textViewLowTemp = obj.getJSONObject("daily").getJSONArray("data");
            String tempMax = textViewLowTemp.getJSONObject(0).getString("temperatureMax");
            float floatTempMax = Float.parseFloat(tempMax);
            int temperatureMax = Math.round(floatTempMax);

            String precipitation = obj.getJSONObject("currently").getString("precipIntensity");
            float Precip = Float.parseFloat(precipitation);
            String prec = null;

            if (Precip >= 0 && Precip < 0.002) {
                prec = "None";
            } else if (Precip >= 0.002 && Precip < 0.017) {
                prec = "Very Light";

            } else if (Precip >= 0.017 && Precip < 0.1) {
                prec = "Light";

            } else if (Precip >= 0.1 && Precip < 0.4) {
                prec = "Moderate";
            } else if (Precip >= 0.4) {
                prec = "Heavy";
            }


            String Rain = obj.getJSONObject("currently").getString("precipProbability");
            float ChanceRain = (Float.parseFloat(Rain)) * 100;
            int ChanceOfRain = Math.round(ChanceRain);

            String Speed = obj.getJSONObject("currently").getString("windSpeed");
            float WSpeed = Float.parseFloat(Speed);
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            String WindSpeed = formatter.format(WSpeed);


            String Dew = obj.getJSONObject("currently").getString("dewPoint");
            float DPoint = Float.parseFloat(Dew);
            //NumberFormat formatt = NumberFormat.getNumberInstance();
            //formatt.setMinimumFractionDigits(2);
            //formatt.setMaximumFractionDigits(2);
            int DewPoint = Math.round(DPoint);

            String Hum = obj.getJSONObject("currently").getString("humidity");
            float Humid = (Float.parseFloat(Hum)) * 100;
            int Humidity = Math.round(Humid);

            String Visib = obj.getJSONObject("currently").getString("visibility");
            float Vis = Float.parseFloat(Visib);
            NumberFormat numForm = NumberFormat.getNumberInstance();
            numForm.setMinimumFractionDigits(2);
            numForm.setMaximumFractionDigits(2);
            String Visibility = numForm.format(Vis);


            JSONArray textViewSunRise = obj.getJSONObject("daily").getJSONArray("data");
            Sunrise = textViewSunRise.getJSONObject(0).getString("sunriseTime");

            Sunset = textViewSunRise.getJSONObject(0).getString("sunsetTime");

            long EventUnixTimeMilli = (Long.valueOf(Sunrise) * 1000);
            long EventUnixSunSetMilli = (Long.valueOf(Sunset) * 1000);


            Date EventDate = new Date(EventUnixTimeMilli);
            Date EventDate1 = new Date(EventUnixSunSetMilli);


            SimpleDateFormat EventTimeFormatter = new SimpleDateFormat("hh:mm aa");
            EventTimeFormatter.setTimeZone(TimeZone.getTimeZone(timezone));
            String sunrise = EventTimeFormatter.format(EventDate);

            SimpleDateFormat EventTimeFormatter1 = new SimpleDateFormat("hh:mm aa");
            EventTimeFormatter1.setTimeZone(TimeZone.getTimeZone(timezone));
            String sunset = EventTimeFormatter.format(EventDate1);


            ImageView imageView = (ImageView) findViewById(R.id.imageView);


            switch (icon) {
                case "clear-day":
                    imageView.setImageResource(R.drawable.clear);
                    facebookIcon = Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/clear.png");
                    break;
                case "clear-night":
                    imageView.setImageResource(R.drawable.clear_night);
                    facebookIcon = Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/clear_night.png");
                    break;
                case "rain":
                    imageView.setImageResource(R.drawable.rain);
                    facebookIcon = Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/rain.png");
                    break;
                case "snow":
                    imageView.setImageResource(R.drawable.snow);
                    facebookIcon = Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/snow.png");
                    break;
                case "sleet":
                    imageView.setImageResource(R.drawable.sleet);
                    facebookIcon = Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/sleet.png");
                    break;
                case "wind":
                    imageView.setImageResource(R.drawable.wind);
                    facebookIcon = Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/wind.png");
                    break;
                case "fog":
                    imageView.setImageResource(R.drawable.fog);
                    facebookIcon = Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/fog.png");
                    break;
                case "cloudy":
                    imageView.setImageResource(R.drawable.cloudy);
                    facebookIcon = Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/cloudy.png");
                    break;
                case "partly-cloudy-day":
                    imageView.setImageResource(R.drawable.cloud_day);
                    facebookIcon = Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/cloud_day.png");
                    break;
                case "partly-cloudy-night":
                    imageView.setImageResource(R.drawable.cloud_night);
                    facebookIcon = Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/cloud_night.png");
                    break;
            }


            SummaryTextView = summary + " in " + cityVal + "," + stateVal;
            TextView textView = (TextView) findViewById(R.id.textViewSummary);
            textView.setText(SummaryTextView);


            TextView textViewTemp = (TextView) findViewById(R.id.textViewTemperature);
            textViewTemp.setText(String.valueOf(temperature));


            TextView textViewTempUnit = (TextView) findViewById(R.id.textViewTempUnit);
            textViewTempUnit.setText("\u2070" + unitValue);

            TextView HighTemp = (TextView) findViewById(R.id.textViewHighLow);
            HighTemp.setText("L:" + String.valueOf(temperatureMin) + "\u2070" + "|H:" + String.valueOf(temperatureMax) + "\u2070");

            TextView textViewPrecip = (TextView) findViewById(R.id.textViewPrecipitation);
            textViewPrecip.setText(prec);

            TextView textViewRain = (TextView) findViewById(R.id.textViewChanceOfRain);
            textViewRain.setText(ChanceOfRain + "%");

            TextView textViewWindSpeed = (TextView) findViewById(R.id.textViewWindSpeed);
            textViewWindSpeed.setText(WindSpeed + unitWind);

            TextView textViewDewPoint = (TextView) findViewById(R.id.textViewDewPoint);
            textViewDewPoint.setText(DewPoint + unitDew);

            TextView textViewHumidity = (TextView) findViewById(R.id.textViewHumidity);
            textViewHumidity.setText(Humidity + "%");

            TextView textViewVisibility = (TextView) findViewById(R.id.textViewVisibility);
            textViewVisibility.setText(Visibility + unitVisibility);

            TextView textViewSunR = (TextView) findViewById(R.id.textViewSunRise);
            textViewSunR.setText(sunrise);

            TextView textViewSet = (TextView) findViewById(R.id.textViewSunSet);
            textViewSet.setText(sunset);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
