package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.IngredientNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.PizzaAlreadyExistsException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.PizzaIngredientNotVeggieException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.PizzaNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Ingredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredientCompositeKey;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.IngredientService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaIngredientService;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public ModelAndView addPizza(@ModelAttribute(name = "pizza") Pizza newPizza, @RequestParam(name = "newIngredients") ArrayList<Long> newIngredients) {
        pizzaService.findAll().forEach(pizza1 -> {
            if (pizza1.getName().equals(newPizza.getName()))
                throw new PizzaAlreadyExistsException();
        });
        pizzaService.save(newPizza);
        Pizza pizza = pizzaService.findByName(newPizza.getName());
        List<PizzaIngredient> pizzaIngredients = new ArrayList<>();
        newIngredients.forEach(ing -> {
            if (ingredientService.findOne(ing).isPresent()) {
                PizzaIngredient pizzaIngredient = new PizzaIngredient();
                pizzaIngredient.setPizza(pizza);
                pizzaIngredient.setIngredient(ingredientService.findOne(ing).get());
                PizzaIngredientCompositeKey compositeKey = new PizzaIngredientCompositeKey();
                compositeKey.setPizzaId(pizza.getId());
                compositeKey.setIngredientId(ing);
                pizzaIngredient.setCompositeKey(compositeKey);
                pizzaIngredients.add(pizzaIngredient);
                pizzaIngredientService.save(pizzaIngredient);
            } else
                throw new IngredientNotFoundException();
        });

        pizza.setPizzaIngredients(pizzaIngredients);
        pizzaService.save(pizza);

        if (pizza.isVeggie())
            pizza.getPizzaIngredients().forEach(ingredient -> {
                if (!ingredient.getIngredient().isVeggie()) {
                    pizzaIngredientService.deleteAllByPizza(pizza);
                    pizzaService.delete(pizza.getId());
                    throw new PizzaIngredientNotVeggieException();
                }
            });
        return new ModelAndView("redirect:/admin/pizzas");
    }

    @PutMapping("/{id}")
    public ModelAndView editPizza(@PathVariable(name = "id") Long pizzaId, @ModelAttribute Pizza pizza, @RequestParam(name = "newIngredients") ArrayList<Long> newIngredients) {
        pizzaService.findOne(pizzaId);
        pizza.setId(pizzaId);
        List<PizzaIngredient> pizzaIngredients = new ArrayList<>();
        List<PizzaIngredient> oldIngredients = pizzaIngredientService.findAll();
        pizzaIngredientService.deleteAllByPizza(pizza);
        newIngredients.forEach(ing -> {
            if (ingredientService.findOne(ing).isPresent()) {
                if (pizza.isVeggie() && !ingredientService.findOne(ing).get().isVeggie())
                    throw new PizzaIngredientNotVeggieException();
                PizzaIngredient pizzaIngredient = new PizzaIngredient();
                pizzaIngredient.setIngredient(ingredientService.findOne(ing).get());
                pizzaIngredient.setPizza(pizza);
                pizzaIngredient.setAmount(200);
                PizzaIngredientCompositeKey compositeKey = new PizzaIngredientCompositeKey();
                compositeKey.setIngredientId(ing);
                compositeKey.setPizzaId(pizzaId);
                pizzaIngredient.setCompositeKey(compositeKey);
                pizzaIngredients.add(pizzaIngredient);
                pizzaIngredientService.save(pizzaIngredient);
            }
        });
        pizza.setPizzaIngredients(pizzaIngredients);
        pizza.setDateUpdated(LocalDateTime.now());
        pizzaService.save(pizza);
        return new ModelAndView("redirect:/admin/pizzas");
    }

    @DeleteMapping("/{id}")
    public ModelAndView deletePizza(@PathVariable(name = "id") Long pizzaId) {
        if (pizzaService.findOne(pizzaId).isPresent()) {
            pizzaIngredientService.deleteAllByPizza(pizzaService.findOne(pizzaId).get());
            pizzaService.delete(pizzaId);
        } else
            throw new PizzaNotFoundException();
        return new ModelAndView("redirect:/admin/pizzas");
    }

    @GetMapping
    public Page<Pizza> getPizzas(@PageableDefault(value = 10) Pageable pageable, @RequestParam(name = "totalIngredients", required = false, defaultValue = "0") float totalIngredients) {
        if (totalIngredients <= 0)
            return pizzaService.findPaginated(pageable);

        //GET PIZZAS WITH INGREDIENT COUNT LESS THAN TOTALINGREDIENTAMOUNT
        List<Pizza> finalList = new ArrayList<>();
        pizzaService.findAll().forEach(pizza -> {
            if (pizza.getPizzaIngredients().size() < totalIngredients)
                finalList.add(pizza);
        });

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), pizzaService.findAll().size());
        Page<Pizza> pages = new PageImpl<Pizza>(finalList.subList(start, end), pageable, finalList.size());
        return new PageImpl<Pizza>(finalList);

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
