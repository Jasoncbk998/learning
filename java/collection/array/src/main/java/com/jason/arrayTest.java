package com.jason;

import com.jason.doamin.Person;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * ClassName arrayTest
 * Description
 * Create by Jason
 * Date 2020/6/27 17:20
 */
public class arrayTest {
    public static void main(String[] args) {
        //数组
        Person[] ps = {new Person("jack", 34)
                , new Person("lucy", 50)
                , new Person("lili", 10)
                ,new Person("jack", 34)};
        System.out.println(ps);
        //数组2 List
        List<Person> personList = Arrays.asList(ps);
        System.out.println("List:"+personList);
        // list 2 set
        HashSet<Person> setPeople = new HashSet<>();
        setPeople.addAll(personList);
        System.out.println("HashSet:"+setPeople);
    }

}
