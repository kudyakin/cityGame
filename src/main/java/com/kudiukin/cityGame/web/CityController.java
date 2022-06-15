package com.kudiukin.cityGame.web;

import com.kudiukin.cityGame.domain.City;
import com.kudiukin.cityGame.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = "/begin")
    public String begin(Model model) {
        City city = cityService.getFirstCity();

        if (city == null) {
            model.addAttribute("message", "Извините, не могу найти подходящий город.");
            return "end";
        } else {
            model.addAttribute("firstCityName", cityService.readableView(city.getName()));
            return "begin";
        }
    }

    @GetMapping(value = "/next")
    public String next(Model model, @RequestParam String userCityName, @RequestParam String serverCityNamePrevious) {
        userCityName = userCityName.toLowerCase();
        char serverCityLastLetter = serverCityNamePrevious.charAt(serverCityNamePrevious.length() - 1);
        if (cityService.checkFirstLetter(userCityName, serverCityLastLetter)) {
            model.addAttribute("error", "Название города должно начинаться на букву "
                    + serverCityLastLetter + ". Попробуйте снова.");
            model.addAttribute("serverCityName", cityService.readableView(serverCityNamePrevious));
        } else {
            City userCity = cityService.searchCityByName(userCityName);
            if (userCity == null) {
                model.addAttribute("error", "Город " + cityService.readableView(userCityName) + " неизвестен. Пожалуйста, назовите другой город.");
                model.addAttribute("serverCityName", serverCityNamePrevious);
            } else {
                if (userCity.getPlayed()) {
                    model.addAttribute("error", "Город "
                            + cityService.readableView(userCity.getName()) + " уже был. Попробуйте другой.");
                    model.addAttribute("serverCityName", cityService.readableView(serverCityNamePrevious));
                } else {
                    City serverCity = cityService.getNextCity(userCityName);
                    if (serverCity == null) {
                        model.addAttribute("message", "Не могу подобрать город. Вы победили!");
                        return "end";
                    }
                    model.addAttribute("serverCityName", cityService.readableView(serverCity.getName()));
                }
            }
        }
        model.addAttribute("userCityNamePrevious", cityService.readableView(userCityName));
        return "next";
    }

    @PostMapping(value = "/end")
    public String end(Model model) {
        model.addAttribute("message", "Спасибо за игру");
        return "end";
    }
}
