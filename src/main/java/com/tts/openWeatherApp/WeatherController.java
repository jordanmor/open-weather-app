package com.tts.openWeatherApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WeatherController {
	
	private String url = "https://images.unsplash.com/photo-1417008914239-59b898b49382?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2564&q=80";

    @Autowired
    private WeatherService weatherService;
        
    @GetMapping("/")
    public String getIndex(ZipCode zipCode, Model model) {
    	model.addAttribute("zipCodes", weatherService.getLatestZipCodes(zipCode));
    	model.addAttribute("bgImage", url);
        return "index";
    }
    
    @PostMapping("/")
    public String postIndex(ZipCode zipCode, Model model) {
        Response data = weatherService.getForecast(zipCode);
        model.addAttribute("data", data);
        model.addAttribute("zipCodes", weatherService.getLatestZipCodes(zipCode));
        model.addAttribute("bgImage", weatherService.getBgImage(data));
        return "index";
    }
    
    @GetMapping("/{zip}")
    public String getZip(ZipCode zipCode, Model model, @PathVariable String zip) {
    	zipCode.setZip(zip);
        Response data = weatherService.getForecast(zipCode);
        model.addAttribute("data", data);
        model.addAttribute("zipCodes", weatherService.getLatestZipCodes(zipCode));
        model.addAttribute("bgImage", weatherService.getBgImage(data));
        return "index";
    }
}
