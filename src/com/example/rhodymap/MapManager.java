package com.example.rhodymap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapManager extends FragmentActivity implements OnMapClickListener
{
	
	private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        setUpMapIfNeeded();
        
        gMap.setMyLocationEnabled(true);
        gMap.setOnMapClickListener(this);
 
    }
    
    private void setUpMapIfNeeded() 
    {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gMap == null) 
        {
            gMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map))
                                .getMap();
            // Check if we were successful in obtaining the map.
            if (gMap != null) 
            {
                // The Map is verified. It is now safe to manipulate the map.
            	defaultCameraPosition();

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
		Marker marker = gMap.addMarker(new MarkerOptions()
				.position(point)
				.title("Marker title")
				.snippet("The info window works!")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
	}
    
    public void defaultCameraPosition()
    {
        LatLng uri = new LatLng(41.48639866968497, -71.52702569961548);

        CameraPosition cameraPosition = new CameraPosition.Builder()
            	.target(uri)    	  // Sets the center of the map to Mountain View
            	.zoom(17)                   // Sets the zoom
            	.bearing(90)                // Sets the orientation of the camera to north
            	.tilt(30)                   // Sets the tilt of the camera to 30 degrees
            	.build();                   // Creates a CameraPosition from the builder

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    
}
