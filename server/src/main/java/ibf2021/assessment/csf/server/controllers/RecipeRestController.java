package ibf2021.assessment.csf.server.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ibf2021.assessment.csf.server.models.Recipe;
import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

/* Write your request hander in recipe file */
@RestController
@RequestMapping(path = "/api")
public class RecipeRestController {
    @Autowired
    private RecipeService rSvc;
	private final Logger logger = Logger.getLogger(RecipeRestController.class.getName());


    @GetMapping(
        path = "/recipe/{recipeId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getRecipeById(
        @PathVariable(name = "recipeId", required = true) String id
    ) {
        Recipe recipe = rSvc.getRecipeById(id).orElse(null);
        if (recipe == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Could not find recipe with id " + id
            );
        }
        return new ResponseEntity<String>(
            recipeToString(recipe),
            HttpStatus.OK
        );
    }

    @PostMapping(
        path = "/recipe",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> addRecipe(@RequestBody Recipe recipe) {
        logger.info(recipe.toString());
        rSvc.addRecipe(recipe);
        return new ResponseEntity<String>(
            recipeToString(recipe),
            HttpStatus.CREATED
        );
    }

    private String recipeToString(Recipe recipe) {
        JsonObjectBuilder recipeBuilder = Json.createObjectBuilder();
		JsonArrayBuilder ingredientsBuilder = Json.createArrayBuilder();
		recipeBuilder.add("id", recipe.getId());
		recipeBuilder.add("title", recipe.getTitle());
		recipeBuilder.add("image", recipe.getImage());
		recipeBuilder.add("instruction", recipe.getInstruction());
        recipe.getIngredients()
            .forEach(i -> ingredientsBuilder.add(i));        
        recipeBuilder.add("ingredients", ingredientsBuilder);
        return recipeBuilder.build().toString();
    }
}