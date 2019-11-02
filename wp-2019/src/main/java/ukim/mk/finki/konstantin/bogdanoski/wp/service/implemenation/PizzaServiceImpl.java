package ukim.mk.finki.konstantin.bogdanoski.wp.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Pizza;
import ukim.mk.finki.konstantin.bogdanoski.wp.repository.PizzaRepository;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaService;

import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class PizzaServiceImpl extends BaseEntityCrudServiceImpl<Pizza, PizzaRepository> implements PizzaService {

    private PizzaRepository repository;

    public PizzaServiceImpl(PizzaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected PizzaRepository getRepository() {
        return repository;
    }

    public List<Pizza> listPizzas() {
        return repository.findAll();
    }
}
