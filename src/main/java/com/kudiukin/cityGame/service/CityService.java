package com.kudiukin.cityGame.service;

import com.kudiukin.cityGame.domain.City;

public interface CityService {

    City searchCityByName(String cityName);

    void markCityPlayed(City city);

    void markCityAsNotPlayed();

    City getFirstCity();

    City getNextCity(char firstLetter);

    boolean isWrongFirstLetter(String cityName, char givenLetter);

}
