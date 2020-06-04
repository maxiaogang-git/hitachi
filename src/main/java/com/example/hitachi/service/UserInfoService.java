package com.example.hitachi.service;

import com.example.hitachi.entity.UserInfo;
import com.example.hitachi.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo getUserByUserNameAndPass(String userName,String pass){

        return userInfoMapper.getUser(userName,pass);
    }


}
