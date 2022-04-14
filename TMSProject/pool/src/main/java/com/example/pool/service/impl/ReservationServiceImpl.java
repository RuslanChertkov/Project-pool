package com.example.pool.service.impl;

import com.example.pool.model.Reservation;
import com.example.pool.model.User;
import com.example.pool.repository.ReservationRepository;
import com.example.pool.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.getById(id);
    }

    @Override
    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }
}
