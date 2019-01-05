package brews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Mash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @EqualsAndHashCode.Include
    @ToString.Include
    private String name;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Double stepTemp;

    @EqualsAndHashCode.Include
    @ToString.Include
    private Double stepTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Recipe recipe;

}
