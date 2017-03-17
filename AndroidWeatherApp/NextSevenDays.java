package com.mycompany.hw9;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class NextSevenDays extends Fragment {

    int DateMonth = 0;
    int IconValue = 0;
    int MinMaxTemp = 0;
    String timezone = null;
    String unitValue = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_next_seven_days, container, false);
        super.onCreate(savedInstanceState);


        String value = this.getArguments().getString("result");

        JSONObject obj = null;
        try {
            obj = new JSONObject(value);
            timezone = obj.getString("timezone");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String icon = null;
        String time = null;
        //int temperature = 0;

        try {

            JSONArray jsonArray = obj.getJSONObject("daily").getJSONArray("data");
            for (int i = 1; i < jsonArray.length(); i++) {
                icon = jsonArray.getJSONObject(i).getString("icon");
                time = jsonArray.getJSONObject(i).getString("time");

                long EventUnixTimeMilli = (Long.valueOf(time) * 1000);

                Date EventDate = new Date(EventUnixTimeMilli);

                SimpleDateFormat EventTimeFormatter = new SimpleDateFormat("EEEE ,MMM dd");
                EventTimeFormatter.setTimeZone(TimeZone.getTimeZone(timezone));
                String timeDisplay = EventTimeFormatter.format(EventDate);


                String tempMin = jsonArray.getJSONObject(i).getString("temperatureMin");
                float floatTemp = Float.parseFloat(tempMin);
                int temperatureMin = Math.round(floatTemp);

                String tempMax = jsonArray.getJSONObject(i).getString("temperatureMax");
                float floatTempMax = Float.parseFloat(tempMax);
                int temperatureMax = Math.round(floatTempMax);


                switch (i) {
                    case 1:
                        DateMonth = R.id.textViewDayMonthOne;
                        IconValue = R.id.textViewIconOne;
                        MinMaxTemp = R.id.textViewTempOne;
                        break;

                    case 2:
                        DateMonth = R.id.textViewDayMonthTwo;
                        IconValue = R.id.textViewIconTwo;
                        MinMaxTemp = R.id.textViewTempTwo;
                        break;
                    case 3:
                        DateMonth = R.id.textViewDayMonthThree;
                        IconValue = R.id.textViewIconThree;
                        MinMaxTemp = R.id.textViewTempThree;
                        break;
                    case 4:
                        DateMonth = R.id.textViewDayMonthFour;
                        IconValue = R.id.textViewIconFour;
                        MinMaxTemp = R.id.textViewTempFour;
                        break;
                    case 5:
                        DateMonth = R.id.textViewDayMonthFive;
                        IconValue = R.id.textViewIconFive;
                        MinMaxTemp = R.id.textViewTempFive;
                        break;
                    case 6:
                        DateMonth = R.id.textViewDayMonthSix;
                        IconValue = R.id.textViewIconSix;
                        MinMaxTemp = R.id.textViewTempSix;
                        break;
                    case 7:
                        DateMonth = R.id.textViewDayMonthSeven;
                        IconValue = R.id.textViewIconSeven;
                        MinMaxTemp = R.id.textViewTempSeven;
                        break;


                }

                TextView textView = (TextView) view.findViewById(DateMonth);
                textView.setText(timeDisplay);
                textView.setTextSize(18);


                ImageView imageView = (ImageView) view.findViewById(IconValue);
                switch (icon) {
                    case "clear-day":
                        imageView.setImageResource(R.drawable.clear);
                        break;
                    case "clear-night":
                        imageView.setImageResource(R.drawable.clear_night);
                        break;
                    case "rain":
                        imageView.setImageResource(R.drawable.rain);
                        break;
                    case "snow":
                        imageView.setImageResource(R.drawable.snow);
                        break;
                    case "sleet":
                        imageView.setImageResource(R.drawable.sleet);
                        break;
                    case "wind":
                        imageView.setImageResource(R.drawable.wind);
                        break;
                    case "fog":
                        imageView.setImageResource(R.drawable.fog);
                        break;
                    case "cloudy":
                        imageView.setImageResource(R.drawable.cloudy);
                        break;
                    case "partly-cloudy-day":
                        imageView.setImageResource(R.drawable.cloud_day);
                        break;
                    case "partly-cloudy-night":
                        imageView.setImageResource(R.drawable.cloud_night);
                        break;
                }

                unitValue = this.getArguments().getString("unitTempVal").toString();
                TextView textView1 = (TextView) view.findViewById(MinMaxTemp);
                textView1.setTextSize(20);
                if (unitValue.equals("F")) {
                    textView1.setText("Min: " + temperatureMin + "\u2070" + "F" + "|" + "Max: " + temperatureMax + "\u2070" + "F");
                } else if (unitValue.equals("C")) {

                    textView1.setText("Min: " + temperatureMin + "\u2070" + "C" + "|" + "Max: " + temperatureMax + "\u2070" + "C");
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }


}
