package services;

import dto.MovieDto;
import repositories.MovieRepo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieAnalyticsService {
    MovieRepo movieRepo = new MovieRepo();

    public List<MovieDto> getTopRatedMovies() {

        return movieRepo.getAllMovies().stream()
                .sorted(Comparator.comparingDouble(MovieDto::getImdbRating).reversed())
                .limit(5)
                .toList();
    }

    public double getAverageMovieRatingForSpecificPlatform(String platformName) {
        return movieRepo.getMoviesByPlatform(platformName).stream()
                .mapToDouble(MovieDto::getImdbRating)
                .average()
                .orElse(0.0);
    }

    public List<MovieDto> getMoviesByDuration(int durationLimit) {
        return movieRepo.getAllMovies().stream()
                .filter(m -> m.getDuration() < durationLimit)
                .collect(Collectors.toList());
    }

    public String getMostPopularGenre() {
        return movieRepo.getAllMovies().stream()
                .collect(Collectors.groupingBy(MovieDto::getGenre, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("No such most popular genre");
    }

    public String getDirectorWithHigestAverageIMDbRating() {
        return movieRepo.getAllMovies().stream()
                .collect(Collectors.groupingBy(MovieDto::getDirector, Collectors.averagingDouble(MovieDto::getImdbRating)))
                .entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("No such director found");
    }

    public List<MovieDto> getMoviesByGenre(String genre) {
        return movieRepo.getAllMovies().stream()
             //   .filter(movieDto -> movieDto.getGenre() != null)
                .filter(movieDto -> genre.equalsIgnoreCase(movieDto.getGenre()))
                .collect(Collectors.toList());
    }
}
