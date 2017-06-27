package com.example.ideo2.checkweather;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

//open weather map api
//api key:    872e464cc604b22d3461e6d1a52b5f54

public class WeatherActivity extends AppCompatActivity {

    String placeName; //place name from list activity
    int placeId;    //id of place from list activity



    //values used in this activity
    public static String cityName;
    public static String countryCode;
    public static ArrayList<Double> tempMornArray;
    public static ArrayList<Double> tempDayArray;
    public static ArrayList<Double> tempNightArray;
    public static ArrayList<String> mainArray;
    public static ArrayList<String> descriptionArray;

    //additional values used in viewpager
    public static ArrayList<Double> pressure;
    public static ArrayList<Double> humidity;



    //layout elements
    TextView cityNameId;
    TextView countryCodeId;

    TextView dayOneWeather;
    TextView dayOneMorn;
    TextView dayOneDay;
    TextView dayOneNight;

    TextView dayTwoWeather;
    TextView dayTwoMorn;
    TextView dayTwoDay;
    TextView dayTwoNight;

    TextView dayThreeWeather;
    TextView dayThreeMorn;
    TextView dayThreeDay;
    TextView dayThreeNight;

    TextView dayFourWeather;
    TextView dayFourMorn;
    TextView dayFourDay;
    TextView dayFourNight;

    TextView dayFiveWeather;
    TextView dayFiveMorn;
    TextView dayFiveDay;
    TextView dayFiveNight;

    TextView daySixWeather;
    TextView daySixMorn;
    TextView daySixDay;
    TextView daySixNight;

    TextView daySevenWeather;
    TextView daySevenMorn;
    TextView daySevenDay;
    TextView daySevenNight;

    RelativeLayout topBar;
    LinearLayout weatherLayout;
    RelativeLayout loadingLayout;
    RelativeLayout weatherLineOneLayout;
    RelativeLayout weatherLineTwoLayout;
    RelativeLayout weatherLineThreeLayout;
    RelativeLayout weatherLineFourLayout;
    RelativeLayout weatherLineFiveLayout;
    RelativeLayout weatherLineSixLayout;
    RelativeLayout weatherLineSevenLayout;

    TextView dayThreeName;
    TextView dayFourName;
    TextView dayFiveName;
    TextView daySixName;
    TextView daySevenName;

    TextView loadingText;
    ProgressBar loadingCircle;

    ImageView weatherImage1;
    ImageView weatherImage2;
    ImageView weatherImage3;
    ImageView weatherImage4;
    ImageView weatherImage5;
    ImageView weatherImage6;
    ImageView weatherImage7;

