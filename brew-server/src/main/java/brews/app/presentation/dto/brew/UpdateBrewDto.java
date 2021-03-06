package brews.app.presentation.dto.brew;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdateBrewDto {
    private Long id;
    private Long recipeId;
    private OffsetDateTime brewDate;
    private Integer score;
    private Double spargeVol;
    private Double totalWater;
    private Double fermenterVol;
    private Double estimatedOriginalGravity;
    private Double measuredOriginalGravity;
    private Double estimatedPreboilGravity;
    private Double measuredPreboilGravity;
    private Double estimatedFinalGravity;
    private Double measuredFinalGravity;
    private Double estimatedFermentVolume;
    private Double measuredFermentVolume;
    private Double estimatedBottleVolume;
    private Double measuredBottleVolume;
    private String notes;
    private String tastingNotes;
    private Set<MeasurementDto> measurements;
    private Long versionId;
}
