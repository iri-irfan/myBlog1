package com.myblog.blog1;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestClass {

    public static void main(String[] args) {

        List<Integer> data= Arrays.asList(10,20,16,16,15,35,15,30);
        List<Integer> newData = data.stream().distinct().collect(Collectors.toList());
        System.out.println(newData);
        System.out.println("hello");
        System.out.println("hi");
    }
}
