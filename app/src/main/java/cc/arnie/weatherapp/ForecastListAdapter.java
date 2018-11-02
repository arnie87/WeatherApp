package cc.arnie.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ForecastListAdapter extends BaseAdapter {

    private ForecastActivity context;
    private ArrayList<ForecastObject> forecastObjectList;

    public ForecastListAdapter(Context context, ArrayList<ForecastObject> forecastObjectList) {

        this.context = (ForecastActivity) context;
        this.forecastObjectList = forecastObjectList;
    }

    public static class ViewHolder {
        TextView dateText;
        TextView descriptionText;
        TextView temperatureText;
        TextView windspeedText;
    }

    @Override
    public int getCount() {
        return forecastObjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return forecastObjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ForecastListAdapter.ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.listitem_forecast, null);

            viewHolder = new ForecastListAdapter.ViewHolder();
            viewHolder.dateText = (TextView) convertView.findViewById(R.id.dateTextView);
            viewHolder.descriptionText = (TextView) convertView.findViewById(R.id.descriptionTextView);
            viewHolder.temperatureText = (TextView) convertView.findViewById(R.id.temperatureTextView);
            viewHolder.windspeedText = (TextView) convertView.findViewById(R.id.windspeedTextView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ForecastListAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.dateText.setText(new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(forecastObjectList.get(position).getDate()));
        viewHolder.descriptionText.setText(forecastObjectList.get(position).getDescription());
        viewHolder.temperatureText.setText(forecastObjectList.get(position).getTemperature());
        viewHolder.windspeedText.setText("Windspeed: "+forecastObjectList.get(position).getWindspeed());

        return convertView;
    }
}