package com.sks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sks.model.Car;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByCarNumber(String carNumber);
}
