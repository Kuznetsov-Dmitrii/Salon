package com.example.eurekaclient.service;

import com.example.eurekaclient.dto.ClientAndRecordsDto;
import com.example.eurekaclient.dto.ClientDto;
import com.example.eurekaclient.dto.RecordsClientDto;
import com.example.eurekaclient.entity.DateHairdresserWorkEntity;
import com.example.eurekaclient.entity.Records;
import com.example.eurekaclient.repository.DateHairdresserWorkRepo;
import com.example.eurekaclient.repository.RecordsRepo;
import com.example.eurekaclient.repository.UserEntityRepo;
import com.example.eurekaclient.utils.WorkWeekUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Value("${keycloak.realm_id}")
    private String realm_id;

    private final UserEntityRepo userEntityRepo;
    private final RecordsRepo recordsRepo;
    private final UserInfoService userInfoService;
    private final DateHairdresserWorkRepo hairdresserWorkRepo;

    public ClientService(UserEntityRepo userEntityRepo, RecordsRepo recordsRepo, UserInfoService userInfoService, DateHairdresserWorkRepo hairdresserWorkRepo) {
        this.userEntityRepo = userEntityRepo;
        this.recordsRepo = recordsRepo;
        this.userInfoService = userInfoService;
        this.hairdresserWorkRepo = hairdresserWorkRepo;
    }

    @Transactional
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

    @Transactional
    public ClientAndRecordsDto getProfileClient(){
        ClientDto clientDto = null;
        try {
            clientDto = userEntityRepo.getClientProfile(realm_id,userInfoService.getUserId());
        } catch (DataAccessException e) {
            System.err.println("Ошибка доступа к базе данных: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Неизвестная ошибка: " + e.getMessage());
        }

        List<Records> recordsList=recordsRepo.getRecordsByClient(userInfoService.getUserId(), LocalDateTime.now());

        List<RecordsClientDto> recordsHairdresserDtoList=new ArrayList<>();
        for (Records record:recordsList){
            RecordsClientDto recordsHairdresserDto= RecordsClientDto.builder()
                    .id(record.getId())
                    .firstName(record.getClient().getFirst_name())
                    .lastName(record.getClient().getLast_name())
                    .phoneNumber(userEntityRepo.getPhoneNumberByUserId(record.getClient().getId()))
                    .localDateTime(record.getLocalDateTime())
                    .build();

            recordsHairdresserDtoList.add(recordsHairdresserDto);
        }

        return ClientAndRecordsDto.builder()
                .clientDto(clientDto)
                .recordsClientDtoList(recordsHairdresserDtoList)
                .build();
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
