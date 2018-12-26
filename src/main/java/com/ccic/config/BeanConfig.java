package com.ccic.config;

import com.ccic.convert.pesonalargumentresolvertest.PersonalArgumentResolver;
import com.ccic.databus.client.publish.DataBusPublishClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 8000600758 on 2018/11/20.
 */
@Configuration
public class BeanConfig {
    private static final String DATABUS_MOGODB_URL = "databus_mogodb_url";

    @Autowired
    Environment environment;

    @Bean
    public PersonalArgumentResolver getResolver(){
        return new PersonalArgumentResolver();
    }


    @Bean
    public DataBusPublishClient getDatabusClent(){
        String mogodbUrl = environment.getProperty(DATABUS_MOGODB_URL);
        return new DataBusPublishClient(mogodbUrl);
    }


}
