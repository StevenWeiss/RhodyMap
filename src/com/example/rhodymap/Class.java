package com.example.rhodymap;

import com.google.android.gms.maps.model.LatLng;

public class Class extends Point
{

    private Building building;
    
    // TODO parse this string.
    private String meetingTimes;

    private String name;
    
    public Class(String name, String meetingTimes, Building building)
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

    @Override
    public LatLng toLatLng()
    {
        return building.toLatLng();
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
