package com.example.eurekaclient.repository;

import com.example.eurekaclient.entity.DateHairdresserWorkEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DateHairdresserWorkRepo extends JpaRepository<DateHairdresserWorkEntity, DateHairdresserWorkEntity.DateHairdresserWorkId> {

    @Modifying
    @Transactional
    @Query(value = "delete from work_hours_monday \n" +
            "where work_id_hairdresser_id= ?1 \n" +
            "and monday_list= ?2", nativeQuery = true)
    void deleteWorkMondayByHairAndTime(@Param("work_id_hairdresser_id") String idHair,
                                       @Param("friday_list") LocalDateTime date);

    @Modifying
    @Transactional
    @Query(value = "delete from work_hours_tuesday \n" +
            "where work_id_hairdresser_id= ?1 \n" +
            "and tuesday_list= ?2", nativeQuery = true)
    void deleteWorkTuesdayByHairAndTime(@Param("work_id_hairdresser_id") String idHair,@Param("friday_list") LocalDateTime date);

    @Modifying
    @Transactional
    @Query(value = "delete from work_hours_wednesday \n" +
            "where work_id_hairdresser_id= ?1 \n" +
            "and wednesday_list= ?2", nativeQuery = true)
    void deleteWorkWednesdayByHairAndTime(@Param("work_id_hairdresser_id") String idHair,@Param("friday_list") LocalDateTime date);

    @Modifying
    @Transactional
    @Query(value = "delete from work_hours_thursday \n" +
            "where work_id_hairdresser_id= ?1 \n" +
            "and thursday_list= ?2", nativeQuery = true)
    void deleteWorkThursdayByHairAndTime(@Param("work_id_hairdresser_id") String idHair,@Param("friday_list") LocalDateTime date);

    @Modifying
    @Transactional
    @Query(value = "delete from work_hours_friday \n" +
            "where work_id_hairdresser_id= ?1 \n" +
            "and friday_list= ?2", nativeQuery = true)
    void deleteWorkFridayByHairAndTime(@Param("work_id_hairdresser_id") String idHair,@Param("friday_list") LocalDateTime date);


    @Query(value = "SELECT * FROM hairdresser_work\n" +
            "WHERE first_day_of_week >= :date", nativeQuery = true)
    List<DateHairdresserWorkEntity> allDateHairdresserWorkEntityByFirstDay(@Param("date") LocalDate localDate);

}
