package ukim.mk.finki.konstantin.bogdanoski.wp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.PizzaIngredientCompositeKey;
import ukim.mk.finki.konstantin.bogdanoski.wp.repository.PizzaIngredientRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface PizzaIngredientService {

    public PizzaIngredient save(PizzaIngredient entity);

    public PizzaIngredientRepository getRepository();

    public List<PizzaIngredient> save(Iterable<PizzaIngredient> entities);


    public PizzaIngredient saveAndFlush(PizzaIngredient entity);


    public List<PizzaIngredient> findAll();


    public Page<PizzaIngredient> findAll(Pageable pageable);

    public List<PizzaIngredient> findAll(Sort sort);

    public long count();


    public Optional<PizzaIngredient> findOne(PizzaIngredientCompositeKey id);

    public boolean exists(PizzaIngredientCompositeKey id);

    public void delete(PizzaIngredientCompositeKey id);

    public void delete(PizzaIngredient entity);

    public void delete(Iterable<PizzaIngredient> entities);

    public void deleteAll();
}
