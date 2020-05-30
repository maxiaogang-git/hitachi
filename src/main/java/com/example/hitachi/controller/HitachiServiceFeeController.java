package com.example.hitachi.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HitachiServiceFeeController {


    @GetMapping(value = "/addServiceFeeBefore")
    public String addServiceFeeBefore(){
        return "/hitachi/add";
    }

}
