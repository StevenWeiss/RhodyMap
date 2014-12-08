/**
 * By Steven Weiss
 * 
 * This will search a query and return the results
 */

package com.example.rhodymap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SearchResultsActivity extends Activity
{
	private TextView txtQuery;
	
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
	 * Handle the intent data
	 */
	private void handleIntent(Intent intent)
	{
		if(Intent.ACTION_SEARCH.equals(intent.getAction()))
		{
			String query = intent.getStringExtra(SearchManager.QUERY);
			
			/**
			 * use query to display SQLite search results
			 */
			
			txtQuery.setText("Search Query: " + query);
		}
		
	}
	
	
	
	
	
}
