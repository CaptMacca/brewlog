package brews.app.presentation.dto.recipe;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class HopDto extends IngredientDto implements Comparable<HopDto> {
    private Double alpha;
    private Double additionTime;
    private String hopUsage;

    public int compareTo(HopDto target) {
        if (this.additionTime < target.additionTime ) {
            return -1;
        } else if (this.additionTime > target.additionTime) {
            return 1;
        } else {
            return 0;
        }
    }
}
