package org.learning.java.springlamiapizzeriacrud;

import jakarta.validation.Valid;
import org.learning.java.springlamiapizzeriacrud.model.Pizza;
import org.learning.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class pizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping("/")
    public String index(Model model){
        List<Pizza> pizzasList = pizzaRepository.findAll();
        model.addAttribute("pizza", pizzasList);
        return "index";
    }

    @GetMapping("/pizza/{pizzaId}")
    public String pizza(@PathVariable("pizzaId") Integer id, Model model){
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isPresent()){
            Pizza pizzaFromDB = pizzaOptional.get();
            model.addAttribute("pizza", pizzaFromDB);
            return "detail";
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("pizza", new Pizza());
        return "form";
    }

    @PostMapping ("/create")
    public String doCreate(@Valid @ModelAttribute("pizza") Pizza fromPizza,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "form";
        }
        pizzaRepository.save(fromPizza);
        return "redirect:/";
    }

}
