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
<<<<<<< HEAD
=======

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) 
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
}