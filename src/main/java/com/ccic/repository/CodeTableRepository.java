package com.ccic.repository;

import com.ccic.domain.CodeTable;
import com.ccic.domain.QSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 8000600758 on 2018/9/29.
 */
public interface CodeTableRepository extends JpaRepository<CodeTable,String> {
    List<CodeTable> findByCodeArea(String codeArea);
}
