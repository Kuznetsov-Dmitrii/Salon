package com.example.eurekahairdresser.service;

import com.example.eurekahairdresser.entity.DateHairdresserWorkEntity;
import com.example.eurekahairdresser.entity.Records;
import com.example.eurekahairdresser.repository.DateHairdresserWorkRepo;
import com.example.eurekahairdresser.repository.RecordsRepo;
import com.example.eurekahairdresser.utils.LogUtil;
import com.example.eurekahairdresser.utils.WorkWeekUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {
    private final RecordsRepo recordsRepo;
    private final LogUtil log;
    private final DateHairdresserWorkRepo hairdresserWorkRepo;

    public ScheduleService(RecordsRepo recordsRepo, LogUtil log, DateHairdresserWorkRepo hairdresserWorkRepo) {
        this.recordsRepo = recordsRepo;
        this.log = log;
        this.hairdresserWorkRepo = hairdresserWorkRepo;
    }


    public void recordDelete(Long id) throws IOException {
        Records records = null;

        try {
            records = recordsRepo.getRecordsById(id);
            log.info(String.format("Парикмахер получил Records с id:%s", id));
        } catch (Exception e) {
            log.error(String.format("Ошибка получения информации о Records c id: %s. %s", id, e.getMessage()));
            throw new IOException(String.format("Ошибка получения информации о Records c id: %s. %s", id, e.getMessage()));
        }

        DateHairdresserWorkEntity.DateHairdresserWorkId workId = new DateHairdresserWorkEntity.DateHairdresserWorkId(
                records.getHairdresser().getId(),
                WorkWeekUtil.getMondayOfWeek(LocalDate.from(records.getLocalDateTime()))
        );
        DateHairdresserWorkEntity dateHairdresserWorkEntity = null;

        try {
            dateHairdresserWorkEntity = hairdresserWorkRepo.getReferenceById(workId);
            log.info(String.format("Парикмахер получил DateHairdresserWorkEntity с id:%s", workId));
        } catch (Exception e) {
            log.error(String.format("Ошибка получения DateHairdresserWorkEntity с id %s. %s", workId, e.getMessage()));
        }

        if (dateHairdresserWorkEntity != null) {
            DayOfWeek dayOfWeek = records.getLocalDateTime().getDayOfWeek();
            switch (dayOfWeek) {
                case MONDAY -> {
                    List<LocalDateTime> list = dateHairdresserWorkEntity.getMondayList();
                    setWorkList(list, records);
                    dateHairdresserWorkEntity.setMondayList(list);
                    try {
                        hairdresserWorkRepo.save(dateHairdresserWorkEntity);
                        log.info("Пользователь сохранил DateHairdresserWorkEntity");
                    } catch (Exception e) {
                        log.error(String.format("Ошибка сохранения DateHairdresserWorkEntity. %s", e.getMessage()));
                    }
                }
                case TUESDAY -> {
                    List<LocalDateTime> list = dateHairdresserWorkEntity.getTuesdayList();
                    setWorkList(list, records);
                    dateHairdresserWorkEntity.setTuesdayList(list);
                    try {
                        hairdresserWorkRepo.save(dateHairdresserWorkEntity);
                        log.info("Пользователь сохранил DateHairdresserWorkEntity");
                    } catch (Exception e) {
                        log.error(String.format("Ошибка сохранения DateHairdresserWorkEntity. %s", e.getMessage()));
                    }
                }
                case WEDNESDAY -> {
                    List<LocalDateTime> list = dateHairdresserWorkEntity.getWednesdayList();
                    setWorkList(list, records);
                    dateHairdresserWorkEntity.setWednesdayList(list);
                    try {
                        hairdresserWorkRepo.save(dateHairdresserWorkEntity);
                        log.info("Пользователь сохранил DateHairdresserWorkEntity");
                    } catch (Exception e) {
                        log.error(String.format("Ошибка сохранения DateHairdresserWorkEntity. %s", e.getMessage()));
                    }
                }
                case THURSDAY -> {
                    List<LocalDateTime> list = dateHairdresserWorkEntity.getThursdayList();
                    setWorkList(list, records);
                    dateHairdresserWorkEntity.setThursdayList(list);
                    try {
                        hairdresserWorkRepo.save(dateHairdresserWorkEntity);
                        log.info("Пользователь сохранил DateHairdresserWorkEntity");
                    } catch (Exception e) {
                        log.error(String.format("Ошибка сохранения DateHairdresserWorkEntity. %s", e.getMessage()));
                    }
                }
                case FRIDAY -> {
                    List<LocalDateTime> list = dateHairdresserWorkEntity.getFridayList();
                    setWorkList(list, records);
                    dateHairdresserWorkEntity.setFridayList(list);
                    try {
                        hairdresserWorkRepo.save(dateHairdresserWorkEntity);
                        log.info("Пользователь сохранил DateHairdresserWorkEntity");
                    } catch (Exception e) {
                        log.error(String.format("Ошибка сохранения DateHairdresserWorkEntity. %s", e.getMessage()));
                    }
                }
            }
        }

        try {
            recordsRepo.deleteById(id);
            log.info(String.format("Пользователь удалил Records с id:%s", id));
        } catch (Exception e) {
            log.error(String.format("Ошибка удаления Records. %s", e.getMessage()));
        }

    }

    private static void setWorkList(List<LocalDateTime> list, Records records) {
        list.add(LocalDateTime.of(
                records.getLocalDateTime().getYear(),
                records.getLocalDateTime().getMonth(),
                records.getLocalDateTime().getDayOfMonth(),
                records.getLocalDateTime().getHour(),
                records.getLocalDateTime().getMinute()
        ));
    }

}
