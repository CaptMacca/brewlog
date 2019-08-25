package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mapstruct.IterableMapping;

import java.util.Set;

@Getter
@Setter
@ToString
public class RecipeDto {

    private Long id;
    private String name;
    private String style;
    private String type;
    private String estimatedAbv;
    private String batchSize;
    private String estimatedColour;
    private String estimatedIbu;
    private String originalGravity;
    private String finalGravity;
    private String boilTime;
    private String notes;
    private UserDto user;
    private Set<IngredientDto> ingredients;
    private Set<FermentableDto> fermentables;
    private Set<HopDto> hops;
    private Set<YeastDto> yeasts;
    private Set<MashDto> mashes;
    private Long versionId;
}
