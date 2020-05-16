package com.axeane.appmanagement.repository;

import com.axeane.appmanagement.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query(value = "select * from module as m where m.application_id =:id", nativeQuery = true)
    List<Module> getAllModulesByApp(@Param("id") Long id);
}
