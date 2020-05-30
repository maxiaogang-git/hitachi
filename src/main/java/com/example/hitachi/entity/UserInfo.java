package com.example.hitachi.entity;


public class UserInfo {

    private String name;
    private String empno;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpno() {
        return empno;
    }

    public void setEmpno(String empno) {
        this.empno = empno;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", empno='" + empno + '\'' +
                '}';
    }
}
