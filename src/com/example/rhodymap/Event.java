package com.example.rhodymap;

import com.google.android.gms.maps.model.LatLng;

/**
 *	Event is the upcoming events from URI's Event
 *	Schedule
 */
public class Event extends Point
{
	/**
	 * The building of the event
	 */
	private Building building;
    
    /**
     * The meeting time of the events
     */
	// TODO parse this string.
    private String meetingTimes;

    /**
     * The name of the event
     */
    private String name;
    
    /**
     * Constructor with parameters
     * 
     * @param name	the name of the event
     * @param meetingTimes	the meeting times of the event
     * @param building		the location of the event
     */
    public Event(String name, String meetingTimes, Building building)
    {
        this.name = name;
        this.meetingTimes = meetingTimes;
        this.building = building;
    }
        
    /**
     * Returns the latitude of the event
     *@return	the latitude of the building
     */
    @Override
    public double getLatitude()
    {
        return building.getLatitude();
    }

    /**
     * Returns the longitude of the event
     * @return	the longitude of the building
     */
    @Override
    public double getLongitude()
    {
        return building.getLongitude();
    }

    /**
     * Return a LatLng of the event
     * @return	the LatLng of the building
     */
    public LatLng getCoordinates()
    {
        return building.getPosition();
    }

    /**
     * Returns the building of the event
     * @return building 	the building of the event
     */
    public Building getBuilding()
    {
        return building;
    }

    /**
     * Returns the name of the event
     * @return name		the name of the event
     */
    public String getName()
    {
        return name;
    }   
    
    /**
     * Returns the meeting times of the event
     * @return	meetingTimes	the meeting times of the event
     */
    public String getMeetingTimes()
    {
        return meetingTimes;
    }  
    
}
