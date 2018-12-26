package com.ccic.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 8000600758 on 2018/10/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserServiceImplTest {
    @Autowired
    SysUserServiceImpl sysUserServiceImpl;
    @Test
    public void findBySysNum() throws Exception {
        System.out.println(sysUserServiceImpl.findBySysNum("2000006"));
    }

    @Test
    public void findByUserNum() throws Exception {
    }

}