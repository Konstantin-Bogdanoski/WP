package ukim.mk.finki.konstantin.bogdanoski.wp.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Ingredient;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public interface IngredientService extends BaseEntityCrudService<Ingredient> {
}
