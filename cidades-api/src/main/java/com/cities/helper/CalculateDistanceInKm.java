package com.cities.helper;

public class CalculateDistanceInKm {

	public static int calculateDistanceInKm(double firLongitude, double firLatitude, double secLongitude, double secLatitude) {
		
		final int earthRadius = 6371;
		
	    double lngDistance = Math.toRadians(firLongitude - secLongitude);
	    double latDistance = Math.toRadians(firLatitude - secLatitude);

	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	  	      + Math.cos(Math.toRadians(firLatitude)) * Math.cos(Math.toRadians(secLatitude))
	  	      * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
	    
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    
	    return (int) (Math.round(earthRadius * c));
		
	}
	
}
