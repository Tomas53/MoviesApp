package services;

import dto.MovieDto;
import repositories.MovieRepo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieAnalyticsService {
    public List<MovieDto> getTopRatedMovies(){
        MovieRepo movieRepo=new MovieRepo();
        return movieRepo.getAllMovies().stream()
                .sorted(Comparator.comparingDouble(MovieDto::getImdbRating).reversed())
                .limit(5)
                .collect(Collectors.toUnmodifiableList());
    }
    public double getAverageMovieRatingForSpecificPlatform(String platformName){
        MovieRepo movieRepo=new MovieRepo();

        return movieRepo.getMoviesByPlatform(platformName).stream()
                .mapToDouble(MovieDto::getImdbRating)
                .average()
                .orElse(0.0);
    }
    public List<MovieDto>getMoviesByDuration (int durationLimit){
        MovieRepo movieRepo=new MovieRepo();
        return movieRepo.getAllMovies().stream()
                .filter(m->m.getDuration()<durationLimit)
                .collect(Collectors.toList());
    }
}
