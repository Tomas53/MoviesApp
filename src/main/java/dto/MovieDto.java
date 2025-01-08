package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MovieDto {
    private int movieId;
    private String title;
    private String genre;
    private String director;
    private double price;
    private List<String> cast;
    private int duration;
    private double IMDbRating;
    private double metascore;
}
