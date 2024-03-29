package com.tts.openWeatherApp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
	
	private int limit = 12;
		
    @Value("${api_key}")
    private String apiKey;
    
    @Value("${client_id}")
    private String clientId;
    
	@Autowired
	ZipCodesRepository zipCodesRepository;
    
    public Response getForecast(ZipCode zipCode) {
        String url = "http://api.openweathermap.org/data/2.5/weather?zip=" + 
            zipCode.getZip() + "&units=imperial&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        
        try {
        	// Only save zip code to db if the input is not blank
            if(zipCode.getZip() != "") {
        		zipCodesRepository.save(zipCode);
            }
            return restTemplate.getForObject(url, Response.class);
        }
        catch (HttpClientErrorException ex) {
            Response response = new Response();
            response.setName("error");
            return response;
        }
    }
    
    public List<ZipCode> getLatestZipCodes(ZipCode zipCode) {
    	Pageable pageable = PageRequest.of(0, limit, Sort.by("id").descending());
    	Page<ZipCode> page = zipCodesRepository.findAll(pageable);
    	return page.getContent();
    }
    
    public String getWeatherDescription(Response data) {
    	Map<String, String> weather = data.getWeather().get(0);
    	return weather.get("description").trim().replace(" ", "-");
    }
    
    public String getBgImage(Response data) {
    	String query = getWeatherDescription(data);
    	String url = "https://api.unsplash.com/search/photos?page=1&per_page=1&query=" + 
    			query + "&client_id=" + clientId;
    	RestTemplate restTemplate = new RestTemplate();
    	UnsplashJson result = restTemplate.getForObject(url, UnsplashJson.class);
    	return result.getResults().get(0).getUrls().getRegular();
    }
}
