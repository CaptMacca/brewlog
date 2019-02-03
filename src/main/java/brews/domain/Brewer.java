package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@Entity
public class Brewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userid;
    private String givenName;
    private String surname;
    private String email;
    private Boolean enabled;
    private OffsetDateTime createdOn;
    @Version
    private Long versionId;
}
