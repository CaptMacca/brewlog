package brews.controllers;

import brews.domain.dto.BrewDto;
import brews.domain.dto.BrewRequest;
import brews.services.BrewService;
import brews.exceptions.BrewServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * The Brew API.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
public class BrewController {

    private final BrewService brewService;

    @Autowired
    public BrewController(BrewService brewService) {
        this.brewService = brewService;
    }

    @GetMapping("/brews")
    public List<BrewDto> getBrews() {
        return brewService.getAllBrews();
    }

    @GetMapping("/brews/{id}")
    public BrewDto get(@PathVariable Long id) {
        return brewService.getBrew(id);
    }

    @PostMapping("/brews/new")
    public ResponseEntity<?> create(@RequestBody BrewDto brewDto) {

        if (brewDto.getRecipe() == null) {
            return new ResponseEntity<>("No recipe id to save", HttpStatus.BAD_REQUEST);
        }

        if (brewDto.getBrewer() == null) {
            return new ResponseEntity<>("No brewer details to save", HttpStatus.BAD_REQUEST);
        }

        BrewDto newBrew = brewService.newBrew(brewDto);

        return new ResponseEntity<>(newBrew, HttpStatus.ACCEPTED);
    }

    @PutMapping("/brews")
    public ResponseEntity<?> update(@RequestBody BrewDto brewDto) {

        BrewDto updatedBrewDto;

        try {
            updatedBrewDto = brewService.saveBrew(brewDto);
        } catch (BrewServiceException e) {
            return new ResponseEntity<>("Brew record could not be saved", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedBrewDto, HttpStatus.OK);
    }

    @DeleteMapping("brews/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        brewService.deleteBrew(id);
        return new ResponseEntity<>(this.getBrews(), HttpStatus.ACCEPTED);
    }

}
