package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping(value = "/pizzas")
@CrossOrigin(origins = "http://localhost:3000")
public class PizzaController {
    private PizzaService pizzaService;
    private PizzaIngredientService pizzaIngredientService;
    private IngredientService ingredientService;
    private final Logger logger;
    private static final Pattern p = Pattern.compile("[^\\d]*[\\d]+[^\\d]+([\\d]+)");

    public PizzaController(PizzaService pizzaService, PizzaIngredientService pizzaIngredientService, IngredientService ingredientService, Logger logger) {
        this.pizzaService = pizzaService;
        this.pizzaIngredientService = pizzaIngredientService;
        this.ingredientService = ingredientService;
        this.logger = logger;
    }

    /*@PostMapping
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
    }*/

    @PostMapping
    public Pizza addPizza(@RequestParam Map<String, String> newIngredients) {
        String name = newIngredients.get("name");
        String description = newIngredients.get("description");
        boolean veggie = newIngredients.get("veggie").equals("true");
        pizzaService.findAll().forEach(pizza1 -> {
            if (pizza1.getName().equals(name))
                throw new PizzaAlreadyExistsException();
        });

        Set<String> keys = newIngredients.keySet();
        Map<String, String> newIngr = new HashMap<>();
        keys.forEach(val -> {
            if (val.contains("newIngredient")) {
                String amount = newIngredients.get(val);
                Matcher m = p.matcher(val);
                String ingID = null;
                if (m.find()) {
                    ingID = (m.group(1)); // second matched digits
                }
                newIngr.put(ingID, amount);
            }
        });

        Pizza newPizza = new Pizza();
        newPizza.setName(name);
        newPizza.setDescription(description);
        newPizza.setVeggie(veggie);
        pizzaService.save(newPizza);
        Pizza pizza = pizzaService.findByName(name);
        List<PizzaIngredient> pizzaIngredients = new ArrayList<>();
        newIngr.forEach((ing, amount) -> {
            if (ingredientService.findOne(Long.parseLong(ing)).isPresent()) {
                PizzaIngredient pizzaIngredient = new PizzaIngredient();
                pizzaIngredient.setPizza(pizza);
                pizzaIngredient.setIngredient(ingredientService.findOne(Long.parseLong(ing)).get());
                PizzaIngredientCompositeKey compositeKey = new PizzaIngredientCompositeKey();
                compositeKey.setPizzaId(pizza.getId());
                compositeKey.setIngredientId(Long.parseLong(ing));
                pizzaIngredient.setCompositeKey(compositeKey);
                pizzaIngredient.setAmount(Float.parseFloat(amount));
                pizzaIngredients.add(pizzaIngredient);
                pizzaIngredientService.save(pizzaIngredient);
            } else
                throw new IngredientNotFoundException();
        });

        pizza.setPizzaIngredients(pizzaIngredients);
        pizza.setDateCreated(LocalDateTime.now());
        pizza.setDateUpdated(LocalDateTime.now());
        pizzaService.save(pizza);

        if (pizza.isVeggie())
            pizza.getPizzaIngredients().forEach(ingredient -> {
                if (!ingredient.getIngredient().isVeggie()) {
                    pizzaIngredientService.deleteAllByPizza(pizza);
                    pizzaService.delete(pizza.getId());
                    throw new PizzaIngredientNotVeggieException();
                }
            });
        return pizza;
    }

    @PutMapping("/{id}")
    public ModelAndView editPizza(@PathVariable(name = "id") Long pizzaId, @ModelAttribute Pizza pizza, @RequestParam(name = "newIngredients") ArrayList<Long> newIngredients) {
        pizzaService.findOne(pizzaId);
        pizza.setId(pizzaId);
        List<PizzaIngredient> pizzaIngredients = new ArrayList<>();
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
    public Pizza deletePizza(@PathVariable(name = "id") Long pizzaId) {
        logger.info("\u001B[33mDELETE method CALLED from PizzaController\u001B[0m");
        Pizza pizza = pizzaService.findOne(pizzaId).get();
        if (pizzaService.findOne(pizzaId).isPresent()) {
            pizzaIngredientService.deleteAllByPizza(pizzaService.findOne(pizzaId).get());
            pizzaService.delete(pizzaId);
        } else
            throw new PizzaNotFoundException();
        return pizza;
    }

    @GetMapping
    public Page<Pizza> getPizzas(@RequestParam(name = "totalIngredients", required = false, defaultValue = "0") Long totalIngredients, Pageable pageable) {
        logger.info("\u001B[33mGET method CALLED from PizzaController\u001B[0m");
        if (totalIngredients <= 0)
            return pizzaService.findPaginated(pageable);

        List<Pizza> finalList = new ArrayList<>();
        pizzaService.findAll().forEach(pizza -> {
            if (pizza.getPizzaIngredients().size() < totalIngredients)
                finalList.add(pizza);
        });

        return new PageImpl<>(finalList);
    }

    @GetMapping("/{id}")
    public Pizza getPizza(@PathVariable(name = "id") Long pizzaId) {
        logger.info("\u001B[33mGET{id} method CALLED from PizzaController\u001B[0m");
        if (pizzaService.findOne(pizzaId).isPresent())
            return pizzaService.findOne(pizzaId).get();
        throw new PizzaNotFoundException();
    }

    @GetMapping("/compare")
    public List<Ingredient> comparePizzas(@RequestParam(name = "pizza1") Long id1, @RequestParam(name = "pizza2") Long id2) {
        logger.info("\u001B[33mGET{compare} method CALLED from PizzaController\u001B[0m");
        if (pizzaService.findOne(id1).isPresent() && pizzaService.findOne(id2).isPresent()) {
            Pizza pizza1 = pizzaService.findOne(id1).get();
            Pizza pizza2 = pizzaService.findOne(id2).get();
            List<Ingredient> ingredients = new ArrayList<>();
            pizza1.getPizzaIngredients().forEach(ing1 -> pizza2.getPizzaIngredients().forEach(ing2 -> {
                if (ing1.getIngredient().equals(ing2.getIngredient()))
                    ingredients.add(ing1.getIngredient());
            }));
            return ingredients;
        }
        throw new PizzaNotFoundException();
    }

    @GetMapping("/{id}/ingredients")
    public Map<String, Float> pizzaIngredients(@PathVariable(name = "id") Long pizzaId) {
        logger.info("\u001B[33mGET{ingredients} method CALLED from PizzaController\u001B[0m");
        Map<String, Float> ingredients = new HashMap<>();
        if (!pizzaService.findOne(pizzaId).isPresent())
            throw new PizzaNotFoundException();
        pizzaService.findOne(pizzaId).get().getPizzaIngredients().forEach(pizzaIngredient -> ingredients.put(pizzaIngredient.getIngredient().getName(), pizzaIngredient.getAmount()));
        return ingredients;
    }
}
