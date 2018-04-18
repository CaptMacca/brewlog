package brews.domain.dto;

import brews.domain.Brew;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class MeasurementDto {

    private Long id;
    private BrewDto brewDto;
    private String type;
    private Date measurementDate;
    private Double value;
}
