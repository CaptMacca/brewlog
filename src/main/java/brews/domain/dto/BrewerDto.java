package brews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
public class BrewerDto {

    private Long id;
    private String givenName;
    private String surname;
    private String email;
    private String userid;
    private Boolean enabled;
    private OffsetDateTime createdOn;
}
