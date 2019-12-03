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
    public ModelAndView deleteIngredient(@PathVariable Long id, ModelMap model) {
        if (userService.findOne(id).isPresent())
            userService.delete(id);
        else
            throw new UserNotFoundException();
        return new ModelAndView("redirect:/admin/users", model);
    }

    @PatchMapping("/{id}")
    public ModelAndView editUser(@PathVariable Long id, @ModelAttribute(name = "newUser") User newUser, ModelMap model) {
        newUser.setId(id);
        if (userService.findOne(id).isPresent()) {
            if (newUser.getUserRole().equals("ROLE_ADMIN"))
                throw new UnauthorizedAdminDeletionException();
            userService.save(newUser);
        } else
            throw new UserNotFoundException();
        return new ModelAndView("redirect:/admin/users", model);
    }
}
