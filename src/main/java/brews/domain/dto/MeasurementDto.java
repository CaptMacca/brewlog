package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
public class MeasurementDto {

    private Long id;
    private Long brewId;
    private OffsetDateTime measurementDate;
    private Double value;
    private Long versionId;
}
