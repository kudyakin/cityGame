package com.kudiukin.cityGame.service;

import com.kudiukin.cityGame.domain.City;
import com.kudiukin.cityGame.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CityService {

    private CityRepository cityRepository;

    private static final Random random = new Random();

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City searchCityByName(String cityName) {
        return cityRepository.findByName(cityName);
    }

    public void markCityPlayed(City city){
        if (city != null) {
            city.setPlayed(true);
            cityRepository.save(city);
        }
    }

    public void markCityAsNotPlayed(){
        List<City> cities = cityRepository.findAll();
        for(City city : cities) {
            city.setPlayed(false);
        }
        cityRepository.saveAll(cities);
    }

    public City getFirstCity(){
        markCityAsNotPlayed();
        List<City> allCities = cityRepository.findAll();
        City firstCity = allCities.get(random.nextInt(allCities.size()));
            markCityPlayed(firstCity);
        return firstCity;
    }

    public City getNextCity(String userCityName){
        List<City> Cities = cityRepository.findAllByNameStartsWithAndPlayedIsFalse (userCityName.charAt(0));
        City nextCity = Cities.get(random.nextInt(Cities.size()));
        markCityPlayed(nextCity);
        return nextCity;
    }

    public boolean checkFirstLetter(String cityName, char givenLetter){
        cityName.toLowerCase();
        char fistLetter = cityName.charAt(0);
        return fistLetter != givenLetter;
    }

    public String readableView(String cityName){
        return cityName.substring(0,1).toUpperCase() + cityName.substring(1).toLowerCase();
    }

}
