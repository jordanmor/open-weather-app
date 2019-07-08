package com.tts.openWeatherApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
		
    @Value("${api_key}")
    private String apiKey;
    
	@Autowired
	ZipCodesRepository zipCodesRepository;
    
    public Response getForecast(ZipCode zipCode) {
        String url = "http://api.openweathermap.org/data/2.5/weather?zip=" + 
            zipCode.getZip() + "&units=imperial&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        try {
        	zipCodesRepository.save(zipCode);
            return restTemplate.getForObject(url, Response.class);
        }
        catch (HttpClientErrorException ex) {
            Response response = new Response();
            response.setName("error");
            return response;
        }
    }
    
}
