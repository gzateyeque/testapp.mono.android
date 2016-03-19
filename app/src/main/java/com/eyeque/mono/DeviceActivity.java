package com.eyeque.mono;
/**
 * Created by georgez on 2/4/16.
 */

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import android.content.Intent;

public class DeviceActivity extends Activity {
    // List of array string which will serve as list items
    ArrayList<String> listItems = new ArrayList<String>();

    // Defining a string adapter whcih will handle the data of the ListView
    ArrayAdapter<String> adapter;

    // Record the number of clicks
    int clickCounter = 0;
    String[] items;

    // Tag for log message
    private static final String TAG = DeviceActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_device);

        final ListView deviceListView = (ListView) this.findViewById(R.id.deviceListView);
        listItems.add("#1");
        listItems.add("#2");
        listItems.add("#3");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        deviceListView.setAdapter(adapter);

        deviceListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView) view).getText().toString();
                // Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                Intent result = new Intent();
                result.putExtra("index", new Integer(position).toString());
                result.putExtra("result", item);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    // Method to handle the dynamic insertion
    public void addItems(View v) {
        listItems.add("Clicked : " + clickCounter++);
        adapter.notifyDataSetChanged();
    }
}