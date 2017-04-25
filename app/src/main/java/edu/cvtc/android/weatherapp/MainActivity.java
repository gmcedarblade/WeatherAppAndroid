package edu.cvtc.android.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cvtc.android.weatherapp.api.OpenWeather;

public class MainActivity extends AppCompatActivity {

    /**
     * Containers for our JSON response data.
     */
    private Map<String, String> openWeatherData = new HashMap<>();
    private Map<String, String> weatherData = new HashMap<>();
    private Map<String, String> mainData = new HashMap<>();
    private Map<String, String> windData = new HashMap<>();
    private Map<String, String> sysData = new HashMap<>();

    private static final String CITY_ID = "5251436";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new OpenWeatherTaskRunner().execute(CITY_ID);

    }

    private class OpenWeatherTaskRunner extends AsyncTask<String, Void, List<Map<String, String>>> {


        @Override
        protected List<Map<String, String>> doInBackground(String... params) {
            return OpenWeather.retrieveWeather(params[0]);
        }

        @Override
        protected void onPostExecute(List<Map<String, String>> data) {

            super.onPostExecute(data);

            if (data.size() > 0) {

                openWeatherData = data.get(0);
                weatherData = data.get(1);
                mainData = data.get(2);
                windData = data.get(3);
                sysData = data.get(4);

            }

            updateUI();
        }
    }

    private void updateUI() {

        ((TextView) findViewById(R.id.name)).setText("Location: " + openWeatherData.get("name"));

        ((TextView) findViewById(R.id.main)).setText("Conditions: " + weatherData.get("main"));
        ((TextView) findViewById(R.id.description)).setText("Description: " + weatherData.get("description"));
        ((TextView) findViewById(R.id.weather_icon)).setText("Icon: " + weatherData.get("icon"));

        ((TextView) findViewById(R.id.temp)).setText("Current Temp: " + mainData.get("temp"));
        ((TextView) findViewById(R.id.pressure)).setText("Pressure: " + mainData.get("pressure"));
        ((TextView) findViewById(R.id.humidity)).setText("Humidity: " + mainData.get("humidity"));
        ((TextView) findViewById(R.id.temp_min)).setText("Low: " + mainData.get("temp_min"));
        ((TextView) findViewById(R.id.temp_max)).setText("High: " + mainData.get("temp_max"));

        ((TextView) findViewById(R.id.speed)).setText("Wind Speed: " + windData.get("speed"));
        ((TextView) findViewById(R.id.deg)).setText("High: " + windData.get("deg"));

        ((TextView) findViewById(R.id.sunrise)).setText("Sunrise: " + sysData.get("sunrise"));
        ((TextView) findViewById(R.id.sunset)).setText("Sunset: " + sysData.get("sunset"));


    }

}
