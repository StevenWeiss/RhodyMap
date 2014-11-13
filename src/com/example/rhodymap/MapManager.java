package com.example.rhodymap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
<<<<<<< HEAD
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
=======
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

<<<<<<< HEAD
public class MapManager extends FragmentActivity implements OnMapClickListener, OnInfoWindowClickListener
=======
public class MapManager extends FragmentActivity implements OnMapClickListener
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
{
	
	private GoogleMap gMap;

<<<<<<< HEAD
	/**
	 * Creates and setup the activity
	 */
=======
    @Override
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        setUpMapIfNeeded();
        
        gMap.setMyLocationEnabled(true);
        gMap.setOnMapClickListener(this);
<<<<<<< HEAD
        gMap.setOnInfoWindowClickListener(this);
 
    }
    
    /**
     * Sets up the map if there isn't one already
     */
    private void setUpMapIfNeeded() 
    {
      
=======
 
    }
    
    private void setUpMapIfNeeded() 
    {
        // Do a null check to confirm that we have not already instantiated the map.
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
        if (gMap == null) 
        {
            gMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map))
                                .getMap();
<<<<<<< HEAD
          
            if (gMap != null) 
            {
            	defaultCameraPosition();
=======
            // Check if we were successful in obtaining the map.
            if (gMap != null) 
            {
                // The Map is verified. It is now safe to manipulate the map.
            	defaultCameraPosition();

>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
            }
        }
    }

<<<<<<< HEAD

    /**
     * Creates the options menu
     */
=======
    @Override
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
<<<<<<< HEAD
        
        return true;
    }

    /**
     * Associates actions with the press of a menu
     */
=======
        return true;
    }

    @Override
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
<<<<<<< HEAD
        
        switch(id)
        {
        case R.id.menu_toggleicons:
        	return true;
        case R.id.menu_changemap:
        	return true;
        case R.id.menu_viewschedule:
        	return true;
        case R.id.menu_settings:
        	return true;
        case R.id.menu_logout:
        	return true;
        }
        
=======
        if (id == R.id.action_settings)
        {
            return true;
        }
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
        return super.onOptionsItemSelected(item);
    }
    

<<<<<<< HEAD
    /**
     * Adds a marker when you click on the map
     */
=======
    @Override
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
	public void onMapClick(LatLng point) 
	{
		Marker marker = gMap.addMarker(new MarkerOptions()
				.position(point)
				.title("Marker title")
				.snippet("The info window works!")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		
	}
<<<<<<< HEAD
	
	/**
	 * Removes a marker when you click on its info window
	 */
	public void onInfoWindowClick(Marker arg0)
	{
		arg0.remove();
	}
	
    /**
     * Starts the camera at URI
     */
=======
    
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386
    public void defaultCameraPosition()
    {
        LatLng uri = new LatLng(41.48639866968497, -71.52702569961548);

        CameraPosition cameraPosition = new CameraPosition.Builder()
<<<<<<< HEAD
            	.target(uri)    	  
            	.zoom(17)                   
            	.bearing(90)               
            	.tilt(30)                   
            	.build();                 
=======
            	.target(uri)    	  // Sets the center of the map to Mountain View
            	.zoom(17)                   // Sets the zoom
            	.bearing(90)                // Sets the orientation of the camera to north
            	.tilt(30)                   // Sets the tilt of the camera to 30 degrees
            	.build();                   // Creates a CameraPosition from the builder
>>>>>>> f1015963ccda356d5e39e5664acb3a2346309386

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    
}
