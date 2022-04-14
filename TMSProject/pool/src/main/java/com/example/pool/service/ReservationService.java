package com.example.pool.service;

import com.example.pool.model.Reservation;
import com.example.pool.model.User;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();
    Reservation findById(Long id);
    void save(Reservation reservation);
}
