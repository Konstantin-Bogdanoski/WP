package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
            newIngredient.setDateUpdated(LocalDateTime.now());
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
    public Page<Ingredient> getIngredients(@PageableDefault(value = 5) Pageable pageable, @RequestParam(name = "spicy", required = false) boolean spicy) {
        List<Ingredient> ingredients = ingredientService.findAll();
        if (!spicy) {
            if (!ingredients.isEmpty()) {
                Collections.sort(ingredients);
                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), ingredients.size());
                return new PageImpl<>(ingredients.subList(start, end), pageable, ingredients.size());
            }
            throw new IngredientNotFoundException();
        } else {
            List<Ingredient> spicyIngredients = ingredients.stream().filter(Ingredient::isSpicy).sorted().collect(Collectors.toList());
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), spicyIngredients.size());
            return new PageImpl<>(spicyIngredients.subList(start, end), pageable, spicyIngredients.size());
        }
    }

    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable Long id) {
        if (ingredientService.findOne(id).isPresent())
            return ingredientService.findOne(id).get();
        throw new IngredientNotFoundException();
    }

    @GetMapping("/{id}/pizzas")
    public List<Pizza> getPizzasWithIngredient(@PathVariable(name = "id") Long ingredientID) {
        return pizzaIngredientService.findAll()
                .stream()
                .filter(ingr -> ingr.getIngredient().getId().equals(ingredientID))
                .map(PizzaIngredient::getPizza)
                .collect(Collectors.toList());
    }
}
