package com.mycompany.hw9;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class NextTwentyFourHours extends Fragment {



    String unitValue = null;
    String timezone =null;
    TableLayout tableLayout = null;
    TableLayout tableLayoutNew = null;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_next_twenty_four, container, false);
        super.onCreate(savedInstanceState);

        tableLayoutNew = (TableLayout) view.findViewById(R.id.tableLayoutNew);
        //set visibility to GONE
        tableLayoutNew.setVisibility(View.GONE);
       // mLinearLayoutHeader = (LinearLayout) view.findViewById(R.id.header);



        String data = this.getArguments().getString("result");

        JSONObject obj = null;
        try {
            obj = new JSONObject(data);
            timezone = obj.getString("timezone");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String icon = null;
        String time = null;
        int temperature = 0;
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams layoutParams1 = new TableRow.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.tab_dim), TableRow.LayoutParams.WRAP_CONTENT, 1);

        if(this.getArguments()!=null) {
            unitValue = this.getArguments().getString("unitTempVal").toString();
            if (unitValue.equals("F")){
                TextView textView = (TextView) view.findViewById(R.id.textViewTemperature);
                textView.setText("Temp(" + "\u2070" + "F" + ")");
                textView.setTextSize(20);
            } else if (unitValue.equals("C")) {
                TextView textView = (TextView) view.findViewById(R.id.textViewTemperature);
                textView.setText("Temp(" + "\u2070" + "C" + ")");
                textView.setTextSize(20);
            }
        }
        try {

            JSONArray jsonArray = obj.getJSONObject("hourly").getJSONArray("data");
            for (int i = 1; i < 25; i++) {
                icon = jsonArray.getJSONObject(i).getString("icon");
                time = jsonArray.getJSONObject(i).getString("time");


                long EventUnixTimeMilli = (Long.valueOf(time)*1000);

                Date EventDate= new Date(EventUnixTimeMilli);

                SimpleDateFormat EventTimeFormatter = new SimpleDateFormat("hh:mm aa");
                EventTimeFormatter.setTimeZone(TimeZone.getTimeZone(timezone));
                String timeDisplay = EventTimeFormatter.format(EventDate);

                String temp = jsonArray.getJSONObject(i).getString("temperature");
                float floatTemp = Float.parseFloat(temp);
                temperature = Math.round(floatTemp);

                 tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);
                ImageView imageView = new ImageView(getActivity());
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
                //imageView.setLayoutParams(new TableRow.LayoutParams(35,150,1));

                TableRow tableRow = new TableRow(getActivity());
                TableRow.LayoutParams imageParams = new TableRow.LayoutParams();
                imageParams.gravity = Gravity.CENTER;
                imageParams.height=120;
                imageParams.width=600;
                imageView.setLayoutParams(imageParams);

                /*  ImageView image = new ImageView(this);
                image.setImageResource(android.R.drawable.btn_plus);
                //Note that here you must use TableRow.LayoutParams
                //since TableRow is the parent of this ImageView
                TableRow.LayoutParams imageParams = new TableRow.LayoutParams();

                //And this is how you set the gravity:
                imageParams.gravity = Gravity.CENTER;
                image.setLayoutParams(imageParams);
                tr.addView(image);*/

                tableRow.setLayoutParams(layoutParams);
                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(layoutParams1);
                textView.setGravity(Gravity.CENTER);

                TextView txtV = new TextView(getActivity());
                textView.setLayoutParams(layoutParams1);

                textView.setText(timeDisplay);
                textView.setTextSize(15);
                textView.setPadding(60,0,0,0);
                txtV.setText(String.valueOf(temperature));
                txtV.setGravity(Gravity.CENTER);
                txtV.setTextSize(15);
                txtV.setPadding(0,0,80,0);

                tableRow.addView(textView);
                tableRow.addView(imageView);
                tableRow.addView(txtV);

                tableLayout.addView(tableRow);

                if (i % 2 == 0) {
                    tableRow.setBackgroundResource(R.color.colorGrey);
                } else {
                    tableRow.setBackgroundResource(R.color.colorWhite);
                }


            }

            final TableRow tableRow1 = new TableRow(getActivity());
            final Button button = new Button(getActivity());
            button.setText("+");

            button.setLayoutParams(layoutParams1);

            tableRow1.addView(button);
            tableRow1.setBackgroundResource(R.color.colorWhite);
            tableLayout.addView(tableRow1);

            for(int j=25;j<jsonArray.length();j++) {

                icon = jsonArray.getJSONObject(j).getString("icon");
                time = jsonArray.getJSONObject(j).getString("time");


                long EventUnixTimeMilli = (Long.valueOf(time)*1000);

                Date EventDate= new Date(EventUnixTimeMilli);

                SimpleDateFormat EventTimeFormatter = new SimpleDateFormat("hh:mm aa");
                EventTimeFormatter.setTimeZone(TimeZone.getTimeZone(timezone));
                String timeDisplay = EventTimeFormatter.format(EventDate);

                String temp = jsonArray.getJSONObject(j).getString("temperature");
                float floatTemp = Float.parseFloat(temp);
                temperature = Math.round(floatTemp);

                tableLayoutNew = (TableLayout) view.findViewById(R.id.tableLayoutNew);
                ImageView imageView = new ImageView(getActivity());
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
                //imageView.setLayoutParams(new TableRow.LayoutParams(35,150,1));

                TableRow tableRow = new TableRow(getActivity());
                TableRow.LayoutParams imageParams = new TableRow.LayoutParams();
                imageParams.gravity = Gravity.CENTER;
                imageParams.height=120;
                imageParams.width=600;
                imageView.setLayoutParams(imageParams);

                tableRow.setLayoutParams(layoutParams);

                TextView textView = new TextView(getActivity());
                textView.setLayoutParams(layoutParams1);
                textView.setGravity(Gravity.CENTER);

                TextView txtV = new TextView(getActivity());
                textView.setLayoutParams(layoutParams1);

                textView.setText(timeDisplay);
                textView.setTextSize(15);
                textView.setPadding(60, 0, 0, 0);
                txtV.setText(String.valueOf(temperature));
                txtV.setTextSize(15);
                txtV.setPadding(0,0,80,0);

                txtV.setGravity(Gravity.CENTER);

                tableRow.addView(textView);
                tableRow.addView(imageView);
                tableRow.addView(txtV);


                tableLayoutNew.addView(tableRow);

                if (j % 2 == 0) {
                    tableRow.setBackgroundResource(R.color.colorGrey);
                } else {
                    tableRow.setBackgroundResource(R.color.colorWhite);
                }



            }
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (tableLayoutNew.getVisibility() == View.GONE) {
                        ViewGroup viewGroup = (ViewGroup)button.getParent();
                        tableLayout.removeView(viewGroup);
                        expand();
                    }
                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void expand() {
        //set Visible
        tableLayoutNew.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tableLayoutNew.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, tableLayoutNew.getMeasuredHeight());
        mAnimator.start();
    }


    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = tableLayoutNew.getLayoutParams();
                layoutParams.height = value;
                tableLayoutNew.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }


}

