package com.example.rhodymap;

/**
 *	Class is the class from the user's schedule
 */
public class Class extends Event
{
    /**
     * Contructor with parameters
     * 
     * @param name		the name of the class
     * @param meetingTimes	the times the classes meet
     * @param building		the location of the class
     */
    public Class(String name, String meetingTimes, Building building)
    {
       super(name, meetingTimes, building);
    }
    
}
