package com.shilov.spring_reservation.entities;

import com.shilov.spring_reservation.common.enums.ReservationStatus;
import com.shilov.spring_reservation.models.ReservationModel;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "space_id")
    private Space space;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Embedded
    private ReservationDateTime reservationDateTime;

    public Reservation() {}

    public Reservation(User customer, Space space, ReservationStatus status,
                       ReservationDateTime reservationDateTime) {
        this.customer = customer;
        this.space = space;
        this.status = status;
        this.reservationDateTime = reservationDateTime;
    }

    public Reservation(Long id, User customer, Space space, ReservationStatus status,
                       ReservationDateTime reservationDateTime) {
        this.id = id;
        this.customer = customer;
        this.space = space;
        this.status = status;
        this.reservationDateTime = reservationDateTime;
    }

    public Reservation(Builder builder) {
        this.id = builder.id;
        this.customer = builder.customer;
        this.space = builder.space;
        this.status = builder.status;
        this.reservationDateTime = builder.reservationDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public ReservationDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(ReservationDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(customer, that.customer) && Objects.equals(space, that.space) && status == that.status && Objects.equals(reservationDateTime, that.reservationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, space, status, reservationDateTime);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", customer=" + customer +
                ", space=" + space +
                ", status=" + status +
                ", reservationDateTime=" + reservationDateTime +
                '}';
    }

    public ReservationModel toReservationModel() {
        return new ReservationModel(
                this.id,
                this.space.getId(),
                this.customer.getId(),
                this.reservationDateTime.getDate(),
                this.reservationDateTime.getStartTime(),
                this.reservationDateTime.getEndTime(),
                this.status.name()
        );
    }

    public static class Builder {

        private User customer;
        private Space space;
        private ReservationStatus status;
        private ReservationDateTime reservationDateTime;
        private Long id;

        public Builder setCustomer(User customer) {
            this.customer = customer;
            return this;
        }

        public Builder setSpace(Space space) {
            this.space = space;
            return this;
        }

        public Builder setStatus(ReservationStatus status) {
            this.status = status;
            return this;
        }

        public Builder setReservationDateTime(ReservationDateTime reservationDateTime) {
            this.reservationDateTime = reservationDateTime;
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Reservation createReservation() {
            return new Reservation(id, customer, space, status, reservationDateTime);
        }

        public Reservation build(){
            return new Reservation(this);
        }
    }
}
