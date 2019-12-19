package ukim.mk.finki.konstantin.bogdanoski.wp.service.implemenation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.PizzaNotFoundException;
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

    @Override
    public Pizza findByName(String name) {
        if (repository.findByName(name).isPresent())
            return repository.findByName(name).get();
        throw new PizzaNotFoundException();
    }

    @Override
    public Page<Pizza> findPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Pizza> findByPizzaNameLike(String searchTerm) {
        return repository.findAllByNameContains(searchTerm);
    }
}
