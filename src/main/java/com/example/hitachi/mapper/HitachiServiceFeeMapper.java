package com.example.hitachi.mapper;


import com.example.hitachi.entity.HitachiServiceFeeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface HitachiServiceFeeMapper {

    List<HitachiServiceFeeEntity> getServiceFeeInfo(@Param(value = "pageNo") int pageNo,
                                                    @Param(value = "pageSize") int pageSize);


    int addServiceFee(Map<String,Object> params);

    int getDataTotal();


}