package cc.arnie.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CurrentWeatherActivity extends AppCompatActivity {

    private static final String TAG = CurrentWeatherActivity.class.getSimpleName();

    private ListView currentWeatherListView;
    private ArrayList<String> myCityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentWeatherListView = (ListView) findViewById(R.id.currentWeatherListView);

        currentWeatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(CurrentWeatherActivity.this, ForecastActivity.class);
                myIntent.putExtra("city", myCityList.get(position));
                CurrentWeatherActivity.this.startActivity(myIntent);
            }
        });

        myCityList = new ArrayList<String>();
        myCityList.add("London");
        myCityList.add("Dublin");
        myCityList.add("Vienna");

        new WeatherAsyncTask(this, currentWeatherListView, myCityList).execute();
    }

}
