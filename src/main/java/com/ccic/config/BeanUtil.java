package com.ccic.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by 8000600758 on 2018/11/13.
 */

/**
 * 获取bean工厂的实现类
 */
@Configuration
public class BeanUtil implements BeanFactoryAware {
    // Spring的bean工厂
    private static BeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        beanFactory=factory;
    }
    public static<T> T getBean(String beanName){
        return (T) beanFactory.getBean(beanName);
    }
}