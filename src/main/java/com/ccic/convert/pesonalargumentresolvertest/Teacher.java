package com.ccic.convert.pesonalargumentresolvertest;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by 8000600758 on 2018/11/20.
 */
@Data
public class Teacher {


    private String name;

    private int age;

    private Date date;

    private List<String> love;


    public Teacher() {
        super();
    }
}