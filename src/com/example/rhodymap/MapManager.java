package com.example.rhodymap;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

public class MapManager extends FragmentActivity implements OnMapClickListener, OnInfoWindowClickListener
{

    private GoogleMap gMap;
    private ClusterManager<Item> mManager;

    /**
     * Creates and setup the activity
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        setUpMapIfNeeded();
        setUpClusterer();

        gMap.setMyLocationEnabled(true);
        gMap.setOnMapClickListener(this);
        gMap.setOnInfoWindowClickListener(this);


        Intent intent = this.getIntent();

        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");

        if (username != null && password != null)
        {
            ServerMediator.getClasses(username, password, this);
        }
        
        ServerMediator.getEvents(this);
    }

    /**
     * Sets up the map if there isn't one already
     */
    private void setUpMapIfNeeded() 
    {
    	if (gMap == null) 
        {
            gMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            if (gMap != null) 
            {
                defaultCameraPosition();
            }
        }
    }
    
    /**
     * Sets up the cluster manager
     */
    private void setUpClusterer()
    {    	
    	mManager = new ClusterManager<Item>(this, gMap);
    	mManager.setRenderer(new CustomClusterRenderer(this,gMap, mManager));
    	gMap.setOnCameraChangeListener(mManager);
        gMap.setOnMarkerClickListener(mManager);

    }


    /**
     * Creates the options menu
     */
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    /**
     * Associates actions with the press of a menu
     */
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

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

        return super.onOptionsItemSelected(item);
    }


    /**
     * Adds a marker when you click on the map
     */
    public void onMapClick(LatLng point) 
    {
    	Item myItem = new Item("Marker", "info window", 
    			BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
    	
    	myItem.setLat(point.latitude);
    	myItem.setLong(point.longitude);
    	
    	mManager.addItem(myItem);
    	mManager.cluster();
    }

    /**
     * Starts the camera at URI
     */
    public void defaultCameraPosition()
    {
        LatLng uri = new LatLng(41.48639866968497, -71.52702569961548);

        CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(uri)    	  
        .zoom(17)                   
        .bearing(90)               
        .tilt(30)                   
        .build();                 

        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * Adds the classes in the schedule on the map.
     */
    public void addClasses(List<Class> schedule)
    {      

        for (Class course : schedule)
        {
            Log.v("MapManager", course.getName());
            
            //Adds information about the marker
            Item newClass = new Item(course.getName(), course.getMeetingTimes(),
            		BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            
            newClass.setLat(course.getLatitude());
            newClass.setLong(course.getLongitude());
            
            //Adds it to the cluster manager and re-clusters
            mManager.addItem(newClass);
            mManager.cluster();
        }

    }
    
    /**
     * Adds the events in the schedule on the map.
     */
    public void addEvents(List<Event> events)
    {      

        for (Event event : events)
        {
            Log.v("MapManager", event.getName());
            
          //Adds information about the marker
            Item newEvent = new Item(event.getName(), event.getMeetingTimes(),
            		BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            
            newEvent.setLat(event.getLatitude());
            newEvent.setLong(event.getLongitude());
            
          //Adds it to the cluster manager and re-clusters
            mManager.addItem(newEvent);
            mManager.cluster();
        }
        

    }

	@Override
	public void onInfoWindowClick(Marker arg0) 
	{
		arg0.remove();
	}

}