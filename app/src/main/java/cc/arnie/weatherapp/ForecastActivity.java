package cc.arnie.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class ForecastActivity extends AppCompatActivity {

    private static final String TAG = ForecastActivity.class.getSimpleName();

    private ListView forecastListView;
    private String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        forecastListView = (ListView)findViewById(R.id.forecastListView);

        Intent intent = getIntent();
        selectedCity = intent.getStringExtra("city");

        TextView cityTextView = findViewById(R.id.cityTextView);
        cityTextView.setText(selectedCity);

        ArrayList<String> cityList = new ArrayList<String>();
        cityList.add(selectedCity);

        new WeatherAsyncTask(this, forecastListView, cityList).execute();
    }
}
