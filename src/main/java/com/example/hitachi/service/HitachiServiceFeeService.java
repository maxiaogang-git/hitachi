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

    public Map<String,Object> getServiceFeeInfo(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        int pageNo = ((Integer) params.get("pageNo") - 1) * (Integer) params.get("pageSize");
        params.put("pageNo",pageNo);
        List<HitachiServiceFeeEntity> list = hitachiServiceFeeMapper.getServiceFeeInfo(params);
        int total = hitachiServiceFeeMapper.getDataTotal(params);
        map.put("count",total);
        map.put("data",list);
        return map;

    }

    public int addServiceFee(Map<String,Object> params){
        String uuid= UUID.randomUUID().toString();
        params.put("uuid",uuid);
        Object obj = params.get("is_three_contract");
        if(obj == null){
            params.put("is_three_contract","0");
        }
        return hitachiServiceFeeMapper.addServiceFee(params);
    }




    public int batchDeleteServiceFee(List<String> list){
        return hitachiServiceFeeMapper.batchDeleteServiceFee(list);
    }


    public HitachiServiceFeeEntity getServiceFeeDataByKey(String uuid){
        return hitachiServiceFeeMapper.getServiceFeeDataByKey(uuid);

    }

    public int updateServiceFee(Map<String,Object> params){
        return hitachiServiceFeeMapper.updateServiceFee(params);
    }



}
