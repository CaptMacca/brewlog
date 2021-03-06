package brews.app.presentation.dto.recipe;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HopDto.class, name = "Hop"),
        @JsonSubTypes.Type(value = YeastDto.class, name = "Yeast"),
        @JsonSubTypes.Type(value = FermentableDto.class, name = "Fermentable")
})
public abstract class IngredientDto {
    private Long id;
    private Double amount;
    private String name;
    private String type;
    private Long versionId;
}
