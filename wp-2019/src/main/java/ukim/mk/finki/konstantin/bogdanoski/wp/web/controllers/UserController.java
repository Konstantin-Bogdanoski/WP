package ukim.mk.finki.konstantin.bogdanoski.wp.web.controllers;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.IngredientNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.NoMoreSpicyIngredientsException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.UnauthorizedAdminDeletionException;
import ukim.mk.finki.konstantin.bogdanoski.wp.exception.UserNotFoundException;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.Ingredient;
import ukim.mk.finki.konstantin.bogdanoski.wp.model.user.User;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.UserService;

import java.time.LocalDateTime;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteIngredient(@PathVariable Long id) {
        if (userService.findOne(id).isPresent()) {
            if (userService.findOne(id).get().getUserRole().equals("ROLE_ADMIN"))
                throw new UnauthorizedAdminDeletionException();
            userService.delete(id);
        } else
            throw new UserNotFoundException();
        return new ModelAndView("redirect:/admin/users");
    }

    @PatchMapping("/{id}")
    public ModelAndView editUser(@PathVariable Long id, @ModelAttribute(name = "newUser") User newUser) {
        newUser.setId(id);
        if (userService.findOne(id).isPresent()) {
            newUser.setPassword(userService.findOne(id).get().getPassword());
            newUser.setDateUpdated(LocalDateTime.now());
            userService.save(newUser);
        } else
            throw new UserNotFoundException();
        return new ModelAndView("redirect:/admin/users");
    }
}
