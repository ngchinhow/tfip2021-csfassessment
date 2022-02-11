package ibf2021.assessment.csf.server.models;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

/*
 * IMPORTANT: Do not change this class
 */

public class Recipe {
	private String id;
	private String title;
	private String image;
	private String instruction;
	private List<String> ingredients = new LinkedList<>();

	public Recipe() { 
		id = UUID.randomUUID().toString().substring(0, 8);
	}

	public String getId() { return this.id;}
	public void setId(String id) { this.id = id; }

	public String getTitle() { return this.title; }
	public void setTitle(String title) { this.title = title; }

	public String getImage() { return this.image; }
	public void setImage(String image) { this.image = image; }

	public String getInstruction() { return this.instruction; }
	public void setInstruction(String instruction) { this.instruction = instruction; }

	public List<String> getIngredients() { return this.ingredients; }
	public void addIngredient(String ingredient) { this.ingredients.add(ingredient); }

	public JsonObject toJson() {
        JsonObjectBuilder recipeBuilder = Json.createObjectBuilder();
		JsonArrayBuilder ingredientsBuilder = Json.createArrayBuilder();
        recipeBuilder.add("id", this.getId());
        recipeBuilder.add("title", this.getTitle());
        recipeBuilder.add("image", this.getImage());
        recipeBuilder.add("instruction", this.getInstruction());
		this.getIngredients()
			.forEach(i -> ingredientsBuilder.add(i));
		recipeBuilder.add("ingredients", ingredientsBuilder);
        return recipeBuilder.build();
    }
	
	@Override
	public String toString() {
		return this.toJson().toString();
	}
}

