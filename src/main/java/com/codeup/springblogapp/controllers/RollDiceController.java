package com.codeup.springblogapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    public String startGame() {
        return "rollDice";
    }

    @PostMapping("/roll-dice/{num}")
    public String test (@RequestParam(name = "guess") int guess, Model model) {
        Boolean match = false;
        Random r = new Random();
        int diceRoll = 0;
        model.addAttribute("dice-roll", diceRoll);
        model.addAttribute("guess", guess);

        for (int i = 0; i < 50; i++) {
        diceRoll = r.nextInt(6);
        diceRoll++;
        }
        String response;
        if (guess == diceRoll) {
            response = "You rolled the matching number of " + guess;
        }

        return "diceResults";
    }
}
