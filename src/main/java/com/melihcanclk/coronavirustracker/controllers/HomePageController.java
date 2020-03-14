package com.melihcanclk.coronavirustracker.controllers;

import com.melihcanclk.coronavirustracker.models.LoadModel;
import com.melihcanclk.coronavirustracker.services.CoronaVirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {

    @Autowired
    CoronaVirusService coronaVirusService;

    @GetMapping("/")
    public String homePage( Model model) {
        List<LoadModel> allStats = coronaVirusService.getAllModels();
        int totalCases = allStats.stream().mapToInt(stat -> stat.getLastRecord()).sum();
        model.addAttribute("stats", allStats);
        model.addAttribute("totalCases", totalCases);

        return "home";
    }


}
