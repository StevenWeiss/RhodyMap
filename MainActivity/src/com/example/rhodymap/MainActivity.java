package com.example.rhodymap;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends Activity implements OnMapClickListener
{
	private GoogleMap gMap;
	private Marker marker;
	private Location location;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setUpMapIfNeeded();
        
        gMap.setMyLocationEnabled(true);
        gMap.setOnMapClickListener(this);
    }
    
    private void setUpMapIfNeeded() 
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gMap == null) 
        {
            gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                                .getMap();
            // Check if we were successful in obtaining the map.
            if (gMap != null) 
            {
                // The Map is verified. It is now safe to manipulate the map.

            }
        }
    }

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
    

	@Override
	public void onMapClick(LatLng point) 
	{
		gMap.addMarker(new MarkerOptions()
	    .position(point)
	    .title("Marker title")
	    .snippet("The info window works!")
	    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
	}
}