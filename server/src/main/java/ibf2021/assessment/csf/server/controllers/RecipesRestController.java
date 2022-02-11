package ibf2021.assessment.csf.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.assessment.csf.server.services.RecipeService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;

/* Write your request hander in this file */
@RestController
@RequestMapping(path = "/api")
public class RecipesRestController {

    @Autowired
    private RecipeService rSvc;

    @GetMapping(
        path = "/recipes",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getAllRecipes() {
        JsonArrayBuilder responseBuilder = Json.createArrayBuilder();
        rSvc.getAllRecipes()
            .forEach(r -> {
                JsonObjectBuilder partialRecipeBuilder = Json.createObjectBuilder();
                partialRecipeBuilder.add("id", r.getId());
                partialRecipeBuilder.add("title", r.getTitle());
                responseBuilder.add(partialRecipeBuilder);
            });
        return new ResponseEntity<String> (
            responseBuilder.build().toString(),
            HttpStatus.OK
        );
    }
}