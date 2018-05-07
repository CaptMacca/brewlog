package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HopDto {

    private Long id;
    private String name;
    private Double alpha;
    private Double additionTime;
    private String hopUsage;
    private Double amount;
}
