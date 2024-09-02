package com.sks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sks.model.Car;
import com.sks.repository.CarRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CarService {

    private static final Logger LOGGER = Logger.getLogger(CarService.class.getName());

    @Autowired
    private CarRepository carRepository;

    @Transactional
    public Car addCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        LOGGER.info("Adding car: " + car);
        return carRepository.save(car);
    }

    @Transactional
    public void removeCar(Long carId) {
        try {
            carRepository.deleteById(carId);
            LOGGER.info("Removed car with ID: " + carId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warning("Attempted to remove car with ID: " + carId + " but it does not exist.");
        }
    }

    public List<Car> listAvailableCars() {
        LOGGER.info("Listing available cars");
        return carRepository.findAll();
    }

    public Car findCarById(Long carId) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car == null) {
            LOGGER.warning("Car with ID: " + carId + " not found.");
        } else {
            LOGGER.info("Found car: " + car);
        }
        return car;
    }

	public Car updateCar(Car car) {
		// TODO Auto-generated method stub
		return null;
	}
//?? complete cheyyi idhi
	public List<Car> retriveCar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Car retriveCarById(Long car_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
