package com.example.ideo2.checkweather;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ideo2 on 23.09.2016.
 */

public class ScreenSlidePageFragment5 extends android.support.v4.app.Fragment {


    TextView cityNameL;
    TextView countryCodeL;
    TextView dayOfWeek;
    TextView weatherDesc;
    TextView mornT;
    TextView dayT;
    TextView nightT;
    TextView pressureL;
    TextView humidityL;

    RelativeLayout parentLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen, container, false);


        //variables from activity3
        String cityName = WeatherActivity.cityName;
        String countryCode = WeatherActivity.countryCode;
        ArrayList<Double> tempMornArray = WeatherActivity.tempMornArray;
        ArrayList<Double> tempDayArray = WeatherActivity.tempDayArray;
        ArrayList<Double> tempNightArray = WeatherActivity.tempNightArray;
        ArrayList<Double> humidity = WeatherActivity.humidity;
        ArrayList<Double> pressure = WeatherActivity.pressure;
        ArrayList<String> mainArray = WeatherActivity.mainArray;
        ArrayList<String> descriptionArray = WeatherActivity.descriptionArray;

        //layout elements
        cityNameL = (TextView) rootView.findViewById(R.id.cityId);
        countryCodeL = (TextView) rootView.findViewById(R.id.countryCodeId);
        dayOfWeek  = (TextView) rootView.findViewById(R.id.dayScroll);
        weatherDesc  = (TextView) rootView.findViewById(R.id.weatherScroll);
        mornT =  (TextView) rootView.findViewById(R.id.morningScroll);
        dayT =  (TextView) rootView.findViewById(R.id.tempDayScroll);
        nightT =  (TextView) rootView.findViewById(R.id.nightScroll);
        pressureL =  (TextView) rootView.findViewById(R.id.pressureScroll);
        humidityL = (TextView) rootView.findViewById(R.id.humidityScroll);
        parentLayout = (RelativeLayout) rootView.findViewById(R.id.parentLayout);

        //set data
        cityNameL.setText(cityName);
        countryCodeL.setText(countryCode);
        weatherDesc.setText(descriptionArray.get(4));
        mornT.setText("Morning: "+(double)Math.round((tempMornArray.get(4)-273.15)*10d)/10d+" \u00B0C");
        dayT.setText((double)Math.round((tempDayArray.get(4)-273.15)*10d)/10d+" \u00B0C");
        nightT.setText("Night: "+(double)Math.round((tempNightArray.get(4)-273.15)*10d)/10d+" \u00B0C");
        pressureL.setText("Pressure: "+pressure.get(4)+" hPa");
        humidityL.setText("Humidity: "+humidity.get(4)+" %");


        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {

            case Calendar.MONDAY: {
                dayOfWeek.setText("Friday");
                break;
            }
            case Calendar.TUESDAY: {
                dayOfWeek.setText("Saturday");
                break;
            }
            case Calendar.WEDNESDAY: {
                dayOfWeek.setText("Sunday");
                break;
            }
            case Calendar.THURSDAY: {
                dayOfWeek.setText("Monday");
                break;
            }
            case Calendar.FRIDAY: {
                dayOfWeek.setText("Tuesday");
                break;
            }
            case Calendar.SATURDAY: {
                dayOfWeek.setText("Wednesday");
                break;
            }
            case Calendar.SUNDAY: {
                dayOfWeek.setText("Thursday");
                break;
            }
            default: {
                dayOfWeek.setText("Day5");
                break;
            }

        }


        if (mainArray.get(4).matches("Clear")) parentLayout.setBackgroundResource(R.drawable.clear_background);
        if (mainArray.get(4).matches("Rain")) parentLayout.setBackgroundResource(R.drawable.rain_background);
        if (mainArray.get(4).matches("Clouds")) parentLayout.setBackgroundResource(R.drawable.clouds_background);
        if (mainArray.get(4).matches("Snow")) parentLayout.setBackgroundResource(R.drawable.snow_background);

        return rootView;
    }
}
