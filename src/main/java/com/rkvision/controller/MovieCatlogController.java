package com.rkvision.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.rkvision.model.CatlogItem;
import com.rkvision.model.Movie;
import com.rkvision.model.Rating;
import com.rkvision.model.UserRating;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;



@RestController
@RequestMapping("/catlog")
public class MovieCatlogController {
	
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	WebClient.Builder webClientBuilder;
	
	public static final String namewa="caltlogService";
	
	@GetMapping("/{userId}")
	@CircuitBreaker(name = namewa,fallbackMethod = "getFallbackCatlog")
	//@HystrixCommand(fallbackMethod = "getFallbackCatlog")
	public List<CatlogItem> getCatlog(@PathVariable("userId")String userId){
		System.out.println("RK :"+userId);
		
		//ParameterizedTypeReferences<ResponseWrapper<T>>(){}
		//get all rated movie id's
		UserRating userRating = restTemplate.getForObject("http://MOVIE-RATING-SERVICE/ratingsdata/users/"+ userId , UserRating.class);
	
		
		return userRating.getUserRating().stream().map( rating -> {
	//	Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/" +rating.getMovieId(), Movie.class);
			
		// for each movie id, call movie info service and get details
		//webclient builder
			
			  Movie movie = webClientBuilder.build() .get()
			  .uri("http://MOVIE-INFO-SERVICE/movies/"+ rating.getMovieId()) .retrieve()
			  .bodyToMono(Movie.class) .block();
			 
		return new CatlogItem(movie.getName(),movie.getDescription(),rating.getRating());
				
		})
				.collect(Collectors.toList());
	}
	
	
	public List<CatlogItem> getFallbackCatlog(Exception e){
		return Arrays.asList(new CatlogItem("no movie fallback","",0));
	}
}
	
