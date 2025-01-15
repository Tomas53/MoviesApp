package services;

import dto.MovieDto;
import repositories.MovieRepo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

    public String getMostPopularGenre (){
        MovieRepo movieRepo=new MovieRepo();
        return movieRepo.getAllMovies().stream()
                .collect(Collectors.groupingBy(MovieDto::getGenre, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("No such most popular genre");
    }

    public String getDirectorWithHigestAverageIMDbRating(){
        MovieRepo movieRepo=new MovieRepo();
        return movieRepo.getAllMovies().stream()
                .collect(Collectors.groupingBy(MovieDto::getDirector, Collectors.averagingDouble(MovieDto::getImdbRating)))
                .entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("No such director found");

    }

    public List<MovieDto>getMoviesByGenre (String genre){
        MovieRepo movieRepo=new MovieRepo();
        return movieRepo.getAllMovies().stream()
                .filter(movieDto -> movieDto.getGenre().equals(genre))
                .collect(Collectors.toList());
    }

}
