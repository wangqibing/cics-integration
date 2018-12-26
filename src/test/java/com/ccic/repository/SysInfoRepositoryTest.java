package com.ccic.repository;

import com.ccic.domain.SysInfo;
import com.ccic.util.PinYinUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 8000600758 on 2018/10/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysInfoRepositoryTest {
    String [] coreSys = new String[]{"车险","非车险","意健险","再保","通用","监管平台"};
    @Autowired
    private SysInfoRepository sysInfoRepository;
    @Test
    public void findDistinctByCreateTimeIsNotNull() throws Exception {
        for(int i=0;i<coreSys.length;i++){
            SysInfo sysInfo = new SysInfo();
            sysInfo.setSysDutyDirectNo("00000");
            sysInfo.setSysDutyDevNo("0000");
            sysInfo.setSysEname("icore"+ PinYinUtil.ToPinyin(coreSys[i]));
            sysInfo.setSysCname(coreSys[i]);
            sysInfoRepository.save(sysInfo);
        }
    }

}