package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MashDto {

    private Long id;
    private String name;
    private Double stepTemp;
    private Double stepTime;
    private Long versionId;
}
