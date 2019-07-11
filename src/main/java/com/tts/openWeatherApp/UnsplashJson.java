package com.tts.openWeatherApp;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnsplashJson {
	private List<Results> results;
}
