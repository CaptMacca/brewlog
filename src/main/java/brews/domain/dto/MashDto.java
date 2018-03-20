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
    private String stepTemp;
    private Integer stepTime;

}
