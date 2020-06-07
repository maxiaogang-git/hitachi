package com.example.hitachi.service;


import com.example.hitachi.entity.HitachiServiceFeeEntity;
import com.example.hitachi.mapper.HitachiServiceFeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HitachiServiceFeeService {

    @Autowired
    private HitachiServiceFeeMapper hitachiServiceFeeMapper;

    public Map<String,Object> getServiceFeeInfo(int pageNo,int pageSize){
        Map<String,Object> map = new HashMap<>();
        pageNo = (pageNo - 1) * pageSize;
        List<HitachiServiceFeeEntity> list = hitachiServiceFeeMapper.getServiceFeeInfo(pageNo,pageSize);
        int total = hitachiServiceFeeMapper.getDataTotal();
        map.put("count",total);
        map.put("data",list);
        return map;

    }

    public int addServiceFee(Map<String,Object> params){
        String uuid= UUID.randomUUID().toString();
        params.put("uuid",uuid);
        String is_three_contract = params.get("is_three_contract").toString();
        if(is_three_contract == null){
            is_three_contract = "0";
            params.put("is_three_contract",is_three_contract);
        }
        return hitachiServiceFeeMapper.addServiceFee(params);
    }



}
