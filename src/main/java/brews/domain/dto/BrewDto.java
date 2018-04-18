package brews.domain.dto;

import brews.domain.Measurement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class BrewDto {

    private Long id;
    private Date brewDate;
    private RecipeDto recipe;
    private String brewer;
    private List<MeasurementDto> measurements;
}
