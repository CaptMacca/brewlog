package brews.controllers;

import brews.domain.BrewDay;
import brews.repository.BrewDayRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Steve on 11/06/2017.
 */
@RestController
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
public class BrewDayController {

    private final BrewDayRepository brewDayRepository;

    @Autowired
    public BrewDayController(BrewDayRepository brewDayRepository) {
        this.brewDayRepository = brewDayRepository;
    }

    @GetMapping("/brews")
    public List<BrewDay> getBrewDays() {
        return brewDayRepository.findAll();
    }

    @GetMapping("/brews/{id}")
    public BrewDay get(@PathVariable Long id) {
        return brewDayRepository.findOne(id);
    }

    @PostMapping("brews")
    public ResponseEntity<?> create(@RequestBody BrewDay brewDay) {
        BrewDay newBrewDay = brewDayRepository.save(brewDay);

        if (newBrewDay==null) {
            return new ResponseEntity<>("Brew Details could not be saved", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newBrewDay, HttpStatus.ACCEPTED);
    }

    @PutMapping("brews/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BrewDay brewDay) {

        BrewDay existingBrewDay = brewDayRepository.findOne(id);

        if (existingBrewDay==null) {
            return new ResponseEntity<>("Brew record could not be found", HttpStatus.BAD_REQUEST);
        }

        BeanUtils.copyProperties(brewDay,existingBrewDay);
        brewDay = brewDayRepository.saveAndFlush(existingBrewDay);

        return new ResponseEntity<>(brewDay, HttpStatus.OK);
    }

    @DeleteMapping("brews/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        BrewDay existingBrewDay = brewDayRepository.findOne(id);

        if (existingBrewDay==null) {
            return new ResponseEntity<>("Brew record could not be found to delete", HttpStatus.BAD_REQUEST);
        }

        brewDayRepository.delete(id);
        List<BrewDay> brews = brewDayRepository.findAll();
        return new ResponseEntity<>(brews,HttpStatus.ACCEPTED);
    }

}
