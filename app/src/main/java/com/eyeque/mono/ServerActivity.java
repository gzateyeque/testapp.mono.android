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

public class ServerActivity extends Activity {
    // List of array string which will serve as list items
    ArrayList<String> listItems = new ArrayList<String>();

    // Defining a string adapter whcih will handle the data of the ListView
    ArrayAdapter<String> adapter;

    int clickCounter = 0;  // record how many times the button has been clicked
    String[] items;


    private static final String TAG = ServerActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_server);

        final ListView serverListView = (ListView) this.findViewById(R.id.serverListView);
        listItems.add("Internal");
        listItems.add("Amazon Cloud");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        serverListView.setAdapter(adapter);

        serverListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView) view).getText().toString();
                Intent result = new Intent();
                result.putExtra("index", new Integer(position).toString());
                result.putExtra("result", item);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    // Method to handle dynamic insertion
    public void addItems(View v) {
        listItems.add("Clicked : " + clickCounter++);
        adapter.notifyDataSetChanged();
    }
}
