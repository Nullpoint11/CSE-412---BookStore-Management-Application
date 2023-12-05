package com.example.phase3;

import java.sql.Date;

public class Reservation {
    private int reservation_id;
    private int user_id;
    private int copy_id;

    public int getCopy_id() {
        return copy_id;
    }

    public void setCopy_id(int copy_id) {
        this.copy_id = copy_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Reservation(int reservation_id, int user_id, int copy_id) {
        this.reservation_id = reservation_id;
        this.user_id = user_id;
        this.copy_id = copy_id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation id='" + reservation_id + '\'' +
                ", copyId=" + copy_id +
                ", user id='" + user_id + '\'' +
                '}';
    }
}
