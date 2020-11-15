package com.example.hitachi.utils;

import java.util.UUID;

public class UUID_TEST {

    public  static void main(String args[]){

        for (int i=0;i<6000;i++){
            String uuid= UUID.randomUUID().toString();
            System.out.println(uuid);

        }
    }
}
