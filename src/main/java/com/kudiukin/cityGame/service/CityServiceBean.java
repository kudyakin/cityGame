package com.kudiukin.cityGame.service;

import com.kudiukin.cityGame.domain.City;
import com.kudiukin.cityGame.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CityServiceBean implements CityService{

    private CityRepository cityRepository;

    private static final Random random = new Random();

    public CityServiceBean(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City searchCityByName(String cityName) {
        return cityRepository.findByName(cityName);
    }

    @Override
    public void markCityPlayed(City city){
        if (city != null) {
            city.setPlayed(true);
            cityRepository.save(city);
        }
    }

    @Override
    public void markCityAsNotPlayed(){
        List<City> cities = cityRepository.findAll();
        for(City city : cities) {
            city.setPlayed(false);
        }
        cityRepository.saveAll(cities);
    }

    @Override
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

    @Override
    public City getNextCity(char firstLetter){
        List<City> Cities = cityRepository.findByNameStartsWithAndPlayedFalse(String.valueOf(firstLetter));

        City nextCity = null;
        if (!Cities.isEmpty()) {
            nextCity = Cities.get(random.nextInt(Cities.size()));
            markCityPlayed(nextCity);
        }

        return nextCity;
    }

    @Override
    public boolean isWrongFirstLetter(String cityName, char givenLetter){
        return cityName.charAt(0) != givenLetter;
    }

}
