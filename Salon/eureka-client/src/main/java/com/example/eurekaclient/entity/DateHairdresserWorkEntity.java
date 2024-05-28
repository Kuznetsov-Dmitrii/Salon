package com.example.eurekaclient.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hairdresser_work")
public class DateHairdresserWorkEntity {
    @EmbeddedId
    private DateHairdresserWorkId id;

    @MapsId("hairdresserId") // Maps the 'hairdresserId' part of the embedded ID
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hairdresser_id", referencedColumnName = "id")
    private UserEntity hairdresser;

    @ElementCollection
    @CollectionTable(name = "work_hours_monday", joinColumns = {
            @JoinColumn(name = "work_id_hairdresser_id", referencedColumnName = "hairdresser_id"),
            @JoinColumn(name = "work_id_first_day_of_week", referencedColumnName = "first_day_of_week")
    })
    private List<LocalDateTime> mondayList;

    @ElementCollection
    @CollectionTable(name = "work_hours_tuesday", joinColumns = {
            @JoinColumn(name = "work_id_hairdresser_id", referencedColumnName = "hairdresser_id"),
            @JoinColumn(name = "work_id_first_day_of_week", referencedColumnName = "first_day_of_week")
    })
    private List<LocalDateTime> tuesdayList;

    @ElementCollection
    @CollectionTable(name = "work_hours_wednesday", joinColumns = {
            @JoinColumn(name = "work_id_hairdresser_id", referencedColumnName = "hairdresser_id"),
            @JoinColumn(name = "work_id_first_day_of_week", referencedColumnName = "first_day_of_week")
    })
    private List<LocalDateTime> wednesdayList;

    @ElementCollection
    @CollectionTable(name = "work_hours_thursday", joinColumns = {
            @JoinColumn(name = "work_id_hairdresser_id", referencedColumnName = "hairdresser_id"),
            @JoinColumn(name = "work_id_first_day_of_week", referencedColumnName = "first_day_of_week")
    })
    private List<LocalDateTime> thursdayList;

    @ElementCollection
    @CollectionTable(name = "work_hours_friday", joinColumns = {
            @JoinColumn(name = "work_id_hairdresser_id", referencedColumnName = "hairdresser_id"),
            @JoinColumn(name = "work_id_first_day_of_week", referencedColumnName = "first_day_of_week")
    })
    private List<LocalDateTime> fridayList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class DateHairdresserWorkId implements Serializable {
        @Column(name = "hairdresser_id")
        private String hairdresserId;

        @Column(name = "first_day_of_week")
        private LocalDate firstDayOfWeek;
    }
}
