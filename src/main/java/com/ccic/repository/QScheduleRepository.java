package com.ccic.repository;

import com.ccic.domain.QSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by 8000600758 on 2018/9/29.
 */
public interface QScheduleRepository extends JpaRepository<QSchedule,String> {
    @Query(value = "from QSchedule a where (a.questionPerson =:userid OR :userid = null) and (a.questionSys =:userSys OR :userSys = null) and (a.resolveStatus =:status OR :status = null) and (a.createTime =:date OR :date=null) and (a.id in :bizNos OR :bizNos=null) and 1=1 order by a.id desc")
     Page<QSchedule> findByPage(@Param("userid") String  userid, @Param("userSys")String userSys, @Param("status")Integer status, @Param("date")Date date,@Param("bizNos") List bizNos, Pageable pageable);

    @Query(value = "select distinct a.resolveDutyPerson from QSchedule a")
    List<String> findDistinctResolveName();

    Optional<QSchedule> findById(String id);

    @Query(value = "from QSchedule a where a.id in :bizNos and 1=1 order by a.id desc")
    Page<QSchedule> findByIdPage(@Param("bizNos") List bizNos, Pageable pageable);
}
