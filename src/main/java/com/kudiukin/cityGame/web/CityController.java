package com.kudiukin.cityGame.web;

import com.kudiukin.cityGame.domain.City;
import com.kudiukin.cityGame.service.CityService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = "/begin")
    public String begin(Model model) {
        City city = cityService.getFirstCity();

        if (city == null) {
            model.addAttribute("message", "Извините, не могу найти подходящий город :(");
            return "end";
        } else {
            model.addAttribute("firstCityName", city.getName());
            return "begin";
        }
    }

    @GetMapping(value = "/next")
    public String next(Model model, @RequestParam String userCityName, @RequestParam String serverCityNamePrevious) {
        userCityName = userCityName.toLowerCase();
        char serverCityLastLetter = serverCityNamePrevious.charAt(serverCityNamePrevious.length() - 1);

        if (cityService.isWrongFirstLetter(userCityName, serverCityLastLetter)) {
            model.addAttribute("error", "Название города должно начинаться на букву "
                    + serverCityLastLetter + ". Попробуйте снова.");
            model.addAttribute("serverCityName", serverCityNamePrevious);
        } else {
            City userCity = cityService.searchCityByName(userCityName);

            if (userCity == null) {
                model.addAttribute("error", "Город " + userCityName + " неизвестен. Пожалуйста, назовите другой город.");
                model.addAttribute("serverCityName", serverCityNamePrevious);
            } else {
                if (userCity.getPlayed()) {
                    model.addAttribute("error", "Город "
                            + userCity.getName() + " уже был. Попробуйте снова.");
                    model.addAttribute("serverCityName", serverCityNamePrevious);
                } else {
                    cityService.markCityPlayed(userCity);

                    char lastLetterUser = userCityName.toLowerCase().charAt(userCityName.length() - 1);
                    City serverCity = cityService.getNextCity(lastLetterUser);
                    if (serverCity == null) {
                        model.addAttribute("message", "Не могу подобрать город. Вы победили. Поздравляю :)");
                        return "end";
                    }
                    model.addAttribute("serverCityName", serverCity.getName());
                }
            }
        }
        model.addAttribute("userCityNamePrevious", userCityName);
        return "next";
    }

    @PostMapping(value = "/end")
    public String end() {
        return "Спасибо за игру!";
    }
}
