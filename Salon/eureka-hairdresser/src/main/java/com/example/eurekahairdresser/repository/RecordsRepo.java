package com.example.eurekahairdresser.repository;

import com.example.eurekahairdresser.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RecordsRepo extends JpaRepository<Records, Long> {

    @Query(value = "select * from records\n" +
            "where login_hair=?1  and date>= ?2",nativeQuery = true)
    List<Records> getRecordsByHairdresser(@Param("login_hair") String loginHair,
                                          @Param("date") LocalDateTime date);

    @Query(value = "select * from public.records\n" +
            "    where id=?1", nativeQuery = true)
    Records getRecordsById(@Param("id") Long id);

}
