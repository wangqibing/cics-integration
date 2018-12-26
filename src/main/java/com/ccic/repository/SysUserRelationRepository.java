package com.ccic.repository;

import com.ccic.domain.SysInfo;
import com.ccic.domain.SysUserRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 8000600758 on 2018/10/7.
 */
public interface SysUserRelationRepository extends JpaRepository<SysUserRelation,Long>{
        List<SysUserRelation> findBySysNum(String sysNum);
        SysUserRelation findByUserNum(String userNum);
}
