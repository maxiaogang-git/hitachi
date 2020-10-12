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

    int addServiceFeeChild(Map<String,Object> params);

    int getDataTotal(Map<String,Object> params);

    int batchDeleteServiceFee(List<String> list);

    HitachiServiceFeeEntity getServiceFeeDataByKey(@Param(value = "uuid") String uuid);

    int updateServiceFee(Map<String,Object> params);

    int updateChildByParent(Map<String,Object> params);


    List<HitachiServiceFeeEntity> getServiceFeeInfoChild(Map<String,Object> params);

    int getDataTotalChild(Map<String,Object> params);

    HitachiServiceFeeEntity getServiceFeeChildDataByKey(@Param(value = "uuid") String uuid);

    int updateServiceFeeChild(Map<String,Object> params);


    int getServeFeeByContractNO(Map<String,Object> params);


    int deleteServiceFeeChild(@Param(value="list")List<String> list,@Param(value="type") String type);

    List<String> getServeFeeChildList(List<String> list);

    List<HitachiServiceFeeEntity> getExportData();

    List<HitachiServiceFeeEntity> getExportChildData();

}
