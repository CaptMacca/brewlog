package brews.controllers;

import brews.domain.Brew;
import brews.domain.dto.BrewDto;
import brews.domain.dto.CreateBrewRequest;
import brews.domain.dto.UpdateBrewRequest;
import brews.mapper.domain.BrewMapper;
import brews.services.BrewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
    private final BrewMapper brewMapper;

    public BrewController(BrewService brewService, BrewMapper brewMapper) {
        this.brewService = brewService;this.brewMapper = brewMapper;
    }

    @GetMapping("/all")
    @ApiOperation(value="Returns all brews in the repository", authorizations = { @Authorization(value="jwtToken")})
    @RolesAllowed("ROLE_ADMIN")
    public List<BrewDto> getAllBrews() {
        return brewMapper.toBrewDtos(brewService.getAllBrews());
    }

    @GetMapping()
    @ApiOperation(value="Returns all brews in the repository for a given username", authorizations = { @Authorization(value="jwtToken")})
    public List<BrewDto> getBrews(@RequestParam String username) {
        return brewMapper.toBrewDtos(brewService.getAllBrewsForUser(username));
    }

    @GetMapping("{id}")
    @ApiOperation(value="Returns an individual brew identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public BrewDto get(@PathVariable Long id) {
        return brewMapper.toBrewDto(brewService.getBrew(id));
    }

    @GetMapping("/top5")
    @ApiOperation(value="Returns top 5 recent brews stored in the repository for a user", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<List<BrewDto>> getTop5RatedBrewsForUser(@RequestParam String username) {
        return ResponseEntity.ok(brewMapper.toBrewDtos(brewService.getTop5BrewsForUser(username)));
    }

    @PostMapping()
    @ApiOperation(value="Creates a new brew", authorizations = {@Authorization(value="jwtToken")})
    public ResponseEntity<BrewDto> create(@RequestBody CreateBrewRequest createBrewRequest) {
        log.debug("Processing new brew request");
        String username = createBrewRequest.getUsername();
        BrewDto brewDto = createBrewRequest.getBrew();
        Brew brew = brewMapper.toBrew(brewDto);

        if (username == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (brewDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Brew newBrew = brewService.saveBrew(brew, username);

        return new ResponseEntity<>(brewMapper.toBrewDto(newBrew), HttpStatus.ACCEPTED);
    }

    @PutMapping()
    @ApiOperation(value="Updates a brew identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<BrewDto> update(@RequestBody UpdateBrewRequest updateBrewRequest) {
        Brew brew = brewMapper.updateBrewDtotoBrew(updateBrewRequest);
        log.debug("Updating brew with details {} ", brew);
        if (brew == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Brew updatedBrew = brewService.updateBrew(brew);
        return ResponseEntity.ok(brewMapper.toBrewDto(updatedBrew));
    }

    @DeleteMapping("{id}")
    @ApiOperation(value="Deletes a brew identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brewService.deleteBrew(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
