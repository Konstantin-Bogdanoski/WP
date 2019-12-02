package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.IngredientAlreadyExistsException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.IngredientNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.NoMoreSpicyIngredientsException;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Ingredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.IngredientService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaIngredientService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private IngredientService ingredientService;
    private PizzaIngredientService pizzaIngredientService;

    public IngredientController(IngredientService ingredientService, PizzaIngredientService pizzaIngredientService) {
        this.ingredientService = ingredientService;
        this.pizzaIngredientService = pizzaIngredientService;
    }

    @PostMapping
    public ModelAndView addIngredient(@ModelAttribute Ingredient ingredient, ModelMap model) {
        ingredientService.findAll().forEach(ing -> {
            if (ing.getName().equals(ingredient.getName()))
                throw new IngredientAlreadyExistsException();
        });
        if (ingredientService.findAll().stream().filter(Ingredient::isSpicy).map(Ingredient::getId).count() == 4)
            throw new NoMoreSpicyIngredientsException();
        ingredient.setDateCreated(LocalDateTime.now());
        ingredientService.save(ingredient);
        return new ModelAndView("redirect:/admin/ingredients", model);
    }

    @PatchMapping("/{id}")
    public ModelAndView editIngredient(@PathVariable Long id, @ModelAttribute(name = "newIngredient") Ingredient newIngredient, ModelMap model) {
        newIngredient.setId(id);
        if (ingredientService.findOne(id).isPresent()) {
            if (ingredientService.findAll().stream().filter(Ingredient::isSpicy).map(Ingredient::getId).count() == 4)
                throw new NoMoreSpicyIngredientsException();
            ingredientService.save(newIngredient);
        } else
            throw new IngredientNotFoundException();
        return new ModelAndView("redirect:/admin/ingredients", model);
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteIngredient(@PathVariable Long id, ModelMap model) {
        if (ingredientService.findOne(id).isPresent())
            ingredientService.delete(id);
        else
            throw new IngredientNotFoundException();
        return new ModelAndView("redirect:/admin/ingredients", model);
    }

    @GetMapping
    public List<Ingredient> getIngredients(@RequestParam(name = "spicy", required = false) boolean spicy) {
        List<Ingredient> ingredients = ingredientService.findAll();
        if (!spicy) {
            if (!ingredients.isEmpty()) {
                Collections.sort(ingredients);
                return ingredients;
            }
            throw new IngredientNotFoundException();
        } else
            return ingredients.stream().filter(Ingredient::isSpicy).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable Long id) {
        if (ingredientService.findOne(id).isPresent())
            return ingredientService.findOne(id).get();
        throw new IngredientNotFoundException();
    }

    @GetMapping("/{id}/pizzas")
    public List<Pizza> getPizzasWithIngredient(@PathVariable Long ingredientID) {
        return pizzaIngredientService.findAll()
                .stream()
                .filter(ingr -> ingr.getIngredient().getId().equals(ingredientID))
                .map(PizzaIngredient::getPizza)
                .collect(Collectors.toList());
    }
}
