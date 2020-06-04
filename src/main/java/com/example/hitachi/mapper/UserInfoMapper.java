package com.example.hitachi.mapper;


import com.example.hitachi.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoMapper {

    UserInfo getUser(@Param(value = "userName") String userName,
                     @Param(value = "pss") String pass);


}
