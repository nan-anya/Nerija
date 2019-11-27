package com.example.nerija;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class LocationManager
{
    public coord locToCoord(String loc, Context context)
    {
        List<Address> list = null;

        final Geocoder geocoder = new Geocoder(context);

        try
        {
            list = geocoder.getFromLocationName(loc, 10);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
            return null;
        }

        if (list != null)
        {
            if (list.size() == 0)
            {
                return null;
            }
            else
            {
                return new coord(list.get(0).getLatitude(), list.get(0).getLongitude());
            }
        }
        return null;
    }

    private static double distance(coord coord1, coord coord2)
    {

        double theta = coord1.longtitude - coord2.longtitude;

        double dist = Math.sin(deg2rad(coord1.latitude)) * Math.sin(deg2rad(coord2.latitude)) + Math.cos(deg2rad(coord1.latitude)) * Math.cos(deg2rad(coord2.latitude)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1609.344;

        return (dist);
    }

    private static double deg2rad(double deg)
    {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad)
    {
        return (rad * 180 / Math.PI);
    }

}

class coord
{
    double latitude;
    double longtitude;

    public coord(double la, double lo)
    {
        this.latitude = la;
        this.longtitude =lo;
    }

    public String toString()
    {
        return "Latitude : " + latitude + " Longtitude : " + longtitude;
    }
}
