package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.ModelMap;
import org.springframework.util.MimeTypeUtils;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping(path = "/ingredients", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:3000")
public class IngredientController {
    private IngredientService ingredientService;
    private PizzaIngredientService pizzaIngredientService;
    private final Logger logger;

    public IngredientController(IngredientService ingredientService, PizzaIngredientService pizzaIngredientService, Logger logger) {
        this.ingredientService = ingredientService;
        this.pizzaIngredientService = pizzaIngredientService;
        this.logger = logger;
    }

    /*@PostMapping
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
    }*/

    @PostMapping
    public Ingredient addIngredient(@RequestParam(value = "name") String name,
                                    @RequestParam(value = "spicy") boolean spicy,
                                    @RequestParam(value = "veggie") boolean veggie) {
        logger.info("\u001B[33mPOST method CALLED from IngredientController\u001B[0m");
        ingredientService.findAll().forEach(ing -> {
            if (ing.getName().equals(name))
                throw new IngredientAlreadyExistsException();
        });
        if (ingredientService.findAll().stream().filter(Ingredient::isSpicy).map(Ingredient::getId).count() == 4)
            throw new NoMoreSpicyIngredientsException();
        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(name);
        newIngredient.setSpicy(spicy);
        newIngredient.setVeggie(veggie);
        newIngredient.setDateCreated(LocalDateTime.now());
        newIngredient.setDateUpdated(LocalDateTime.now());
        ingredientService.save(newIngredient);
        return newIngredient;
    }

    /*@PatchMapping("/{id}")
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
    }*/

    @PatchMapping("/{id}")
    public Ingredient editIngredientRest(@PathVariable(name = "id") Long id,
                                         @RequestParam(value = "name") String name,
                                         @RequestParam(value = "spicy") boolean spicy,
                                         @RequestParam(value = "veggie") boolean veggie) {
        logger.info("\u001B[33mPATCH method CALLED from IngredientController\u001B[0m");
        if (ingredientService.findOne(id).isPresent()) {
            if (ingredientService.findAll().stream().filter(Ingredient::isSpicy).map(Ingredient::getId).count() == 4)
                throw new NoMoreSpicyIngredientsException();
            Ingredient newIngredient = ingredientService.findOne(id).get();
            newIngredient.setVeggie(veggie);
            newIngredient.setSpicy(spicy);
            newIngredient.setName(name);
            newIngredient.setDateUpdated(LocalDateTime.now());
            ingredientService.save(newIngredient);
            return newIngredient;
        } else
            throw new IngredientNotFoundException();
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteIngredient(@PathVariable Long id, ModelMap model) {
        logger.info("\u001B[33mDELETE method CALLED from IngredientController\u001B[0m");
        if (ingredientService.findOne(id).isPresent())
            ingredientService.delete(id);
        else
            throw new IngredientNotFoundException();
        return new ModelAndView("redirect:/admin/ingredients", model);
    }

    @GetMapping
    public Page<Ingredient> getIngredients(@PageableDefault(value = 5) Pageable pageable, @RequestParam(name = "spicy", required = false) boolean spicy) {
        logger.info("\u001B[33mGET method CALLED from IngredientController\u001B[0m");
        List<Ingredient> ingredients = ingredientService.findAll();
        if (!spicy) {
            if (!ingredients.isEmpty()) {
                Collections.sort(ingredients);
                return new PageImpl<>(ingredients);
            }
            throw new IngredientNotFoundException();
        } else {
            List<Ingredient> spicyIngredients = ingredients.stream().filter(Ingredient::isSpicy).sorted().collect(Collectors.toList());
            return new PageImpl<>(spicyIngredients);
        }
    }

    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable Long id) {
        logger.info("\u001B[33mGET{id} method CALLED from IngredientController\u001B[0m");
        if (ingredientService.findOne(id).isPresent())
            return ingredientService.findOne(id).get();
        throw new IngredientNotFoundException();
    }

    @GetMapping("/{id}/pizzas")
    public List<Pizza> getPizzasWithIngredient(@PathVariable(name = "id") Long ingredientID) {
        logger.info("\u001B[33mGET{pizzas}" +
                " method CALLED from IngredientController\u001B[0m");
        return pizzaIngredientService.findAll()
                .stream()
                .filter(ingr -> ingr.getIngredient().getId().equals(ingredientID))
                .map(PizzaIngredient::getPizza)
                .collect(Collectors.toList());
    }
}
