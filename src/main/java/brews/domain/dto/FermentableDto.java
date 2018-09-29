package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FermentableDto extends IngredientDto {


    private Boolean addAfterBoil;
}
