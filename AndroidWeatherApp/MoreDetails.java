package com.mycompany.hw9;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TabActivity;

import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MoreDetails extends FragmentActivity {

    String unitValue =null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String display = getIntent().getExtras().get("result").toString();
        unitValue = getIntent().getExtras().get("unitTempVal").toString();

        String cityData = getIntent().getExtras().get("city").toString();
        String stateData = getIntent().getExtras().get("state").toString();
        String headerDisplay = "More details for " + cityData + "," + stateData;

        Fragment fragmentOne = new NextTwentyFourHours();

        Bundle b = new Bundle();
        b.putString("result", display);
        b.putString("unitTempVal", unitValue);
        fragmentOne.setArguments(b);


        fragmentTransaction.add(R.id.frameLayoutFragmentContainer, fragmentOne);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
        setContentView(R.layout.activity_details);
        TextView textView = (TextView) findViewById(R.id.textViewDetails);
        textView.setText(headerDisplay);



    //public void onButtonClicked()
        final Button button =(Button) findViewById(R.id.Next24Hours);
    final Button bn = (Button) findViewById(R.id.Next7Days);
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bn.setBackgroundResource(R.color.colorBlueFade);
                button.setBackgroundResource(R.color.colorGrey);

                String display = getIntent().getExtras().get("result").toString();
                unitValue = getIntent().getExtras().get("unitTempVal").toString();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragmentTwo = new NextSevenDays();
                Bundle b1 = new Bundle();
                b1.putString("result", display);
                b1.putString("unitTempVal",unitValue);

                fragmentTwo.setArguments(b1);

                fragmentTransaction.replace(R.id.frameLayoutFragmentContainer, fragmentTwo);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }

        });


        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                                button.setBackgroundResource(R.color.colorBlueFade);
                                            bn.setBackgroundResource(R.color.colorGrey);
                                              String display = getIntent().getExtras().get("result").toString();
                                              FragmentManager fragmentManager = getFragmentManager();
                                              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                              Fragment fragmentTwo = new NextTwentyFourHours();

                                             Bundle b1 = new Bundle();
                                              b1.putString("result", display);
                                              b1.putString("unitTempVal",unitValue);
                                              fragmentTwo.setArguments(b1);




                                              fragmentTransaction.replace(R.id.frameLayoutFragmentContainer, fragmentTwo);
                                              fragmentTransaction.addToBackStack(null);

                                              fragmentTransaction.commit();
                                          }

                                  }
            );


}
    }










