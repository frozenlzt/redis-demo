package com.liang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@SpringBootApplication
public class AuxApplication {
    public static void main(String[] args) {
        HashMap<String,String> s1=new HashMap<>();
        s1.put("1","姓名");
        s1.put("2","年龄");
        s1.put("3","性别");
        HashMap<String,String> s2=new HashMap<>();
        s2.put("11","姓名1");
        s2.put("21","年龄1");
        s2.put("31","性别1");
        List<HashMap> s3=new ArrayList<>();
        s3.add(s1);
        s3.add(s2);
        HashMap<String,Object> inMap=new HashMap<>();
        inMap.put("123",s3);
        ArrayList<HashMap> s4=(ArrayList)inMap.get("123");
        System.out.println(s4.get(0).get("1"));
        System.out.println(inMap);
        //SpringApplication.run(AuxApplication.class,args);
    }
}
