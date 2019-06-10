package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class YeastDto extends IngredientDto {

    private String productId;
    private String laboratory;

}
