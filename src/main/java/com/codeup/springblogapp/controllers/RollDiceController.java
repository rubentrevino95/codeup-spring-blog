package com.codeup.springblogapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class RollDiceController {

    @GetMapping("/roll-dice")
    public String showRollDice() {
        return "rollDice";
    }


    @GetMapping("/roll-dice/{num}")
    @ResponseBody
    public String test (@PathVariable int num, Model model) {
        Boolean match = false;
        Random r = new Random();
        int diceRoll = 0;
        for (int i = 0; i < 50; i++) {
        diceRoll = r.nextInt(6);
        diceRoll++;
        }
        String response;
        model.addAttribute("dice-roll", diceRoll);
        model.addAttribute("num", num);
        if (num == diceRoll) {
            response = "You rolled the matching number of " + num;
        }

        return "roll-dice";
    }
}
