package cc.arnie.weatherapp;

import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WeatherAsyncTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = WeatherAsyncTask.class.getSimpleName();

    private HttpHandler sh = new HttpHandler();
    private Context context;
    private ListView listView;
    private ArrayList<String> cityList;

    private ArrayList<CurrentWeatherObject> currentWeatherObjectList = new ArrayList<CurrentWeatherObject>();
    private ArrayList<ForecastObject> forecastObjectList = new ArrayList<ForecastObject>();

    // current weather constants:
    private static final String TAG_CITY = "name";
    private static final String TAG_MAIN = "main";
    private static final String TAG_MAIN_TEMP = "temp";
    private static final String TAG_WEATHER = "weather";
    private static final String TAG_WEATHER_DESCRIPTION = "description";

    // weather forecast constants:
    private static final String TAG_LIST = "list";
    private static final String TAG_LIST_DATE = "dt_txt";
    private static final String TAG_LIST_WEATHER = "weather";
    private static final String TAG_LIST_WEATHER_DESCRIPTION = "description";
    private static final String TAG_LIST_MAIN = "main";
    private static final String TAG_LIST_MAIN_TEMPERATURE = "temp";
    private static final String TAG_LIST_WIND = "wind";
    private static final String TAG_LIST_WIND_SPEED = "speed";

    public WeatherAsyncTask(Context context, ListView listView, ArrayList<String> cityList) {
        this.context = context;
        this.listView = listView;
        this.cityList = cityList;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        for (String city : cityList) {

            String purpose = "weather";

            switch (context.getClass().getSimpleName()) {
                case "CurrentWeatherActivity":
                    purpose = "weather";
                    break;
                case "ForecastActivity":
                    purpose = "forecast";
                    break;
                default:
                    break;
            }

            String parseUrl = "http://api.openweathermap.org/data/2.5/" + purpose + "?q=" + city + "&APPID=" + context.getResources().getString(R.string.api_id);
            String jsonStr = sh.makeServiceCall(parseUrl);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                switch (context.getClass().getSimpleName()) {
                    case "CurrentWeatherActivity":
                        createCurrentWeatherObjectList(jsonStr);
                        break;
                    case "ForecastActivity":
                        createWeatherForecastObjectList(jsonStr);
                        break;
                    default:
                        break;
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        switch (context.getClass().getSimpleName()) {
            case "CurrentWeatherActivity":
                listView.setAdapter(new CurrentWeatherListAdapter(context, currentWeatherObjectList));
                break;
            case "ForecastActivity":
                listView.setAdapter(new ForecastListAdapter(context, forecastObjectList));
                break;
            default:
                break;
        }
    }

    private void createCurrentWeatherObjectList(String jsonStr) {

        CurrentWeatherObject o = new CurrentWeatherObject();

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            o.setCity(jsonObj.get(TAG_CITY).toString());

            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);
            JSONObject main = jsonObj.getJSONObject(TAG_MAIN);
            Double cel = Double.valueOf(main.get(TAG_MAIN_TEMP).toString()) + Double.valueOf(context.getResources().getString(R.string.kelvin_to_celsius));
            o.setTemperature(df.format(cel).toString() + " °C");

            JSONObject weather = jsonObj.getJSONArray(TAG_WEATHER).getJSONObject(0);
            o.setDescription(weather.get(TAG_WEATHER_DESCRIPTION).toString());

            currentWeatherObjectList.add(o);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createWeatherForecastObjectList(String jsonStr) {

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray forecastArray = jsonObj.getJSONArray(TAG_LIST);

            for (int i = 0; i < forecastArray.length(); i++) {
                JSONObject j = forecastArray.getJSONObject(i);

                ForecastObject o = new ForecastObject();

                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                    o.setDate(dateFormat.parse(j.getString(TAG_LIST_DATE)));
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }

                JSONObject weatherJsonObj = j.getJSONArray(TAG_LIST_WEATHER).getJSONObject(0);
                o.setDescription(weatherJsonObj.getString(TAG_LIST_WEATHER_DESCRIPTION));

                DecimalFormat df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.CEILING);
                JSONObject mainJsonObj = j.getJSONObject(TAG_LIST_MAIN);
                Double cel = Double.valueOf(mainJsonObj.get(TAG_LIST_MAIN_TEMPERATURE).toString()) + Double.valueOf(context.getResources().getString(R.string.kelvin_to_celsius));
                o.setTemperature(df.format(cel).toString() + " °C");

                JSONObject windJsonObj = j.getJSONObject(TAG_LIST_WIND);
                o.setWindspeed(windJsonObj.getString(TAG_LIST_WIND_SPEED));

                forecastObjectList.add(o);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
