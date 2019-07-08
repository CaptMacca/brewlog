package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateBrewDto {
    String username;
    BrewDto brew;
    RecipeDto recipe;
}
