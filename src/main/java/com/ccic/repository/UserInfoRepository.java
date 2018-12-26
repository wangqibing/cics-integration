package com.ccic.repository;

import com.ccic.domain.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 8000600758 on 2018/10/7.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,Long>{
    UserInfo findByUserName(String userName);
    //通过权限Code查询用户
    List<UserInfo> findByAuthority(String authorityCode);
}
