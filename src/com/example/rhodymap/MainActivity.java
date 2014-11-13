package com.example.rhodymap;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button submitButton = (Button)this.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
        
        Button skipButton = (Button)this.findViewById(R.id.skipButton);
        skipButton.setOnClickListener(this);
        
        DataManager data = new DataManager(this);
        data.getReadableDatabase(); // Creates the database.
                
        test("ch", data);
        test("tyler", data);
    }
    
    private void test(String query, DataManager data)
    {   
        List<Building> bs = data.getBuildings(query);
        for (Building b : bs)
        {
        	Log.v("MainActivity", b.getName());
        }
    }
    
    public void onClick(View v)
    {
        Intent myIntent = new Intent(this, MapManager.class);
        startActivity(myIntent);
    }
}