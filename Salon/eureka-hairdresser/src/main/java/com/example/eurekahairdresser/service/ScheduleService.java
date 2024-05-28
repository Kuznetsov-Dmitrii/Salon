package com.example.eurekahairdresser.service;

import com.example.eurekahairdresser.entity.DateHairdresserWorkEntity;
import com.example.eurekahairdresser.entity.Records;
import com.example.eurekahairdresser.repository.DateHairdresserWorkRepo;
import com.example.eurekahairdresser.repository.RecordsRepo;
import com.example.eurekahairdresser.repository.UserEntityRepo;
import com.example.eurekahairdresser.utils.WorkWeekUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ScheduleService {
    private final UserEntityRepo userEntityRepo;
    private final RecordsRepo recordsRepo;
    private final UserInfoService userInfoService;
    private final DateHairdresserWorkRepo hairdresserWorkRepo;

    @Value("${keycloak.realm_id}")
    private String realm_id;

    public ScheduleService(UserEntityRepo userEntityRepo, RecordsRepo recordsRepo, UserInfoService userInfoService, DateHairdresserWorkRepo hairdresserWorkRepo) {
        this.userEntityRepo = userEntityRepo;
        this.recordsRepo = recordsRepo;
        this.userInfoService = userInfoService;
        this.hairdresserWorkRepo = hairdresserWorkRepo;
    }

    @Transactional
    public void addRecords(Map<String, String> request){

        LocalDateTime localDateTime = LocalDateTime.of(
                Integer.parseInt(request.get("firstDayOfWeek").substring(0, 4)),
                Integer.parseInt(request.get("firstDayOfWeek").substring(5, 7)),
                Integer.parseInt(request.get("firstDayOfWeek").substring(8, 10)),
                Integer.parseInt(request.get("time").substring(0, 2)),
                0);

        Optional<Records> record = recordsRepo.getRecordsByClientAndHair((request.get("hairdresserId")),
                userInfoService.getUserId(),
                localDateTime);


        if (record.isEmpty()) {
            Records records = Records.builder()
                    .client(userEntityRepo.getUserById(userInfoService.getUserId(), realm_id))
                    .hairdresser(userEntityRepo.getUserById(request.get("hairdresserId"), realm_id))
                    .localDateTime(localDateTime)
                    .build();

            recordsRepo.save(records);

            switch (request.get("day")) {
                case ("mondayList") -> {
                    System.out.println(1);
                    hairdresserWorkRepo.deleteWorkMondayByHairAndTime(request.get("hairdresserId"), localDateTime);
                }
                case ("tuesdayList") -> {
                    System.out.println(2);
                    hairdresserWorkRepo.deleteWorkTuesdayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(1));
                }
                case ("wednesdayList") -> {
                    System.out.println(3);
                    hairdresserWorkRepo.deleteWorkWednesdayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(2));
                }
                case ("thursdayList") -> {
                    System.out.println(4);
                    hairdresserWorkRepo.deleteWorkThursdayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(3));
                }
                case ("fridayList") -> {
                    System.out.println("пятница удалена");
                    hairdresserWorkRepo.deleteWorkFridayByHairAndTime(request.get("hairdresserId"), localDateTime.plusDays(4));
                }
            }
        }else {
            System.out.println("Такая запис существует");
        }
    }

    public void recordDelete(Long id){

        Records records= recordsRepo.getRecordsById(id);

        DateHairdresserWorkEntity.DateHairdresserWorkId workId = new DateHairdresserWorkEntity.DateHairdresserWorkId(
                records.getHairdresser().getId(),
                WorkWeekUtil.getMondayOfWeek(LocalDate.from(records.getLocalDateTime()))
        );
        DateHairdresserWorkEntity dateHairdresserWorkEntity=hairdresserWorkRepo.getReferenceById(workId);

        DayOfWeek dayOfWeek = records.getLocalDateTime().getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY -> {
                List<LocalDateTime> list= dateHairdresserWorkEntity.getMondayList();
                setWorkList(list, records);
                dateHairdresserWorkEntity.setMondayList(list);
                hairdresserWorkRepo.save(dateHairdresserWorkEntity);
            }
            case TUESDAY -> {
                List<LocalDateTime> list= dateHairdresserWorkEntity.getTuesdayList();
                setWorkList(list, records);
                dateHairdresserWorkEntity.setTuesdayList(list);
                hairdresserWorkRepo.save(dateHairdresserWorkEntity);
            }
            case WEDNESDAY -> {
                List<LocalDateTime> list= dateHairdresserWorkEntity.getWednesdayList();
                setWorkList(list, records);
                dateHairdresserWorkEntity.setWednesdayList(list);
                hairdresserWorkRepo.save(dateHairdresserWorkEntity);
            }
            case THURSDAY -> {
                List<LocalDateTime> list= dateHairdresserWorkEntity.getThursdayList();
                setWorkList(list, records);
                dateHairdresserWorkEntity.setThursdayList(list);
                hairdresserWorkRepo.save(dateHairdresserWorkEntity);
            }
            case FRIDAY -> {
                List<LocalDateTime> list= dateHairdresserWorkEntity.getFridayList();
                setWorkList(list, records);
                dateHairdresserWorkEntity.setFridayList(list);
                hairdresserWorkRepo.save(dateHairdresserWorkEntity);
            }
        }

        try {
            recordsRepo.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private static void setWorkList(List<LocalDateTime> list, Records records){
        list.add(LocalDateTime.of(
                records.getLocalDateTime().getYear(),
                records.getLocalDateTime().getMonth(),
                records.getLocalDateTime().getDayOfMonth(),
                records.getLocalDateTime().getHour(),
                records.getLocalDateTime().getMinute()
        ));
    }

}