    /**
     * Uruchomienie downloadtask z parametrem w zaleznosci z ktorego activity pochodza dane
     * inicjalizacja zmiennych
     * uzycie kalendaza do przypisania dni tygodnia
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //check source, which intent started this activity
        Intent i = getIntent();
        String source = i.getStringExtra("source");

        if (source.matches("IntroActivity")) {
            String lat = i.getStringExtra("lat");
            String lng = i.getStringExtra("lng");

            DownloadTask task = new DownloadTask();
            task.execute("http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lng+"&APPID=872e464cc604b22d3461e6d1a52b5f54");

        }

        if (source.matches("ListActivity")) {

            placeId = i.getIntExtra("placeId", -1);
            if (placeId != -1) {
                placeName = ListActivity.places.get(placeId);
                //Log.i("LOG-MES-PlaceMessage", placeName);
            } else {
                Log.i("LOG-MES-PlaceMessage", "Error");
            }

            //encoding place name and executing task
            try {
                //encode places name
                String encodePlaceName = URLEncoder.encode(placeName, "UTF-8");

                DownloadTask task = new DownloadTask();
                task.execute("http://api.openweathermap.org/data/2.5/forecast/daily?q="+encodePlaceName+"&APPID=872e464cc604b22d3461e6d1a52b5f54");


            } catch (UnsupportedEncodingException e) {
                //Toast.makeText(getApplicationContext(), "Could not encode the place name.", Toast.LENGTH_SHORT).show();
                Log.i("LOG-MES-Error: ", "Could not encode the place name.");

            }
        }


        //initializing layout elements
        cityNameId = (TextView) findViewById(R.id.cityId);
        countryCodeId = (TextView) findViewById(R.id.countryCodeId);

        dayOneWeather = (TextView) findViewById(R.id.dayOneWeather);
        dayOneMorn = (TextView) findViewById(R.id.dayOneMorn);
        dayOneDay = (TextView) findViewById(R.id.dayOneDay);
        dayOneNight = (TextView) findViewById(R.id.dayOneNight);

        dayTwoWeather = (TextView) findViewById(R.id.dayTwoWeather);
        dayTwoMorn = (TextView) findViewById(R.id.dayTwoMorn);
        dayTwoDay = (TextView) findViewById(R.id.dayTwoDay);
        dayTwoNight = (TextView) findViewById(R.id.dayTwoNight);

        dayThreeWeather = (TextView) findViewById(R.id.dayThreeWeather);
        dayThreeMorn = (TextView) findViewById(R.id.dayThreeMorn);
        dayThreeDay = (TextView) findViewById(R.id.dayThreeDay);
        dayThreeNight = (TextView) findViewById(R.id.dayThreeNight);

        dayFourWeather = (TextView) findViewById(R.id.dayFourWeather);
        dayFourMorn = (TextView) findViewById(R.id.dayFourMorn);
        dayFourDay = (TextView) findViewById(R.id.dayFourDay);
        dayFourNight = (TextView) findViewById(R.id.dayFourNight);

        dayFiveWeather = (TextView) findViewById(R.id.dayFiveWeather);
        dayFiveMorn = (TextView) findViewById(R.id.dayFiveMorn);
        dayFiveDay = (TextView) findViewById(R.id.dayFiveDay);
        dayFiveNight = (TextView) findViewById(R.id.dayFiveNight);

        daySixWeather = (TextView) findViewById(R.id.daySixWeather);
        daySixMorn = (TextView) findViewById(R.id.daySixMorn);
        daySixDay = (TextView) findViewById(R.id.daySixDay);
        daySixNight = (TextView) findViewById(R.id.daySixNight);

        daySevenWeather = (TextView) findViewById(R.id.daySevenWeather);
        daySevenMorn = (TextView) findViewById(R.id.daySevenMorn);
        daySevenDay = (TextView) findViewById(R.id.daySevenDay);
        daySevenNight = (TextView) findViewById(R.id.daySevenNight);

        topBar = (RelativeLayout) findViewById(R.id.topBarLayout);
        weatherLayout = (LinearLayout) findViewById(R.id.weatherLinesLayout);
        loadingLayout = (RelativeLayout) findViewById(R.id.loadingLayout);
        weatherLineOneLayout = (RelativeLayout) findViewById(R.id.dayOneLayout);
        weatherLineTwoLayout = (RelativeLayout) findViewById(R.id.dayTwoLayout);
        weatherLineThreeLayout = (RelativeLayout) findViewById(R.id.dayThreeLayout);
        weatherLineFourLayout = (RelativeLayout) findViewById(R.id.dayFourLayout);
        weatherLineFiveLayout = (RelativeLayout) findViewById(R.id.dayFiveLayout);
        weatherLineSixLayout = (RelativeLayout) findViewById(R.id.daySixLayout);
        weatherLineSevenLayout = (RelativeLayout) findViewById(R.id.daySevenLayout);


        topBar.setVisibility(View.INVISIBLE);
        topBar.animate().translationY(-500f);

        weatherLayout.setVisibility(View.GONE);
        weatherLineOneLayout.animate().translationX(1000f);
        weatherLineTwoLayout.animate().translationX(1400f);
        weatherLineThreeLayout.animate().translationX(1800f);
        weatherLineFourLayout.animate().translationX(2200f);
        weatherLineFiveLayout.animate().translationX(2600f);
        weatherLineSixLayout.animate().translationX(3000f);
        weatherLineSevenLayout.animate().translationX(3400f);

        loadingLayout.setVisibility(View.VISIBLE);

        dayThreeName = (TextView) findViewById(R.id.dayThreeId);
        dayFourName = (TextView) findViewById(R.id.dayFourId);
        dayFiveName = (TextView) findViewById(R.id.dayFiveId);
        daySixName = (TextView) findViewById(R.id.daySixId);
        daySevenName = (TextView) findViewById(R.id.daySevenId);

        loadingText = (TextView) findViewById(R.id.loadingText);
        loadingCircle = (ProgressBar) findViewById(R.id.loadingCircle);

        weatherImage1 = (ImageView) findViewById(R.id.dayOneIcon);
        weatherImage2 = (ImageView) findViewById(R.id.dayTwoIcon);
        weatherImage3 = (ImageView) findViewById(R.id.dayThreeIcon);
        weatherImage4 = (ImageView) findViewById(R.id.dayFourIcon);
        weatherImage5 = (ImageView) findViewById(R.id.dayFiveIcon);
        weatherImage6 = (ImageView) findViewById(R.id.daySixIcon);
        weatherImage7 = (ImageView) findViewById(R.id.daySevenIcon);

        //calendar --- day of week

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {

            case Calendar.MONDAY: {
                dayThreeName.setText("Wednesday");
                dayFourName.setText("Thursday");
                dayFiveName.setText("Friday");
                daySixName.setText("Saturday");
                daySevenName.setText("Sunday");

                break;
            }
            case Calendar.TUESDAY: {
                dayThreeName.setText("Thursday");
                dayFourName.setText("Friday");
                dayFiveName.setText("Saturday");
                daySixName.setText("Sunday");
                daySevenName.setText("Monday");

                break;
            }
            case Calendar.WEDNESDAY: {
                dayThreeName.setText("Friday");
                dayFourName.setText("Saturday");
                dayFiveName.setText("Sunday");
                daySixName.setText("Monday");
                daySevenName.setText("Tuesday");

                break;
            }
            case Calendar.THURSDAY: {
                dayThreeName.setText("Saturday");
                dayFourName.setText("Sunday");
                dayFiveName.setText("Monday");
                daySixName.setText("Tuesday");
                daySevenName.setText("Wednesday");

                break;
            }
            case Calendar.FRIDAY: {
                dayThreeName.setText("Sunday");
                dayFourName.setText("Monday");
                dayFiveName.setText("Tuesday");
                daySixName.setText("Wednesday");
                daySevenName.setText("Thursday");

                break;
            }
            case Calendar.SATURDAY: {
                dayThreeName.setText("Monday");
                dayFourName.setText("Tuesday");
                dayFiveName.setText("Wednesday");
                daySixName.setText("Thursday");
                daySevenName.setText("Friday");

                break;
            }
            case Calendar.SUNDAY: {
                dayThreeName.setText("Tuesday");
                dayFourName.setText("Wednesday");
                dayFiveName.setText("Thursday");
                daySixName.setText("Friday");
                daySevenName.setText("Saturday");

                break;
            }

            default: {
                dayThreeName.setText("Day3");
                dayFourName.setText("Day4");
                dayFiveName.setText("Day5");
                daySixName.setText("Day6");
                daySevenName.setText("Day7");

                break;
            }

        }

    }


    /**
     * Pobieranie danych z api jako JSON
     */
    //download json from api class
    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            InputStream in = null;
            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1) {

                    /*
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                    */
                    char current = (char) data;
                    stringBuilder.append(current);
                    data = reader.read();
                }
                result = stringBuilder.toString();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("LOG-MES-Error: ", "Exception, cannot download weather data");
            }
            finally {
                if (in !=null) {
                    try {
                        in.close();
                    }catch (IOException ioex) {
                        Log.i("LOG-MES-Error: ", "Input stream exception");
                    }
                }
            }
            return null;
        }
        /**
         * onPostExecute
         * dzielenie danych z api na mniejsze obiekty
         * oraz przypisanie ich do zmiennych
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                String finalResult = "";
                JSONObject jsonObject= new JSONObject(result); //whole JSON file

                String cityInfo = jsonObject.getString("city"); //city part
                JSONObject jsonCity = new JSONObject(cityInfo); //city part into object


                cityName =jsonCity.getString("name");    //final city name
                countryCode =jsonCity.getString("country"); //final country code

                JSONArray forecastArray = jsonObject.getJSONArray("list"); //list part

                tempMornArray = new ArrayList<>();
                tempDayArray = new ArrayList<>();
                tempNightArray = new ArrayList<>();
                mainArray = new ArrayList<>();
                descriptionArray = new ArrayList<>();
                pressure = new ArrayList<>();
                humidity = new ArrayList<>();

                double tempMorn;
                double tempDay;
                double tempNight;
                double press;
                double humi;
                String main;
                String description;


                    for (int i=0; i<forecastArray.length(); i++) {
                        JSONObject dailyForecast = forecastArray.getJSONObject(i);
                        JSONObject tempObject = dailyForecast.getJSONObject("temp");
                            tempMorn = tempObject.getDouble("morn");
                            tempDay = tempObject.getDouble("day");
                            tempNight = tempObject.getDouble("night");

                            tempMornArray.add(tempMorn);
                            tempDayArray.add(tempDay);
                            tempNightArray.add(tempNight);

                            press = dailyForecast.getDouble("pressure");
                            humi = dailyForecast.getDouble("humidity");
                            pressure.add(press);
                            humidity.add(humi);

                            JSONArray weatherObject = dailyForecast.getJSONArray("weather");
                            JSONObject weatherObjectInfo = weatherObject.getJSONObject(Integer.parseInt("0"));

                            main = weatherObjectInfo.getString("main");
                            description = weatherObjectInfo.getString("description");

                            mainArray.add(main);
                            descriptionArray.add(description);
                    }

                    /*

                    display data to logs

                    for (Double i : tempMornArray) Log.i("LOG-MES: Morn Temp: ", i.toString());
                    for (Double i : tempDayArray) Log.i("LOG-MES: Day Temp: ", i.toString());
                    for (Double i : tempNightArray) Log.i("LOG-MES: Night Temp: ", i.toString());
                    for (String i : mainArray) Log.i("LOG-MES: Main: ", i);
                    for (String i : descriptionArray) Log.i("LOG-MES: Description: ", i);

                    for (Double i : pressure) Log.i("LOG-MES: Morn press: ", i.toString());
                    for (Double i : humidity) Log.i("LOG-MES: Day hum: ", i.toString());
                    */

         //--------setting values to layout
                cityNameId.setText(cityName);   //setting city name to layout
                countryCodeId.setText(countryCode); //setting country code to layout

                dayOneWeather.setText(mainArray.get(0)+": "+descriptionArray.get(0)); //setting weather and description
                dayOneMorn.setText("Morning \n"+(double)Math.round((tempMornArray.get(0)-273.15)*10d)/10d+" \u00B0C"); //setting temp, calculating from kelvin to celsius and rounding to one decimal place
                dayOneDay.setText("Day \n"+(double)Math.round((tempDayArray.get(0)-273.15)*10d)/10d+" \u00B0C");
                dayOneNight.setText("Night \n"+(double)Math.round((tempNightArray.get(0)-273.15)*10d)/10d+" \u00B0C");
                    if (mainArray.get(0).matches("Clouds"))   weatherImage1.setImageResource(R.drawable.clouds_icon);
                    if (mainArray.get(0).matches("Rain"))   weatherImage1.setImageResource(R.drawable.rain_icon);
                    if (mainArray.get(0).matches("Snow"))   weatherImage1.setImageResource(R.drawable.snow_icon);

                dayTwoWeather.setText(mainArray.get(1)+": "+descriptionArray.get(1));
                dayTwoMorn.setText("M: "+(double)Math.round((tempMornArray.get(1)-273.15)*10d)/10d+" \u00B0C");
                dayTwoDay.setText("D: "+(double)Math.round((tempDayArray.get(1)-273.15)*10d)/10d+" \u00B0C");
                dayTwoNight.setText("N: "+(double)Math.round((tempNightArray.get(1)-273.15)*10d)/10d+" \u00B0C");
                    if (mainArray.get(1).matches("Clouds"))   weatherImage2.setImageResource(R.drawable.clouds_icon);
                    if (mainArray.get(1).matches("Rain"))   weatherImage2.setImageResource(R.drawable.rain_icon);
                    if (mainArray.get(1).matches("Snow"))   weatherImage2.setImageResource(R.drawable.snow_icon);

                dayThreeWeather.setText(mainArray.get(2)+": "+descriptionArray.get(2));
                dayThreeMorn.setText("M: "+(double)Math.round((tempMornArray.get(2)-273.15)*10d)/10d+" \u00B0C");
                dayThreeDay.setText("D: "+(double)Math.round((tempDayArray.get(2)-273.15)*10d)/10d+" \u00B0C");
                dayThreeNight.setText("N: "+(double)Math.round((tempNightArray.get(2)-273.15)*10d)/10d+" \u00B0C");
                    if (mainArray.get(2).matches("Clouds"))   weatherImage3.setImageResource(R.drawable.clouds_icon);
                    if (mainArray.get(2).matches("Rain"))   weatherImage3.setImageResource(R.drawable.rain_icon);
                    if (mainArray.get(2).matches("Snow"))   weatherImage3.setImageResource(R.drawable.snow_icon);

                dayFourWeather.setText(mainArray.get(3)+": "+descriptionArray.get(3));
                dayFourMorn.setText("M: "+(double)Math.round((tempMornArray.get(3)-273.15)*10d)/10d+" \u00B0C");
                dayFourDay.setText("D: "+(double)Math.round((tempDayArray.get(3)-273.15)*10d)/10d+" \u00B0C");
                dayFourNight.setText("N: "+(double)Math.round((tempNightArray.get(3)-273.15)*10d)/10d+" \u00B0C");
                    if (mainArray.get(3).matches("Clouds"))   weatherImage4.setImageResource(R.drawable.clouds_icon);
                    if (mainArray.get(3).matches("Rain"))   weatherImage4.setImageResource(R.drawable.rain_icon);
                    if (mainArray.get(3).matches("Snow"))   weatherImage4.setImageResource(R.drawable.snow_icon);

                dayFiveWeather.setText(mainArray.get(4)+": "+descriptionArray.get(4));
                dayFiveMorn.setText("M: "+(double)Math.round((tempMornArray.get(4)-273.15)*10d)/10d+" \u00B0C");
                dayFiveDay.setText("D: "+(double)Math.round((tempDayArray.get(4)-273.15)*10d)/10d+" \u00B0C");
                dayFiveNight.setText("N: "+(double)Math.round((tempNightArray.get(4)-273.15)*10d)/10d+" \u00B0C");
                    if (mainArray.get(4).matches("Clouds"))   weatherImage5.setImageResource(R.drawable.clouds_icon);
                    if (mainArray.get(4).matches("Rain"))   weatherImage5.setImageResource(R.drawable.rain_icon);
                    if (mainArray.get(4).matches("Snow"))   weatherImage5.setImageResource(R.drawable.snow_icon);

                daySixWeather.setText(mainArray.get(5)+": "+descriptionArray.get(5));
                daySixMorn.setText("M: "+(double)Math.round((tempMornArray.get(5)-273.15)*10d)/10d+" \u00B0C");
                daySixDay.setText("D: "+(double)Math.round((tempDayArray.get(5)-273.15)*10d)/10d+" \u00B0C");
                daySixNight.setText("N: "+(double)Math.round((tempNightArray.get(1)-273.15)*10d)/10d+" \u00B0C");
                    if (mainArray.get(5).matches("Clouds"))   weatherImage6.setImageResource(R.drawable.clouds_icon);
                    if (mainArray.get(5).matches("Rain"))   weatherImage6.setImageResource(R.drawable.rain_icon);
                    if (mainArray.get(5).matches("Snow"))   weatherImage6.setImageResource(R.drawable.snow_icon);

                daySevenWeather.setText(mainArray.get(6)+": "+descriptionArray.get(6));
                daySevenMorn.setText("M: "+(double)Math.round((tempMornArray.get(6)-273.15)*10d)/10d+" \u00B0C");
                daySevenDay.setText("D: "+(double)Math.round((tempDayArray.get(6)-273.15)*10d)/10d+" \u00B0C");
                daySevenNight.setText("N: "+(double)Math.round((tempNightArray.get(6)-273.15)*10d)/10d+" \u00B0C");
                    if (mainArray.get(6).matches("Clouds"))   weatherImage7.setImageResource(R.drawable.clouds_icon);
                    if (mainArray.get(6).matches("Rain"))   weatherImage7.setImageResource(R.drawable.rain_icon);
                    if (mainArray.get(6).matches("Snow"))   weatherImage7.setImageResource(R.drawable.snow_icon);

                //setting visibility of  layouts and other elements

                //loading layout
                loadingLayout.setVisibility(View.GONE);

                //topbar
                topBar.setVisibility(View.VISIBLE);
                topBar.animate().translationY(0).setDuration(1000);

                //weather layout
                weatherLayout.setVisibility(View.VISIBLE);
                weatherLineOneLayout.animate().translationX(0).setDuration(2000);
                weatherLineTwoLayout.animate().translationX(0).setDuration(2400);
                weatherLineThreeLayout.animate().translationX(0).setDuration(2800);
                weatherLineFourLayout.animate().translationX(0).setDuration(3400);
                weatherLineFiveLayout.animate().translationX(0).setDuration(3800);
                weatherLineSixLayout.animate().translationX(0).setDuration(4200);
                weatherLineSevenLayout.animate().translationX(0).setDuration(4600);



            } catch (JSONException e) {
                e.printStackTrace();
                //Log.i("LOG-MES-Error: ", "JSON data error");
                loadingText.setText("Data loading error!");
                loadingCircle.setVisibility(View.INVISIBLE);

            }

        }

    }
    /**
     * Metoda przenosząca do viewpager 1
     */
    public void goToPager1(View view){

        if (mainArray!=null) {
            Intent i = new Intent(getApplicationContext(), WeatherPagerActivity.class);
            i.putExtra("fragment", "fragment1");
            startActivity(i);
        }
    }
    /**
     * Metoda przenosząca do viewpager 2
     */
    public void goToPager2(View view){
        if (mainArray!=null) {
            Intent i = new Intent(getApplicationContext(), WeatherPagerActivity.class);
            i.putExtra("fragment", "fragment2");
            startActivity(i);
        }
    }
    /**
     * Metoda przenosząca do viewpager 3
     */
    public void goToPager3(View view){
        if (mainArray!=null) {
            Intent i = new Intent(getApplicationContext(), WeatherPagerActivity.class);
            i.putExtra("fragment", "fragment3");
            startActivity(i);
        }
    }
    /**
     * Metoda przenosząca do viewpager 4
     */
    public void goToPager4(View view){
        if (mainArray!=null) {
            Intent i = new Intent(getApplicationContext(), WeatherPagerActivity.class);
            i.putExtra("fragment", "fragment4");
            startActivity(i);
        }
    }
    /**
     * Metoda przenosząca do viewpager 5
     */
    public void goToPager5(View view){
        if (mainArray!=null) {
            Intent i = new Intent(getApplicationContext(), WeatherPagerActivity.class);
            i.putExtra("fragment", "fragment5");
            startActivity(i);
        }
    }
    /**
     * Metoda przenosząca do viewpager 6
     */
    public void goToPager6(View view){
        if (mainArray!=null) {
            Intent i = new Intent(getApplicationContext(), WeatherPagerActivity.class);
            i.putExtra("fragment", "fragment6");
            startActivity(i);
        }
    }
    /**
     * Metoda przenosząca do viewpager 7
     */
    public void goToPager7(View view){
        if (mainArray!=null) {
            Intent i = new Intent(getApplicationContext(), WeatherPagerActivity.class);
            i.putExtra("fragment", "fragment7");
            startActivity(i);
        }
    }

}
