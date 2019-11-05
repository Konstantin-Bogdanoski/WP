package ukim.mk.finki.konstantin.bogdanoski.wp.service;

import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;

import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface UserService extends BaseEntityCrudService<User> {
    public Optional<User> findByUsername(String username);
}
