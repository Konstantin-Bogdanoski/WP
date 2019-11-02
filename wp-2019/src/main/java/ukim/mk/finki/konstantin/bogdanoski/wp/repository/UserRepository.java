package ukim.mk.finki.konstantin.bogdanoski.wp.repository;

import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;

import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface UserRepository extends JpaSpecificationRepository<User> {
    Optional<User> findByUsername(String username);
}
