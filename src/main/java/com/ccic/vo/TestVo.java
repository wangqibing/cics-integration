package com.ccic.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 8000600758 on 2018/10/18.
 */
@Data
public class TestVo {
    private String name;
    private Integer age;

}
