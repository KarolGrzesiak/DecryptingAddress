package Projekt;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

import java.util.List;

import static Projekt.ProcessData.LOCATION;

public class AddressAPI {
    static String API_KEY = "";
    public static Place checkAddress(){
        GooglePlaces client = new GooglePlaces(API_KEY);
        List<Place> placeList = client.getPlacesByQuery(LOCATION);
        System.out.println(LOCATION);
        Place foundPlace = placeList.get(0).getDetails();
        return foundPlace;
    }
}
