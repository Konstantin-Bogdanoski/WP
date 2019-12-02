package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.IngredientAlreadyExistsException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.IngredientNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.NoMoreSpicyIngredientsException;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Ingredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.IngredientService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaIngredientService;

import javax.servlet.http.HttpServletRequest;
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
    public void addIngredient(@ModelAttribute Ingredient ingredient) {
        ingredientService.findAll().forEach(ing -> {
            if (ing.getName().equals(ingredient.getName()))
                throw new IngredientAlreadyExistsException();
        });
        if (ingredientService.findAll().stream().filter(Ingredient::isSpicy).map(Ingredient::getId).count() == 4)
            throw new NoMoreSpicyIngredientsException();
        ingredient.setDateCreated(LocalDateTime.now());
        ingredientService.save(ingredient);
    }

    @PostMapping("/{id}")
    public void deleteIng(@PathVariable Long id, @ModelAttribute Ingredient ingredient, Model model) {
        boolean temp = model.getAttribute("method") != null;
        if (model.getAttribute("method") != null) {
            if (model.getAttribute("method").toString().equals("delete")) {
                if (ingredientService.findOne(id).isPresent())
                    deleteIngredient(id);
                else
                    throw new IngredientNotFoundException();
            } else if (model.getAttribute("method").toString().equals("patch")) {
                if (ingredientService.findOne(id).isPresent())
                    editIngredient(id, ingredient);
                else
                    throw new IngredientNotFoundException();
            }
        }
    }

    @PatchMapping("/{id}")
    public void editIngredient(@PathVariable Long id, @ModelAttribute Ingredient ingredient) {
        ingredient.setId(id);
        if (ingredientService.findOne(id).isPresent())
            ingredientService.save(ingredient);
        else
            throw new IngredientNotFoundException();
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable Long id) {
        if (ingredientService.findOne(id).isPresent())
            ingredientService.delete(id);
        else
            throw new IngredientNotFoundException();
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
