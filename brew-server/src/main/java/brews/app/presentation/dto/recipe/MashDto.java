package brews.app.presentation.dto.recipe;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MashDto {

    private Long id;
    private String name;
    private Double stepTemp;
    private Double stepTime;
    private Long versionId;
}
