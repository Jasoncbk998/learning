package com.jason.hashtb;

/**
 * @Classname Emp
 * @Description TODO
 * @Date 2020/7/28 4:55 下午
 * @Created by jason
        模拟雇员
 */

public class Emp {
    public int id;
    public String name;
    public Emp next; //next 默认为 null
    public Emp(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}
