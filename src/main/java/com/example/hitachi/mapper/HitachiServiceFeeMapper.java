package com.example.hitachi.mapper;


import com.example.hitachi.entity.HitachiServiceFeeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HitachiServiceFeeMapper {

    List<HitachiServiceFeeEntity> getServiceFeeInfo(Map<String,Object> params);

    int addServiceFee(Map<String,Object> params);

    int getDataTotal();

    int batchDeleteServiceFee(List<String> list);


}
