package com.example.chupeng.rxjavafilteroperatordemo;

import java.util.List;

/**
 * Created by ChuPeng on 2017/4/16.
 */

public class Student
{
    private String name;//姓名
    private String number;//学号
    private String gender;//性别
    private List<Integer> scoreList;//课程分数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Integer> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Integer> scoreList) {
        this.scoreList = scoreList;
    }
}
