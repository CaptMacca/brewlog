package brews.app.presentation.rest.controllers;

import brews.app.presentation.dto.brew.BrewDto;
import brews.app.presentation.dto.brew.CreateBrewDto;
import brews.app.presentation.dto.brew.UpdateBrewDto;
import brews.app.presentation.dto.recipe.RecipeDto;
import brews.domain.Brew;
import brews.domain.Recipe;
import brews.domain.User;
import brews.domain.mapper.BrewMapper;
import brews.domain.mapper.RecipeMapper;
import brews.services.BrewService;
import brews.services.RecipeService;
import brews.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * The Brew API.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/brews")
@CrossOrigin(origins = "*")
@Api("API for creating, retrieving, deleting and updating a brew")
public final class BrewController {

    private final BrewService brewService;
    private final RecipeService recipeService;
    private final UserService userService;
    private final BrewMapper brewMapper;
    private final RecipeMapper recipeMapper;

    @GetMapping("/all")
    @ResponseBody
    @ApiOperation(value="Returns all brews in the repository", authorizations = { @Authorization(value="jwtToken")})
    @RolesAllowed("ROLE_ADMIN")
    public List<BrewDto> getAllBrews() {
        return brewMapper.toBrewDtos(brewService.getAllBrews());
    }

    @GetMapping()
    @ResponseBody
    @ApiOperation(value="Returns all brews in the repository for a given username", authorizations = { @Authorization(value="jwtToken")})
    public List<BrewDto> getBrews() {
        User user = userService.getCurrentUserDetails();
        return brewMapper.toBrewDtos(brewService.getAllBrewsForUser(user.getUsername()));
    }

    @GetMapping(value = "{id}/notes", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    @ApiOperation(value="Returns brew notes identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public String getNotes(@PathVariable Long id) {
        return brewService.getNotesForBrew(id);
    }

    @GetMapping(value = "{id}/tastingnotes", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    @ApiOperation(value="Returns brew notes identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public String getTastingNotes(@PathVariable Long id) {
        return brewService.getTastingNotesForBrew(id);
    }

    @GetMapping("{id}")
    @ResponseBody
    @ApiOperation(value="Returns an individual brew identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public BrewDto get(@PathVariable Long id) {
        return brewMapper.toBrewDto(brewService.getBrew(id));
    }

    @GetMapping("/top5")
    @ResponseBody
    @ApiOperation(value="Returns top 5 recent brews stored in the repository for a user", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<List<BrewDto>> getTop5RatedBrewsForUser(@RequestParam String username) {
        return ResponseEntity.ok(brewMapper.toBrewDtos(brewService.getTop5BrewsForUser(username)));
    }

    @PostMapping()
    @ResponseBody
    @ApiOperation(value="Creates a new brew", authorizations = {@Authorization(value="jwtToken")})
    public ResponseEntity<BrewDto> create(@RequestBody CreateBrewDto createBrewRequest) {
        log.debug("Processing new brew request");
        BrewDto brewDto = createBrewRequest.getBrew();
        RecipeDto recipeDto = createBrewRequest.getRecipe();
        if (brewDto == null || recipeDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Brew brew = brewMapper.toBrew(brewDto);
        Recipe selectedRecipe = recipeMapper.toRecipe(recipeDto);

        User user = userService.getCurrentUserDetails();
        Recipe recipe = recipeService.getRecipeById(selectedRecipe.getId());
        brew.setRecipe(recipe);
        Brew newBrew = brewService.saveBrew(brew, user);

        return new ResponseEntity<>(brewMapper.toBrewDto(newBrew), HttpStatus.ACCEPTED);
    }

    @PutMapping("{id}")
    @ResponseBody
    @ApiOperation(value="Updates a brew identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<UpdateBrewDto> update(@PathVariable Long id, @RequestBody UpdateBrewDto updateBrewDto) {
        Brew brew = brewMapper.updateBrewDtotoBrew(updateBrewDto);
        log.debug("Updating brew with details {} ", brew);
        if (brew == null) {
            return ResponseEntity.badRequest().body(null);
        }
        User user = userService.getCurrentUserDetails();
        Brew updatedBrew = brewService.updateBrew(id, brew, user);
        return ResponseEntity.ok(brewMapper.toUpdateBrewDto(updatedBrew));
    }

    @DeleteMapping("{id}")
    @ApiOperation(value="Deletes a brew identified by the id", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brewService.deleteBrew(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
