package com.mycompany.hw9;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

//import static com.mycompany.hw9.R.id.editText;
//import static com.mycompany.hw9.R.id.editText1;

public class MainActivity extends AppCompatActivity {

    private EditText streetNameTextView;
    private EditText cityNameTextView;
    private TextView display;
    private Spinner stateName;
    String street;
    String city;
    String state;
    String unit;
    String unitTempVal = null;
    String unitWind = null;
    String unitDew = null;
    String unitVisibility = null;
    RadioButton rbFahrenheit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rbFahrenheit = (RadioButton) findViewById(R.id.radioButtonFahrenheit);

        stateName = (Spinner) findViewById(R.id.spinner);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.states, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLink = new Intent("android.intent.action.VIEW", Uri.parse("https://www.forecast.io"));
                startActivity(intentLink);

            }
        });

        Button btn = (Button) findViewById(R.id.AboutMe);
        btn.setOnClickListener(new OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                                       startActivity(intent);
                                   }
                               }
        );

        Button bn = (Button) findViewById(R.id.Clear);
        bn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                streetNameTextView.setText("");
                cityNameTextView.setText("");
                stateName.setSelection(0);
                rbFahrenheit.setChecked(true);
                display = (TextView) findViewById(R.id.textViewMsgDisplay);
                display.setText("");


            }
        });

        Button b = (Button) findViewById(R.id.SearchWeather);
        b.setOnClickListener(new OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     streetNameTextView = (EditText) findViewById(R.id.editText);
                                     street = streetNameTextView.getText().toString();

                                     cityNameTextView = (EditText) findViewById(R.id.editText1);
                                     city = cityNameTextView.getText().toString();

                                     stateName = (Spinner) findViewById(R.id.spinner);
                                     state = stateName.getSelectedItem().toString();




                                     RadioButton rbCelsius;
                                     rbCelsius = (RadioButton) findViewById(R.id.radioButtonCelsius);


                                     if (rbFahrenheit.isChecked()) {
                                         unit = "us";
                                         unitTempVal = "F";
                                         unitWind = "mph";
                                         unitDew = "\u2070" + "F";
                                         unitVisibility = "mi";
                                     } else if (rbCelsius.isChecked()) {
                                         unit = "si";
                                         unitTempVal = "C";
                                         unitWind = "m/s";
                                         unitDew = "\u2070" + "C";
                                         unitVisibility = "km";
                                     }

                                     if (street.trim().length() == 0) {
                                         display = (TextView) findViewById(R.id.textViewMsgDisplay);
                                         display.setText("Please enter the street");
                                     } else if (city.trim().length() == 0) {
                                         display = (TextView) findViewById(R.id.textViewMsgDisplay);
                                         display.setText("Please enter the city");
                                     } else if (state.equals("Select")) {
                                         display = (TextView) findViewById(R.id.textViewMsgDisplay);
                                         display.setText("Please select a state");
                                     } else {
                                         new DataPopulate().execute(street, city, state);
                                     }
                                 }
                             }
        );


        streetNameTextView = (EditText) findViewById(R.id.editText);
        streetNameTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (streetNameTextView.getText().length() == 0) {
                    display = (TextView) findViewById(R.id.textViewMsgDisplay);
                    display.setText("Please enter a street address");
                }

            }

        });

        streetNameTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                cityNameTextView = (EditText) findViewById(R.id.editText1);
                if (cityNameTextView.getText().length() == 0) {
                    display = (TextView) findViewById(R.id.textViewMsgDisplay);
                    display.setText("Please enter the city");
                }

            }

        });

        cityNameTextView = (EditText) findViewById(R.id.editText1);
        cityNameTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (cityNameTextView.getText().length() == 0) {
                    display = (TextView) findViewById(R.id.textViewMsgDisplay);
                    display.setText("Please enter the city");
                }

            }

        });


        cityNameTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {


                if (stateName.getSelectedItem().toString().equals("Select")) {
                    display = (TextView) findViewById(R.id.textViewMsgDisplay);
                    display.setText("Please select a state");
                }

            }

        });

        stateName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedIndex = parent.getItemAtPosition(position).toString();
                if (!selectedIndex.equals("Select")) {
                    display = (TextView) findViewById(R.id.textViewMsgDisplay);
                    display.setText("");
                }
            }
        });


    }

    public class DataPopulate extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String result) {
            //Log.d("display", result);
            Intent intent = new Intent(MainActivity.this, ResultDisplay.class);
            intent.putExtra("result", result);
            intent.putExtra("city", city);
            intent.putExtra("state", state);
            intent.putExtra("unitTempVal", unitTempVal);
            intent.putExtra("unitWind", unitWind);
            intent.putExtra("unitVisibility", unitVisibility);
            intent.putExtra("unitDew", unitDew);
            startActivity(intent);
        }

        @Override
        protected String doInBackground(String... params) {

            URL url = null;
            StringBuilder result = null;
            try {
                String param0 = URLEncoder.encode(params[0], "UTF-8");
                String param1 = URLEncoder.encode(params[1], "UTF-8");
                url = new URL("http://Forecastassignment-env.elasticbeanstalk.com/?street=" + param0 + "&city=" + param1 + "&state=" + params[2] + "&degree=" + unit);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result.toString();

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
