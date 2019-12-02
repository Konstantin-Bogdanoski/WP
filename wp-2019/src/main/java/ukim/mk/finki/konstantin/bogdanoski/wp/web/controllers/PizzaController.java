package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.web.bind.annotation.*;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.PizzaAlreadyExistsException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.PizzaIngredientNotVeggieException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.PizzaNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Ingredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.IngredientService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaIngredientService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaService;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping(value = "/pizzas")
public class PizzaController {
    private PizzaService pizzaService;
    private PizzaIngredientService pizzaIngredientService;
    private IngredientService ingredientService;

    public PizzaController(PizzaService pizzaService, PizzaIngredientService pizzaIngredientService, IngredientService ingredientService) {
        this.pizzaService = pizzaService;
        this.pizzaIngredientService = pizzaIngredientService;
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public void addPizza(@ModelAttribute Pizza pizza) {
        pizzaService.findAll().forEach(pizza1 -> {
            if (pizza1.getName().equals(pizza.getName()))
                throw new PizzaAlreadyExistsException();
        });
        if (pizza.isVeggie())
            pizza.getPizzaIngredients().forEach(ingredient -> {
                if (!ingredient.getIngredient().isVeggie())
                    throw new PizzaIngredientNotVeggieException();
            });
        pizzaService.save(pizza);
    }

    @PutMapping("/{id}")
    public void editPizza(@PathVariable(name = "id") Long pizzaId, @ModelAttribute Pizza pizza) {
        pizzaService.findByName(pizza.getName());
        pizza.setId(pizzaId);
        pizzaService.save(pizza);
    }

    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable(name = "id") Long pizzaId) {
        if (pizzaService.findOne(pizzaId).isPresent())
            pizzaService.delete(pizzaId);
        else
            throw new PizzaNotFoundException();
    }

    @GetMapping
    public List<Pizza> getPizzas(@RequestParam(name = "totalIngredients", required = false, defaultValue = "0") float totalIngredients) {
        if (totalIngredients <= 0)
            return pizzaService.findAll();

        //GET PIZZAS WITH INGREDIENT COUNT LESS THAN TOTALINGREDIENTAMOUNT
        List<Pizza> finalList = new ArrayList<>();
        pizzaService.findAll().forEach(pizza -> {
            if (pizza.getPizzaIngredients().size() < totalIngredients)
                finalList.add(pizza);
        });
        return finalList;

        //GET PIZZAS WITH TOTAL INGREDIENT AMOUNT LESS THAN TOTALINGREDIENTAMOUNT
        //=======================================================================
        /*List<Pizza> pizzaList = pizzaService.findAll();
        List<Pizza> finalList = new ArrayList<>();
        for (Pizza pizza : pizzaList) {
            List<PizzaIngredient> ingredients = pizza.getPizzaIngredients();
            float totalIng = 0;
            for (PizzaIngredient ing : ingredients) {
                totalIng += ing.getAmount();
            }
            if (totalIng < totalIngredients)
                finalList.add(pizza);
        }
        return finalList;*/
    }

    @GetMapping("/{id}")
    public Pizza getPizza(@PathVariable(name = "id") Long pizzaId) {
        if (pizzaService.findOne(pizzaId).isPresent())
            return pizzaService.findOne(pizzaId).get();
        throw new PizzaNotFoundException();
    }

    @GetMapping("/compare")
    public List<Ingredient> comparePizzas(@RequestParam(name = "pizza1") Long id1, @RequestParam(name = "pizza2") Long id2) {
        if (pizzaService.findOne(id1).isPresent() && pizzaService.findOne(id2).isPresent()) {
            Pizza pizza1 = pizzaService.findOne(id1).get();
            Pizza pizza2 = pizzaService.findOne(id2).get();
            List<Ingredient> ingredients = new ArrayList<>();
            pizza1.getPizzaIngredients().forEach(ing1 -> {
                pizza2.getPizzaIngredients().forEach(ing2 -> {
                    if (ing1.getIngredient().equals(ing2.getIngredient()))
                        ingredients.add(ing1.getIngredient());
                });
            });
            return ingredients;
        }
        throw new PizzaNotFoundException();
    }
}
