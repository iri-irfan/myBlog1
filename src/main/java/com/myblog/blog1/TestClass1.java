package com.myblog.blog1;

import java.lang.reflect.Array;
import java.util.Arrays;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TestClass1 {
    public static void main(String[] args) {
     List<Employee> employees = Arrays.asList(
             new Employee("mike", 30, "chennai" ),
             new Employee("adam", 34, "bangalore"),
             new Employee("sam", 34, "chennai"),
             new Employee("stallin", 29, "pune")
     );
        Map<String, List<Employee>> group = employees.stream().collect(Collectors.groupingBy(e -> e.getCity()));

        System.out.println(group);

        for (Map.Entry<String, List<Employee>> entry : group.entrySet()) {
            String city = entry.getKey();
            List<Employee> employeesWithCity = entry.getValue();
            System.out.println("city:"+city+"----");

            for(Employee e:employeesWithCity ){
                System.out.println(e.getAge());
                System.out.println(e.getName());

            }
        }


    }

    }

