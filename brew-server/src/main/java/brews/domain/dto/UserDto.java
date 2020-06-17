package brews.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDto {

    private Long id;
    private String firstName;
    private String surname;
    private String email;
    private String username;

}
