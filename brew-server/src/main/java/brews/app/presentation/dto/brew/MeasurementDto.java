package brews.app.presentation.dto.brew;

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
    private OffsetDateTime measurementDate;
    private Double value;
    private Long versionId;
}
