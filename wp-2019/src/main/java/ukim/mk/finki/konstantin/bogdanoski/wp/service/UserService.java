package ukim.mk.finki.konstantin.bogdanoski.wp.service;

import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface UserService extends BaseEntityCrudService<User> {
    public User findByUsername(String username);
}
