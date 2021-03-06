package brews.app.presentation.dto.recipe;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class FermentableDto extends IngredientDto {

    private Boolean addAfterBoil;
}
