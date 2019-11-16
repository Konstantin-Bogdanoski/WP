package ukim.mk.finki.konstantin.bogdanoski.wp.web;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ukim.mk.finki.konstantin.bogdanoski.wp.service.PizzaService;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Controller
@AllArgsConstructor
public class MainController {
    private PizzaService pizzaService;

    @RequestMapping(value = "/controller", produces = "application/json")
    @ResponseBody
    public String pizza() {
        StringBuilder stringBuilder = new StringBuilder();
        pizzaService.findAll().stream().forEach(pizza -> {
            stringBuilder.append(pizza.getName());
        });
        return stringBuilder.toString();
    }
}
