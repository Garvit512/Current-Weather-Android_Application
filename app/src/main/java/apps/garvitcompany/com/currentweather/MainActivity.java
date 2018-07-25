package apps.garvitcompany.com.currentweather;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText cityinput;
    TextView decr;
    TextView humidity;
    TextView icon;
    MenuInflater mif;
    TextView pressure;
    RequestQueue requestQueue;
    Button search;
    ProgressBar spinner;
    TextView temperature;
    TextView visibility;
    TextView wind;
    //public String urlJsonObj = "http://api.openweathermap.org/data/2.5/weather?q=" + MainActivity.this.cityinput.getText().toString() + "&APPID=6767c5b948045dd2cbb527dc654b11e5&units=metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = (Button) findViewById(R.id.button);
        temperature = (TextView) findViewById(R.id.temperature);
        pressure = (TextView) findViewById(R.id.pressure);
        humidity = (TextView) findViewById(R.id.humidity);
        wind = (TextView) findViewById(R.id.wind);
        visibility = (TextView) findViewById(R.id.visibility);
        requestQueue = Volley.newRequestQueue(this);
        decr = (TextView) findViewById(R.id.description);
        cityinput = (EditText) findViewById(R.id.cityinput);
        cityinput.getText();

        getWindow().setSoftInputMode(3);

        search.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://api.openweathermap.org/data/2.5/weather?q=" + MainActivity.this.cityinput.getText().toString() + "&APPID=6767c5b948045dd2cbb527dc654b11e5&units=metric",null,
                        new Listener<JSONObject>()
                        { @Override
                            public void onResponse(JSONObject response)
                            {
                             try {
                            JSONObject jsonObject1 = response.getJSONObject("main");
                            JSONObject jsonObject2 = response.getJSONObject("wind");
                            JSONArray jsonArray = response.getJSONArray("weather");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MainActivity.this.decr.append(jsonArray.getJSONObject(i).getString("description"));
                            }
                            String Temperature = jsonObject1.getString("temp");
                            String Pressure = jsonObject1.getString("pressure");
                            String Humidity = jsonObject1.getString("humidity");
                            MainActivity.this.wind.append("wind:                            " + jsonObject2.getString("speed") + "Km/h");
                            MainActivity.this.visibility.append("visibility:                        N/A");
                            MainActivity.this.temperature.append(" " + Temperature + "\u2103");
                            MainActivity.this.pressure.append("pressure:                      " + Pressure + " hPa");
                            MainActivity.this.humidity.append("humidity:                      " + Humidity + "%");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new ErrorListener() {
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("VOLLEY", "ERROR");
                    }
                });
                MainActivity.this.temperature.setText(BuildConfig.FLAVOR);
                MainActivity.this.pressure.setText(BuildConfig.FLAVOR);
                MainActivity.this.humidity.setText(BuildConfig.FLAVOR);
                MainActivity.this.wind.setText(BuildConfig.FLAVOR);
                MainActivity.this.visibility.setText(BuildConfig.FLAVOR);
                MainActivity.this.decr.setText(BuildConfig.FLAVOR);
                MainActivity.this.requestQueue.add(jsonObjectRequest);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.mif = getMenuInflater();
        //this.mif.inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}


