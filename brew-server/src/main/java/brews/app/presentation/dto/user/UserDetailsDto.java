package brews.app.presentation.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDetailsDto {
    private String firstName;
    private String surname;
    private String email;
}
