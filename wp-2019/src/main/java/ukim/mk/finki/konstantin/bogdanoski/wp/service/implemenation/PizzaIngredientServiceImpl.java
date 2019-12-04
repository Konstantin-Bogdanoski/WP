package ukim.mk.finki.konstantin.bogdanoski.wp.service.implemenation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredientCompositeKey;
import ukim.mk.finki.konstantin.bogdanoski.wp.repository.PizzaIngredientRepository;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaIngredientService;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class PizzaIngredientServiceImpl implements PizzaIngredientService {
    private PizzaIngredientRepository repository;

    public PizzaIngredientServiceImpl(PizzaIngredientRepository repository) {
        this.repository = repository;
    }

    public PizzaIngredient save(PizzaIngredient entity) {
        return getRepository().save(entity);
    }

    public PizzaIngredientRepository getRepository() {
        return this.repository;
    }

    public List<PizzaIngredient> save(Iterable<PizzaIngredient> entities) {
        return getRepository().saveAll(entities);
    }

    public PizzaIngredient saveAndFlush(PizzaIngredient entity) {
        return getRepository().saveAndFlush(entity);
    }

    public List<PizzaIngredient> findAll() {
        return getRepository().findAll();
    }

    public Page<PizzaIngredient> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    public List<PizzaIngredient> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    public long count() {
        return getRepository().count();
    }

    public Optional<PizzaIngredient> findOne(PizzaIngredientCompositeKey id) {
        return getRepository().findById(id);
    }

    public boolean exists(PizzaIngredientCompositeKey id) {
        return getRepository().existsById(id);
    }

    public void delete(PizzaIngredientCompositeKey id) {
        getRepository().deleteById(id);
    }

    public void delete(PizzaIngredient entity) {
        getRepository().delete(entity);
    }

    public void delete(Iterable<PizzaIngredient> entities) {
        getRepository().deleteAll(entities);
    }

    @Override
    public void deleteAllByPizza(Pizza pizza) {
        getRepository().deletePizzaIngredientByPizza(pizza);
    }

    public void deleteAll() {
        getRepository().deleteAll();
    }
}
