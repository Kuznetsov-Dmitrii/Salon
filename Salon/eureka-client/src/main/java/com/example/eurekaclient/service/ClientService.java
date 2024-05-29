package com.example.eurekaclient.service;

import com.example.eurekaclient.dto.ClientAndRecordsDto;
import com.example.eurekaclient.dto.ClientDto;
import com.example.eurekaclient.dto.RecordsClientDto;
import com.example.eurekaclient.entity.DateHairdresserWorkEntity;
import com.example.eurekaclient.entity.Records;
import com.example.eurekaclient.repository.DateHairdresserWorkRepo;
import com.example.eurekaclient.repository.RecordsRepo;
import com.example.eurekaclient.repository.UserEntityRepo;
import com.example.eurekaclient.utils.LogUtil;
import com.example.eurekaclient.utils.WorkWeekUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Value("${keycloak.realm_id}")
    private String realm_id;
    private final LogUtil log;
    private final UserEntityRepo userEntityRepo;
    private final RecordsRepo recordsRepo;
    private final UserInfoService userInfoService;
    private final DateHairdresserWorkRepo hairdresserWorkRepo;
    private final WorkWeekUtil workWeekUtil;

    public ClientService(LogUtil log, UserEntityRepo userEntityRepo, RecordsRepo recordsRepo, UserInfoService userInfoService, DateHairdresserWorkRepo hairdresserWorkRepo, WorkWeekUtil workWeekUtil) {
        this.log = log;
        this.userEntityRepo = userEntityRepo;
        this.recordsRepo = recordsRepo;
        this.userInfoService = userInfoService;
        this.hairdresserWorkRepo = hairdresserWorkRepo;
        this.workWeekUtil = workWeekUtil;
    }

    @Transactional
    public void recordDelete(Long id) throws IOException {
        Records records = null;

        try {
            records = recordsRepo.getRecordsById(id);
            log.info(String.format("Пользователь получил Records с id:%s", id));
        } catch (DataAccessException e) {
            log.error(String.format("Пользователь не смог получить информацию о Records с id: %s.Ошибка доступа к базе данных: %s", id, e.getMessage()));
            throw new IOException(String.format("Пользователь не смог получить информацию о Records с id: %s.Ошибка доступа к базе данных: %s", id, e.getMessage()));
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
            log.info(String.format("Пользователь получил DateHairdresserWorkEntity с id:%s", workId));
        } catch (DataAccessException e) {
            log.error(String.format("Ошибка получения DateHairdresserWorkEntity с id %s: .Ошибка доступа к базе данных: %s", workId, e.getMessage()));
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

    @Transactional
    public List<DateHairdresserWorkEntity> showAddSchedule() {
        List<DateHairdresserWorkEntity> list = new ArrayList<>();

        try {
            list = hairdresserWorkRepo.allDateHairdresserWorkEntityByFirstDay(workWeekUtil.getMondayOfThisWeek());
            log.info("Пользователь получил List<DateHairdresserWorkEntity>");
        } catch (DataAccessException e) {
            log.error(String.format("Ошибка получения List<DateHairdresserWorkEntity>.Ошибка доступа к базе данных: %s", e.getMessage()));
        } catch (Exception e) {
            log.error(String.format("Ошибка получения List<DateHairdresserWorkEntity>. %s", e.getMessage()));
        }

        return list;
    }

    @Transactional
    public ClientAndRecordsDto getProfileClient() {
        ClientDto clientDto = null;
        try {
            clientDto = userEntityRepo.getClientProfile(realm_id, userInfoService.getUserId());
            log.info(String.format("Пользователь получил ClientDto с id: %s", userInfoService.getUserId()));
        } catch (Exception e) {
            log.error(String.format("Ошибка получения информации о профиле. %s", e.getMessage()));
        }

        List<Records> recordsList = null;
        List<RecordsClientDto> recordsHairdresserDtoList = new ArrayList<>();

        try {
            recordsList = recordsRepo.getRecordsByClient(userInfoService.getUserId(), LocalDateTime.now());
            log.info(String.format("Пользователь получил List<Records> с id: %s", userInfoService.getUserId()));

            for (Records record : recordsList) {
                RecordsClientDto recordsHairdresserDto = RecordsClientDto.builder()
                        .id(record.getId())
                        .firstName(record.getClient().getFirst_name())
                        .lastName(record.getClient().getLast_name())
                        .phoneNumber(userEntityRepo.getPhoneNumberByUserId(record.getClient().getId()))
                        .localDateTime(record.getLocalDateTime())
                        .build();

                recordsHairdresserDtoList.add(recordsHairdresserDto);
            }
        } catch (Exception e) {
            log.error(String.format("Ошибка получения информации о Records. %s", e.getMessage()));
        }

        return ClientAndRecordsDto.builder()
                .clientDto(clientDto)
                .recordsClientDtoList(recordsHairdresserDtoList)
                .build();
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
