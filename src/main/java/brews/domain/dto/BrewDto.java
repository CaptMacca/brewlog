package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class BrewDto {

    private Long id;
    private Date brewDate;
    private RecipeDto recipe;
    private String brewer;

}
