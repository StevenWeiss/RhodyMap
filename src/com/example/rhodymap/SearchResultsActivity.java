package com.example.rhodymap;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This will search a query and return the results
 */

public class SearchResultsActivity extends Activity
{
	//text field
	private TextView txtQuery;
	//search area
	private DataManager database;
	//results
	private List<Building> buildings;
	
	/**
	 * constructor
	 */
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//get action bar
		ActionBar actionBar = getActionBar();
		
		//enable back navigation on action bar icon
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		txtQuery = (TextView) findViewById(R.id.txtQuery);
		
		handleIntent(getIntent());
	}
	
	protected void onNewIntent(Intent intent)
	{
		setIntent(intent);
		handleIntent(intent);
	}
	
	/**
	 * Search function
	 */
	private void handleIntent(Intent intent)
	{
		if(Intent.ACTION_SEARCH.equals(intent.getAction()))
		{
			//get entered query
			String query = intent.getStringExtra(SearchManager.QUERY);
			//get a list of buildings that match query
			buildings = database.getBuildings(query);
			//display results
			if(buildings.contains(query))
			{
				txtQuery.setText(query);
			}
		}
	}
}
