package com.example.rhodymap;

import com.google.android.gms.maps.model.LatLng;

/**
 *	Point is a location on the map
 */
public class Point
{
	/**
	 * The latitude of the point
	 */
	private double latitude;
	
	/**
	 * The longitude of the point
	 */
	private double longitude;
	
	/**
	 * Default constructor
	 */
	public Point()
	{
		this(0, 0);
	}
	
	/**
	 * Constructor with parameters
	 * 
	 * @param latitude	the latitude
	 * @param longitude	the longitude
	 */
	public Point(double latitude, double longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	/**
	 * Sets the latitude of a point
	 * @param lat	latitude
	 */
	public void setLat(Double lat)
	{
		latitude = lat;
	}
	
	/**
	 * Sets the longitude of a point
	 * @param lng	longitude
	 */
	public void setLong(Double lng)
	{
		longitude = lng;
	}
	
	/**
	 * Returns the latitude
	 * @return	latitude	the latitude
	 */
	public double getLatitude()
	{
		return latitude;
	}
	
	/**
	 * Returns the longitude
	 * @return longitude	the longitude
	 */
	public double getLongitude()
	{
		return longitude;
	}
	
	/**
	 * Returns a LatLng of a point
	 * @return	latlng
	 */
	public LatLng getPosition()
	{
		return new LatLng(latitude, longitude);
	}

}
