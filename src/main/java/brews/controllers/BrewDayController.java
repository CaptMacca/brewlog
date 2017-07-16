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

    BrewDayRepository brewDayRepository;

    @RequestMapping(value = "/brews", method = RequestMethod.GET)
    public List<BrewDay> getBrewDays() {
        return brewDayRepository.findAll();
    }

    @RequestMapping(value = "/brews/{id}", method = RequestMethod.GET)
    public BrewDay get(@PathVariable Long id) {
        return brewDayRepository.findOne(id);
    }

    @RequestMapping(value = "brews", method = RequestMethod.POST)
    public void create(@RequestBody BrewDay brewDay) {
        brewDayRepository.save(brewDay);
    }

    @RequestMapping(value = "brews/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody BrewDay brewDay) {
        BrewDay existingBrewDay = brewDayRepository.findOne(id);
        BeanUtils.copyProperties(brewDay,existingBrewDay);
        brewDayRepository.saveAndFlush(brewDay);
    }

    @RequestMapping(value = "brews/{id}", method = RequestMethod.DELETE)
    public BrewDay delete(@PathVariable Long id) {
        BrewDay existingBrewDay = brewDayRepository.findOne(id);
        brewDayRepository.delete(id);
        return existingBrewDay;
    }

    @Autowired
    public void setBrewDayRepository(BrewDayRepository brewDayRepository) {
        this.brewDayRepository = brewDayRepository;
    }
}
