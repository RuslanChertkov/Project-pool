package com.example.pool.repository;

import com.example.pool.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
