package brews.app.presentation.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdatePasswordRequest {
    private String username;
    public String currentPassword;
    public String newPassword;
}
