package com.ccic.test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 8000600758 on 2018/10/22.
 */
public class TestMain {
    public static void main(String[] args) throws JsonProcessingException {

        ValueAddedServiceList valueAddedServiceList = new ValueAddedServiceList();
        List list = new ArrayList();
        for(int i =0;i<3;i++){
            ValueAddedServiceDTO valueAddedServiceDTO = new ValueAddedServiceDTO();
            valueAddedServiceDTO.setPolicyFormCode("1");
            valueAddedServiceDTO.setUpdateTime(new Date());
            list.add(valueAddedServiceDTO);
        }
        valueAddedServiceList.setValueAddedServiceList(list);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(valueAddedServiceList));
        
    }
}
