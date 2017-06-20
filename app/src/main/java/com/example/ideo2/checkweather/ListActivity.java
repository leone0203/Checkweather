package com.example.ideo2.checkweather;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ListActivity extends AppCompatActivity {

    static ArrayList<String> places = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    Set<String> placesSet;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final EditText userPlace = (EditText) findViewById(R.id.editUserPlace);
        Button addButton = (Button) findViewById(R.id.addButton);

        ListView listView = (ListView) findViewById(R.id.listView);
        final SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.ideo2.checkweather", Context.MODE_PRIVATE);
        placesSet = sharedPreferences.getStringSet("places", null);

        places.clear();
        if (placesSet!=null) {
            places.addAll(placesSet);
        }else {
            placesSet = new HashSet<String>();
            placesSet.addAll(places);
            sharedPreferences.edit().putStringSet("places", placesSet).apply();
        }

        arrayAdapter = new ArrayAdapter(this, R.layout.custom_list_layout, places);
        listView.setAdapter(arrayAdapter);


        //on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), WeatherActivity.class);
                i.putExtra("source", "ListActivity");
                i.putExtra("placeId", position);
                startActivity(i);

            }
        });

        //on long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(ListActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete current item.")
                        .setMessage("Do you want to delete this place?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                places.remove(position);

                               SharedPreferences sharedPreferences = ListActivity.this.getSharedPreferences("com.example.ideo2.checkweather", Context.MODE_PRIVATE);
                                if (placesSet == null) {
                                    placesSet = new HashSet<String>();
                                } else {
                                    placesSet.clear();
                                }

                                placesSet.addAll(places);
                                sharedPreferences.edit().remove("places").apply();
                                sharedPreferences.edit().putStringSet("places", placesSet).apply();
                                arrayAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

        //click on add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (userPlace.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(), "Please input desired place.", Toast.LENGTH_SHORT).show();
            }else {
                places.add(userPlace.getText().toString());
                SharedPreferences sharedPreferences = ListActivity.this.getSharedPreferences("com.example.ideo2.checkweather", Context.MODE_PRIVATE);
                if (placesSet == null) {
                    placesSet = new HashSet<String>();
                } else {
                    placesSet.clear();
                }

                placesSet.addAll(places);
                arrayAdapter.notifyDataSetChanged();
                sharedPreferences.edit().remove("places").apply();
                sharedPreferences.edit().putStringSet("places", placesSet).apply();
                userPlace.setText("");

                //code for closing keypad
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(userPlace.getWindowToken(), 0);

            }


            }
        });

    }
}
