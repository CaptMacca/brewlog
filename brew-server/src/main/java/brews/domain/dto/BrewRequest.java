package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrewRequest {

    private Long recipeId;
    private String brewer;
}
