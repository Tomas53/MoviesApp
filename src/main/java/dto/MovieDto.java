package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class MovieDto {
    private int movieId;
    private String title;
    private String genre;
    private String director;
    private double price;
    private int duration;
    private double imdbRating;
    private double metascore;
}
