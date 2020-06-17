package brews.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MeasurementDto {

    private Long id;
    private Long brewId;
    private OffsetDateTime measurementDate;
    private Double value;
    private Long versionId;
}
