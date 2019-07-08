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
    
	@Autowired
	ZipCodesRepository zipCodesRepository;
    
    @GetMapping(value="/")
    public String getIndex(ZipCode zipCode) {
        return "index";
    }
    
    @PostMapping
    public String postIndex(ZipCode zipCode, Model model) {
    	zipCodesRepository.save(zipCode);
        Response data = weatherService.getForecast(zipCode.getZip());
        model.addAttribute("data", data);
        return "index";
    }
}
