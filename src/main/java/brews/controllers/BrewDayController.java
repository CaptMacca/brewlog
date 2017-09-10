package brews.controllers;

import brews.domain.BrewDay;
import brews.repository.BrewDayRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Steve on 11/06/2017.
 */
@RestController
@RequestMapping("api/v1/")
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
    public void create(@RequestBody BrewDay brewDay) {
        brewDayRepository.save(brewDay);
    }

    @PutMapping("brews/{id}")
    public void update(@PathVariable Long id, @RequestBody BrewDay brewDay) {
        BrewDay existingBrewDay = brewDayRepository.findOne(id);
        BeanUtils.copyProperties(brewDay,existingBrewDay);
        brewDayRepository.saveAndFlush(brewDay);
    }

    @DeleteMapping("brews/{id}")
    public BrewDay delete(@PathVariable Long id) {
        BrewDay existingBrewDay = brewDayRepository.findOne(id);
        brewDayRepository.delete(id);
        return existingBrewDay;
    }

}
