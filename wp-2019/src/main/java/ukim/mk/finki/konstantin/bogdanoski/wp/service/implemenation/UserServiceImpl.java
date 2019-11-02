package ukim.mk.finki.konstantin.bogdanoski.wp.service.implemenation;

import org.springframework.stereotype.Service;
import ukim.mk.finki.konstantin.bogdanoski.wp.exceptions.UserNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.repository.UserRepository;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

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
    public User findByUsername(String username) {
        if (repository.findByUsername(username).isPresent())
            return repository.findByUsername(username).get();
        else
            throw new UserNotFoundException();
    }
}
