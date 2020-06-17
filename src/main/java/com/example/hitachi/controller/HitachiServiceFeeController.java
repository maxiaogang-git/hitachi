package com.example.hitachi.controller;


import com.example.hitachi.entity.HitachiServiceFeeEntity;
import com.example.hitachi.service.HitachiServiceFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class HitachiServiceFeeController {

    @Autowired
    private HitachiServiceFeeService hitachiServiceFeeService;


    @GetMapping(value = "/addServiceFeeBefore")
    public String addServiceFeeBefore(){
        return "/hitachi/add";
    }


    @PostMapping(value = "/addServiceFee")
    public String addServiceFee(@RequestParam Map<String ,Object> data){

        hitachiServiceFeeService.addServiceFee(data);
        return "redirect:/getServiceFeeInfo";
    }

    @GetMapping(value = "/getServiceFeeInfo")
    @ResponseBody
    public Map<String,Object> getServiceFeeInfo(Model model,
                                                @RequestParam(name = "currNo",defaultValue = "1") int currNo,
                                                @RequestParam(name = "currSize",defaultValue = "10") int currSize){
        Map<String,Object> resMap = hitachiServiceFeeService.getServiceFeeInfo(currNo,currSize);
        model.addAttribute(resMap);
        return resMap;
    }


    @PostMapping(value = "/batchDeleteServiceFee")
    public String batchDeleteServiceFee(@RequestBody List<String> list){
        hitachiServiceFeeService.batchDeleteServiceFee(list);
        return "redirect:/getServiceFeeInfo";
    }

}
