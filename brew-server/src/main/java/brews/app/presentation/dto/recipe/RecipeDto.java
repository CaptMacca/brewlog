package brews.app.presentation.dto.recipe;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.SortedSet;

@Getter
@Setter
@ToString
@EqualsAndHashCode
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
    private Short rating;
    private String username;
    private List<IngredientDto> ingredients;
    private List<FermentableDto> fermentables;
    private SortedSet<HopDto> hops;
    private List<YeastDto> yeasts;
    private List<MashDto> mashes;
    private Long versionId;
}
