package com.coderscampus.Assignment9.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class RecipeService {
	private List<Recipe> recipes = new ArrayList<>();

	@PostConstruct
	public void init() throws IOException {
		
		try {
			FileReader in = new FileReader("recipes.txt");
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withIgnoreSurroundingSpaces().withEscape('\\').withFirstRecordAsHeader()
					.parse(in);
			for (CSVRecord record : records) {
				Recipe recipe = new Recipe();
				recipe.setCookingMinutes(Integer.parseInt(record.get("CookingMinutes").trim()));
				recipe.setDairyFree(Boolean.parseBoolean(record.get("DairyFree").trim()));
				recipe.setGlutenFree(Boolean.parseBoolean(record.get("GlutenFree").trim()));
				recipe.setInstructions(record.get("Instructions").trim());
				try {
					recipe.setPreparationMinutes(Double.parseDouble(record.get("PreparationMinutes").trim()));
				} catch (NumberFormatException e) {
					System.err.println(
							"Invalid number format for PreparationMinutes: " + record.get("PreparationMinutes"));
				}

				try {
					recipe.setPricePerServing(Double.parseDouble(record.get("PricePerServing").trim()));
				} catch (NumberFormatException e) {
					System.err.println("Invalid number format for PricePerServing: " + record.get("PricePerServing"));
				}

				try {
					recipe.setSpoonacularScore(Double.parseDouble(record.get("SpoonacularScore").trim()));
				} catch (NumberFormatException e) {
					System.err.println("Invalid number format for SpoonacularScore: " + record.get("SpoonacularScore"));
				}
				recipe.setTitle(record.get("Title").trim());
				recipe.setVegan(Boolean.parseBoolean(record.get("Vegan").trim()));
				recipe.setVegetarian(Boolean.parseBoolean(record.get("Vegetarian").trim()));
				recipe.toString();
				recipes.add(recipe);
			}
		} catch (IOException e) {
			System.out.println("An error occurred while trying to read the file");
			e.printStackTrace();
		}
	}

	public List<Recipe> getAllRecipes() {
		return recipes;
	}

	public List<Recipe> getGlutenFreeRecipes() {
		return recipes.stream().filter(Recipe::getGlutenFree).collect(Collectors.toList());
	}

	public List<Recipe> getVeganRecipes() {
		return recipes.stream().filter(Recipe::getVegan).collect(Collectors.toList());
	}

	public List<Recipe> getVeganAndGlutenFreeRecipes() {
		return recipes.stream().filter(r -> r.getVegan() && r.getGlutenFree()).collect(Collectors.toList());
	}

	public List<Recipe> getVegetarianRecipes() {
		return recipes.stream().filter(Recipe::getVegetarian).collect(Collectors.toList());
	}
}