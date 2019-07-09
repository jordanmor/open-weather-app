package com.tts.openWeatherApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;
        
    @GetMapping("/")
    public String getIndex(ZipCode zipCode, Model model) {
    	model.addAttribute("zipCodes", weatherService.getLatestZipCodes(zipCode));
        return "index";
    }
    
    @PostMapping("/")
    public String postIndex(ZipCode zipCode, Model model) {
        Response data = weatherService.getForecast(zipCode);
        model.addAttribute("data", data);
        model.addAttribute("zipCodes", weatherService.getLatestZipCodes(zipCode));
        return "index";
    }
}
