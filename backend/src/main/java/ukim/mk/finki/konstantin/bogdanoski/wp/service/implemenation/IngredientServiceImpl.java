package ukim.mk.finki.konstantin.bogdanoski.wp.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Ingredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.repository.IngredientRepository;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.IngredientService;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class IngredientServiceImpl extends BaseEntityCrudServiceImpl<Ingredient, IngredientRepository> implements IngredientService {
    private IngredientRepository repository;

    public IngredientServiceImpl(IngredientRepository repository) {
        this.repository = repository;
    }

    @Override
    protected IngredientRepository getRepository() {
        return repository;
    }
}
