package com.shilov.spring_reservation.entities;

import com.shilov.spring_reservation.common.exceptions.ReservationDateTimeFormatException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Embeddable
public class ReservationDateTime implements Serializable {

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "ent_time")
    private LocalTime endTime;

    private LocalDate date;

    public ReservationDateTime() {}

    public ReservationDateTime(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public ReservationDateTime(String date, String startTime, String endTime)
            throws ReservationDateTimeFormatException {
        try {
            this.date = LocalDate.parse(date);
            this.startTime = LocalTime.parse(startTime);
            this.endTime = LocalTime.parse(endTime);
        } catch (DateTimeParseException e) {
            throw new ReservationDateTimeFormatException(e.getMessage());
        }
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationDateTime that = (ReservationDateTime) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, date);
    }

    @Override
    public String toString() {
        return "ReservationTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                '}';
    }
}
