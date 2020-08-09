package brews.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdatePasswordDto {
    private String username;
    public String currentPassword;
    public String newPassword;
}
