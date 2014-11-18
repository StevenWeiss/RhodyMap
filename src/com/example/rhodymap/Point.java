package com.example.rhodymap;

import com.google.android.gms.maps.model.LatLng;

/**
 *	Location on the map
 */
public class Point
{
	
	private double latitude;
	private double longitude;
	
	public Point()
	{
		this(0, 0);
	}
	
	public Point(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void setLat(Double lat)
	{
		latitude = lat;
	}
	
	public void setLong(Double lng)
	{
		longitude = lng;
	}
	
	public double getLatitude()
	{
		return latitude;
	}
	
	public double getLongitude()
	{
		return longitude;
	}
	
	public LatLng getPosition()
	{
		return new LatLng(latitude, longitude);
	}

}
