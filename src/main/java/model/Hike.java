package model;

/**
 * this class creates a hike object
 *
 * @author Hanchen Liu
 * @version 1.0
 */
public class Hike
{
    //private fields of hike object
    private String location;
    private String date;

    /**
     * this method gets the hike location
     *
     * @return hike location
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * this method sets the hike location
     *
     * @param location hike location
     */
    public void setLocation(String location)
    {
        this.location = location;
    }

    /**
     * this method gets the hike date
     *
     * @return hike date
     */
    public String getDate()
    {
        return date;
    }

    /**
     * this method sets the hike date
     *
     * @param date hike date
     */
    public void setDate(String date)
    {
        this.date = date;
    }
}
