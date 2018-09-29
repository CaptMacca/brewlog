package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HopDto extends IngredientDto {

    private Double alpha;
    private Double additionTime;
    private String hopUsage;
}
