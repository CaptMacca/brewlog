package brews.controllers;

import brews.domain.dto.BrewDto;
import brews.services.BrewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    @ApiOperation("Returns all brews in the repository")
    public List<BrewDto> getBrews() {
        return brewService.getAllBrews();
    }

    @GetMapping("{id}")
    @ApiOperation("Returns an individual brew identified by the id")
    public BrewDto get(@PathVariable Long id) {
        return brewService.getBrew(id);
    }

    @PostMapping()
    @ApiOperation("Creates a new brew")
    public ResponseEntity<BrewDto> create(@RequestBody BrewDto brewDto) {

        if (brewDto.getRecipe() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (brewDto.getBrewer() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        BrewDto newBrew = brewService.saveBrew(brewDto);

        return new ResponseEntity<>(newBrew, HttpStatus.ACCEPTED);
    }

    @PutMapping()
    @ApiOperation("Updates a brew identified by the id")
    public ResponseEntity<BrewDto> update(@RequestBody BrewDto brewDto) {

        BrewDto updatedBrewDto;

//        try {
            updatedBrewDto = brewService.updateBrew(brewDto);
//        } catch (BrewNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

        return new ResponseEntity<>(updatedBrewDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation("Deletes a brew identified by the id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brewService.deleteBrew(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
