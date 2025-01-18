package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatformDto {
    private int platformId;
    private String platformName;
    private String subscriptionType;
    private double price;
    private List<MovieDto> movies;
}
