package com.example.rhodymap;

import java.util.List;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

public class MapManager extends FragmentActivity implements OnMapClickListener, OnInfoWindowClickListener
{

    private GoogleMap gMap;
    private ClusterManager<Item> mManager;

    //used for changing the map view
    private String mapTypeNormal = "MAP_TYPE_NORMAL";
    private String mapTypeSatellite = "MAP_TYPE_SATELLITE";
    private String currentMapType = mapTypeNormal;
    //used for toggling markers
    private boolean showClasses = true;
    private boolean showEvents = true;

    private String[] drawerListViewItems;
    private DrawerLayout drawerLayout;
    
    private ListView drawerListView;
    @SuppressWarnings("deprecation")
    // TODO apparently this class is deprecated.
    private ActionBarDrawerToggle actionBarDrawerToggle;

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

        setupNavDrawer();

        Intent intent = this.getIntent();

        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");

        if (username != null && password != null)
        {
            ServerMediator.getClasses(username, password, this);
        }

        ServerMediator.getEvents(this);
    }

    private void setupNavDrawer()
    {
        // get list items from strings.xml
        drawerListViewItems = getResources().getStringArray(R.array.items);

        // get ListView defined in activity_main.xml
        drawerListView = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerListViewItems));

        // App Icon 
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,   /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                );

        // Set actionBarDrawerToggle as the DrawerListener
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true); 

        // just styling option add shadow the right edge of the drawer
        //drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
         actionBarDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
  
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Toast.makeText(MapManager.this, ((TextView)view).getText(), Toast.LENGTH_LONG).show();
            drawerLayout.closeDrawer(drawerListView);
 
        }
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

        // Inflate the search button; adds search button to the action bar.
        getMenuInflater().inflate(R.menu.search_action, menu);

        // associate searchable config with the searchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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
        
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch(id)
        {
        case R.id.menu_toggleicons:
            return true;
        case R.id.menu_changemap:
            if(currentMapType == mapTypeNormal) //sets map to normal (no satellite)
            {
                currentMapType = mapTypeSatellite;
                gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
            else //sets map to satellite
            {
                currentMapType = mapTypeNormal;
                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
            return true;
        case R.id.menu_viewschedule:
            return true;
        case R.id.menu_settings:
            return true;
        case R.id.menu_logout:
            return true;
        case R.id.action_search:
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