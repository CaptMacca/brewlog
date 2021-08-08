package brews.app.presentation.dto.user;

import brews.app.presentation.dto.recipe.RecipeDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateRatingRequest {
    private RecipeDto recipeDto;
    private Short rating;
}
