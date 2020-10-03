package com.example.hitachi.service;


import com.example.hitachi.entity.HitachiServiceFeeEntity;
import com.example.hitachi.mapper.HitachiServiceFeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        String send_start_date = (String)params.get("send_start_date");
        String receive_start_date = (String)params.get("receive_start_date");
        if(!StringUtils.isEmpty(send_start_date)){
            params.put("send_start_date",send_start_date.split("-")[0].trim());
            params.put("send_end_date",send_start_date.split("-")[1].trim());
        }
        if(!StringUtils.isEmpty(receive_start_date)){
            params.put("receive_start_date",receive_start_date.split("-")[0].trim());
            params.put("receive_end_date",receive_start_date.split("-")[1].trim());

        }
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
        int count = hitachiServiceFeeMapper.getServeFeeByContractNO(params);
        if(count>0){
            return -1;
        }
        return hitachiServiceFeeMapper.addServiceFee(params);
//        return hitachiServiceFeeMapper.addServiceFeeChild(params);
    }




    public int batchDeleteServiceFee(List<String> list){
        List<String> contractNoList = hitachiServiceFeeMapper.getServeFeeChildList(list);
        hitachiServiceFeeMapper.deleteServiceFeeChild(contractNoList,"parent");
        return hitachiServiceFeeMapper.batchDeleteServiceFee(list);
    }


    public HitachiServiceFeeEntity getServiceFeeDataByKey(String uuid){
        return hitachiServiceFeeMapper.getServiceFeeDataByKey(uuid);

    }

    public int updateServiceFee(Map<String,Object> params){
        return hitachiServiceFeeMapper.updateServiceFee(params);
    }



    public Map<String,Object> getServiceFeeInfoChild(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        int pageNo = ((Integer) params.get("pageNo") - 1) * (Integer) params.get("pageSize");
        params.put("pageNo",pageNo);
        HitachiServiceFeeEntity obj = hitachiServiceFeeMapper.getServiceFeeDataByKey(params.get("uuid").toString());
        List<HitachiServiceFeeEntity> list = hitachiServiceFeeMapper.getServiceFeeInfoChild(params);
        int total = hitachiServiceFeeMapper.getDataTotalChild(params);
        map.put("count",total);
        map.put("data",list);
        map.put("obj",obj);
        return map;

    }


    /**
     * 修改下面table
     * @param uuid
     * @return
     */
    public HitachiServiceFeeEntity getServiceFeeChildDataByKey(String uuid){
        return hitachiServiceFeeMapper.getServiceFeeChildDataByKey(uuid);

    }


    /**
     * 增加子table信息
     * @param params
     * @return
     */
    public int addServiceFeeChild(Map<String,Object> params){
        String uuid= UUID.randomUUID().toString();
        params.put("uuid",uuid);
        return hitachiServiceFeeMapper.addServiceFeeChild(params);
    }

    /**
     * 修改子table信息
     * @param params
     * @return
     */
    public int updateServiceFeeChild(Map<String,Object> params){
        return hitachiServiceFeeMapper.updateServiceFeeChild(params);
    }


    /**
     * 删除下面table数据
     * @param list
     * @return
     */
    public int deleteServiceFeeChild(List<String> list){
        return hitachiServiceFeeMapper.deleteServiceFeeChild(list,"");
    }


}
