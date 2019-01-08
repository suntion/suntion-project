package suns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public Object index(Model model){
        model.addAttribute("name","suntion");
        return "suntion";
    }
}
