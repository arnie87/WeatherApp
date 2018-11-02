package cc.arnie.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CurrentWeatherListAdapter extends BaseAdapter {

    private CurrentWeatherActivity context;
    private ArrayList<CurrentWeatherObject> currentWeatherObjectList;

    public CurrentWeatherListAdapter(Context context, ArrayList<CurrentWeatherObject> currentWeatherObjectList) {

        this.context = (CurrentWeatherActivity)context;
        this.currentWeatherObjectList = currentWeatherObjectList;
    }

    public static class ViewHolder {
        TextView cityText;
        TextView temperatureText;
        TextView descriptionText;
    }

    @Override
    public int getCount() {
        return currentWeatherObjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return currentWeatherObjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.listitem_main, null);

            viewHolder = new ViewHolder();
            viewHolder.cityText = (TextView) convertView.findViewById(R.id.cityTextView);
            viewHolder.temperatureText = (TextView) convertView.findViewById(R.id.temperatureTextView);
            viewHolder.descriptionText = (TextView) convertView.findViewById(R.id.descriptionTextView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.cityText.setText(currentWeatherObjectList.get(position).getCity());
        viewHolder.temperatureText.setText(currentWeatherObjectList.get(position).getTemperature());
        viewHolder.descriptionText.setText(currentWeatherObjectList.get(position).getDescription());

        return convertView;
    }
}
