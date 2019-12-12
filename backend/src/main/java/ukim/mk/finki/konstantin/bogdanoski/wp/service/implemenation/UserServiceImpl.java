package ukim.mk.finki.konstantin.bogdanoski.wp.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.repository.UserRepository;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class UserServiceImpl extends BaseEntityCrudServiceImpl<User, UserRepository> implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected UserRepository getRepository() {
        return repository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
