package com.example.rhodymap;

public class Building extends Point
{
	private String name = "";
	
	// TODO more data members.
	
	public Building()
	{
		this("", 0, 0);
	}
	
	public Building(String name, double latitude, double longitude)
	{
		super(latitude, longitude);
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
