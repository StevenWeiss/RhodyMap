package com.example.rhodymap;

/**
 * Building is location on the map from the user's schedule and/or
 * events
 */
public class Building extends Point
{
	/**
	 * The name of the building
	 */
	private String name;
	
	// TODO more data members.
	
	/**
	 * Default Constructor
	 */
	public Building()
	{
		this("", 0, 0);
	}
	
	/**
	 * Constructor with parameters
	 * 
	 * @param name		the name of the building
	 * @param latitude	the latitude of the building
	 * @param longitude	the longitude of the building
	 */
	public Building(String name, double latitude, double longitude)
	{
		super(latitude, longitude);
		this.name = name;
	}

	/**
	 * Returns the name of the building
	 * 
	 * @return	name	the name of the building
	 */
	public String getName()
	{
		return name;
	}
}
