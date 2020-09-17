package com.example.gshare.ModelClasses.Location;

/**
 * the location class that calculates locations and distances
 *
 * @version  23.12.2019
 */
public class LocationG {
    private double latitude;
    private double longitude;

    /**
     * Constructor of the locationG class
     */
    public LocationG(){
        latitude = 2;//MainActivity.lat;
        longitude = 2;//MainActivity.lon;
    }

    /**
     * gets latitude to user.
     * @return latitude the latitude.
     */
    public double getLatitude(){
        return latitude;
    }

    /**
     * gets the longtitude to user.
     * @return longtitude the longtitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * sets the latitude.
     * @param latitude thw latitude.
     */
    public void setLatitude( double latitude ) {
        this.latitude = latitude;
    }

    /**
     * sets the longtitude.
     * @param longitude the longtitude
     */
    public void setLongitude( double longitude ) {
        this.longitude = longitude;
    }

    /**
     * takes the distance form the location given in parameter
     * @param location the location to find the distance
     * @return distance the distance.
     */
    public double distanceFrom(LocationG location ){//NOT SURE BUT I GUESS IT WORKS
        return Math.abs( distance( latitude, longitude, location.getLatitude() , location.getLongitude(), 'K' ) );
    }
    public boolean isInRange(){ return true; }

    /*
    https://dzone.com/articles/distance-calculation-using-3
     */

    /**
     * method for finding the distance
     * @param lat1 first latitude
     * @param lon1 second latitude
     * @param lat2 first longtitude
     * @param lon2 second longtitude
     * @param unit the unit.
     * @return dist the distance.
     */
    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /**
     * to string method a string representation
     * @return string representation
     */
    public String toString(){
        return latitude + " " + longitude;
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/

    /**
     * deg2rad method that converts degree to radius
     * @param deg degree
     * @return the radius conversion
     */
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/

    /**
     * rad2deg method that converts radius to degree
     * @param rad radius
     * @return the degree conversion.
     */
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}