package com.example.rhodymap;

import com.google.android.gms.maps.model.LatLng;

public class Event extends Point
{
 private Building building;
    
    // TODO parse this string.
    private String meetingTimes;

    private String name;
    
    public Event(String name, String meetingTimes, Building building)
    {
        this.name = name;
        this.meetingTimes = meetingTimes;
        this.building = building;
    }
        
    @Override
    public double getLatitude()
    {
        return building.getLatitude();
    }

    @Override
    public double getLongitude()
    {
        return building.getLongitude();
    }

    public LatLng getCoordinates()
    {
        return building.getPosition();
    }

    public Building getBuilding()
    {
        return building;
    }

    public String getName()
    {
        return name;
    }   
    
    public String getMeetingTimes()
    {
        return meetingTimes;
    }  
    
}
