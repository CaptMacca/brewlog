package brews.app.presentation.dto.brew;

import brews.app.presentation.dto.recipe.RecipeDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CreateBrewDto {
    BrewDto brew;
    RecipeDto recipe;
}
