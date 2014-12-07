package com.example.rhodymap;

import java.util.ArrayList;
import java.util.Collection;
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
import com.google.android.gms.maps.model.MarkerOptions;

public class MapManager extends FragmentActivity implements OnMapClickListener, OnInfoWindowClickListener, ListView.OnItemClickListener
{

    private GoogleMap gMap;
    private Collection<Marker> mManager = new ArrayList<Marker>();

    //used for changing the map view
    private String mapTypeNormal = "MAP_TYPE_NORMAL";
    private String mapTypeSatellite = "MAP_TYPE_SATELLITE";
    private String currentMapType = mapTypeNormal;
    //used for toggling markers
    private boolean showClasses = true;
    private boolean showEvents = true;
    private boolean toggleIcons = true;

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

    @SuppressWarnings("deprecation")
    private void setupNavDrawer()
    {
        // get list items from strings.xml
        drawerListViewItems = getResources().getStringArray(R.array.items);

        // get ListView defined in activity_main.xml
        drawerListView = (ListView) findViewById(R.id.left_drawer);

        drawerListView.setDividerHeight(10);

        drawerListView.setOnItemClickListener(this);

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

        drawerListView.setOnItemClickListener(this); // See onItemClick();

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
     * Adds a marker when you click on the map
     */
    public void onMapClick(LatLng point) 
    {
        Marker marker = gMap.addMarker(new MarkerOptions()
        .position(point)
        .title("Marker")
        .snippet("Info Window")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        mManager.add(marker);
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

            Marker newClass = gMap.addMarker(new MarkerOptions()
            .position(course.getCoordinates())
            .title(course.getName())
            .snippet(course.getMeetingTimes())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

            mManager.add(newClass);
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

            Marker newEvent = gMap.addMarker(new MarkerOptions()
            .position(event.getCoordinates())
            .title(event.getName())
            .snippet(event.getMeetingTimes())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            mManager.add(newEvent);

        }

    }

    @Override
    public void onInfoWindowClick(Marker arg0) 
    {
        arg0.remove();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id)
    {
        switch(position)
        {
        case 1:
            if(toggleIcons == true)
            {
                for(Marker m : mManager)
                {
                    m.setVisible(false);
                }

                toggleIcons = false;
            }
            else
            {
                for(Marker m : mManager)
                {
                    m.setVisible(true);
                }

                toggleIcons = true;
            }
            break;
        case 2:
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
            break;
        }
        drawerListView.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerListView);
    }

}