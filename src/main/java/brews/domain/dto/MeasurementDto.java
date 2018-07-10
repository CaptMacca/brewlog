package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class MeasurementDto {

    private Long id;
    private Long brewId;
    private String type;
    private String typeDescription;
   // @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
    private Date measurementDate;
    private Double value;

}
