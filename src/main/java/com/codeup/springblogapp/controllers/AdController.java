package com.codeup.springblogapp.controllers;

import com.codeup.springblogapp.model.Ad;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdController {

    @GetMapping
    public String showAds(Model model) {
        List<Ad> adList = new ArrayList<>();
        Ad ad = new Ad("Car", "new");
        adList.add(ad);
        model.addAttribute("ads", adList);
        return "ads/index";
    }
}
