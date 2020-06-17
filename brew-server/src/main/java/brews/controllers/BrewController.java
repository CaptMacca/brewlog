package brews.controllers;

import brews.domain.dto.BrewDto;
import brews.domain.dto.CreateBrewDto;
import brews.domain.dto.RecipeDto;
import brews.domain.dto.UpdateBrewDto;
import brews.services.BrewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * The Brew API.
 */
@Slf4j
@RestController
@RequestMapping("api/brews")
@CrossOrigin(origins = "*")
@Api("API for creating, retrieving, deleting and updating a brew")
public final class BrewController {

    private final BrewService brewService;

    public BrewController(BrewService brewService) {
        this.brewService = brewService;
    }

    @GetMapping("/all")
    @ApiOperation("Returns all brews in the repository")
    @RolesAllowed("ROLE_ADMIN")
    public List<BrewDto> getAllBrews() {
        return brewService.getAllBrews();
    }

    @GetMapping()
    @ApiOperation("Returns all brews in the repository for a given username")
    public List<BrewDto> getBrews(@RequestParam String username) {
        return brewService.getAllBrewsForUser(username);
    }

    @GetMapping("{id}")
    @ApiOperation("Returns an individual brew identified by the id")
    public BrewDto get(@PathVariable Long id) {
        return brewService.getBrew(id);
    }

    @GetMapping("/top5")
    @ApiOperation("Returns top 5 recent brews stored in the repository for a user")
    public ResponseEntity<List<BrewDto>> getTop5RatedBrewsForUser(@RequestParam String username) {
        return ResponseEntity.ok(brewService.getTop5BrewsForUser(username));
    }

    @PostMapping()
    @ApiOperation("Creates a new brew")
    public ResponseEntity<BrewDto> create(@RequestBody CreateBrewDto createBrewDto) {

        String username = createBrewDto.getUsername();
        BrewDto brewDto = createBrewDto.getBrew();
        RecipeDto recipeDto = createBrewDto.getRecipe();

        if (recipeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (username == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (brewDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BrewDto newBrew = brewService.saveBrew(brewDto, username);

        return new ResponseEntity<>(newBrew, HttpStatus.ACCEPTED);
    }

    @PutMapping()
    @ApiOperation("Updates a brew identified by the id")
    public ResponseEntity<BrewDto> update(@RequestBody UpdateBrewDto updateBrewDto) {
        BrewDto updatedBrewDto = brewService.updateBrew(updateBrewDto);
        return ResponseEntity.ok(updatedBrewDto);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a brew identified by the id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brewService.deleteBrew(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
