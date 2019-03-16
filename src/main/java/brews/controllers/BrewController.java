package brews.controllers;

import brews.domain.dto.BrewDto;
import brews.domain.dto.CreateBrewDto;
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
@Api(description = "API for creating, retrieving, deleting and updating a brew")
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

    @PostMapping()
    @ApiOperation("Creates a new brew")
    public ResponseEntity<BrewDto> create(@RequestBody CreateBrewDto createBrewDto) {

        String username = createBrewDto.getUsername();
        BrewDto brewDto = createBrewDto.getBrew();

        if (brewDto.getRecipe() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (brewDto.getUser() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BrewDto newBrew = brewService.saveBrew(brewDto, "joe");

        return new ResponseEntity<>(newBrew, HttpStatus.ACCEPTED);
    }

    @PutMapping()
    @ApiOperation("Updates a brew identified by the id")
    public ResponseEntity<BrewDto> update(@RequestBody BrewDto brewDto) {
        BrewDto updatedBrewDto = brewService.updateBrew(brewDto);
        return ResponseEntity.ok(updatedBrewDto);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a brew identified by the id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brewService.deleteBrew(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
