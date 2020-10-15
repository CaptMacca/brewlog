package brews.app.presentation.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateRatingRequest {
    private Long id;
    private Short rating;
}
