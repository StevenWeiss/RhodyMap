package com.example.rhodymap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.JsonReader;
import android.util.Log;


public class DataManager extends SQLiteOpenHelper
{
	
	public static final String TABLE_BUILDINGS = "buildings";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_PHOTO_URL = "photo_url";
	public static final String COLUMN_OUTLINE = "outline";
	
	public static final String DATABASE_NAME = "rhody_map.db";
	public static final int DATABASE_VERSION = 1;
		
	/**
	 * Reference to a context to open files.
	 */
	private Context context;
	
	public DataManager(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		Log.v("DataManager", "Called constructor");
		
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		Log.v("DataManager", "Creating database");
		
		String createDatabase = "create table "
			      + TABLE_BUILDINGS + "(" 
			      + COLUMN_ID + " integer primary key, "
			      + COLUMN_NAME + " text not null, "
			      + COLUMN_LATITUDE + " real, "
			      + COLUMN_LONGITUDE + " real, "
			      + COLUMN_ADDRESS + " text, "
			      + COLUMN_PHOTO_URL + " text, "
			      + COLUMN_OUTLINE + " text);";
		
		db.execSQL(createDatabase);
		loadBuildingsTable(db);
	}
	
	/**
	 * Fills the rows of the Buildings table.
	 * 
	 * @param db
	 */
	private void loadBuildingsTable(SQLiteDatabase db)
	{
		// To open files.
		AssetManager am = context.getAssets();
		JsonReader jsonReader = null;
		
		try 
		{
			// Opens the buildings.json file.
			InputStream stream = am.open("buildings.json");
			InputStreamReader streamReader = new InputStreamReader(stream);
			jsonReader = new JsonReader(streamReader);

			
			jsonReader.beginArray();
			while(jsonReader.hasNext()) 
			{
				insertBuildingsRow(db, jsonReader);				
			}
			
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		 		
	}
	
	/**
	 * Takes the next object from the jsonReader and inserts its data as a row
	 * in the buildings table
	 * 
	 * @param db
	 * @param jsonReader
	 * @throws IOException 
	 */
	private void insertBuildingsRow(SQLiteDatabase db, JsonReader jsonReader) throws IOException
	{
		jsonReader.beginObject();
		
		// The values of each column of the buildings table for the new row.
		// All are initially set to null.
		String id = "null", name = "null", lat = "null", lng = "null",
				address = "null", photoUrl = "null", outline = "null";
		
		while (jsonReader.hasNext()) 
		{
			switch (jsonReader.nextName())
			{
			case "id": 
				id = jsonReader.nextString();
				break;
			case "name":
				name = jsonReader.nextString();
				break;
			case "lat":
				lat = jsonReader.nextString();
				break;
			case "lng":
				lng = jsonReader.nextString();
				break;
			default: jsonReader.nextString();
			}
		}
		
		jsonReader.endObject();	
		
		String command = "insert into " + TABLE_BUILDINGS + " values ("
				+ id + ", '" + name + "'," + lat + ", " + lng + ", " + address 
				+ ", " + photoUrl + ", " + outline + ");";
		
		db.execSQL(command);
	}
	
	/**
	 * Returns a list of buildings that matches the given name.
	 * 
	 * TODO Sort results?
	 * @param name
	 * @return
	 */
	public List<Building> getBuildings(String name)
	{

		Log.v("DataManager", "getBuildings(" + name + ")");
		List<Building> results = new ArrayList<Building>();
		
		SQLiteDatabase database = this.getReadableDatabase();
		
		String[] columns = {COLUMN_NAME, COLUMN_LATITUDE, COLUMN_LONGITUDE};
		String whereClause = "name like ?";
		String[] whereArg = {"%" + name + "%"};
		
		Cursor cursor = database.query(TABLE_BUILDINGS, 
				columns, whereClause, whereArg, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			String buildingName = cursor.getString(0);
			double latitude = cursor.getDouble(1);
			double longitude = cursor.getDouble(2);
			Log.v("DataManager", buildingName + latitude + "," + longitude);
			results.add(new Building(buildingName, latitude, longitude));
			cursor.moveToNext();
		}
		
		database.close();
		Log.v("DataManager", "There are " + results.size() + " results");
		return results;
	}
	
	/**
	 * TODO when we add more tables or columns
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
	    Log.w(DataManager.class.getName(),
	            "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUILDINGS);
	        onCreate(db);
		
	}
	

}
