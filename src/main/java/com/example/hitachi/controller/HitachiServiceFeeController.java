package com.example.hitachi.controller;


import com.example.hitachi.entity.HitachiServiceFeeEntity;
import com.example.hitachi.service.HitachiServiceFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HitachiServiceFeeController {

    @Autowired
    private HitachiServiceFeeService hitachiServiceFeeService;


    /**
     * 跳转到增加服务费页面
     * @return
     */
    @GetMapping(value = "/addServiceFeeBefore")
    public String addServiceFeeBefore(){
        return "hitachi/add_parent";
    }


    /**
     * 增加服务费
     * @param data
     * @return
     */
    @PostMapping(value = "/addServiceFee")
    @ResponseBody
    public int addServiceFee(@RequestParam Map<String ,Object> data){
        return hitachiServiceFeeService.addServiceFee(data);
    }

    /**
     * 查询服务费列表
     * @param model
     * @param currNo
     * @param currSize
     * @return
     */
    @GetMapping(value = "/getServiceFeeInfo")
    @ResponseBody
    public Map<String,Object> getServiceFeeInfo(Model model,
                                                @RequestParam(name = "currNo",defaultValue = "1") int currNo,
                                                @RequestParam(name = "currSize",defaultValue = "10") int currSize,
                                                @RequestParam(name = "contract_no",defaultValue = "") String contract_no,
                                                @RequestParam(name = "contract_buyer",defaultValue = "") String contract_buyer,
                                                @RequestParam(name = "use_company",defaultValue = "") String use_company,
                                                @RequestParam(name = "person_liable",defaultValue = "") String person_liable,
                                                @RequestParam(name = "is_three_contract",defaultValue = "") String is_three_contract,
                                                @RequestParam(name = "send_start_date",defaultValue = "") String send_start_date,
                                                @RequestParam(name = "receive_start_date",defaultValue = "") String receive_start_date
                                                ){
        Map<String,Object> params = new HashMap<>();
        params.put("contract_no",contract_no.trim());
        params.put("contract_buyer",contract_buyer.trim());
        params.put("use_company",use_company.trim());
        params.put("person_liable",person_liable.trim());
        params.put("is_three_contract",is_three_contract);
        params.put("send_start_date",send_start_date);
        params.put("receive_start_date",receive_start_date);
        params.put("pageNo",currNo);
        params.put("pageSize",currSize);
        Map<String,Object> resMap = hitachiServiceFeeService.getServiceFeeInfo(params);
        model.addAttribute(resMap);
        return resMap;
    }


    /**
     * 批量删除
     * @param list
     * @return
     */
    @PostMapping(value = "/batchDeleteServiceFee")
    public String batchDeleteServiceFee(@RequestBody List<String> list){
        hitachiServiceFeeService.batchDeleteServiceFee(list);
        return "redirect:/getServiceFeeInfo";
    }

    /**
     * 跳转至修改上面table页面
     * @param uuid
     * @param model
     * @return
     */
    @GetMapping(value = "/editServiceFeeBefore")
    public String editServiceFeeBefore(@RequestParam(name = "uuid") String uuid,Model model){
        HitachiServiceFeeEntity obj = hitachiServiceFeeService.getServiceFeeDataByKey(uuid);
        model.addAttribute("data",obj);
        return "hitachi/edit_parent";
    }


    /**
     * 修改上面table信息
     * @param params
     * @return
     */
    @PutMapping(value = "/addServiceFee")
    @ResponseBody
    public int updateServiceFee(@RequestParam Map<String ,Object> params){
        return hitachiServiceFeeService.updateServiceFee(params);

    }

    /**
     * 查看服务费详情
     * @param model
     * @param currNo
     * @param currSize
     * @return
     */
    @GetMapping(value = "/getServiceFeeInfoChild")
    public String getServiceFeeInfoChild(Model model,
                                                @RequestParam(name = "currNo",defaultValue = "1") int currNo,
                                                @RequestParam(name = "currSize",defaultValue = "10") int currSize,
                                                @RequestParam(name = "contractNo",defaultValue = "") String contractNo,
                                                @RequestParam(name = "uuid") String uuid

    ){
        Map<String,Object> params = new HashMap<>();
        params.put("uuid",uuid);
        params.put("contract_no",contractNo.trim());
        params.put("pageNo",currNo);
        params.put("pageSize",currSize);
        Map<String,Object> resMap = hitachiServiceFeeService.getServiceFeeInfoChild(params);
        model.addAttribute("resMap",resMap);
        return "hitachi/details";
    }

    /**
     * 跳转至修改下面table页面
     * @param uuid
     * @param model
     * @return
     */
    @GetMapping(value = "/editServiceFeeChildBefore")
    public String editServiceFeeChildBefore(@RequestParam(name = "uuid", defaultValue = "") String uuid,Model model){
        HitachiServiceFeeEntity obj = hitachiServiceFeeService.getServiceFeeChildDataByKey(uuid);
        model.addAttribute("data",obj);
        return "hitachi/edit_child";
    }

    /**
     * 增加下面table信息
     * @param params
     * @return
     */
    @PostMapping(value = "/addServiceFeeChild")
    @ResponseBody
    public int addServiceFeeChild(@RequestParam Map<String ,Object> params){
        return hitachiServiceFeeService.addServiceFeeChild(params);

    }

    /**
     * 修改下面table信息
     * @param params
     * @return
     */
    @PutMapping(value = "/addServiceFeeChild")
    @ResponseBody
    public int updateServiceFeeChild(@RequestParam Map<String ,Object> params){
        return hitachiServiceFeeService.updateServiceFeeChild(params);

    }


    /**
     * 删除下面table数据
     * @param list
     * @return
     */
    @PostMapping(value = "/deleteServiceFeeChild")
    @ResponseBody
    public int deleteServiceFeeChild(@RequestBody List<String> list){
        return hitachiServiceFeeService.deleteServiceFeeChild(list);
    }

    /**
     * EXCEL下载
     * @throws Exception
     */
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) throws Exception{
        hitachiServiceFeeService.exportData(response);
    }
}
