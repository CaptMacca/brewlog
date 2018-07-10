package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FermentableDto {

    private Long id;
    private Double amount;
    private String name;
    private String type;

}