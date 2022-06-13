package com.kudiukin.cityGame.service;

import com.kudiukin.cityGame.domain.City;
import com.kudiukin.cityGame.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CityService {

    private final CityRepository cityRepository;

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

    private void markCityAsNotPlayed(){
        List<City> cities = cityRepository.findAll();
        for(City city : cities) {
            city.setPlayed(false);
        }
        cityRepository.saveAll(cities);
    }

    public City getFirstCity(){
        markCityAsNotPlayed();

        List<City> allCities = cityRepository.findAll();

        City firstCity = null;
        if (!allCities.isEmpty()) {
            firstCity = allCities.get(random.nextInt(allCities.size()));
            markCityPlayed(firstCity);
        }

        return firstCity;
    }

    public City getNextCity(char firstLetter){
        List<City> suitableCities = cityRepository.findByNameStartsAndNotPlayed(String.valueOf(firstLetter));

        City nextCity = null;
        if (!suitableCities.isEmpty()) {
            nextCity = suitableCities.get(random.nextInt(suitableCities.size()));
            markCityPlayed(nextCity);
        }

        return nextCity;
    }

    public static boolean isWrongFirstLetter(String cityName, char givenLetter){
        return cityName.charAt(0) != givenLetter;
    }

}
