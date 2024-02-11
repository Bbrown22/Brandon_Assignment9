package com.coderscampus.Assignment9.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coderscampus.Assignment9.service.RecipeService;

@Configuration
public class RecipeConfig {

    @Bean
    public RecipeService recipeService() {
        return new RecipeService();
    }
}